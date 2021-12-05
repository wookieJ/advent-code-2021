package day5

import checkSolution
import kotlin.math.abs

fun main() {
    fun part1(input: String): Int {
        val regex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
        val occurrences = mutableMapOf<Pair<Int, Int>, Int>()
        input.lines().filter { it.isNotEmpty() }.forEach { line ->
            val (x1s, y1s, x2s, y2s) = regex.find(line)?.destructured ?: error("Cannot deserialize")
            val x1 = x1s.toInt()
            val y1 = y1s.toInt()
            val x2 = x2s.toInt()
            val y2 = y2s.toInt()
            var bottom = y2
            var up = y1

            if (x1 == x2) {
                if (y1 < y2) {
                    bottom = y1; up = y2
                }
                for (i in bottom..up) {
                    occurrences.putIfAbsent(Pair(x1, i), 0)
                    occurrences.compute(Pair(x1, i)) { _, v -> v!!.plus(1) }
                }
            } else if (y1 == y2) {
                if (x1 < x2) {
                    bottom = x1; up = x2
                } else {
                    bottom = x2; up = x1
                }
                for (i in bottom..up) {
                    occurrences.putIfAbsent(Pair(i, y1), 0)
                    occurrences.compute(Pair(i, y1)) { _, v -> v!!.plus(1) }
                }
            }
        }
        return occurrences.filter { it.value > 1 }.count()
    }

    fun part2(input: String): Int {
        val regex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
        val occurrences = mutableMapOf<Pair<Int, Int>, Int>()
        input.lines().filter { it.isNotEmpty() }.forEach { line ->
            val (x1s, y1s, x2s, y2s) = regex.find(line)?.destructured ?: error("Cannot deserialize")
            val x1 = x1s.toInt()
            val y1 = y1s.toInt()
            val x2 = x2s.toInt()
            val y2 = y2s.toInt()
            var bottom = y2
            var up = y1

            if (x1 == x2) {
                if (y1 < y2) {
                    bottom = y1; up = y2
                }
                for (i in bottom..up) {
                    occurrences.putIfAbsent(Pair(x1, i), 0)
                    occurrences.compute(Pair(x1, i)) { _, v -> v!!.plus(1) }
                }

            } else if (y1 == y2) {
                if (x1 < x2) {
                    bottom = x1; up = x2
                } else {
                    bottom = x2; up = x1
                }
                for (i in bottom..up) {
                    occurrences.putIfAbsent(Pair(i, y1), 0)
                    occurrences.compute(Pair(i, y1)) { _, v -> v!!.plus(1) }
                }
            } else if (x2 > x1) { // to the right
                if (y2 > y1) { // go down
                    for (i in 0..abs(x2 - x1)) {
                        val pair = Pair(x1 + i, y1 + i)
                        occurrences.putIfAbsent(pair, 0)
                        occurrences.compute(pair) { _, v -> v!!.plus(1) }
                    }
                } else { // down
                    for (i in 0..abs(x2 - x1)) {
                        val pair = Pair(x1 + i, y1 - i)
                        occurrences.putIfAbsent(pair, 0)
                        occurrences.compute(pair) { _, v -> v!!.plus(1) }
                    }
                }
            } else { // to the left
                if (y2 > y1) { // go down
                    for (i in 0..abs(x2 - x1)) {
                        val pair = Pair(x1 - i, y1 + i)
                        occurrences.putIfAbsent(pair, 0)
                        occurrences.compute(pair) { _, v -> v!!.plus(1) }
                    }
                } else { // up
                    for (i in 0..abs(x2 - x1)) {
                        val pair = Pair(x1 - i, y1 - i)
                        occurrences.putIfAbsent(pair, 0)
                        occurrences.compute(pair) { _, v -> v!!.plus(1) }
                    }
                }
            }
        }
        return occurrences.filter { it.value > 1 }.count()
    }

    checkSolution(5, ::part1, ::part2, 5, 12)
}
