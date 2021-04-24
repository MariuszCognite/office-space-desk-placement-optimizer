package ui

import controller.AppController
import javafx.geometry.Insets
import tornadofx.View
import tornadofx.addChildIfPossible
import tornadofx.addClass
import tornadofx.button
import tornadofx.hbox
import tornadofx.label
import tornadofx.vbox

class MainView : View("office space desk placement optimizer") {
    private val canvas = RoomCanvas(1300.0, 800.0)
    val controller: AppController by inject()

    override val root = vbox {
        padding = Insets(10.0, 10.0, 10.0, 10.0)
        hbox {
            spacing = 2.0
            label("Iteration: ") {
                addClass(MainStyle.iterationslabel)
            }
            label(controller.iterations) {
                addClass(MainStyle.iterationslabel)
            }
            hbox {
                padding = Insets(0.0, 0.0, 0.0, 20.0)
                spacing = 10.0
                button("next iteration")
                button("next 10 iterations")
                button("next 100 iterations")
                button("RESET") {
                    addClass(MainStyle.resetbutton)
                }
            }
        }
        this.addChildIfPossible(canvas)
    }
}
