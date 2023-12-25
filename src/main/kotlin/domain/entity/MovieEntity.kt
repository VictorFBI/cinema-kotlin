package domain.entity

import data.DurationSerializer
import kotlinx.serialization.Serializable
import presentation.io.customToString
import java.time.Duration




@Serializable
data class MovieEntity(
    var name: String,
    @Serializable(with = DurationSerializer::class) var duration: Duration
) {
    override fun toString(): String {
        return "Name: \"${name}\", duration: ${duration.customToString()}"
    }
}