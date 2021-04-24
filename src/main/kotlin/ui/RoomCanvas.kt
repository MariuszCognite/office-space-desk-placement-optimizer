package ui

import javafx.scene.canvas.Canvas

class RoomCanvas(val canvas_width: Double, val canvas_height: Double) : Canvas(canvas_width, canvas_height) {

    fun update() {
        val gc = this.graphicsContext2D
        gc.beginPath()
        gc.moveTo(30.5, 30.5)
        gc.lineTo(150.5, 30.5)
        gc.lineTo(150.5, 150.5)
        gc.lineTo(30.5, 30.5)
        gc.stroke()
    }
}
