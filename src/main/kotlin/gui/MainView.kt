package gui

import controller.AppController
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Insets
import tornadofx.View
import tornadofx.action
import tornadofx.addChildIfPossible
import tornadofx.addClass
import tornadofx.button
import tornadofx.hbox
import tornadofx.label
import tornadofx.vbox

class MainView : View("office space desk placement optimizer") {
    private val canvas = RoomCanvas(1300.0, 800.0)
    val controller: AppController by inject()
    val iterations = SimpleIntegerProperty(0)

    override val root = vbox {
        padding = Insets(10.0, 10.0, 10.0, 10.0)
        hbox {
            spacing = 2.0
            label("Iteration: ") {
                addClass(MainStyle.iterationslabel)
            }
            label(iterations) {
                addClass(MainStyle.iterationslabel)
            }
            hbox {
                padding = Insets(0.0, 0.0, 0.0, 20.0)
                spacing = 10.0
                button("next iteration") {
                    action {
                        runAsync {
                            controller.nextIteration()
                        }.ui {
                            iterations.set(controller.iterations)
                            canvas.update(controller.room, it)
                        }
                    }
                }
                button("next 10 iterations") {
                    action {
                        repeat(10) {
                            runAsync {
                                controller.nextIteration()
                            }.ui {
                                iterations.set(controller.iterations)
                                canvas.update(controller.room, it)
                            }
                        }
                    }
                }
                button("next 1000 iterations") {
                    action {
                        repeat(1000) {
                            runAsync {
                                val it = controller.nextIteration()
                                tornadofx.runLater {
                                    iterations.set(controller.iterations)
                                    canvas.update(controller.room, it)
                                }
                            }
                        }
                    }
                }
                button("RESET") {
                    addClass(MainStyle.resetbutton)
                }
            }
        }
        this.addChildIfPossible(canvas)
    }

    init {
        canvas.update(controller.room, controller.population.all.get(0))
    }
}
