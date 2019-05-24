package com.example.demo.util.scp

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.processor.PageProcessor

class ScpPageProcessor : PageProcessor {
    private val site = Site.me().setSleepTime(100).setRetryTimes(3)
    val spider: Spider by lazy {
        Spider.create(ScpPageProcessor()).addUrl("http://scp-wiki-cn.wikidot.com/")
                .addPipeline(ScpPipeline())
    }

    override fun getSite(): Site = site

    override fun process(page: Page?) {
        val url = page?.url.toString()

        if (url.contains("scp-series") && (!url.contains("tales"))) {
            val items = page?.html?.xpath("//div[@class='content-panel standalone series']/ul/li")?.all()?.filter {
                val regex = Regex("[\\s\\S]*SCP-(CN-)?\\d+[\\s\\S]*")
                it.matches(regex)
            }
            page?.putField("ScpItem", items)

        } else if (url.matches(Regex("http://scp-wiki-cn\\.wikidot\\.com/scp-(cn-)?[0-9]+"))) {
            page?.putField("title", page.html.xpath("//div[@id='page-title']/text()").toString().trim())
            page?.putField("content", page.html)
            page?.putField("image",page.html.xpath("//div[@class='scp-image-block block-right']/img/@src").toString())
        }


        page?.addTargetRequests(page.html.links().regex("http://scp-wiki-cn\\.wikidot\\.com/scp-series[\\w-]*").all())
        page?.addTargetRequests(page.html.links().all().filter {
            it.matches(Regex("http://scp-wiki-cn\\.wikidot\\.com/scp-(cn-)?[0-9]+"))
        })
    }
}