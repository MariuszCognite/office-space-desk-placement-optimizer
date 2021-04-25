package model

data class Population(val all: List<Specimen>) {
    companion object {
        fun generateRandom(room: Room): Population {
            val population = ArrayList<Specimen>()
            repeat(Config.populationSize) {
                population.add(Specimen.generateRandom(room))
            }
            return Population(population)
        }
    }
}
