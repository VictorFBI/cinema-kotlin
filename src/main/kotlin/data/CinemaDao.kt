package data

import domain.entity.MovieEntity
import domain.entity.SessionEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.Duration
import java.time.LocalDate

interface CinemaDao {
    fun getPlaces(session: SessionEntity): Array<Array<Int>>
    fun bookTicket(session: SessionEntity, place: Pair<Int, Int>)
    fun returnTicket(session: SessionEntity, place: Pair<Int, Int>)
    fun addSession(movie: MovieEntity, date: LocalDate)
    fun addMovie(name: String, duration: Duration)
    fun deleteSession(date: LocalDate)
    fun deleteMovie(name: String)
    fun getSessions(): MutableList<SessionEntity>
    fun getMovies(): MutableList<MovieEntity>
}

class RuntimeCinemaDao : CinemaDao {
    val sessions = mutableMapOf<LocalDate, SessionEntity>()
    val movies = mutableMapOf<String, MovieEntity>()

    override fun getPlaces(session: SessionEntity): Array<Array<Int>> {
        val sessionDao = RuntimeSessionDao(session)
        return sessionDao.getPlaces()
    }


    override fun bookTicket(session: SessionEntity, place: Pair<Int, Int>) {
        val sessionDao = RuntimeSessionDao(session)
        sessionDao.occupy(place)
        updateFileSession()
    }

    override fun returnTicket(session: SessionEntity, place: Pair<Int, Int>) {
        val sessionDao = RuntimeSessionDao(session)
        sessionDao.free(place)
        updateFileSession()
    }

    override fun addSession(movie: MovieEntity, date: LocalDate) {
        val session = SessionEntity(movie, date)
        sessions[date] = session
        updateFileSession()
    }

    override fun addMovie(name: String, duration: Duration) {
        val movie = MovieEntity(name, duration)
        movies[name] = movie
        updateFileMovies()
    }

    override fun deleteMovie(name: String) {
        movies.remove(name)
        updateFileMovies()
    }

    override fun deleteSession(date: LocalDate) {
        sessions.remove(date)
        updateFileSession()
    }

    override fun getSessions(): MutableList<SessionEntity> {
        val ans = mutableListOf<SessionEntity>()
        for (session in sessions.values) {
            ans.add(session)
        }

        return ans
    }

    override fun getMovies(): MutableList<MovieEntity> {
        val ans = mutableListOf<MovieEntity>()
        for (movie in movies.values) {
            ans.add(movie)
        }

        return ans
    }

    private fun updateFileSession() {
        val json = Json { serializersModule = module }
        val strSessions = json.encodeToString(sessions)
        val fileSessions = File("src/main/resources/sessions.json")
        fileSessions.writeText(strSessions)
    }

    private fun updateFileMovies() {
        val strMovies = Json.encodeToString(movies)
        val fileMovies = File("src/main/resources/movies.json")
        fileMovies.writeText(strMovies)
    }
}