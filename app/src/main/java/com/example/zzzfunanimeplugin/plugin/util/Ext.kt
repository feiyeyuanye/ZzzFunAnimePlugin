package com.example.zzzfunanimeplugin.plugin.util

object Text {
    private val trimRegex = Regex("\\s+")
    fun String.trimAll() = trimRegex.replace(this, "")
}