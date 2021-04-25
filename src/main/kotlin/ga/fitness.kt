package ga

import model.Population
import model.Specimen

internal data class SpecimenWithFitness(val specimen: Specimen, val fitness: Double)
internal data class PopulationWithFitness(val all: List<SpecimenWithFitness>) {
    fun sorted(): PopulationWithFitness {
        return PopulationWithFitness(all.sortedByDescending { it.fitness })
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
        population.all.map {
            SpecimenWithFitness(it, calculateSpecimensFitness(it))
        }
    )
}

private fun calculateSpecimensFitness(specimen: Specimen): Double {
    return averageDistanceBetweenDesks(specimen)
}

// Warning: O(n^2)
private fun averageDistanceBetweenDesks(specimen: Specimen): Double {
    var accumulator = 0.0
    var numConnections = 0
    for (i in 0..(specimen.desks.size - 2)) {
        val p1 = specimen.desks[i].headPositionToCoordinate()
        for (j in (i..specimen.desks.size - 1)) {
            val p2 = specimen.desks[j].headPositionToCoordinate()
            accumulator += p1.distance(p2)
            numConnections++
        }
    }
    return accumulator / numConnections
}
