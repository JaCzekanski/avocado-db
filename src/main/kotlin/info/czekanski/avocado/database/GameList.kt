package info.czekanski.avocado.database

import info.czekanski.avocado.database.model.GameEntry
import info.czekanski.avocado.database.model.Region
import info.czekanski.avocado.database.model.Status

class GameList {
    private val service = SpreadsheetService()
    private val spreadsheetId = "1iih6wONV4EunFpHYkqC0fALjb10xZQTObC1mkIWSGXA"
    private val issueUrl = "https://github.com/JaCzekanski/Avocado/issues/"

    val list by lazy { downloadList() }

    private fun downloadList(): List<GameEntry> {
        val spreadsheets = service.get().values()

        val response = spreadsheets
            .get(spreadsheetId, "A:D")
            .execute()

        val values = response.getValues()
        if (values.isEmpty()) {
            throw RuntimeException("Unable to load data.")
        }

        val gameList = values.drop(1).map { row ->
            val title = row[0].toString().toUpperCase()
            val region = when {
                title.endsWith("(U)") -> Region.NTSC
                title.endsWith("(J)") -> Region.NTSCJ
                title.endsWith("(P)") -> Region.PAL
                else -> Region.UNKNOWN
            }

            val removeRegionRegex = Regex("\\(.\\)$")
            val name = removeRegionRegex
                .replace(row[0].toString(), "")
                .trim()

            val issueNum = row.getOrNull(3)
            val issueLink = if (issueNum != null) {
                issueUrl + issueNum.toString().removePrefix("#")
            } else {
                null
            }

            GameEntry(
                name = name,
                status = Status.fromString(row[1].toString()),
                region = region,
                build = row.getOrNull(2)?.toString(),
                issue = issueLink
            )
        }

        return gameList
    }
}