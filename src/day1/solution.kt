package day1

import readInput
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<Int>): Int {
        return input.zipWithNext().count { it.second > it.first }
    }

    fun part2(input: List<Int>): Int {
        return input.windowed(3).map { it.sum() }.zipWithNext().count { it.second > it.first }
    }

    val testInput = readInput("day1/data_test").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("day1/data").map { it.toInt() }

    var part1Result: Int
    var part2Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }

    println("$part1Result elapses in $part1Benchmark ms")
    println("$part2Result elapses in $part2Benchmark ms")
}
