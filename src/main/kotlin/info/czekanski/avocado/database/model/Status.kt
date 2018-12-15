package info.czekanski.avocado.database.model

enum class Status {
    Playable,
    InGame,
    IntroMenus,
    Broken,
    Unknown;

    companion object {
        fun fromString(s: String): Status {
            return when (s.trim().toLowerCase()) {
                "broken" -> Status.Broken
                "intro", "menus", "intromenus", "intro-menus" -> Status.IntroMenus
                "ingame", "in-game" -> Status.InGame
                "playable" -> Status.Playable
                else -> Status.Unknown
            }
        }
    }
}