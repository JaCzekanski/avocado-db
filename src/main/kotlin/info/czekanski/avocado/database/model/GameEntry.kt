package info.czekanski.avocado.database.model

data class GameEntry(
    val name: String,
    val status: Status,
    val region: Region,
    val build: String?,
    val issue: String?
)