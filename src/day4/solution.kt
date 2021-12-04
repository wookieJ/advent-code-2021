package day4

import checkSolution
import transpose

fun main() {
    fun anyTableWins(listOfTables: List<List<List<Int>>>, marks: MutableMap<Int, MutableList<Int>>): Int {
        listOfTables.forEachIndexed { tableIdx, table ->
            val transposedTable = transpose(table)
            for (i in table.indices) {
                if (marks[tableIdx]!!.containsAll(table[i]) || marks[tableIdx]!!.containsAll(transposedTable[i])) {
                    return table.flatten().filter { !marks[tableIdx]!!.contains(it) }.sum()
                }
            }
        }
        return -1
    }

    fun part1(input: String): Int {
        val numbers = input.substringBefore("\n")
        val tables = input.substringAfter("\n")
        val listOfTables = tables.split("\n\n")
            .map { table ->
                table.split("\n")
                    .filter { it.isNotEmpty() }
                    .map { line -> line.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }
            }
        val marks = mutableMapOf<Int, MutableList<Int>>()
        numbers.split(",").map { it.toInt() }.forEach { number ->
            listOfTables.forEachIndexed { tableIdx, table ->
                table.forEach { row ->
                    marks.putIfAbsent(tableIdx, mutableListOf())
                    if (row.contains(number)) {
                        marks[tableIdx]!!.add(number)
                    }
                }
            }
            val result: Int = anyTableWins(listOfTables, marks)
            if (result > -1) {
                return result * number
            }
        }
        return -1
    }

    fun part2(input: String): Int {
        val numbers = input.substringBefore("\n")
        val tables = input.substringAfter("\n")
        val listOfTables = tables.split("\n\n")
            .map { tab ->
                tab.split("\n")
                    .filter { it.isNotEmpty() }
                    .map { line -> line.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }
            }
        val marks = mutableMapOf<Int, MutableList<Int>>()
        val wonTables = mutableListOf<Int>()
        numbers.split(",").map { it.toInt() }.forEach { number ->
            listOfTables.forEachIndexed { tableIdx, table ->
                table.forEach { row ->
                    marks.putIfAbsent(tableIdx, mutableListOf())
                    if (row.contains(number)) {
                        marks[tableIdx]!!.add(number)
                    }
                }
            }
            listOfTables.forEachIndexed { tableIdx, table ->
                if (tableIdx !in wonTables) {
                    val transposedTable = transpose(table)
                    for (i in table.indices) {
                        if (marks[tableIdx]!!.containsAll(table[i]) || marks[tableIdx]!!.containsAll(transposedTable[i])) {
                            if (wonTables.size < listOfTables.size - 1) {
                                wonTables.add(tableIdx)
                                wonTables.forEach { marks[it] = mutableListOf() }
                            } else {
                                return table.flatten().filter { it !in marks[tableIdx]!! }.sum() * number
                            }
                        }
                    }
                }
            }
        }
        return -1
    }
    checkSolution(4, ::part1, ::part2, 4512, 1924)
}
