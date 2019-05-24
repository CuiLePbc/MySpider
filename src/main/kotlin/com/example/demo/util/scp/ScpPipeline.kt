package com.example.demo.util.scp

import com.example.demo.view.MainView
import tornadofx.*
import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline
import us.codecraft.webmagic.selector.Html

class ScpPipeline : Pipeline {
    init {
        ScpDbController.INSTANCE.createTables()
    }

    override fun process(resultItems: ResultItems?, task: Task?) {
        val url = resultItems?.request?.url.toString()

        if (url.contains("scp-series") && (!url.contains("tales"))) {

        } else if (url.matches(Regex("http://scp-wiki-cn\\.wikidot\\.com/scp-(cn-)?[0-9]+"))) {
            val title = resultItems?.get<String>("title")
            val imageUrl = resultItems?.get<String>("image")
            val content = resultItems?.get<Html>("content").apply {
                this?.document?.getElementById("header")?.remove()
                this?.document?.getElementById("side-bar")?.remove()
                this?.document?.getElementById("wad-scp-wiki-cn-below-content")?.remove()
                this?.document?.getElementById("page-options-container")?.remove()
                this?.document?.getElementById("footer")?.remove()
                this?.document?.getElementById("action-area-top")?.remove()
                this?.document?.getElementById("license-area")?.remove()
                this?.document?.getElementById("extrac-div-1")?.remove()
                this?.document?.getElementById("extrac-div-2")?.remove()
                this?.document?.getElementById("extrac-div-3")?.remove()
                this?.document?.getElementById("footer-bar")?.remove()
                this?.document?.getElementById("extra-div-1")?.remove()
                this?.document?.getElementById("extra-div-2")?.remove()
                this?.document?.getElementById("extra-div-3")?.remove()
                this?.document?.getElementById("extra-div-4")?.remove()
                this?.document?.getElementById("extra-div-5")?.remove()
                this?.document?.getElementById("extra-div-6")?.remove()
                this?.document?.getElementById("page-options-bottom-tips")?.remove()
                this?.document?.getElementById("page-options-bottom-2-tips")?.remove()
                this?.document?.getElementById("odialog-hovertips")?.remove()

                this?.document?.getElementsByClass("page-tags")?.remove()
                this?.document?.getElementsByClass("creditRate")?.remove()

//                this?.document?.allElements?.removeAttr("background-color")
                this?.document?.allElements?.attr("style","background-color:white")


                this?.document?.getElementById("page-title")?.remove()
            }.toString()

            val result = ScpDbController.INSTANCE.insert(ScpItem(title!!, imageUrl, content))

            runAsync {  } ui {
                MainView.num += result
                println("$title, ${MainView.num}")
            }
        }


    }
}