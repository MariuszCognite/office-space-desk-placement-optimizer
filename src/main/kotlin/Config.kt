import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory

object Config {
    // dimensions in mm
    val room = GeometryFactory().createPolygon(
        listOf(
            Coordinate(0.0, 0.0),
            Coordinate(0.0, 10_000.0),
            Coordinate(16_000.0, 10_000.0),
            Coordinate(16_000.0, 5_000.0),
            Coordinate(10_000.0, 5_000.0),
            Coordinate(10_000.0, 0.0),
            Coordinate(0.0, 0.0)
        ).toTypedArray()
    )
}
