package ga

import model.Population

fun nextGeneration(currentPopulation: Population): Population {
    // first, calculate fitness of existing population
    val currentPopulationWithFitness = calculatePopulationFitness(currentPopulation).sorted()
    println(currentPopulationWithFitness)
    return currentPopulation
}
