package model

data class Specimen(val desks: List<Desk>) {
    companion object {
        fun generateRandom(room: Room): Specimen {
            val desks = ArrayList<Desk>()
            repeat(Config.numberOfDesks) {
                desks.add(Desk.generateRandom(room))
            }
            return Specimen(desks)
        }
    }
}
