package ui

import javafx.scene.paint.Color
import tornadofx.CssBox
import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.px

class MainStyle : Stylesheet() {
    companion object {
        val iterationslabel by cssclass()
        val resetbutton by cssclass()
    }

    init {
        iterationslabel {
            fontSize = 14.px
            padding = CssBox(4.px, 0.px, 0.px, 0.px)
        }
        resetbutton {
            baseColor = Color.RED
        }
    }
}
