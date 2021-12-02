package day1

import readInput
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        val data = input.map { it.toInt() }
        var previous = data[0]
        data.forEach {
            if (it > previous) {
                result++
            }
            previous = it
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        val data = input.map { it.toInt() }
        var previous = data[0] + data[1] + data[2]
        for (i in 0..data.size - 1) {
            if (i + 1 < data.size && i + 2 < data.size) {
                if (data[i] + data[i + 1] + data[i + 2] > previous) {
                    result++
                }
                previous = data[i] + data[i + 1] + data[i + 2]
            }
        }
        return result
    }

    val testInput = readInput("day1/data_test")
    check(part1(testInput) == 7)

    val input = readInput("day1/data")

    var part1Result: Int
    var part2Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }

    println("$part1Result elapses in $part1Benchmark ms")
    println("$part2Result elapses in $part2Benchmark ms")
}
