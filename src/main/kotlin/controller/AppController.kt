package controller

import javafx.beans.property.SimpleIntegerProperty
import model.Room
import tornadofx.Controller

class AppController() : Controller() {

    val iterations: SimpleIntegerProperty = SimpleIntegerProperty(0)
    val room = Room.get()

    fun nextIteration() {
        iterations.set(iterations.value + 1)
    }
}
