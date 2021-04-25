package ga

import model.Desk
import model.Population
import model.Specimen
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * @return (Pair of) population of the next generation and the most fit individual from previous generation
 */
fun nextGeneration(currentPopulation: Population): Pair<Population, Specimen> {
    // first, calculate fitness of existing population
    val currentPopulationWithFitness = calculatePopulationFitness(currentPopulation).sorted()

    val newPopulation = ArrayList<Specimen>()

    // elitism: N best fit individuals will move to new population without changes
    if (Config.elitism > 0) {
        repeat(Config.elitism) {
            newPopulation.add(currentPopulationWithFitness.all[currentPopulationWithFitness.all.lastIndex - it].specimen)
        }
    }

    // select parents via roulette wheel selection
    val totalFitness = currentPopulationWithFitness.all.fold(0.0) { acc, specimen ->
        acc + specimen.fitness
    }
    val currentPopulationWithRankRanges =
        currentPopulationWithFitness.all.scanIndexed(0.0..0.0 to currentPopulationWithFitness.all.last()) { index, acc, specimen ->

            val range_start = acc.first.endInclusive
            val range_end = range_start + (specimen.fitness / totalFitness)

            range_start..range_end to specimen
        }

    // generate new specimens until new population is full
    while (newPopulation.size < Config.populationSize) {
        val firstParent = pickNextParent(currentPopulationWithRankRanges, null)
        val secondParent = pickNextParent(currentPopulationWithRankRanges, firstParent)
        newPopulation.add(mutate(crossover(firstParent, secondParent)))
    }

    // debug: best specimen of the last generation
    println(currentPopulationWithFitness.all.last().fitness)

    return Population(newPopulation) to currentPopulationWithFitness.all.last().specimen
}

private fun crossover(firstParent: Specimen, secondParent: Specimen): Specimen {
    val crossoverPoint = Random.nextInt(1, Config.numberOfDesks - 1)
    val newDeskSetup = ArrayList<Desk>()
    newDeskSetup.addAll(firstParent.desks.subList(0, crossoverPoint))
    newDeskSetup.addAll(secondParent.desks.subList(crossoverPoint, secondParent.desks.size))
    return Specimen(newDeskSetup)
}

private fun mutate(specimen: Specimen): Specimen {
    val desksMutated = specimen.desks.map {
        val newX = if (Random.nextDouble(1.0) < Config.mutationRate) {
            it.x + Random.nextInt(-Config.maxPositionMutation, Config.maxPositionMutation)
        } else {
            it.x
        }
        val newY = if (Random.nextDouble(1.0) < Config.mutationRate) {
            it.y + Random.nextInt(-Config.maxPositionMutation, Config.maxPositionMutation)
        } else {
            it.y
        }
        val newRotation = min(
            360,
            max(
                0,
                if (Random.nextDouble(1.0) < Config.mutationRate) {
                    it.rotation + Random.nextInt(-Config.maxRotationMutation, Config.maxRotationMutation)
                } else {
                    it.rotation
                }
            )
        )
        Desk(newX, newY, newRotation / 30 * 30)
    }
    return Specimen(desksMutated)
}

// closure for picking next parent for crossover
private fun pickNextParent(
    currentPopulationWithRankRanges: List<Pair<ClosedFloatingPointRange<Double>, SpecimenWithFitness>>,
    exclude: Specimen?
): Specimen {
    while (true) {
        val randomDouble = Random.nextDouble(1.0)
        val selected = currentPopulationWithRankRanges.find { it.first.contains(randomDouble) }
        checkNotNull(selected)
        if (selected.second.specimen != exclude) {
            return selected.second.specimen
        } else {
            continue
        }
    }
}
