import javafx.geometry.Insets
import javafx.scene.paint.Color
import tornadofx.CssBox
import tornadofx.Stylesheet
import tornadofx.View
import tornadofx.addChildIfPossible
import tornadofx.addClass
import tornadofx.button
import tornadofx.cssclass
import tornadofx.hbox
import tornadofx.label
import tornadofx.px
import tornadofx.reloadStylesheetsOnFocus
import tornadofx.vbox

class App : tornadofx.App(MasterView::class, AppStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

class MasterView : View("office space desk placement optimizer") {
    private val canvas = RoomCanvas(1300.0, 800.0)
    val controller: AppController by inject()

    override val root = vbox {
        padding = Insets(10.0, 10.0, 10.0, 10.0)
        hbox {
            spacing = 2.0
            label("Iteration: ") {
                addClass(AppStyle.iterationslabel)
            }
            label(controller.iterations) {
                addClass(AppStyle.iterationslabel)
            }
            hbox {
                padding = Insets(0.0, 0.0, 0.0, 20.0)
                spacing = 10.0
                button("next iteration")
                button("next 10 iterations")
                button("next 100 iterations")
                button("RESET") {
                    addClass(AppStyle.resetbutton)
                }
            }
        }
        this.addChildIfPossible(canvas)
    }
}

class AppStyle : Stylesheet() {
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
