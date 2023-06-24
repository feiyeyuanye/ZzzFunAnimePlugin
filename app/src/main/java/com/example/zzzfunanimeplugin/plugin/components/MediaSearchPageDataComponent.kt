package com.example.zzzfunanimeplugin.plugin.components

import android.net.Uri
import com.example.zzzfunanimeplugin.plugin.components.Const.host
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil
import com.su.mediabox.pluginapi.components.IMediaSearchPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import org.jsoup.select.Elements

class MediaSearchPageDataComponent : IMediaSearchPageDataComponent {

    override suspend fun getSearchData(keyWord: String, page: Int): List<BaseData> {
        val searchResultList = mutableListOf<BaseData>()
        // http://www.zzzfun.com/vod_search.html?wd=%E9%BE%99
        // http://www.zzzfun.com/vod_search_page_1_wd_%E9%BE%99.html
        val url = "${host}/vod_search_page_${page}_wd_${Uri.encode(keyWord, ":/-![].,%?&=")}"
        val document = JsoupUtil.getDocument(url)
        val lpic: Elements = document.select("#list-focus").select(".show-list")
        searchResultList.addAll(ParseHtmlUtil.parseSearchEm(lpic[0], url))
        return searchResultList
    }

}