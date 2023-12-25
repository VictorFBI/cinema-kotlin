package di

import data.RuntimeCinemaDao
import domain.CinemaController
import domain.RuntimeCinemaController
import domain.RuntimeCinemaValidator
import presentation.io.InputOutputHelper
import presentation.io.RuntimeInputOutputHelper

object DI {
    private val cinemaValidator: RuntimeCinemaValidator
        get() = RuntimeCinemaValidator()
    val cinemaDao: RuntimeCinemaDao by lazy {
        RuntimeCinemaDao()
    }

    val cinemaController: CinemaController
        get() = RuntimeCinemaController(
            cinemaDao,
            cinemaValidator
        )
    val cinemaHelper: InputOutputHelper
        get() = RuntimeInputOutputHelper()
}