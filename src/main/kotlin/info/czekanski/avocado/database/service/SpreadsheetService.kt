package info.czekanski.avocado.database.service

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.InputStreamReader

class SpreadsheetService {
    private val APPLICATION_NAME = "Avocado Database"
    private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    private val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()

    private fun getCredentials(): Credential {
        val scopes = listOf(SheetsScopes.SPREADSHEETS_READONLY)
        val inputStream = this::class.java.getResourceAsStream("/credentials.json")

        return GoogleCredential.fromStream(inputStream)
            .createScoped(scopes)
    }

    fun get(): Sheets.Spreadsheets {
        return Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
            .setApplicationName(APPLICATION_NAME)
            .build().spreadsheets()
    }
}