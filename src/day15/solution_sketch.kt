package day15

import checkSolution
import kotlin.math.sqrt

typealias IntPair = Pair<Int, Int>

fun main() {
    fun creteRiskMap(input: String): Map<IntPair, Int> {
        val riskMap = mutableMapOf<IntPair, Int>()
        input.lines().forEachIndexed { row, line ->
            line.forEachIndexed { col, it -> riskMap[Pair(col, row)] = it.toString().toInt() }
        }
        return riskMap
    }

    fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(): Set<T> = this
        .map { (a, b) -> listOf(a, b) }
        .flatten()
        .toSet()

    fun <T> List<Pair<T, T>>.getUniqueValuesFromPairs(predicate: (T) -> Boolean): Set<T> = this
        .map { (a, b) -> listOf(a, b) }
        .flatten()
        .filter(predicate)
        .toSet()

    data class Graph<T>(
        val vertices: Set<T>,
        val edges: Map<T, Set<T>>,
        val weights: Map<Pair<T, T>, Int>
    ) {
        constructor(weights: Map<Pair<T, T>, Int>) : this(
            vertices = weights.keys.toList().getUniqueValuesFromPairs(),
            edges = weights.keys
                .groupBy { it.first }
                .mapValues { it.value.getUniqueValuesFromPairs { x -> x !== it.key } }
                .withDefault { emptySet() },
            weights = weights
        )
    }

    fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
        val vertices: MutableSet<T> = mutableSetOf() // a subset of vertices, for which we know the true distance
        val delta = graph.vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0
        val previous: MutableMap<T, T?> = graph.vertices.associateWith { null }.toMutableMap()
        while (vertices != graph.vertices) {
            val v: T = delta
                .filter { !vertices.contains(it.key) }
                .minByOrNull { it.value }!!
                .key

            graph.edges.getValue(v).minus(vertices).forEach { neighbor ->
                val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

                if (newPath < delta.getValue(neighbor)) {
                    delta[neighbor] = newPath
                    previous[neighbor] = v
                }
            }

            vertices.add(v)
        }
        return previous.toMap()
    }

    fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
        fun pathTo(start: T, end: T): List<T> {
            if (shortestPathTree[end] == null) return listOf(end)
            return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
        }

        return pathTo(start, end)
    }

    fun IntPair.adjacent(rowsNumber: Int, colsNumber: Int): List<IntPair> {
        return listOf(
            Pair(this.first - 1, this.second),
            Pair(this.first + 1, this.second),
            Pair(this.first, this.second - 1),
            Pair(this.first, this.second + 1)
        ).filter { point -> point.first in 0 until colsNumber && point.second in 0 until rowsNumber }
    }

    fun createWeights(riskMap: Map<IntPair, Int>): Map<Pair<IntPair, IntPair>, Int> {
        val mapSize = sqrt(riskMap.size.toDouble()).toInt()
        val weights = mutableMapOf<Pair<IntPair, IntPair>, Int>()
        riskMap.forEach { pair ->
            val neighbours = pair.key.adjacent(mapSize, mapSize)
            neighbours.forEach { neighbour ->
                weights[Pair(pair.key, neighbour)] = riskMap[neighbour]!!
            }
        }
        return weights
    }

    fun part1(input: String): Int {
        val riskMap = creteRiskMap(input)
        val weights = createWeights(riskMap)
        val start = Pair(0, 0)
        val mapSize = input.lines().size
        val end = Pair(mapSize - 1, mapSize - 1)
        val graph = Graph(weights)
        val shortestPathTree = dijkstra(graph, start)
        val shortestPath = shortestPath(shortestPathTree, start, end)
        return shortestPath.drop(1).sumOf { riskMap[it]!! }
    }

    fun creteRiskMapBig(input: String): Map<Pair<Int, Int>, Int> {
        val riskMap = creteRiskMap(input).toMutableMap()
        val copyResult = mutableMapOf<Pair<Int, Int>, Int>()
        val mapSize = (sqrt(riskMap.size.toDouble())).toInt()
        repeat(5) { col ->
            repeat(5) { row ->
                riskMap.forEach { risk ->
                    val newX = row * mapSize + risk.key.first
                    val newY = col * mapSize + risk.key.second
                    val newPair = Pair(newX, newY)
                    val newValue = if (col == 0 && row == 0) {
                        risk.value
                    } else {
                        var newVal = risk.value
                        repeat(col + row) {
                            newVal++
                            if (newVal > 9) {
                                newVal = 1
                            }
                        }
                        newVal
                    }
                    copyResult[newPair] = newValue
                }
            }
        }
        return copyResult
    }

    fun printMap(riskMap: Map<Pair<Int, Int>, Int>) {
        val mapSize = sqrt(riskMap.size.toDouble()).toInt()
        (0 until mapSize).forEach { y ->
            if (y % (mapSize / 5) == 0) {
                println("_________________________________________________________")
            }
            (0 until mapSize).forEach { x ->
                if (x % (mapSize / 5) == 0) {
                    print("|")
                }
                print(riskMap[Pair(x, y)])
            }
            println()
        }
        println()
    }

    fun part2(input: String): Int {
        val riskMap = creteRiskMapBig(input)
//        printMap(riskMap)
        println("Have risk map")
        val weights = createWeights(riskMap)
        println("Have weights")
        val start = Pair(0, 0)
        val mapSize = sqrt(riskMap.size.toDouble()).toInt()
        val end = Pair(mapSize - 1, mapSize - 1)
        val graph = Graph(weights)
        println("Have graph")
        val shortestPathTree = dijkstra(graph, start)
        println("Have shortestPathTree")
        val shortestPath = shortestPath(shortestPathTree, start, end)
        println("Have shortestPath")
        return shortestPath.drop(1).sumOf { riskMap[it]!! }
    }

    checkSolution(15, ::part1, ::part2, 40, 315)
}
