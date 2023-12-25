package domain.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import presentation.io.customToString
import java.time.LocalDate


@Serializable
data class SessionEntity(
    var movie: MovieEntity,
    @Contextual var date: LocalDate,
) {
    val places: Array<Array<Int>> = Array(10) { Array(10) { 0 } }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SessionEntity

        if (movie != other.movie) return false
        if (date != other.date) return false
        if (!places.contentDeepEquals(other.places)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movie.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + places.contentDeepHashCode()
        return result
    }

    override fun toString(): String {
        return "$movie, date: ${date.customToString()}"
    }
}