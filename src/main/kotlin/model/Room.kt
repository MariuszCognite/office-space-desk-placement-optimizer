package model

import org.locationtech.jts.geom.Polygon

data class Room(val geometry: Polygon) {
    private val boundingBox = geometry.envelope

    /**
     * @return (width, height) in mm
     */
    fun size(): Pair<Double, Double> {
        val roomWidth = maxX() - minX()
        val roomHeight = maxY() - minY()
        return roomWidth to roomHeight
    }

    fun minX(): Double {
        return boundingBox.coordinates.get(0).x
    }

    fun maxX(): Double {
        return boundingBox.coordinates.get(2).x
    }

    fun minY(): Double {
        return boundingBox.coordinates.get(0).y
    }

    fun maxY(): Double {
        return boundingBox.coordinates.get(2).y
    }

    companion object {
        fun get(): Room {
            return Room(Config.room)
        }
    }
}
