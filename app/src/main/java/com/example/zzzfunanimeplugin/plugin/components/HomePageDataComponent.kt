package com.example.zzzfunanimeplugin.plugin.components

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import com.example.zzzfunanimeplugin.plugin.components.Const.host
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.CustomPageAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.components.IHomePageDataComponent
import com.su.mediabox.pluginapi.data.*
import com.su.mediabox.pluginapi.util.UIUtil.dp
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil.getImageUrl

class HomePageDataComponent : IHomePageDataComponent {

    private val layoutSpanCount = 12

    override suspend fun getData(page: Int): List<BaseData>? {
        if (page != 1)
            return null
        val url = host
        val doc = JsoupUtil.getDocument(url)
        val data = mutableListOf<BaseData>()

        //1.横幅
        doc.getElementsByClass("banner").first()?.apply {
            val bannerItems = mutableListOf<BannerData.BannerItemData>()
            this.select("li").forEach{bannerItem ->
                val videoUrl = bannerItem.select("a").attr("href")
                val bannerImage = bannerItem.select("img").attr("src")
                if (bannerImage.isNotBlank()) {
                    Log.d("添加横幅项", "封面：$bannerImage 链接：$videoUrl")
                    bannerItems.add(
                        BannerData.BannerItemData(
                            bannerImage, "", ""
                        ).apply {
                            if (!videoUrl.isNullOrBlank())
                                action = DetailAction.obtain(videoUrl)
                        }
                    )
                }
            }

            if (bannerItems.isNotEmpty())
                data.add(BannerData(bannerItems, 6.dp).apply {
                    layoutConfig = BaseData.LayoutConfig(layoutSpanCount, 14.dp)
                    spanSize = layoutSpanCount
                })
        }

        //2.菜单
        //排行榜
        data.add(
            MediaInfo1Data(
                "", Const.Icon.RANK, "", "排行榜",
                otherColor = 0xff757575.toInt(),
                coverScaleType = ImageView.ScaleType.FIT_CENTER,
                coverHeight = 24.dp,
                gravity = Gravity.CENTER
            ).apply {
                spanSize = layoutSpanCount / 3
                action = CustomPageAction.obtain(RankPageDataComponent::class.java)
            })

        //更新表
        data.add(
            MediaInfo1Data(
                "", Const.Icon.TABLE, "", "时间表",
                otherColor = 0xff757575.toInt(),
                coverScaleType = ImageView.ScaleType.FIT_CENTER,
                coverHeight = 24.dp,
                gravity = Gravity.CENTER
            ).apply {
                spanSize = layoutSpanCount / 3
                action = CustomPageAction.obtain(UpdateTablePageDataComponent::class.java)
            })

        //最近更新
        data.add(
            MediaInfo1Data(
                "", Const.Icon.UPDATE, "", "最近更新",
                otherColor = 0xff757575.toInt(),
                coverScaleType = ImageView.ScaleType.FIT_CENTER,
                coverHeight = 24.dp,
                gravity = Gravity.CENTER
            ).apply {
                spanSize = layoutSpanCount / 3
                action = CustomPageAction.obtain(RecentUpdatesPageDataComponent::class.java)
            })

        // 3.各类推荐
        // 随机推荐
        data.add(SimpleTextData("随机推荐").apply {
            fontSize = 15F
            fontStyle = Typeface.BOLD
            fontColor = Color.BLACK
            spanSize = layoutSpanCount
        })
        doc.select("#newRank122").select("li").forEach {
            val typeName = it.select("a").text()
            val typeUrl = it.select("a").attr("href")
            data.add(SimpleTextData(typeName).apply {
                fontSize = 12F
                gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
                fontColor = Const.INVALID_GREY
                spanSize = layoutSpanCount / 2
            }.apply {
                action = ClassifyAction.obtain(typeUrl, typeName)
            })
        }
        // 其他推荐
        doc.select("div[class='wrap center']").forEach {
            val typeName = it.select("h1").text()
            val typeUrl = it.select("a").attr("href")
            if (!typeName.isNullOrBlank()) {
                data.add(SimpleTextData(typeName).apply {
                    fontSize = 15F
                    fontStyle = Typeface.BOLD
                    fontColor = Color.BLACK
                    spanSize = layoutSpanCount / 2
                })
                data.add(SimpleTextData("查看更多 >").apply {
                    fontSize = 12F
                    gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
                    fontColor = Const.INVALID_GREY
                    spanSize = layoutSpanCount / 2
                }.apply {
                    action = ClassifyAction.obtain(typeUrl, typeName)
                })
                Log.d("视频分类", "typeName=$typeName url=$typeUrl")
            }
            it.select(".v_list").select("li").forEach { v ->
                val name = v.select(".name").text()
                val videoUrl = v.select("a").attr("href")
                val coverUrl = v.select("img").first()?.attr("src")
                val episode = v.select(".note_text").first()?.text()

                if (!name.isNullOrBlank() && !videoUrl.isNullOrBlank() && !coverUrl.isNullOrBlank()) {
                    data.add(
                        MediaInfo1Data(name, coverUrl, videoUrl, episode ?: "")
                            .apply {
                                spanSize = layoutSpanCount / 3
                                action = DetailAction.obtain(videoUrl)
                            })
                    Log.d("添加视频", "($name) ($videoUrl) ($coverUrl) ($episode)")
                }
            }
        }
        return data
    }
}