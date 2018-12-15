package info.czekanski.avocado.database.model

enum class Region {
    NTSC,
    NTSCJ,
    PAL,
    UNKNOWN;

    companion object {
        fun fromString(s: String): Region {
            return when (s.trim().toLowerCase()) {
                "ntsc" -> NTSC
                "ntscj" -> NTSCJ
                "pal" -> PAL
                else -> UNKNOWN
            }
        }
    }
}