package day6

import checkSolution

fun main() {
    fun checkFishNumber(fishList: MutableMap<Int, Long>, days: Int): Long {
        for (k in 1..days) {
            val zero = fishList.getOrDefault(0, 0)
            val seven = fishList.getOrDefault(7, 0)
            for (i in 0..8) {
                when (i) {
                    6 -> fishList[i] = zero + seven
                    8 -> fishList[i] = zero
                    else -> fishList[i] = fishList.getOrDefault(i + 1, 0)
                }
            }
        }
        return fishList.values.sum()
    }

    fun part1(input: String): Long {
        val fishList = input.split(",").map { it.toInt() }.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
        return checkFishNumber(fishList, 80)
    }

    fun part2(input: String): Long {
        val fishList = input.split(",").map { it.toInt() }.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
        return checkFishNumber(fishList, 256)
    }

    checkSolution(6, ::part1, ::part2, 5934L, 26984457539L)
}
