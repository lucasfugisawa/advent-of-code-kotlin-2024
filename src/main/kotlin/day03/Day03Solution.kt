package com.fugisawa.day03

import com.fugisawa.utils.io.readResourceFile
import kotlin.time.measureTimedValue

fun main() {
    val inputText = readResourceFile("input/day-03.txt")

    val (value1, duration1) = measureTimedValue { calculateTotalSum(inputText) }
    val (value2, duration2) = measureTimedValue { calculateTotalSumHandlingDoAndDont(inputText) }

    println("PART 1: Result: $value1. Duration: $duration1")
    println("PART 2: Result: $value2. Duration: $duration2")
}

fun calculateTotalSum(inputText: String): Int = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    .findAll(inputText)
    .sumOf { matchResult ->
        val x = matchResult.groupValues[1].toInt()
        val y = matchResult.groupValues[2].toInt()
        x * y
    }

fun calculateTotalSumHandlingDoAndDont(inputText: String): Int {
    val regex = Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")
    var isEnabled = true

    return regex.findAll(inputText).sumOf { match ->
        when (match.value) {
            "do()" -> {
                isEnabled = true
                0
            }

            "don't()" -> {
                isEnabled = false
                0
            }

            else -> {
                if (isEnabled) {
                    val (x, y) = match.destructured
                    x.toInt() * y.toInt()
                } else 0
            }
        }
    }
}
