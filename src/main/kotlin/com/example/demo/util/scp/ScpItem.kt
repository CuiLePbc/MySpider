package com.example.demo.util.scp

data class ScpItem(val id: Int, val title: String, val img: String?, val content: String?) {
    constructor(title: String, img: String?, content: String?): this(-1, title, img, content)
}