package day9

import checkSolution

fun main() {
    fun part1(input: String): Int {
        val map = input.lines().map { line -> line.split("").filter { it.isNotEmpty() }.map { it.toInt() } }
        var result = 0
        for (row in map.indices) {
            for (col in 0 until map[0].size) {
                val mins = mutableListOf<Int>()
                for (i in -1..1) {
                    for (j in -1..1) {
                        if ((row + i) < map.size && (row + i) >= 0) {
                            if ((col + j) < map[0].size && (col + j) >= 0) {
                                if (!(i == 0 && j == 0)) {
                                    mins.add(map[row + i][col + j])
                                }
                            }
                        }
                    }
                }
                if (mins.all { it >= map[row][col] }) {
                    result += (map[row][col] + 1)
                }
            }
        }
        return result
    }

    fun getBasins(originPoint: Pair<Int, Int>, map: List<List<Int>>, points: MutableSet<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val point = originPoint.copy()
        val neighbours = listOf(
            Pair(point.first - 1, point.second),
            Pair(point.first, point.second - 1),
            Pair(point.first, point.second + 1),
            Pair(point.first + 1, point.second),
        )
        val filteredNeighbours = neighbours.filter { it.first >= 0 && it.first < map.size && it.second >= 0 && it.second < map[0].size }
        val founded = filteredNeighbours.filter { map[it.first][it.second] > map[point.first][point.second] && map[it.first][it.second] != 9 }
        points.addAll(founded)
        if (founded.isNotEmpty()) {
            founded.forEach { points.addAll(getBasins(it, map, points)) }
            return points
        }
        return points
    }

    fun part2(input: String): Int {
        val map = input.lines().map { line -> line.split("").filter { it.isNotEmpty() }.map { it.toInt() } }
        val minimals = mutableListOf<Pair<Int, Int>>()
        for (row in map.indices) {
            for (col in 0 until map[0].size) {
                val neighbours = mutableListOf<Int>()
                for (i in -1..1) {
                    for (j in -1..1) {
                        if ((row + i) < map.size && (row + i) >= 0) {
                            if ((col + j) < map[0].size && (col + j) >= 0) {
                                if (!(i == 0 && j == 0)) {
                                    neighbours.add(map[row + i][col + j])
                                }
                            }
                        }
                    }
                }
                if (neighbours.all { it >= map[row][col] }) {
                    minimals.add(Pair(row, col))
                }
            }
        }
        val basins = minimals.associateWith {
            getBasins(it, map, mutableSetOf()).size + 1
        }.filter { it.value > 0 }
        val largest = basins.values.sortedByDescending { it }.take(3)
        return largest[0] * largest[1] * largest[2]
    }

    checkSolution(9, ::part1, ::part2, 15, 1134)
}
