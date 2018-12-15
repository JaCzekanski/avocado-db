package info.czekanski.avocado.database

import info.czekanski.avocado.database.error.ApiError
import info.czekanski.avocado.database.model.GameEntry
import info.czekanski.avocado.database.model.Region
import info.czekanski.avocado.database.model.Status
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.http.withCharset
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.text.DateFormat

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val games = GameList()

        val server = embeddedServer(Netty, 8080) {
            install(DefaultHeaders) {
                header("Access-Control-Allow-Origin", "*")
            }
            install(CallLogging)
            install(ContentNegotiation) {
                gson {
                    setDateFormat(DateFormat.LONG)
                    setPrettyPrinting()
                    serializeNulls()
                }
            }
            install(StatusPages) {
                exception<ApiError> { cause ->
                    call.respond(HttpStatusCode.BadRequest, cause)
                }
                status(HttpStatusCode.NotFound) {
                    call.respond(
                        TextContent(
                            "${it.value} ${it.description}",
                            ContentType.Text.Plain.withCharset(Charsets.UTF_8),
                            it
                        )
                    )
                }
            }

            routing {
                static {
                    resource("/", "public/index.html")
                    resources("public")
                }
                get("/api/games") {

                    val statusQuery = call.request.queryParameters["status"]
                    val regionQuery = call.request.queryParameters["region"]
                    val sortQuery = call.request.queryParameters["sort"]
                    val phraseQuery = call.request.queryParameters["phrase"]
                    val letterQuery = call.request.queryParameters["letter"]

                    var list = games.list

                    if (!statusQuery.isNullOrEmpty()) {
                        val status = Status.fromString(statusQuery)
                        list = list.filter { it.status == status }
                    }

                    if (!regionQuery.isNullOrEmpty()) {
                        val region = Region.fromString(regionQuery)

                        list = list.filter { it.region == region }
                    }

                    if (!letterQuery.isNullOrEmpty()) {
                        val letter = letterQuery.first()
                        if (!letter.isLetterOrDigit()) {
                            throw ApiError("Invalid letter parameter")
                        }

                        list = list.filter { it.name.startsWith(letter, ignoreCase = true) }
                    }

                    if (!phraseQuery.isNullOrEmpty()) {
                        list = list.filter { it.name.contains(phraseQuery.trim(), ignoreCase = true) }
                    }

                    if (!sortQuery.isNullOrEmpty()) {
                        val dir = sortQuery[0]
                        val field = if (dir == '-' || dir == '+') sortQuery.drop(1) else sortQuery

                        fun compare(it: GameEntry): Comparable<*>? = when (field) {
                            "name" -> it.name
                            "status" -> it.status
                            "region" -> it.region
                            "build" -> it.build
                            "issue" -> it.issue
                            else -> throw ApiError("Invalid sort field")
                        }

                        list = list.sortedWith(when (dir) {
                            '-' -> compareByDescending { compare(it) }
                            else -> compareBy { compare(it) }
                        })
                    }

                    call.respond(list)
                }
                get("/api/games/summary") {
                    val list = games.list
                    val summary = mutableMapOf<String, Int>()

                    Status.values().forEach { status ->
                        summary[status.toString()] = list.filter { it.status == status }.count()
                    }

                    summary["Total"] = list.count()

                    call.respond(summary)
                }
            }
        }

        server.start(wait = true)
    }

}