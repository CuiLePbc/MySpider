package com.example.demo.util.xiaowei

import com.example.demo.view.MainView
import tornadofx.*
import us.codecraft.webmagic.ResultItems
import us.codecraft.webmagic.Task
import us.codecraft.webmagic.pipeline.Pipeline

class XiaoweiPipeline : Pipeline {

    init {
        XiaoweiDbController.INSTANCE.createTables()
    }


    override fun process(resultItems: ResultItems?, task: Task?) {
        val url = resultItems?.request?.url.toString()



        if (resultItems != null && url.matches(Regex("http://111\\.20\\.61\\.194:8080/newxwqy/smallmicro/detail\\.chtml\\?id=[\\s\\S]*"))) {
            val name = resultItems.get<String>("name")
            val code = resultItems.get<String>("code")
            val type = resultItems.get<String>("type")
            val putin = resultItems.get<String>("putin")
            val trade = resultItems.get<String>("trade")

            val xiaoweiItem = XiaoweiItem(name, code, type, putin, trade)


            val result = XiaoweiDbController.INSTANCE.insert(xiaoweiItem)


            runAsync { } ui {
                MainView.num += result
                println("$name, ${MainView.num}")
            }
        }
    }
}