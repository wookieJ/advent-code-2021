import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.measureTimeMillis

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputString(name: String) = File("src", "$name.txt").readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <E> transpose(xs: List<List<E>>): List<List<E>> {
    fun <E> List<E>.head(): E = this.first()
    fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
    fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

    xs.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
            else -> emptyList()
        }
    }
}

fun checkSolution(dayNumber: Int, part1: (String) -> Int, part2: (String) -> Int, firstTestResult: Int = -1, secondTestResult: Int = -1) {
    val testInput = readInputString("day${dayNumber}/data_test").trim()
    val input = readInputString("day${dayNumber}/data").trim()

    print("Part 1 TEST result = ${part1(testInput)}")
    check(part1(testInput) == firstTestResult)
    println(" ✅")

    var part1Result: Int
    val part1Benchmark = measureTimeMillis { part1Result = part1(input) }
    println("Part 1 solution: $part1Result, elapses in $part1Benchmark ms")
    println("--------------")
    print("Part 2 TEST result = ${part2(testInput)}")
    check(part2(testInput) == secondTestResult)
    println(" ✅")

    var part2Result: Int
    val part2Benchmark = measureTimeMillis { part2Result = part2(input) }
    println("Part 2 solution: $part2Result, elapses in $part2Benchmark ms")
}
