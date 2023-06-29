package com.example.zzzfunanimeplugin.plugin.components

import android.util.Log
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.ParseHtmlUtil
import com.su.mediabox.pluginapi.action.ClassifyAction
import com.su.mediabox.pluginapi.components.IMediaClassifyPageDataComponent
import com.su.mediabox.pluginapi.data.BaseData
import com.su.mediabox.pluginapi.data.ClassifyItemData
import com.su.mediabox.pluginapi.util.PluginPreferenceIns
import com.su.mediabox.pluginapi.util.TextUtil.urlDecode
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
        classifyItemDataList.addAll(ParseHtmlUtil.parseClassifyEm(document))
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
        Log.e("TAG", "获取分类数据 ${classifyAction.url}")

        val str = classifyAction.url?.urlDecode() ?: ""
        // 指定要插入的字符 charToInsert
        val charToInsert = "_page_${page}"
        val indexToInsert = str.length - 5

        // 使用 StringBuilder 创建一个可变的字符串，调用 insert() 方法将字符插入到指定位置，最后将结果转换回不可变字符串。
        var url = java.lang.StringBuilder(str).insert(indexToInsert, charToInsert).toString()
        if (!url.startsWith(Const.host)){
            url = Const.host + url
        }
        Log.e("TAG", "获取分类数据 $url")

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