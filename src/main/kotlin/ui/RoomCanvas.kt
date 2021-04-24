package ui

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import model.Room

const val canvasPadding: Double = 50.0

class RoomCanvas(val canvas_width: Double, val canvas_height: Double) : Canvas(canvas_width, canvas_height) {

    fun update(room: Room) {
        val roomBB = room.geometry.envelope
        val roomWidth = roomBB.coordinates.get(2).x - roomBB.coordinates.get(0).x
        val roomHeight = roomBB.coordinates.get(2).y - roomBB.coordinates.get(0).y

        val scale: Double = if (roomWidth >= roomHeight) {
            canvas_height / roomHeight
        } else {
            canvas_width / roomWidth
        } * 0.8

        val gc = this.graphicsContext2D
        gc.clearRect(0.0, 0.0, canvas_width, canvas_height)
        gc.translate(canvasPadding, canvasPadding)
        gc.scale(scale, scale)
        gc.lineWidth = 5.0
        drawRoomBorders(gc)
    }

    private fun drawRoomBorders(gc: GraphicsContext) {
        val room = Room.get()
        gc.beginPath()
        gc.moveTo(0.0, 0.0)
        for (coordinate in room.geometry.coordinates) {
            gc.lineTo(coordinate.x, coordinate.y)
        }
        gc.stroke()
    }
}
