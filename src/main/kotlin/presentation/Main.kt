package presentation

import domain.entity.MovieEntity
import domain.entity.SessionEntity
import di.DI

@Suppress("DuplicatedCode")
fun main() {
    do {
        DI.cinemaHelper.showMenu()
        val command = DI.cinemaHelper.getNumber(1, 10)
        when (command) {
            1 -> {
                var result = DI.cinemaController.showSessions()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val session = DI.cinemaController.getSessions()
                val idx = DI.cinemaHelper.getNumber(1, session.size)
                result = DI.cinemaController.showPlaces(session[idx - 1])
                println(result.message)
            }

            2 -> {
                var result = DI.cinemaController.showSessions()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val sessions = DI.cinemaController.getSessions()
                val idx = DI.cinemaHelper.getNumber(1, sessions.size)
                val place = DI.cinemaHelper.getPlace(sessions[idx - 1])
                result = DI.cinemaController.bookTicket(sessions[idx - 1], place)
                println(result.message)
            }

            3 -> {
                var result = DI.cinemaController.showSessions()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val sessions = DI.cinemaController.getSessions()
                val idx = DI.cinemaHelper.getNumber(1, sessions.size)
                val place = DI.cinemaHelper.getPlace(sessions[idx - 1])
                result = DI.cinemaController.returnTicket(sessions[idx - 1], place)
                println(result.message)
            }

            4 -> {
                var result = DI.cinemaController.showSessions()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val sessions = DI.cinemaController.getSessions()
                val idx = DI.cinemaHelper.getNumber(1, sessions.size)
                val place = DI.cinemaHelper.getPlace(sessions[idx - 1])
                result = DI.cinemaController.checkTicket(sessions[idx - 1], place)
                println(result.message)
            }

            5 -> {
                val name = DI.cinemaHelper.getName()
                val duration = DI.cinemaHelper.getDuration()
                val result = DI.cinemaController.addMovie(MovieEntity(name, duration))
                println(result.message)
            }

            6 -> {
                var result = DI.cinemaController.showMovies()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val movies = DI.cinemaController.getMovies()
                val idx = DI.cinemaHelper.getNumber(1, movies.size)
                result = DI.cinemaController.deleteMovie(movies[idx - 1])
                println(result.message)
            }

            7 -> {
                var result = DI.cinemaController.showMovies()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val movies = DI.cinemaController.getMovies()
                val idx = DI.cinemaHelper.getNumber(1, movies.size)
                val date = DI.cinemaHelper.getDate()
                result = DI.cinemaController.addSession(SessionEntity(movies[idx - 1], date))
                println(result.message)
            }

            8 -> {
                var result = DI.cinemaController.showSessions()
                if (result.message != "Success") {
                    println(result.message)
                    continue
                }
                val sessions = DI.cinemaController.getSessions()
                val idx = DI.cinemaHelper.getNumber(1, sessions.size)
                result = DI.cinemaController.deleteSession(sessions[idx - 1])
                println(result.message)
            }

            9 -> {
                break
            }
        }
    } while (true)

    println("Program finished")
}
