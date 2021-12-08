package day8

import checkSolution

fun main() {
    fun part1(input: String): Int {
        return input.lines().filter { it.isNotEmpty() }.sumOf { line ->
            line.substringAfter("|").trim().split(" ")
                .map { it.length }
                .filter { it in 2..4 || it == 7 }
                .count()
        }
    }

    fun part2(input: String): Int {
        var result = 0
        val digitsLen = mapOf(
            2 to 1,
            4 to 4,
            3 to 7,
            7 to 8
        )
        input.lines().filter { it.isNotEmpty() }.forEach { line ->
            val known = mutableMapOf<Int, Set<String>>()
            var leftSide = line.substringBefore("|").trim().split(" ")
            val uniques = leftSide.filter { it.length in digitsLen.keys }
            uniques.forEach { digit ->
                known.putIfAbsent(digitsLen[digit.length]!!, digit.split("").filter { it.isNotEmpty() }.toSet())
            }
            known[9] = leftSide.filter {
                it.length == 6 && it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[4]!!)
            }.map { it.split("").filter { t -> t.isNotEmpty() } }.first { it.isNotEmpty() }.toSet()
            leftSide = leftSide.filterNot { it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[9]!!) }.toMutableList()
            known[0] = leftSide.filter {
                it.length == 6 && it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[7]!!)
            }.map { it.split("").filter { t -> t.isNotEmpty() } }.first { it.isNotEmpty() }.toSet()
            leftSide = leftSide.filterNot { it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[0]!!) }.toMutableList()
            known[6] = leftSide.first { it.length == 6 }.split("").filter { it.isNotEmpty() }.toSet()
            leftSide = leftSide.filterNot { it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[6]!!) }.toMutableList()
            known[5] = known[6]!!.intersect(known[9]!!)
            leftSide = leftSide.filterNot { it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[5]!!) }.toMutableList()
            known[3] = leftSide.filter {
                it.length == 5 && it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[7]!!)
            }.map { it.split("").filter { t -> t.isNotEmpty() } }.first { it.isNotEmpty() }.toSet()
            leftSide = leftSide.filterNot { it.split("").filter { y -> y.isNotEmpty() }.containsAll(known[3]!!) }.toMutableList()
            known[2] = leftSide.first { it.length == 5 }.split("").filter { it.isNotEmpty() }.toSet()

            val rightDecoded = line.substringAfterLast("|").trim().split(" ")
                .map { digit ->
                    val number = known.entries.find {
                        digit.length == it.value.size && digit.split("").filter { d -> d.isNotEmpty() }.containsAll(it.value)
                    }
                    number!!.key.toString()
                }
            result += rightDecoded.joinToString("").toInt()
        }
        return result
    }

    checkSolution(8, ::part1, ::part2, 26, 61229)
}
