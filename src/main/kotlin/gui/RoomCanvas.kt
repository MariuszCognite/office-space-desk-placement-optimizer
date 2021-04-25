package gui

import Config
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.transform.Rotate
import model.Desk
import model.Room
import model.Specimen

const val canvasPadding: Double = 50.0

class RoomCanvas(val canvas_width: Double, val canvas_height: Double) : Canvas(canvas_width, canvas_height) {

    fun update(room: Room, specimen: Specimen) {

        val (roomWidth, roomHeight) = room.size()

        val scale: Double = if (roomWidth >= roomHeight) {
            canvas_height / roomHeight
        } else {
            canvas_width / roomWidth
        } * 0.8

        val gc = this.graphicsContext2D
        gc.save()
        gc.clearRect(0.0, 0.0, canvas_width, canvas_height)
        gc.translate(canvasPadding, canvasPadding)
        gc.scale(scale, scale)
        gc.lineWidth = 5.0
        drawRoomBorders(gc)

        specimen.desks.forEach {
            drawDesk(it, gc)
        }

        gc.restore()
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

    private fun drawDesk(desk: Desk, gc: GraphicsContext) {
        gc.save()
        val topLeft = desk.x - Config.deskWidth / 2 to desk.y - Config.deskHeight / 2
        val topRight = desk.x + Config.deskWidth / 2 to desk.y - Config.deskHeight / 2
        val bottomLeft = desk.x - Config.deskWidth / 2 to desk.y + Config.deskHeight / 2
        val bottomRight = desk.x + Config.deskWidth / 2 to desk.y + Config.deskHeight / 2

        gc.translate(desk.x.toDouble(), desk.y.toDouble())
        gc.rotate(desk.rotation.toDouble())
        gc.translate(-desk.x.toDouble(), -desk.y.toDouble())
        gc.moveTo(topLeft.first.toDouble(), topLeft.second.toDouble())
        gc.lineTo(topRight.first.toDouble(), topRight.second.toDouble())
        gc.lineTo(bottomRight.first.toDouble(), bottomRight.second.toDouble())
        gc.lineTo(bottomLeft.first.toDouble(), bottomLeft.second.toDouble())
        gc.lineTo(topLeft.first.toDouble(), topLeft.second.toDouble())
        gc.stroke()

        val (headX, headY) = desk.headCoordinate()

        gc.strokeOval(
            (headX - Config.headRadius).toDouble(),
            (headY - Config.headRadius).toDouble(),
            Config.headRadius.toDouble() * 2,
            Config.headRadius.toDouble() * 2
        )
        gc.restore()
    }

    fun rotate(gc: GraphicsContext, angle: Double, px: Double, py: Double) {
        val r = Rotate(angle, px, py)
        gc.setTransform(r.mxx, r.myx, r.mxy, r.myy, r.tx, r.ty)
    }
}
