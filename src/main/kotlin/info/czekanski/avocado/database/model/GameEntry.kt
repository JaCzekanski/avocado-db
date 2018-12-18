package info.czekanski.avocado.database.model

import java.util.*

data class GameEntry(
    val name: String,
    val status: Status,
    val region: Region,
    val updated: Date?,
    val build: String?,
    val issue: String?
)