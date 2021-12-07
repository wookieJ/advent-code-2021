package day7

import checkSolution
import kotlin.math.abs

fun main() {
    fun part1(input: String): Int {
        val crabs = input.split(",").map { it.toInt() }
        return (0..crabs.maxOf { it })
            .minOf { alignToValue ->
                crabs.sumOf { abs(alignToValue - it) }
            }
    }

    fun part2(input: String): Int {
        val crabs = input.split(",").map { it.toInt() }
        return (0..crabs.maxOf { it })
            .minOf { alignToValue ->
                crabs.sumOf { (1..abs(alignToValue - it)).sum() }
            }
    }

    checkSolution(7, ::part1, ::part2, 37, 168)
}
