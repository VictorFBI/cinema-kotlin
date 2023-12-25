package domain

import data.RuntimeCinemaDao
import domain.entity.MovieEntity
import domain.entity.SessionEntity
import presentation.model.OutputModel

interface CinemaController {
    fun showPlaces(session: SessionEntity): OutputModel
    fun bookTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel
    fun returnTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel
    fun checkTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel
    fun addMovie(movie: MovieEntity): OutputModel
    fun deleteMovie(movie: MovieEntity): OutputModel
    fun addSession(session: SessionEntity): OutputModel
    fun deleteSession(session: SessionEntity): OutputModel
    fun showMovies(): OutputModel
    fun showSessions(): OutputModel
    fun getSessions(): MutableList<SessionEntity>
    fun getMovies(): MutableList<MovieEntity>
}

class RuntimeCinemaController(
    private val dao: RuntimeCinemaDao,
    private val validator: RuntimeCinemaValidator
) : CinemaController {
    override fun showPlaces(session: SessionEntity): OutputModel {
        val sessions = dao.getSessions()
        return when (val result = validator.validateList(sessions)) {
            is Result.Success -> {
                val hall = dao.getPlaces(session)
                var rwIdx = 1
                for (row in hall) {
                    print("Row ${rwIdx++} |")
                    for (seat in row) {
                        print(" $seat")
                    }
                    print("\n")
                }
                OutputModel("Success")
            }

            is Result.Error -> result.outputModel
        }
    }


    override fun bookTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel {
        if (dao.getPlaces(session).isEmpty()) return OutputModel("There is no empty places ")

        return when (val result = validator.validatePlace(session.places, place)) {
            is Result.Success -> {
                dao.bookTicket(session, place)
                OutputModel("Success")
            }

            is Result.Error -> result.outputModel
        }
    }

    override fun returnTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel {
        return when (validator.validatePlace(session.places, place)) {
            is Result.Error -> {
                dao.returnTicket(session, place)
                OutputModel("Success")
            }

            is Result.Success -> OutputModel("Error: Place is free")
        }
    }

    override fun checkTicket(session: SessionEntity, place: Pair<Int, Int>): OutputModel {
        return when (val result = validator.validatePlace(session.places, place)) {
            is Result.Success -> OutputModel("Place is empty")
            is Result.Error -> result.outputModel
        }
    }


    override fun addMovie(movie: MovieEntity): OutputModel {
        return when (validator.validateMovie(dao.movies, movie)) {
            is Result.Success -> OutputModel("Movie already exists")
            is Result.Error -> {
                dao.movies[movie.name] = movie
                OutputModel("Success")
            }
        }
    }

    override fun deleteMovie(movie: MovieEntity): OutputModel {
        return when (val result = validator.validateMovie(dao.movies, movie)) {
            is Result.Success -> {
                dao.movies.remove(movie.name)
                OutputModel("Success")
            }

            is Result.Error -> result.outputModel
        }
    }


    override fun addSession(session: SessionEntity): OutputModel {
        return when (validator.validateSession(dao.sessions, session)) {
            is Result.Success -> OutputModel("Session already exists")
            is Result.Error -> {
                dao.sessions[session.date] = session
                OutputModel("Success")
            }
        }
    }

    override fun deleteSession(session: SessionEntity): OutputModel {
        return when (val result = validator.validateSession(dao.sessions, session)) {
            is Result.Success -> {
                dao.sessions.remove(session.date)
                OutputModel("Success")
            }

            is Result.Error -> result.outputModel
        }
    }

    override fun showSessions(): OutputModel {
        val list = dao.sessions.values.toMutableList()
        println("Sessions: ")
        return when (validator.validateList(list)) {
            is Result.Success -> {
                var index = 1
                for (el in list) {
                    println("${index++}. $el")
                }
                return OutputModel("Success")
            }

            is Result.Error -> OutputModel("There is no available sessions")
        }
    }

    override fun showMovies(): OutputModel {
        val list = dao.movies.values.toMutableList()
        println("Movies: ")
        return when (validator.validateList(list)) {
            is Result.Success -> {
                var index = 1
                for (el in list) {
                    println("${index++}. $el")
                }
                return OutputModel("Success")
            }

            is Result.Error -> OutputModel("There is no movies in release")
        }
    }

    override fun getSessions(): MutableList<SessionEntity> {
        return dao.getSessions()
    }

    override fun getMovies(): MutableList<MovieEntity> {
        return dao.getMovies()
    }
}

