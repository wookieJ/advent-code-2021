package day3

import checkSolution

fun main() {
    fun part1(input: String): Int {
        val inputLines = input.lines()
        val gamma = MutableList<LinkedHashMap<Int, Int>>(inputLines[0].length) { linkedMapOf() }
        inputLines.forEach { line ->
            line.toList().forEachIndexed { i, value ->
                val mostCommonBitValue = Character.getNumericValue(value)
                gamma[i].putIfAbsent(mostCommonBitValue, 0)
                gamma[i].compute(mostCommonBitValue) { _, v -> v!!.plus(1) }
            }
        }
        val gammaValue = gamma.map { if (it[0]!! > it[1]!!) 0 else 1 }.joinToString("")
        val epsilonValue = gammaValue.split("").filter { it != "" }.map { it.toInt().xor(1) }.joinToString("")
        return gammaValue.toInt(2) * epsilonValue.toInt(2)
    }

    fun part2(input: String): Int {
        val inputLines = input.lines()
        var inputCopy = input.lines().toMutableList()
        var linesGamma: MutableList<MutableList<Char>>
        linesGamma = inputLines.map { it.toMutableList() }.toMutableList()
        for (col in 0 until inputLines[0].length) {
            if (linesGamma.size <= 1) {
                break
            }
            var col0 = 0
            var col1 = 0
            for (row in 0 until linesGamma.size) {
                if (Character.getNumericValue(linesGamma[row][col]) == 0) {
                    col0++
                } else {
                    col1++
                }
            }
            val prefix = if (col0 > col1) "0" else "1"
            inputCopy = inputCopy.filter { it[col].toString() == prefix }.toMutableList()
            linesGamma = inputCopy.map { it.toMutableList() }.toMutableList()
        }
        var gammaS = ""
        linesGamma[0].toList().forEach {
            gammaS += it.toString()
        }
        val gammaValue = gammaS.toInt(2)
        inputCopy = inputLines.toMutableList()
        linesGamma = inputLines.map { it.toMutableList() }.toMutableList()
        for (col in 0 until inputLines[0].length) {
            if (linesGamma.size <= 1) {
                break
            }
            var col0 = 0
            var col1 = 0
            for (row in 0 until linesGamma.size) {
                if (Character.getNumericValue(linesGamma[row][col]) == 0) {
                    col0++
                } else {
                    col1++
                }
            }
            val prefix = if (col0 > col1) "0" else "1"
            inputCopy = inputCopy.filter { it[col].toString() == prefix }.toMutableList()
            linesGamma = inputCopy.map { it.toMutableList() }.toMutableList()
        }
        gammaS = ""
        linesGamma[0].toList().forEach {
            gammaS += it.toString()
        }
        val epsilonV = gammaS.toInt(2)
        return epsilonV * gammaValue
    }
    checkSolution(3, ::part1, ::part2, 198, 230)
}
