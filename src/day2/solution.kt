package day2

import readInput
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0;
        var depth = 0;
        input.forEach {
            val direction = it.substringBefore(" ")
            val value = it.substringAfter(" ").toInt()
            when (direction) {
                "forward" -> {
                    horizontal += value
                }
                "down" -> {
                    depth += value
                }
                "up" -> {
                    depth -= value
                }
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0;
        var depth = 0;
        var aim = 0;
        input.forEach {
            val direction = it.substringBefore(" ")
            val value = it.substringAfter(" ").toInt()
            when (direction) {
                "forward" -> {
                    horizontal += value
                    depth += aim * value
                }
                "down" -> {
                    aim += value
                }
                "up" -> {
                    aim -= value
                }
            }
        }
        return horizontal * depth
    }

    val dayNumber = 2
    val testResult = 150

    val testInput = readInput("day${dayNumber}/data_test")
    println("TEST result = ${part1(testInput)}")
    check(part1(testInput) == testResult)

    val input = readInput("day${dayNumber}/data")
    var part1Result: Int
    var part2Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }

    println("$part1Result elapses in $part1Benchmark ms")
    println("$part2Result elapses in $part2Benchmark ms")
}
