package model

import Config
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.util.AffineTransformation
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
        val headX = 0
        val headY = -Config.deskHeight / 2 - Config.distanceBetweenDeskAndHead - Config.headRadius
        val geom = GeometryFactory().createPoint(Coordinate(headX.toDouble(), headY.toDouble()))
        val at = AffineTransformation()
        at.rotate(Math.toRadians(rotation.toDouble()))
        at.translate(x.toDouble(), y.toDouble())
        return at.transform(geom).coordinate
    }

    fun toGeometry(): Geometry {
        val points = ArrayList<Coordinate>()
        points.add(Coordinate(-Config.deskWidth / 2.0, -Config.deskHeight.toDouble() / 2))
        points.add(Coordinate(-Config.headRadius.toDouble(), -Config.deskHeight / 2.0))
        points.add(
            Coordinate(
                -Config.headRadius.toDouble(),
                -Config.deskHeight / 2.0 - Config.distanceBetweenDeskAndHead - Config.headRadius * 2
            )
        )
        points.add(
            Coordinate(
                Config.headRadius.toDouble(),
                -Config.deskHeight / 2.0 - Config.distanceBetweenDeskAndHead - Config.headRadius * 2
            )
        )
        points.add(
            Coordinate(
                Config.headRadius.toDouble(),
                -Config.deskHeight / 2.0
            )
        )
        points.add(Coordinate(Config.deskWidth / 2.0, -Config.deskHeight / 2.0))
        points.add(Coordinate(Config.deskWidth / 2.0, Config.deskHeight / 2.0))
        points.add(Coordinate(-Config.deskWidth / 2.0, Config.deskHeight / 2.0))
        points.add(Coordinate(-Config.deskWidth / 2.0, -Config.deskHeight / 2.0))
        val geom = GeometryFactory().createPolygon(points.toTypedArray())
        val at = AffineTransformation()
        at.rotate(Math.toRadians(rotation.toDouble()))
        at.translate(x.toDouble(), y.toDouble())
        return at.transform(geom)
        // return geom
    }
}
