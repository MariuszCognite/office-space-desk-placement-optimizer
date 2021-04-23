import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory

val room = GeometryFactory().createPolygon(
    listOf(
        Coordinate(0.0, 0.0),
        Coordinate(0.0, 10.0),
        Coordinate(10.0, 5.0)
    ).toTypedArray()
)
