package model

import org.locationtech.jts.geom.Polygon

data class Room(val geometry: Polygon) {
    companion object {
        fun get(): Room {
            return Room(Config.room)
        }
    }
}
