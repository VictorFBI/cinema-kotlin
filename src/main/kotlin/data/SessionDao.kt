package data

import domain.entity.SessionEntity

interface SessionDao {
    fun getPlaces(): Array<Array<Int>>
    fun occupy(choice: Pair<Int, Int>)
    fun free(place: Pair<Int, Int>)
}

class RuntimeSessionDao(private val session: SessionEntity) : SessionDao {

    override fun getPlaces(): Array<Array<Int>> {
        return session.places
    }

    override fun occupy(choice: Pair<Int, Int>) {
        session.places[choice.first - 1][choice.second - 1] = 1
    }

    override fun free(place: Pair<Int, Int>) {
        session.places[place.first - 1][place.second - 1] = 0
    }
}