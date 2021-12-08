package day8

import checkSolution

fun main() {
    fun part1(input: String): Int {
        return input.lines().filter { it.isNotEmpty() }.sumOf { line ->
            line.substringAfter("|").trim().split(" ")
                .map { it.length }
                .filter { it in (2..4).plus(7) }
                .count()
        }
    }

    fun part2(input: String): Int {
        return input.lines().map { line ->
            line.split("|")
                .filter { it.isNotEmpty() }
                .map { part -> part.trim().split(" ").map { it.toSet() } }
                .let { (codes, digits) -> Pair(codes, digits) }
        }.sumOf { (codes, digits) ->
            val known = codes
                .sortedByDescending { if (it.size in (2..4).plus(7)) 8 else it.size }
                .fold(HashMap<Int, Set<Char>>(10)) { known, code ->
                    known[when {
                        code.size == 2 -> 1
                        code.size == 3 -> 7
                        code.size == 4 -> 4
                        code.size == 7 -> 8
                        code.size == 6 && code.containsAll(known[4]!!) -> 9
                        code.size == 6 && code.containsAll(known[7]!!) -> 0
                        code.size == 6 -> 6
                        code.size == 5 && known[6]!!.containsAll(code) -> 5
                        code.size == 5 && known[9]!!.containsAll(code) -> 3
                        else -> 2
                    }] = code
                    known
                }.entries.associateBy({ it.value }) { it.key }
            digits.map { known[it] }.joinToString("").toInt()
        }
    }

    checkSolution(8, ::part1, ::part2, 26, 61229)
}
