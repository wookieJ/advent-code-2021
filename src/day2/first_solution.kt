package day2

import readInput
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        var x = 0;
        var y = 0;
        for (i in input.indices) {
            if (input[i].contains("forward")) {
                x += input[i].split(" ")[1].toInt()
            } else if (input[i].contains("down")) {
                y += input[i].split(" ")[1].toInt()
            } else if (input[i].contains("up")) {
                y -= input[i].split(" ")[1].toInt()
            }
        }
        return x * y
    }

    fun part2(input: List<String>): Int {
        var x = 0;
        var y = 0;
        var aim = 0;
        for (i in input.indices) {
            if (input[i].contains("forward")) {
                x += input[i].split(" ")[1].toInt()
                y += aim * input[i].split(" ")[1].toInt()
            } else if (input[i].contains("down")) {
                aim += input[i].split(" ")[1].toInt()
            } else if (input[i].contains("up")) {
                aim -= input[i].split(" ")[1].toInt()
            }
        }
        return x * y
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
