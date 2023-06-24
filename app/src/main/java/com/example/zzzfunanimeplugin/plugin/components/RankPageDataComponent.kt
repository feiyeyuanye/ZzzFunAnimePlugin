package com.example.zzzfunanimeplugin.plugin.components

import com.example.zzzfunanimeplugin.plugin.actions.CustomAction
import com.example.zzzfunanimeplugin.plugin.components.Const.host
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil
import com.su.mediabox.pluginapi.components.ICustomPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.ViewPagerData

class RankPageDataComponent : ICustomPageDataComponent {

    override val pageName = "排行榜"
    override fun menus() = mutableListOf(CustomAction())

    override suspend fun getData(page: Int): List<BaseData>? {
        if (page != 1)
            return null
        val url = host
        val doc = JsoupUtil.getDocument(url)

        //排行榜
        val weekRank =
            doc.getElementsByClass("mainBoxRight").first()?.let {
                    object : ViewPagerData.PageLoader {
                        override fun pageName(page: Int): String {
                            return "本月新番排行榜"
                        }

                        override suspend fun loadData(page: Int): List<BaseData> {
                            return ParseHtmlUtil.parseRankEm(it, url)
                        }
                    }
                }

        return listOf(ViewPagerData(mutableListOf(weekRank!!)).apply {
            layoutConfig = BaseData.LayoutConfig(
                itemSpacing = 0,
                listLeftEdge = 0,
                listRightEdge = 0
            )
        })
    }
}