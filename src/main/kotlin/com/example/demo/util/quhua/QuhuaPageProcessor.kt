package com.example.demo.util.quhua

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.ConsolePipeline
import us.codecraft.webmagic.processor.PageProcessor

class QuhuaPageProcessor : PageProcessor {
    private val site = Site.me().setSleepTime(100).setRetryTimes(3)
    val spider: Spider by lazy {
        Spider.create(QuhuaPageProcessor()).addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html")
                .addPipeline(QuhuaPipeline())
                .addPipeline(ConsolePipeline())
    }

    override fun getSite(): Site = site

    override fun process(page: Page?) {
        val url = page?.url.toString()
        when {
            url.matches(Regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/index.html")) -> {
                page?.putField("province", page.html.xpath("//tr[@class='provincetr']").all())
            }
            url.matches(Regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+.html")) -> {
                page?.putField("cities", page.html.xpath("//tr[@class='citytr']").all())
            }
            url.matches(Regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+.html")) -> {
                page?.putField("counties", page.html.xpath("//tr[@class='countytr']").all())
            }
            url.matches(Regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+/[0-9]+.html")) -> {
                page?.putField("towns", page.html.xpath("//tr[@class='towntr']").all())
            }
            url.matches(Regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+/[0-9]+/[0-9]+.html")) -> {
                page?.putField("villages", page.html.xpath("//tr[@class='villagetr']").all())
            }

        }

        page?.addTargetRequests(page.html.links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+.html").all())
        page?.addTargetRequests(page.html.links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+.html").all())
        page?.addTargetRequests(page.html.links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+/[0-9]+.html").all())
        page?.addTargetRequests(page.html.links().regex("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/[0-9]+/[0-9]+/[0-9]+/[0-9]+.html").all())
    }
}