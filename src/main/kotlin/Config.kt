import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory

object Config {
    // ga parameters:
    val populationSize = 20

    // room parameters (all dimensions are in mm):
    val numberOfDesks = 10
    val deskWidth = 2000 // 2m
    val deskHeight = 800 // 0.8m
    val distanceBetweenDeskAndHead = 200
    val headRadius = 200

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
