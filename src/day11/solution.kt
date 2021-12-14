package day11

import checkSolution

fun main() {
    fun cretePowerMap(input: String): MutableMap<Pair<Int, Int>, Int> {
        val powerMap = mutableMapOf<Pair<Int, Int>, Int>()
        input.lines().forEachIndexed { row, line ->
            line.forEachIndexed { col, it -> powerMap[Pair(col, row)] = it.toString().toInt() }
        }
        return powerMap
    }

    fun flashPoint(point: Pair<Int, Int>, energies: MutableMap<Pair<Int, Int>, Int>): Pair<MutableMap<Pair<Int, Int>, Int>, Int> {
        var flashesCopy = 0
        var energiesCopy = HashMap(energies).toMutableMap()
        val neighbours = point.adjacent(10, 10)
        neighbours.forEach { neighbour ->
            if (energiesCopy[neighbour]!! in 1..9) {
                energiesCopy[neighbour] = energiesCopy[neighbour]!! + 1
                if (energiesCopy[neighbour]!! > 9) {
                    val newEnergies = flashPoint(neighbour, energiesCopy)
                    energiesCopy = newEnergies.first
                    flashesCopy += newEnergies.second
                }
            }
        }
        energiesCopy[point] = 0
        flashesCopy++
        return Pair(energiesCopy, flashesCopy)
    }

    fun part1(input: String): Int {
        var energies = cretePowerMap(input)
        var flashes = 0
        repeat(100) {
            energies = energies.mapValues { it.value + 1 }.toMutableMap()
            energies.forEach { (point, energy) ->
                if (energy > 9) {
                    val newEnergies = flashPoint(point, energies)
                    energies = newEnergies.first
                    flashes += newEnergies.second
                }
            }
        }
        return flashes
    }

    fun part2(input: String): Int {
        var energies = cretePowerMap(input)
        var i = 0
        while (i > -1) {
            if (energies.all { it.value == 0 }) {
                return i
            }
            energies = energies.mapValues { it.value + 1 }.toMutableMap()
            energies.forEach { (point, energy) ->
                if (energy > 9) {
                    val newEnergies = flashPoint(point, energies)
                    energies = newEnergies.first
                }
            }
            i++
        }
        return -1
    }

    checkSolution(11, ::part1, ::part2, 1656, 195)
}

private fun Pair<Int, Int>.adjacent(rowsNumber: Int, colsNumber: Int): List<Pair<Int, Int>> {
    return (-1..1).flatMap { col ->
        (-1..1).map { row -> Pair(this.first + col, this.second + row) }.filter { point ->
            point.first in 0 until colsNumber &&
                point.second in 0 until rowsNumber &&
                !(point.first == this.first && point.second == this.second)
        }
    }
}

