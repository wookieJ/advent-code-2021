package day14

import checkSolution
import java.math.BigInteger

fun main() {
    fun part1(input: String): Int {
        var template = input.substringBefore("\n\n").trim()
        val instruction = input.substringAfter("\n\n").trim().lines().associate {
            val pair = it.substringBefore(" -> ").split("").filter { p -> p.isNotEmpty() }
            val value = it.substringAfter(" -> ").trim()
            Pair(pair[0], pair[1]) to value
        }
        repeat(10) {
            val pairs = template.split("").filter { s -> s.isNotEmpty() }.windowed(2).map { Pair(it[0], it[1]) }
            var temporaryResult = pairs[0].first
            pairs.forEach {
                val middleValue = instruction[it]
                temporaryResult += middleValue + it.second
            }
            template = temporaryResult
        }
        val commons = template.split("").filter { it.isNotEmpty() }.groupingBy { it }.eachCount()
        val max = commons.maxByOrNull { it.value }!!.value
        val min = commons.minByOrNull { it.value }!!.value
        return max - min
    }

    fun part2(input: String): Long {
        val template = StringBuilder(input.substringBefore("\n\n").trim())
        val instruction = input.substringAfter("\n\n").trim().lines().associate {
            val pair = it.substringBefore(" -> ").split("").filter { p -> p.isNotEmpty() }
            val value = it.substringAfter(" -> ").trim()
            Pair(pair[0], pair[1]) to value
        }
        val pairs = template.toList().windowed(2).map { Pair(it[0].toString(), it[1].toString()) }
        var commons = pairs.groupingBy { it }.eachCount().mapValues { it.value.toBigInteger() }
        repeat(40) {
            val nextCommons = mutableMapOf<Pair<String, String>, BigInteger>()
            commons.forEach { (com, value) ->
                val middleValue = instruction[com]!!
                nextCommons.putIfAbsent(Pair(com.first, middleValue), BigInteger.ZERO)
                nextCommons.putIfAbsent(Pair(middleValue, com.second), BigInteger.ZERO)
                nextCommons[Pair(com.first, middleValue)] = (nextCommons[Pair(com.first, middleValue)]!! + value)
                nextCommons[Pair(middleValue, com.second)] = (nextCommons[Pair(middleValue, com.second)]!! + value)
            }
            commons = nextCommons
        }
        val realCommons = mutableMapOf<String, BigInteger>()
        commons.forEach { (k, v) ->
            realCommons.putIfAbsent(k.first, BigInteger.ZERO)
            realCommons[k.first] = (realCommons[k.first]!! + v)
        }
        realCommons[template.last().toString()] = (realCommons[template.last().toString()]!!.plus(BigInteger.ONE))
        val singles = realCommons.values.sorted()
        return (singles.last() - singles.first()).toLong()
    }

    checkSolution(14, ::part1, ::part2, 1588, 2188189693529L)
}
