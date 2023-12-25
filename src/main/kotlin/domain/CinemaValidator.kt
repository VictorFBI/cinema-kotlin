package domain

import domain.entity.MovieEntity
import domain.entity.SessionEntity
import presentation.model.OutputModel
import java.time.LocalDate

sealed class Result {
    data object Success : Result()
    data class Error(val outputModel: OutputModel) : Result()
}

interface CinemaValidator {
    fun validatePlace(places: Array<Array<Int>>, desire: Pair<Int, Int>): Result
    fun validateDate(date: LocalDate): Result
    fun <T> validateList(list: MutableList<T>): Result
    fun validateSession(map: MutableMap<LocalDate, SessionEntity>, session: SessionEntity): Result
    fun validateMovie(map: MutableMap<String, MovieEntity>, movie: MovieEntity): Result

}

class RuntimeCinemaValidator : CinemaValidator {
    override fun validatePlace(places: Array<Array<Int>>, desire: Pair<Int, Int>): Result {
        return when (places[desire.first - 1][desire.second - 1]) {
            0 -> Result.Success
            else -> Result.Error(OutputModel("Place is occupied"))
        }
    }

    override fun validateDate(date: LocalDate): Result {
        return if (LocalDate.now() < date) Result.Success
        else Result.Error(OutputModel("You cannot return a ticket after session was commenced"))
    }

    override fun <T> validateList(list: MutableList<T>): Result {
        return if (list.isEmpty()) Result.Error(OutputModel("List is empty"))
        else Result.Success
    }

    override fun validateSession(map: MutableMap<LocalDate, SessionEntity>, session: SessionEntity): Result {
        return if (map.containsValue(session)) Result.Success
        else Result.Error(OutputModel("Session does not exist"))
    }

    override fun validateMovie(map: MutableMap<String, MovieEntity>, movie: MovieEntity): Result {
        return if (map.containsValue(movie)) Result.Success
        else Result.Error(OutputModel("Movie does not exist"))
    }
}