package com.ivanwooll.sockettester

import java.util.regex.Pattern

fun String.isValidWebSocketUrl(): Boolean {
    val regex =
        "([ws]{2}|[wss]{3}):\\/\\/(.*?):[\\d]{1,5}"
    val pattern: Pattern = Pattern.compile(regex, Pattern.MULTILINE)
    return pattern.matcher(this).matches()
}