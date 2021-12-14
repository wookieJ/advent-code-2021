package day14

import checkSolution

typealias SPair = Pair<String, String>

fun main() {
    fun solve(input: String, times: Int): Long {
        val template: String = input.substringBefore("\n\n").trim()
        val insertions: Map<SPair, String> = input.substringAfter("\n\n").trim().lines().associate {
            val pair = it.substringBefore(" -> ").split("").filter { p -> p.isNotEmpty() }
            val value = it.substringAfter(" -> ").trim()
            SPair(pair[0], pair[1]) to value
        }
        val pairs: List<SPair> = template.toList().windowed(2).map { Pair(it[0].toString(), it[1].toString()) }
        var commons: Map<SPair, Long> = pairs.groupingBy { it }.eachCount().mapValues { it.value.toLong() }
        repeat(times) {
            val nextCommons = mutableMapOf<Pair<String, String>, Long>()
            commons.forEach { (com, value) ->
                val middleValue = insertions[com]!!
                nextCommons.putIfAbsent(Pair(com.first, middleValue), 0L)
                nextCommons.putIfAbsent(Pair(middleValue, com.second), 0L)
                nextCommons.merge(Pair(com.first, middleValue), value, Long::plus)
                nextCommons.merge(Pair(middleValue, com.second), value, Long::plus)
            }
            commons = nextCommons
        }
        val polymerLetterDistribution: MutableMap<String, Long> = commons.entries
            .groupingBy { it.key.first }
            .fold(0L) { acc, entry -> acc + entry.value }
            .toMutableMap()
        polymerLetterDistribution.compute(template.last().toString()) { _, value -> value?.plus(1) }
        val valuesSorted = polymerLetterDistribution.values.sorted()
        return valuesSorted.last() - valuesSorted.first()
    }

    fun part1(input: String): Long {
        return solve(input, 10)
    }

    fun part2(input: String): Long {
        return solve(input, 40)
    }

    checkSolution(14, ::part1, ::part2, 1588L, 2188189693529L)
}
