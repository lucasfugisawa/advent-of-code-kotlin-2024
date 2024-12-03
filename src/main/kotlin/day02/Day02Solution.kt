package com.fugisawa.day02

import com.fugisawa.utils.io.readResourceFileLines
import com.fugisawa.utils.text.repeatedSpacesRegex
import kotlin.math.abs
import kotlin.time.measureTimedValue

fun main() {
    val input = readResourceFileLines("input/day-02.txt")
    val reports = readReports(input)

    val (value1, duration1) = measureTimedValue { reports.count { isReportSafe(it) } }
    val (value2, duration2) = measureTimedValue { reports.count { isReportSafeWithDampener(it) } }

    println("PART 1: Result: $value1. Duration: $duration1")
    println("PART 2: Result: $value2. Duration: $duration2")
}

fun readReports(lines: List<String>): List<List<Int>> =
    lines.mapNotNull { line ->
        line.trim()
            .takeIf { it.isNotEmpty() }
            ?.split(repeatedSpacesRegex)
            ?.mapNotNull(String::toIntOrNull)
            ?.takeIf { it.isNotEmpty() }
    }

fun isReportSafe(levels: List<Int>): Boolean {
    if (levels.size < 2) return false

    val differences = levels.zipWithNext { a, b -> b - a }
    val isIncreasing = differences.first() > 0

    return differences.all { diff ->
        diff != 0
                && abs(diff) in 1..3
                && (diff > 0) == isIncreasing
    }
}

fun isReportSafeWithDampener(levels: List<Int>): Boolean {
    if (isReportSafe(levels)) return true

    return levels.indices.any { i ->
        val modifiedLevels = levels.filterIndexed { index, _ -> index != i }
        isReportSafe(modifiedLevels)
    }
}
