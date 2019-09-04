package info.czekanski.avocado.database.provider

import info.czekanski.avocado.database.model.GameEntry
import info.czekanski.avocado.database.model.Region
import info.czekanski.avocado.database.model.Status
import info.czekanski.avocado.database.service.SpreadsheetService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class GameListProvider {
    private val service = SpreadsheetService()
    private val spreadsheetId = "1iih6wONV4EunFpHYkqC0fALjb10xZQTObC1mkIWSGXA"
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy")

    private var list: List<GameEntry> = listOf()
    private val lock = ReentrantReadWriteLock()

    fun getGameList(): List<GameEntry> {
        lock.read {
            return list
        }
    }

    private fun <T> List<T>.getAsString(index: Int): String {
        return (getOrNull(index) as? String)?.trim() ?: ""
    }

    private fun getIssueNumber(row: MutableList<Any>): String? {
        val issueString = row
            .getAsString(3)
            .removePrefix("#")

        if (issueString.isBlank()) {
            return null
        }

        return issueString
    }

    private fun parseRegion(row: MutableList<Any>): Region{
        val title = row.getAsString(0).toUpperCase()

        return when {
            title.endsWith("(U)") -> Region.NTSC
            title.endsWith("(J)") -> Region.NTSCJ
            title.endsWith("(E)") -> Region.PAL
            else -> Region.UNKNOWN
        }
    }

    private fun getName(row: MutableList<Any>): String {
        val removeRegionRegex = Regex("\\(.\\)$")
        return removeRegionRegex
            .replace(row.getAsString(0), "")
    }

    private fun getDate(row: MutableList<Any>): Date? {
        val updatedAt = row.getOrNull(4)
        return if (updatedAt != null) dateFormatter.parse(updatedAt as String) else null
    }

    private fun downloadList(): List<GameEntry> {
        val spreadsheets = service.get().values()

        val response = spreadsheets
            .get(spreadsheetId, "A:E")
            .execute()

        val values = response.getValues()
        if (values.isEmpty()) {
            throw RuntimeException("Unable to load data.")
        }

        val gameList = values.drop(1).map { row ->
            GameEntry(
                name = getName(row),
                status = Status.fromString(row.getAsString(1)),
                region = parseRegion(row),
                updated = getDate(row),
                build = row.getAsString(2),
                issue = getIssueNumber(row)
            )
        }

        return gameList
    }

    fun refreshPeriodically() {
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            while (true) {
                try {
                    lock.write {
                        this@GameListProvider.list = downloadList()
                    }
                } catch (ex: Throwable) {
                    ex.printStackTrace();
                }

                delay(Duration.ofMinutes(5))
            }
        }
    }
}