package com.example.demo.view

import com.example.demo.util.scp.ScpPageProcessor
import com.example.demo.util.xiaowei.XiaoweiPageProcessor
import javafx.scene.control.Label
import tornadofx.*
import kotlin.properties.Delegates

class MainView : View("Hello TornadoFX") {

    private val scpPageProcessor: ScpPageProcessor by lazy { ScpPageProcessor() }
    private val xwPageProcessor: XiaoweiPageProcessor by lazy { XiaoweiPageProcessor() }

    companion object {
        private lateinit var numLabel: Label
        var num: Int by Delegates.observable(0) { property, oldValue, newValue ->
            if (oldValue != newValue) {
                numLabel.text = newValue.toString()
            }
        }
    }

    override val root = hbox {
        button("ScpStart").action {
            scpPageProcessor.spider.start()
            numLabel.text = "0"
        }
        button("ScpStop").action {
            scpPageProcessor.spider.stop()
        }

        button("XWStart").action {
            xwPageProcessor.spider.start()
        }

        button("XWStop").action {
            xwPageProcessor.spider.stop()
        }

        numLabel = label("0")
    }
}
