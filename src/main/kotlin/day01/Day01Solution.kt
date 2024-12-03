package com.fugisawa.day01

import com.fugisawa.utils.io.readResourceFileLines
import com.fugisawa.utils.text.repeatedSpacesRegex
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val input = readResourceFileLines("input/day-01.txt")
    val inputPairs: List<Pair<Int, Int>> = input.mapNotNull(::parseLine)

    val (value1, duration1) = measureTimedValue { totalDistance(inputPairs) }
    val (value2, duration2) = measureTimedValue { similarityScore(inputPairs) }

    println("PART 1: Result: $value1. Duration: $duration1")
    println("PART 2: Result: $value2. Duration: $duration2")
}

fun totalDistance(pairs: List<Pair<Int, Int>>): Long {
    val leftList = pairs.map { it.first }.sorted()
    val rightList = pairs.map { it.second }.sorted()

    return leftList
        .zip(rightList)
        .sumOf { (left, right) -> abs(left - right).toLong() }
}

fun similarityScore(pairs: List<Pair<Int, Int>>): Long {
    val leftList = pairs.map { it.first }
    val rightList = pairs.map { it.second }

    val rightCounts = rightList.groupingBy { it }.eachCount()

    return leftList.sumOf { number ->
        val countInRight = rightCounts[number] ?: 0
        number.toLong() * countInRight
    }
}

private fun parseLine(line: String): Pair<Int, Int>? = line
    .trim()
    .takeIf(String::isNotBlank)
    ?.split(repeatedSpacesRegex)
    ?.let { it[0].toInt() to it[1].toInt() }
