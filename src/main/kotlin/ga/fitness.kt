package ga

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import model.Population
import model.Specimen

internal data class SpecimenWithFitness(val specimen: Specimen, val fitness: Double)
internal data class PopulationWithFitness(val all: List<SpecimenWithFitness>) {
    fun sorted(): PopulationWithFitness {
        return PopulationWithFitness(all.sortedBy { it.fitness })
    }

    fun toPopulation(): Population {
        return Population(all.map { it.specimen })
    }

    override fun toString(): String {
        var sb = StringBuilder()
        for (specimen in all) {
            sb.append(specimen.fitness)
            sb.append('\n')
        }
        return sb.toString()
    }
}

internal fun calculatePopulationFitness(population: Population): PopulationWithFitness {
    return PopulationWithFitness(
        runBlocking(Dispatchers.Default) {
            population.all.map {
                async {
                    SpecimenWithFitness(it, calculateSpecimensFitness(it))
                }
            }.awaitAll()
        }
    )
}

private suspend fun calculateSpecimensFitness(specimen: Specimen): Double {
    return averageDistanceBetweenDesks(specimen) * Math.pow(
        insideRoomAreaPercentage(specimen),
        4.0
    ) * nonOverlappingAreaPercentage(specimen)
}

// Warning: O(n^2)
private suspend fun averageDistanceBetweenDesks(specimen: Specimen): Double {
    var accumulator = 0.0
    var numConnections = 0

    for (i in 0..specimen.desks.lastIndex) {
        var closestDist = Double.MAX_VALUE
        val p1 = specimen.desks[i].headPositionToCoordinate()
        for (j in 0..specimen.desks.lastIndex) {
            if (i != j) {
                val p2 = specimen.desks[j].headPositionToCoordinate()
                val dist = p1.distance(p2)
                if (dist < closestDist) {
                    closestDist = dist
                }
            }
        }
        accumulator += closestDist
        numConnections += 1
    }
    return (accumulator / numConnections)
}

private suspend fun insideRoomAreaPercentage(specimen: Specimen): Double {
    var totalDeskArea = 0.0
    var areaOutsideOfRoom = 0.0
    val room = Config.room

    specimen.desks.forEach {
        val deskGeometry = it.toGeometry()
        totalDeskArea += deskGeometry.area
        areaOutsideOfRoom += deskGeometry.difference(room).area
    }

    return (totalDeskArea - areaOutsideOfRoom) / totalDeskArea
}

private suspend fun nonOverlappingAreaPercentage(specimen: Specimen): Double {
    var totalDeskArea = specimen.desks[0].toGeometry().area * specimen.desks.size
    val room = Config.room

    var areaOfOverlappingDesks = 0.0
    for (i in 0..(specimen.desks.size - 2)) {
        val deskGeometry = specimen.desks[i].toGeometry()
        for (j in ((i + 1)..specimen.desks.size - 1)) {
            val otherDeskGeometry = specimen.desks[j].toGeometry()
            areaOfOverlappingDesks += (deskGeometry.area - deskGeometry.difference(otherDeskGeometry).area)
        }
    }

    return (totalDeskArea - areaOfOverlappingDesks) / totalDeskArea
}
