package day12

import checkSolution
import startsWithLowerCase

fun main() {
    fun traverseGraph(currentNode: String, graph: MutableMap<String, MutableList<String>>, alreadySeen: MutableSet<String>): Int {
        if (currentNode == "end") {
            return 1
        }
        if (currentNode in alreadySeen && currentNode.startsWithLowerCase()) {
            return 0
        }
        val setCopy = alreadySeen.toMutableSet()
        setCopy.add(currentNode)
        return graph[currentNode]!!.sumOf { traverseGraph(it, graph, setCopy) }
    }

    fun traverseGraph2(currentNode: String, graph: MutableMap<String, MutableList<String>>, alreadySeen: MutableSet<String>, duplicate: String?): Int {
        if (currentNode == "start" && alreadySeen.size > 0) {
            return 0
        }
        if (currentNode == "end") {
            return 1
        }
        var duplicateCopy = duplicate
        if (currentNode in alreadySeen && currentNode.startsWithLowerCase()) {
            if (duplicateCopy == null) {
                duplicateCopy = currentNode
            } else {
                return 0
            }
        }
        val setCopy = alreadySeen.toMutableSet()
        setCopy.add(currentNode)
        return graph[currentNode]!!.sumOf { traverseGraph2(it, graph, setCopy, duplicateCopy) }
    }

    fun createGraph(input: String): MutableMap<String, MutableList<String>> {
        val graph = mutableMapOf<String, MutableList<String>>()
        input.lines().forEach { line ->
            val a = line.substringBefore("-")
            val b = line.substringAfter("-")
            graph.putIfAbsent(a, mutableListOf())
            graph.putIfAbsent(b, mutableListOf())
            graph[a]!!.add(b)
            graph[b]!!.add(a)
        }
        return graph
    }

    fun part1(input: String): Int {
        val graph = createGraph(input)
        return traverseGraph("start", graph, mutableSetOf())
    }

    fun part2(input: String): Int {
        val graph = createGraph(input)
        return traverseGraph2("start", graph, mutableSetOf(), null)
    }

    checkSolution(12, ::part1, ::part2, 10, 36)
}
