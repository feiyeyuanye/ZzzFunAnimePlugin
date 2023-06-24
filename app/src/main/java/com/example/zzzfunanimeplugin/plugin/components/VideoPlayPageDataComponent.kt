package com.example.zzzfunanimeplugin.plugin.components

import android.net.Uri
import android.util.Log
import com.example.zzzfunanimeplugin.plugin.components.Const.host
import com.example.zzzfunanimeplugin.plugin.components.Const.ua
import com.example.zzzfunanimeplugin.plugin.danmaku.OyydsDanmaku
import com.example.zzzfunanimeplugin.plugin.danmaku.OyydsDanmakuParser
import com.example.zzzfunanimeplugin.plugin.util.JsoupUtil
import com.example.zzzfunanimeplugin.plugin.util.Text.trimAll
import com.example.zzzfunanimeplugin.plugin.util.oyydsDanmakuApis
import com.kuaishou.akdanmaku.data.DanmakuItemData
import com.su.mediabox.pluginapi.components.IVideoPlayPageDataComponent
import com.su.mediabox.pluginapi.data.VideoPlayMedia
import com.su.mediabox.pluginapi.util.AppUtil
import com.su.mediabox.pluginapi.util.PluginPreferenceIns
import com.su.mediabox.pluginapi.util.TextUtil.urlDecode
import com.su.mediabox.pluginapi.util.WebUtil
import com.su.mediabox.pluginapi.util.WebUtilIns
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

class VideoPlayPageDataComponent : IVideoPlayPageDataComponent {

    private var episodeDanmakuId = ""
    override suspend fun getDanmakuData(
        videoName: String,
        episodeName: String,
        episodeUrl: String
    ): List<DanmakuItemData>? {
        try {
            val config = PluginPreferenceIns.get(OyydsDanmaku.OYYDS_DANMAKU_ENABLE, true)
            if (!config)
                return null
            val name = videoName.trimAll()
            var episode = episodeName.trimAll()
            //剧集对集去除所有额外字符，增大弹幕适应性
            val episodeIndex = episode.indexOf("集")
            if (episodeIndex > -1 && episodeIndex != episode.length - 1) {
                episode = episode.substring(0, episodeIndex + 1)
            }
            Log.d("请求Oyyds弹幕", "媒体:$name 剧集:$episode")
            return oyydsDanmakuApis.getDanmakuData(name, episode).data.let { danmukuData ->
                val data = mutableListOf<DanmakuItemData>()
                danmukuData?.data?.forEach { dataX ->
                    OyydsDanmakuParser.convert(dataX)?.also { data.add(it) }
                }
                episodeDanmakuId = danmukuData?.episode?.id ?: ""
                data
            }
        } catch (e: Exception) {
            throw RuntimeException("弹幕加载错误：${e.message}")
        }
    }

    override suspend fun putDanmaku(
        videoName: String,
        episodeName: String,
        episodeUrl: String,
        danmaku: String,
        time: Long,
        color: Int,
        type: Int
    ): Boolean = try {
        Log.d("发送弹幕到Oyyds", "内容:$danmaku 剧集id:$episodeDanmakuId")
        oyydsDanmakuApis.addDanmaku(
            danmaku,
            //Oyyds弹幕标准时间是秒
            (time / 1000F).toString(),
            episodeDanmakuId,
            OyydsDanmakuParser.danmakuTypeMap.entries.find { it.value == type }?.key ?: "scroll",
            String.format("#%02X", color)
        )
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    override suspend fun getVideoPlayMedia(episodeUrl: String): VideoPlayMedia {
        val url = host + episodeUrl
        Log.e("TAG", url)

        val cookies = mapOf("cookie" to PluginPreferenceIns.get(JsoupUtil.cfClearanceKey, ""))
        //解析链接
        val videoUrl = withContext(Dispatchers.Main) {
            val iframeUrl = withTimeoutOrNull(10 * 1000) {
                WebUtilIns.interceptResource(
                    url, "(.*).m3u8",
                    loadPolicy = object : WebUtil.LoadPolicy by WebUtil.DefaultLoadPolicy {
                        override val headers = cookies
                        override val userAgentString = ua
                        override val isClearEnv = false
                    }
                )
            } ?: ""
            Log.e("TAG","1 $iframeUrl")
            async {
                //  https://service-i86gk1am-1251249846.gz.apigw.tencentcs.com/pay/hlsjiami/1687572830/be0f4e9dab7a2adeaba9c6ac477cc5ba/2256/01.m3u8
                //  https://service-i86gk1am-1251249846.gz.apigw.tencentcs.com/pay/hlsjiami/1687572678/702ac4e5dc8ff673e0ec55060343b2a6/2245/01.m3u8
                when {
                    iframeUrl.isBlank() -> iframeUrl
                    iframeUrl.contains("pay") -> iframeUrl
                    else -> {}
                }
            }
        }

        // 剧集名
        val document = JsoupUtil.getDocument(url)
        val name = withContext(Dispatchers.Default) {
            async {
                document.select("[class=mt30]").first()?.text()?.replace("当前播放：", "")?.replace(" / 看不了视频？点击左下方的“播放线路”按钮切换线路试试~","")
                    ?: ""
            }
        }
        Log.e("TAG","2 ${videoUrl.await() as String}")
        return VideoPlayMedia(name.await(), videoUrl.await() as String)
    }
}