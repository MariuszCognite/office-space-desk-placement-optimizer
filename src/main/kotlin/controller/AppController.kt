package controller

import ga.nextGeneration
import model.Population
import model.Room
import model.Specimen
import tornadofx.Controller

class AppController() : Controller() {

    val room = Room.get()
    var iterations = 0
    var population = Population.generateRandom(room)

    /**
     * Generate next generation of solutions based on the current population of solutions.
     * @return most fit (best) solution from the newly generated population
     */
    fun nextIteration(): Specimen {
        population = nextGeneration(population)
        iterations++
        return population.all[0]
    }
}
