package domain.entity

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import presentation.io.customToString
import java.time.Duration

@Serializable
data class MovieEntity(
    var name: String,
    @Contextual var duration: Duration
) {
    override fun toString(): String {
        return "Name: \"${name}\", duration: ${duration.customToString()}"
    }
}