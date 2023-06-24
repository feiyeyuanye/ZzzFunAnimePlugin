package com.example.zzzfunanimeplugin.plugin.components

import android.util.Log
import android.view.Gravity
import com.example.zzzfunanimeplugin.plugin.components.Const.host
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil.getImageUrl
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.action.DetailAction
import com.su.mediabox.pluginapi.components.ICustomPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.MediaInfo2Data
import com.su.mediabox.pluginapi.data.SimpleTextData
import com.su.mediabox.pluginapi.data.TagData
import com.su.mediabox.pluginapi.util.PluginPreferenceIns
import com.su.mediabox.pluginapi.util.WebUtil
import com.su.mediabox.pluginapi.util.WebUtilIns
import org.jsoup.Jsoup

/**
 * FileName: RecentUpdatesPageDataComponent
 * Founder: Jiang Houren
 * Create Date: 2023/4/18 12:50
 * Profile: 最近更新
 */
class RecentUpdatesPageDataComponent  : ICustomPageDataComponent {
    override val pageName: String
        get() = "最近更新"

    override suspend fun getData(page: Int): List<BaseData> {
        val hostUrl = "$host/map_index.html"

        val document = JsoupUtil.getDocument(hostUrl)
        val data = mutableListOf<BaseData>()

        document.select(".ui-cnt").select(".lasted-list").select("li").forEach {
            val title = it.select(".show-tipinfo").text()
            val cover = it.select(".play-img").select("img").attr("src").getImageUrl()
            val url = it.select(".show-tipinfo").select("a").attr("href")
            val episode = it.select("div[class='lasted-type fn-left']")[0].text()
            val describe = it.select("div").last()?.text()?:""
            val tag = it.select("div[class='lasted-tags fn-left']")[0].text()
            val tags = mutableListOf<TagData>()
            tags.add(TagData(tag))
            data.add(
                MediaInfo2Data(
                title, cover, host + url, episode, describe, tags
            ).apply {
                action = DetailAction.obtain(url)
            })
        }
        return data
    }
}