package com.fugisawa.utils

fun readResourceFile(fileName: String): String {
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream(fileName)
    return inputStream?.bufferedReader()?.use { it.readText() }
        ?: throw IllegalArgumentException("Resource file '$fileName' not found.")
}

fun readResourceFileLines(fileName: String): List<String> {
    return readResourceFile(fileName).lines()
}
