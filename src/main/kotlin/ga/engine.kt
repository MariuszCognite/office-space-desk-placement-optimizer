package ga

import model.Population
import model.Specimen

/**
 * @return Pair of population of the next generation and the most fit individual from previous generation
 */
fun nextGeneration(currentPopulation: Population): Pair<Population, Specimen> {
    // first, calculate fitness of existing population
    val currentPopulationWithFitness = calculatePopulationFitness(currentPopulation).sorted()

    val newPopulation = ArrayList<Specimen>()

    // next step - elitism, N best fit individuals will move to new 

    return Population(newPopulation) to currentPopulationWithFitness.all[0].specimen
}
