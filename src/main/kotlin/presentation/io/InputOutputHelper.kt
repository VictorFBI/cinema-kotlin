package presentation.io

import data.RuntimeCinemaDao
import domain.RuntimeCinemaValidator
import domain.entity.SessionEntity
import java.lang.NumberFormatException
import java.time.Duration
import java.time.LocalDate

interface InputOutputHelper {
    fun showMenu()
    fun getNumber(min: Int, max: Int): Int
    fun getPlace(session: SessionEntity): Pair<Int, Int>
    fun getDuration(): Duration
    fun getName(): String
    fun getDate(): LocalDate
}

fun Duration.customToString(): String {
    val hours = this.toHours()
    val minutes = this.minusHours(hours).toMinutes()
    val seconds = this.minusHours(hours).minusMinutes(minutes).seconds

    return "$hours hours, $minutes minutes, $seconds seconds"
}

fun LocalDate.customToString(): String {
    val month = this.monthValue
    val day = this.dayOfMonth
    val year = this.year

    return "$day.$month.$year"
}

class RuntimeInputOutputHelper :
    InputOutputHelper {
    private val menu = arrayOf(
        "1. Show places in the session",
        "2. Book a ticket to the session",
        "3. Return a ticket from the session",
        "4. Check a ticket to the session",
        "5. Add movie",
        "6. Delete movie",
        "7. Add session",
        "8. Delete session",
        "9. Exit"
    )

    override fun showMenu() {
        for (el in menu) {
            println(el)
        }
    }

    override fun getNumber(min: Int, max: Int): Int {
        println("Input number")
        var command: Int?
        do {
            try {
                command = readlnOrNull()?.toInt()
                if (command != null && command >= min && command <= max) {
                    return command
                }
            } catch (_: NumberFormatException) {
            }
            println("Incorrect number, try again")
        } while (true)
    }

    override fun getPlace(session: SessionEntity): Pair<Int, Int> {
        println("Print row")
        var row = readlnOrNull()?.toInt()
        println("Print column")
        var column = readlnOrNull()?.toInt()
        while (row == null || column == null || !isValidPlace(row, column)) {
            println("Incorrect input, try again")
            row = readlnOrNull()?.toInt()
            column = readlnOrNull()?.toInt()
        }

        return Pair(row, column)
    }

    override fun getDuration(): Duration {
        println("Enter amount of seconds")
        val seconds = getNumber(0, 59)
        println("Enter amount of minutes")
        val minutes = getNumber(0, 59)
        println("Enter amount of hours")
        val hours = getNumber(0, 23)

        return Duration.ofSeconds((seconds + minutes * 60 + hours * 3600).toLong())
    }

    override fun getName(): String {
        println("Input name of the film")
        var name: String? = readlnOrNull()
        while (name.isNullOrEmpty()) {
            println("Input correct name of the film")
            name = readlnOrNull()
        }
        return name
    }

    override fun getDate(): LocalDate {
        println("Enter month")
        val month = getNumber(1, 12)
        println("Enter day")
        val day: Int = if (month == 2) {
            getNumber(1, 28)
        } else {
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                getNumber(1, 31)
            } else {
                getNumber(1, 30)
            }
        }
        return LocalDate.of(2023, month, day)
    }

    private fun isValidPlace(row: Int, column: Int): Boolean {
        return row in 1..10 && column in 1..10
    }
}