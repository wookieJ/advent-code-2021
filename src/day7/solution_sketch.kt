package day7

import checkSolution
import kotlin.math.abs

fun main() {
    fun part1(input: String): Long {
        val crabs = input.split(",").filter { it.isNotEmpty() }.map { it.toInt() }
        var result = Long.MAX_VALUE
        var fuel = 0L
        for (max in 0..crabs.maxOrNull()!!) {
            for (crab in crabs) {
                fuel += (abs(max - crab))
            }
            if (fuel < result) {
                result = fuel
                fuel = 0L
            }
        }
        return result
    }

    fun part2(input: String): Long {
        val crabs = input.split(",").filter { it.isNotEmpty() }.map { it.toInt() }
        var result = Long.MAX_VALUE
        var fuel = 0L
        for (max in 0..crabs.maxOrNull()!!) {
            for (crab in crabs) {
                for(i in 1..abs(max - crab)) {
                    fuel += i
                }
            }
            if (fuel < result) {
                result = fuel
                fuel = 0L
            }
        }
        return result
    }

    checkSolution(7, ::part1, ::part2, 37, 168)
}
