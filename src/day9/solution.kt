package day9

import checkSolution

fun main() {
    fun Pair<Int, Int>.adjacent() = listOf(
        Pair(this.first - 1, this.second),
        Pair(this.first + 1, this.second),
        Pair(this.first, this.second - 1),
        Pair(this.first, this.second + 1)
    )

    fun createHeightMap(map: List<List<Int>>): MutableMap<Pair<Int, Int>, Int> {
        val heightMap = mutableMapOf<Pair<Int, Int>, Int>()
        map.indices.forEach { row ->
            (0 until map[0].size).forEach { col ->
                heightMap[Pair(row, col)] = map[row][col]
            }
        }
        return heightMap
    }

    fun part1(input: String): Int {
        val map = input.lines().map { line -> line.split("").filter { it.isNotEmpty() }.map { it.toInt() } }
        val heightMap = createHeightMap(map)
        return heightMap.map { (point, value) ->
            value.takeIf {
                point.adjacent()
                    .filter { it.first >= 0 && it.first < map.size && it.second >= 0 && it.second < map[0].size }
                    .map { heightMap[it]!! }
                    .all { it > value }
            } ?: -1
        }.sumOf { it + 1 }
    }

    fun getBasins(point: Pair<Int, Int>, map: List<List<Int>>, points: MutableSet<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val neighbours = point.adjacent()
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
        val heightMap = createHeightMap(map)
        val minimals = heightMap.map { (point, value) ->
            point.takeIf {
                point.adjacent()
                    .filter { it.first >= 0 && it.first < map.size && it.second >= 0 && it.second < map[0].size }
                    .map { heightMap[it]!! }
                    .all { it > value }
            }
        }.filterNotNull().toMutableList()
        val basins = minimals.associateWith {
            getBasins(it, map, mutableSetOf()).size + 1
        }
        return basins.values.sortedByDescending { it }.take(3).reduce { acc, i -> acc * i }
    }

    checkSolution(9, ::part1, ::part2, 15, 1134)
}
