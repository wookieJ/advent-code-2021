package day13

import readInputString
import java.awt.Point
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: String): Int {
        val instructions = input.substringAfter("\n\n").trim().lines().filter { it.isNotEmpty() }.map { it.substringAfter("fold along ") }
        val points = input.substringBefore("\n\n").trim().lines().filter { it.isNotEmpty() }.map {
            val x = it.substringBefore(",").toInt()
            val y = it.substringAfter(",").toInt()
            Point(x, y)
        }.toMutableSet()
        val axis = instructions[0].substringBefore("=")
        val value = instructions[0].substringAfter("=").toInt()
        val result = mutableSetOf<Point>()
        if (axis == "x") {
            points.forEach { point -> // left
                val dx = 2 * value - point.x
                if (point.x > value) {
                    point.move(dx, point.y)
                    result.add(point)
                } else {
                    result.add(point)
                }
            }
        } else { // up
            points.forEach { point -> // left
                val dy = 2 * value - point.y
                if (point.y > value) {
                    point.move(point.x, dy)
                    result.add(point)
                } else {
                    result.add(point)
                }
            }
        }
        return result.size
    }

    fun part2(input: String): Set<Point> {
        val instructions = input.substringAfter("\n\n").trim().lines().filter { it.isNotEmpty() }.map { it.substringAfter("fold along ") }
        val points = input.substringBefore("\n\n").trim().lines().filter { it.isNotEmpty() }.map {
            val x = it.substringBefore(",").toInt()
            val y = it.substringAfter(",").toInt()
            Point(x, y)
        }.toMutableSet()
        val result = mutableSetOf<Point>()
        instructions.forEach { inst ->
            val axis = inst.substringBefore("=")
            val value = inst.substringAfter("=").toInt()
            if (axis == "x") {
                points.forEach { point -> // left
                    val dx = 2 * value - point.x
                    if (point.x > value) {
                        point.move(dx, point.y)
                        result.add(point)
                    } else {
                        result.add(point)
                    }
                }
            } else { // up
                points.forEach { point -> // left
                    val dy = 2 * value - point.y
                    if (point.y > value) {
                        point.move(point.x, dy)
                        result.add(point)
                    } else {
                        result.add(point)
                    }
                }
            }
        }
        return result
    }

    val testInput = readInputString("day13/data_test").trim()
    val input = readInputString("day13/data").trim()

    print("Part 1 TEST result = ${part1(testInput)}")
    check(part1(testInput) == 17)
    println(" ✅")

    var part1Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    println("Part 1 solution: $part1Result, elapses in $part1Benchmark ms")
    println("--------------")

    var part2Result: Set<Point>
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }
    println("Part 2 solution elapses in $part2Benchmark ms:")
    val ys = part2Result.map { it.y }
    val xs = part2Result.map { it.x }
    val ysMin = ys.minOf { it }
    val ysMax = ys.maxOf { it }
    val xsMin = xs.minOf { it }
    val xsMax = xs.maxOf { it }
    for (y in ysMin..ysMax) {
        var line = ""
        for (x in xsMin..xsMax) {
            if (Point(x, y) in part2Result) {
                line += "#"
            } else {
                line += " "
            }
        }
        println(line)
    }
    println("")
}
