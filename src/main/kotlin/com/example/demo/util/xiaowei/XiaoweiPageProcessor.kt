package com.example.demo.util.xiaowei

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.processor.PageProcessor

class XiaoweiPageProcessor : PageProcessor {
    private val site = Site.me().setSleepTime(10).setRetryTimes(3)
    val spider: Spider by lazy {
        Spider.create(XiaoweiPageProcessor()).addUrl("http://111.20.61.194:8080/newxwqy/smallmicro/list.chtml?entparam=%E5%85%AC%E5%8F%B8&orgid=610300")
                .addPipeline(XiaoweiPipeline())
    }

    override fun getSite(): Site = site

    override fun process(page: Page?) {
        val url = page?.url.toString()

        if (url.matches(Regex("http://111\\.20\\.61\\.194:8080/newxwqy/smallmicro/detail\\.chtml\\?id=[\\s\\S]*"))) {
            page?.putField("name", page.html.xpath("//tr[1]/td[2]/text()").toString())
            page?.putField("code", page.html.xpath("//tr[1]/td[4]/text()").toString())
            page?.putField("type", page.html.xpath("//tr[2]/td[2]/text()").toString())
            page?.putField("putin", page.html.xpath("//tr[3]/td[4]/text()").toString())
            page?.putField("trade", page.html.xpath("//tr[4]/td[2]/text()").toString())
        }



        page?.addTargetRequests(page.html.links().regex("http://111\\.20\\.61\\.194:8080/newxwqy/smallmicro/detail\\.chtml\\?id=[\\s\\S]*").all())
        page?.addTargetRequests(page.html.links().regex("http://111\\.20\\.61\\.194:8080/newxwqy/smallmicro/list\\.chtml\\?entparam=公司&orgid=610300&p=[\\w]+").all())

    }
}