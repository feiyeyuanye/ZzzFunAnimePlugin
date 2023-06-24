package com.example.zzzfunanimeplugin.plugin.components

import android.util.Log
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.components.IMediaClassifyPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.ClassifyItemData
import com.su.mediabox.pluginapi.util.PluginPreferenceIns
import com.su.mediabox.pluginapi.util.WebUtil
import com.su.mediabox.pluginapi.util.WebUtilIns
import org.jsoup.Jsoup


class MediaClassifyPageDataComponent : IMediaClassifyPageDataComponent {

    override suspend fun getClassifyItemData(): List<ClassifyItemData> {
        val classifyItemDataList = mutableListOf<ClassifyItemData>()
        val hostUrl = Const.host + "/vod_type_id_1.html"

        // 示例：使用WebUtil解析动态生成的分类项
        val cookies = mapOf("cookie" to PluginPreferenceIns.get(JsoupUtil.cfClearanceKey, ""))
        val document = Jsoup.parse(
            WebUtilIns.getRenderedHtmlCode(hostUrl, loadPolicy = object :
                    WebUtil.LoadPolicy by WebUtil.DefaultLoadPolicy {
                    override val headers = cookies
                    override val userAgentString = Const.ua
                    override val isClearEnv = false
                }
            )
        )
//        val document = JsoupUtil.getDocument(Const.host + "/vod_type_id_1_page_1.html")
        Log.e("TAG","${document}")
        document.select(".tag-box").first()?.also {
            classifyItemDataList.addAll(ParseHtmlUtil.parseClassifyEm(it))
        }
        return classifyItemDataList
    }

    /**
     * 根据选项获取数据
     */
    override suspend fun getClassifyData(
        classifyAction: ClassifyAction,
        page: Int
    ): List<BaseData> {
        val classifyList = mutableListOf<BaseData>()
        // http://www.zzzfun.com/vod_type_id_1_page_1.html
        val urlSb = StringBuilder(classifyAction.url ?: "")
        Log.d("TAG", "获取分类数据 ${classifyAction.url}")

        urlSb.append("_page_${page}.html")
        var url = urlSb.toString()
        Log.d("TAG", "获取分类数据"+url)

        if (!url.startsWith(Const.host))
            url = Const.host + url
        Log.d("TAG", "获取分类数据"+url)

        val document = JsoupUtil.getDocument(url)

        document.select(".main-guide").select(".search-result").first()?.also {
            classifyList.addAll(
                ParseHtmlUtil.parseClassifyEm(
                    it,
                    url
                )
            )
        }
        return classifyList
    }

}