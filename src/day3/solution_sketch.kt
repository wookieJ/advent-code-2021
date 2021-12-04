package day3

import readInput
import readInputString
import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: String): Int {
        val inputLines = input.lines()
        var gamma = mutableListOf<LinkedHashMap<Int, Int>>()
        var epsilon = mutableListOf<LinkedHashMap<Int, Int>>()
        gamma = MutableList(inputLines[0].length) { linkedMapOf() }
        epsilon = MutableList(inputLines[0].length) { linkedMapOf() }
        inputLines.forEachIndexed { mainIdx, line ->
            line.toList().forEachIndexed { i, value ->
                if (Character.getNumericValue(value) == 0) {
                    gamma[i].putIfAbsent(0, 0)
                    gamma[i][0] = gamma[i][0]!! + 1
                } else {
                    gamma[i].putIfAbsent(1, 0)
                    gamma[i][1] = gamma[i][1]!! + 1
                }
                if (Character.getNumericValue(line[line.length - i - 1]) == 0) {
                    epsilon[i].putIfAbsent(0, 0)
                    epsilon[i][0] = epsilon[i][0]!! + 1
                } else {
                    epsilon[i].putIfAbsent(1, 0)
                    epsilon[i][1] = epsilon[i][1]!! + 1
                }

            }
        }
        var gammaS = ""
        var epsilonS = ""
        gamma.forEach {
            if (it[0]!! > it[1]!!) {
                gammaS += "0"
                epsilonS += "1"
            } else {
                gammaS += "1"
                epsilonS += "0"
            }
        }
        return gammaS.toInt(2) * epsilonS.toInt(2)
    }

    fun part2(input: String): Int {
        val inputLines = input.lines()
        var inputCopy = inputLines.toMutableList()
        var linesGamma: MutableList<MutableList<Char>> = mutableListOf(mutableListOf())
        linesGamma = inputLines.map { it.toMutableList() }.toMutableList()
        for (col in 0..inputLines[0].length - 1) {
            if (linesGamma.size <= 1) {
                break
            }
            var col0 = 0
            var col1 = 0
            for (row in 0 until linesGamma.size) {
                if (Character.getNumericValue(linesGamma[row][col]) == 0) {
                    col0++
                } else {
                    col1++
                }
            }
            var prefix = if (col0 > col1) {
                "0"
            } else {
                "1"
            }
            inputCopy = inputCopy.filter { it[col].toString() == prefix }.toMutableList()
            linesGamma = inputCopy.map { it.toMutableList() }.toMutableList()
        }
        var gammaS = ""
        linesGamma[0].toList().forEach {
                gammaS += it.toString()
        }
        var gammaValue = gammaS.toInt(2)
        inputCopy = inputLines.toMutableList()
        linesGamma = inputLines.map { it.toMutableList() }.toMutableList()
        for (col in 0..inputLines[0].length - 1) {
            if (linesGamma.size <= 1) {
                break
            }
            var col0 = 0
            var col1 = 0
            for (row in 0 until linesGamma.size) {
                if (Character.getNumericValue(linesGamma[row][col]) == 0) {
                    col0++
                } else {
                    col1++
                }
            }
            var prefix = if (col0 <= col1) {
                "0"
            } else {
                "1"
            }
            inputCopy = inputCopy.filter { it[col].toString() == prefix }.toMutableList()
            linesGamma = inputCopy.map { it.toMutableList() }.toMutableList()
        }
        gammaS = ""
        linesGamma[0].toList().forEach {
            gammaS += it.toString()
        }
        var epsilonV = gammaS.toInt(2)
        return epsilonV * gammaValue
    }

    val dayNumber = 3
    val testResult = 230

    val testInput = readInputString("day${dayNumber}/data_test")//.map { it.toInt() }
    println("TEST result = ${part1(testInput)}")
    check(part2(testInput) == testResult)

    val input = readInputString("day${dayNumber}/data")//.map { it.toInt() }
    var part1Result: Int
    var part2Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }

    println("$part1Result elapses in $part1Benchmark ms")
    println("$part2Result elapses in $part2Benchmark ms")
}
