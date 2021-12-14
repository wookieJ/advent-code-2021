package day12

import checkSolution

fun main() {
    fun traverseGraph(startNode: String, graph: MutableMap<String, MutableList<String>>, alreadySeen: MutableSet<String>): Int {
        if (startNode == "end") {
            return 1
        }
        if (startNode in alreadySeen && startNode.toList().first().isLowerCase()) {
            return 0
        }
        val setCopy = alreadySeen.toMutableSet()
        setCopy.add(startNode)
        var result = 0
        graph[startNode]!!.forEach {
            result += traverseGraph(it, graph, setCopy)
        }
        return result
    }

    fun traverseGraph2(startNode: String, graph: MutableMap<String, MutableList<String>>, alreadySeen: MutableSet<String>, duplicate: String?): Int {
        if (startNode == "end") {
            return 1
        }
        if (startNode == "start" && alreadySeen.size > 0) {
            return 0
        }
        var duplicateCopy = duplicate
        if (startNode in alreadySeen && startNode.toList().first().isLowerCase()) {
            if (duplicate == null) {
                duplicateCopy = startNode
            } else {
                return 0
            }
        }
        val setCopy = alreadySeen.toMutableSet()
        setCopy.add(startNode)
        var result = 0
        graph[startNode]!!.forEach {
            result += traverseGraph2(it, graph, setCopy, duplicateCopy)
        }
        return result
    }

    fun part1(input: String): Int {
        val graph = mutableMapOf<String, MutableList<String>>()
        input.lines().forEach { line ->
            val a = line.substringBefore("-")
            val b = line.substringAfter("-")
            graph.putIfAbsent(a, mutableListOf())
            graph.putIfAbsent(b, mutableListOf())
            graph[a]!!.add(b)
            graph[b]!!.add(a)
        }
        return traverseGraph("start", graph, mutableSetOf())
    }

    fun part2(input: String): Int {
        val graph = mutableMapOf<String, MutableList<String>>()
        input.lines().forEach { line ->
            val a = line.substringBefore("-")
            val b = line.substringAfter("-")
            graph.putIfAbsent(a, mutableListOf())
            graph.putIfAbsent(b, mutableListOf())
            graph[a]!!.add(b)
            graph[b]!!.add(a)
        }
        return traverseGraph2("start", graph, mutableSetOf(), null)
    }

    checkSolution(12, ::part1, ::part2, 10, 36)
}
