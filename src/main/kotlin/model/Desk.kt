package model

import org.locationtech.jts.geom.Coordinate
import kotlin.random.Random

/**
 * @param x x position expressed as offset from origin, in mm
 * @param y y position expressed as offset from origin, in mm
 * @param rotation rotation around desks center, in degrees
 */
data class Desk(val x: Int, val y: Int, val rotation: Int) {
    companion object {
        fun generateRandom(room: Room): Desk {
            val x = Random.nextInt(room.minX().toInt(), room.maxX().toInt())
            val y = Random.nextInt(room.minY().toInt(), room.maxY().toInt())
            val rotation = Random.nextInt(0, 360)
            return Desk(x, y, rotation)
        }
    }

    /**
     * @return (x,y) of the center of the (employee's) head
     */
    fun headCoordinate(): Pair<Int, Int> {
        val headX = x
        val headY = y - Config.deskHeight / 2 - Config.distanceBetweenDeskAndHead - Config.headRadius
        return headX to headY
    }

    fun headPositionToCoordinate(): Coordinate {
        val head = headCoordinate()
        return Coordinate(head.first.toDouble(), head.second.toDouble())
    }
}
