package com.example.demo.util.xiaowei

data class XiaoweiItem(val id: Int, val name: String, val code: String, val type: String, val putin: String, val trade: String) {
    constructor(name: String, code: String, type: String, putin: String, trade: String): this(-1, name, code, type, putin, trade)
}