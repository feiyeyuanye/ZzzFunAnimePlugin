package com.example.zzzfunanimeplugin.plugin.util

import com.example.zzzfunanimeplugin.plugin.components.Const.host

import android.util.Log
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil.getCoverUrl
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.data.*
import java.net.URL

object ParseHtmlUtil {

    fun getCoverUrl(cover: String, imageReferer: String): String {
        return when {
            cover.startsWith("//") -> {
                try {
                    "${URL(imageReferer).protocol}:$cover"
                } catch (e: Exception) {
                    e.printStackTrace()
                    cover
                }
            }
            cover.startsWith("/") -> {
                //url不全的情况
                host + cover
            }
            else -> cover
        }
    }

    /**
     * 解析搜索下的元素
     *
     * @param element ul的父元素
     */
    fun parseSearchEm(
        element: Element,
        imageReferer: String
    ): List<BaseData> {
        val videoInfoItemDataList = mutableListOf<BaseData>()
        val results: Elements = element.select("li")
        for (i in results.indices) {
            val img = results[i].select(".play-img").select("img")
            var cover = img.attr("src").getImageUrl()
            if (imageReferer.isNotBlank())
                cover = getCoverUrl(cover, imageReferer)
            val title = img.attr("alt")
            val url = results[i].select(".play-img").attr("href")
            val playTxt = results[i].select(".play-txt")
            val episode = playTxt.select(".fn-left")[0].select("dd").text()
            val types = playTxt.select(".fn-right")[0].select("dd").text()
            val tags = mutableListOf<TagData>()
            for (type in types.split(","))
                tags.add(TagData(type))

            val describe = results[i].select(".juqing").text()
            val item = MediaInfo2Data(
                title, cover, host + url,
                episode, describe, tags
            ).apply {
                    action = DetailAction.obtain(url)
                }
            videoInfoItemDataList.add(item)
        }
        return videoInfoItemDataList
    }
    /**
     * 解析分类下的元素
     *
     * @param element ul的父元素
     */
    fun parseClassifyEm(
        element: Element,
        imageReferer: String
    ): List<BaseData> {
        val videoInfoItemDataList = mutableListOf<BaseData>()
        val results: Elements = element.select("a")
        for (i in results.indices) {

            val img = results[i].select(".d-cover-big").select("img")
            var cover = img.attr("src").getImageUrl()
            if (imageReferer.isNotBlank())
                cover = getCoverUrl(cover, imageReferer)
            val title = img.attr("alt")
            val url = results[i].attr("href")
            val episode = results[i].select(".title-sub").select("span")[0].text()
            val types = results[i].select(".title-sub").select("span")[2].text()
            val tags = mutableListOf<TagData>()
            for (type in types.split(","))
                tags.add(TagData(type))

            val describe = results[i].select(".d-descr").text()
            val item = MediaInfo2Data(
                title, cover, host + url,
                episode, describe, tags
            ).apply {
                action = DetailAction.obtain(url)
            }
            videoInfoItemDataList.add(item)
        }
        return videoInfoItemDataList
    }
    /**
     * 解析分类元素
     */
    fun parseClassifyEm(element: Element): List<ClassifyItemData> {
        val classifyItemDataList = mutableListOf<ClassifyItemData>()
        var classifyCategory = ""

        // 语言
        val timeSelection = element.select(".time-selection")

        timeSelection.apply {
            // 时间
            classifyCategory =
                this.select(".tag-title").text().replace(":", "").replace("：", "")
                    .trim()
                this.select(".select-container").select(".li").forEach {
                val a = it.select("a")
                classifyItemDataList.add(ClassifyItemData().apply {
                    action = ClassifyAction.obtain(
                        a.attr("href").apply {
                            Log.d("分类链接", this)
                        },
                        classifyCategory,
                        a.text()
                    )
                })
            }
        }
        // 类型
        timeSelection.select("#vArea").apply {
            classifyCategory =
                this.select(".tag-title").text().replace(":", "").replace("：", "")
                    .trim()
               this.select("a").forEach {
                classifyItemDataList.add(ClassifyItemData().apply {
                    action = ClassifyAction.obtain(
                        it.attr("href").apply {
                            Log.d("分类链接", this)
                        },
                        classifyCategory,
                        it.text()
                    )
                })
            }
        }
        // 类型
        timeSelection.select("#playType").apply {
            classifyCategory =
                this.select(".tag-title").text().replace(":", "").replace("：", "")
                    .trim()
              this.select("a").forEach {
                classifyItemDataList.add(ClassifyItemData().apply {
                    action = ClassifyAction.obtain(
                        it.attr("href").apply {
                            Log.d("分类链接", this)
                        },
                        classifyCategory,
                        it.text()
                    )
                })
            }
        }
        // 剧情
        timeSelection.select("div").last()?.apply {
            classifyCategory =
                this.select(".tag-title").text().replace(":", "").replace("：", "")
                    .trim()
            this.select("a").forEach {
                classifyItemDataList.add(ClassifyItemData().apply {
                    action = ClassifyAction.obtain(
                        it.attr("href").apply {
                            Log.d("分类链接", this)
                        },
                        classifyCategory,
                        it.text()
                    )
                })
            }
        }
        // 字母
        timeSelection.select("englishLetter").apply {
            classifyCategory = "字母"
            this.select("li").forEach {
                classifyItemDataList.add(ClassifyItemData().apply {
                    val a = it.select("a")
                    action = ClassifyAction.obtain(
                        a.attr("href").apply {
                            Log.d("分类链接", this)
                        },
                        classifyCategory,
                        a.text()
                    )
                })
            }
        }
        return classifyItemDataList
    }
    /**
     * 排行榜
     */
    fun parseRankEm(element: Element,imageReferer: String): List<BaseData> {
        val videoInfoItemDataList = mutableListOf<BaseData>()
        val results: Elements = element.select("#newRank1").select("li")
        for (liE in results){
            val i = liE.select("i").first()?.text()
            val title = liE.select("h3").text()
            val dramaCount = liE.select(".drama-count").text()
            val url = liE.select("a").first()?.attr("href")?:""

            val item = SimpleTextData("[${i}] $title -- $dramaCount").apply {
                action = DetailAction.obtain(url)
            }
            videoInfoItemDataList.add(item)
        }
        return videoInfoItemDataList
    }

    fun String.getImageUrl() = if (startsWith("http")) this else "https:$this"
}