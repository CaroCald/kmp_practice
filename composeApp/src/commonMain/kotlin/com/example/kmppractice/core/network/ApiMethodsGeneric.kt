package com.example.kmppractice.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.delete
import io.ktor.client.request.setBody
import kotlinx.io.IOException


suspend inline fun <reified T> safeApiGet(
    httpClient: HttpClient,
    url: String,
    params: Map<String, Any?> = emptyMap()
): T {
    return try {
        httpClient.get(url) {
            params.forEach { (key, value) ->
                if (value != null) parameter(key, value)
            }
        }.body()
    } catch (e: Exception) {
        when (e) {
            is IOException -> throw IOException("Network error: ${e.message}")
            else -> throw IOException("Request failed: ${e.message}")
        }
    }
}

suspend inline fun <reified T, reified Body : Any> safeApiPost(
    httpClient: HttpClient,
    url: String,
    body: Body,
    params: Map<String, Any?> = emptyMap()
): T {
    return try {
        httpClient.post(url) {
            setBody(body)
            params.forEach { (key, value) ->
                if (value != null) parameter(key, value)
            }
        }.body()
    } catch (e: Exception) {
        when (e) {
            is IOException -> throw IOException("Network error: ${e.message}")
            else -> throw IOException("Request failed: ${e.message}")
        }
    }
}

suspend inline fun <reified T, reified Body : Any> safeApiPut(
    httpClient: HttpClient,
    url: String,
    body: Body,
    params: Map<String, Any?> = emptyMap()
): T {
    return try {
        httpClient.put(url) {
            setBody(body)
            params.forEach { (key, value) ->
                if (value != null) parameter(key, value)
            }
        }.body()
    } catch (e: Exception) {
        when (e) {
            is IOException -> throw IOException("Network error: ${e.message}")
            else -> throw IOException("Request failed: ${e.message}")
        }
    }
}

suspend inline fun <reified T> safeApiDelete(
    httpClient: HttpClient,
    url: String,
    params: Map<String, Any?> = emptyMap()
): T {
    return try {
        httpClient.delete(url) {
            params.forEach { (key, value) ->
                if (value != null) parameter(key, value)
            }
        }.body()
    } catch (e: Exception) {
        when (e) {
            is IOException -> throw IOException("Network error: ${e.message}")
            else -> throw IOException("Request failed: ${e.message}")
        }
    }
} 