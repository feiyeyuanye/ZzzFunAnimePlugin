package com.example.zzzfunanimeplugin.plugin.components

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import com.badlogic.gdx.utils.Align.right
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil.getImageUrl
import com.su.mediabox.pluginapi.components.IMediaDetailPageDataComponent
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.action.PlayAction
import com.su.mediabox.pluginapi.data.*
import com.su.mediabox.pluginapi.util.TextUtil.urlEncode
import com.su.mediabox.pluginapi.util.UIUtil.dp
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MediaDetailPageDataComponent : IMediaDetailPageDataComponent {

    override suspend fun getMediaDetailData(partUrl: String): Triple<String, String, List<BaseData>> {
        var cover = ""
        var title = ""
        var desc = ""
        var score = -1F
        var upState = ""
        val url = Const.host + partUrl
        val document = JsoupUtil.getDocument(url)
        val tags = mutableListOf<TagData>()

        val details = mutableListOf<BaseData>()

        //TODO 并发优化
        //番剧头部信息
        val area: Elements = document.select(".page-info")
        val pageCover = area.select(".page-cover")
        cover = pageCover.select("img").attr("src").getImageUrl()
        // 其他信息，如标签、地区等
        val infoContent = area.select(".info-content")
        title = ""  // 名字是图片
        //更新状况
        upState = infoContent.select(".content-count").select(".count-item")[1].select("span")[2].text()
        // 年份
        // 地区
        val animeArea = infoContent.select("#transArea")
        tags.add(TagData(animeArea.text()).apply {
            action = ClassifyAction.obtain(
                "",
                "",
                animeArea.text()
            )
        })
        //评分
        score = 0.0F
        //动漫介绍
        desc = infoContent.select(".content-row").text()

        //播放列表+header
        val playNameList = document.select("#bq4").select(".slider-list")
        val playEpisodeList = document.select(".episode-wrap").select(".episode")
        for (index in 0..playNameList.size) {
            val playName = playNameList.getOrNull(index)?.select("li")?.first()
            val playEpisode = playEpisodeList.getOrNull(index)
            if (playName != null && playEpisode != null) {
                val episodes = parseEpisodes(playEpisode)
                if (episodes.isNullOrEmpty())
                    continue

                details.add(
                    SimpleTextData(
                        playName.select("li")
                            .text() + "(${episodes.size}集)"
                    ).apply {
                        fontSize = 16F
                        fontColor = Color.WHITE
                    }
                )
                details.add(EpisodeListData(episodes))
            }
        }
        // 系列动漫推荐
        document.select(".right-ul").first()?.also {
            val series = parseSeries(it)
            if (series.isNotEmpty()) {
                Log.d("其他系列作品", "size=${series.size}")
                details.add(
                    SimpleTextData("其他系列作品").apply {
                        fontSize = 16F
                        fontColor = Color.WHITE
                    }
                )
                details.addAll(series)
            }
        }
        return Triple(cover, title, mutableListOf<BaseData>().apply {
            add(Cover1Data(cover, score = score).apply {
                layoutConfig =
                    BaseData.LayoutConfig(
                        itemSpacing = 12.dp,
                        listLeftEdge = 12.dp,
                        listRightEdge = 12.dp
                    )
            })
            add(
                SimpleTextData(title).apply {
                    fontColor = Color.WHITE
                    fontSize = 20F
                    gravity = Gravity.CENTER
                    fontStyle = 1
                }
            )
            add(TagFlowData(tags))
            add(
                LongTextData(desc).apply {
                    fontColor = Color.WHITE
                }
            )
            add(LongTextData(douBanSearch(title)).apply {
                fontSize = 14F
                fontColor = Color.WHITE
                fontStyle = Typeface.BOLD
            })
            add(SimpleTextData("·$upState").apply {
                fontSize = 14F
                fontColor = Color.WHITE
                fontStyle = Typeface.BOLD
            })
            addAll(details)
        })
    }

    private fun parseEpisodes(element: Element): List<EpisodeData> {
        val episodeList = mutableListOf<EpisodeData>()
        val elements: Elements = element.select("li")
        for (k in elements.indices) {
            val episodeUrl = elements[k].select("a").attr("href")
            episodeList.add(
                EpisodeData(elements[k].select("a").text(), episodeUrl).apply {
                    action = PlayAction.obtain(episodeUrl)
                }
            )
        }
        return episodeList
    }

    private fun parseSeries(element: Element): List<MediaInfo1Data> {
        val videoInfoItemDataList = mutableListOf<MediaInfo1Data>()
        val results: Elements = element.select("a")
        for (i in results.indices) {
            val cover = results[i].select("img").attr("src").getImageUrl()
            val title = results[i].select(".d-line2").text()
            val url = results[i].attr("href")
            val item = MediaInfo1Data(
                title, cover, Const.host + url,
                nameColor = Color.WHITE, coverHeight = 120.dp
            ).apply {
                action = DetailAction.obtain(url)
            }
            videoInfoItemDataList.add(item)
        }
        return videoInfoItemDataList
    }

    private fun douBanSearch(name: String) =
        "·豆瓣评分：https://m.douban.com/search/?query=${name.urlEncode()}"
}