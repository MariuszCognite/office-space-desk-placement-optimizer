package controller

import javafx.beans.property.SimpleIntegerProperty
import tornadofx.Controller

class AppController() : Controller() {

    val iterations: SimpleIntegerProperty = SimpleIntegerProperty(0)

    fun nextIteration() {
        iterations.set(iterations.value + 1)
    }
}
