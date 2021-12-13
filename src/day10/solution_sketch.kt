package day10

import checkSolution

fun main() {
    val closeToOpen = mapOf(
        ")" to "(",
        "]" to "[",
        "}" to "{",
        ">" to "<"
    )
    val openToClose = mapOf(
        "(" to ")",
        "[" to "]",
        "{" to "}",
        "<" to ">"
    )

    fun part1(input: String): Int {
        val scores = mapOf(
            ")" to 3,
            "]" to 57,
            "}" to 1197,
            ">" to 25137
        )
        var result = 0
        input.lines().forEach { line ->
            val currentOpens = mutableListOf<String>()
            var end = false
            line.split("").filter { it.isNotEmpty() }.forEach {
                if (!end) {
                    if (closeToOpen.values.contains(it)) {
                        currentOpens.add(it)
                    } else {
                        if (closeToOpen[it]!! != currentOpens.last()) {
                            result += scores[it]!!
                            end = true
                        } else {
                            currentOpens.removeLast()
                        }
                    }
                }
            }
        }
        return result
    }

    fun part2(input: String): Long {
        val scores = mapOf(
            ")" to 1L,
            "]" to 2L,
            "}" to 3L,
            ">" to 4L
        )
        val result = input.lines().map { line ->
            val currentOpens = mutableListOf<String>()
            var skipLine = false
            line.split("").filter { it.isNotEmpty() }.forEach {
                if (closeToOpen.values.contains(it)) {
                    currentOpens.add(it)
                } else {
                    if (closeToOpen[it]!! == currentOpens.last()) { // correct bracket
                        currentOpens.removeLast()
                    } else {
                        skipLine = true
                    }
                }
            }
            if (!skipLine) {
                currentOpens.reversed()
                    .map { scores[openToClose[it]!!]!! }
                    .reduce { acc, i -> acc * 5L + i }
            } else {
                0L
            }
        }.filter { it > 0L }.sorted()
        return result[result.size / 2]
    }

    checkSolution(10, ::part1, ::part2, 26397, 288957L)
}
