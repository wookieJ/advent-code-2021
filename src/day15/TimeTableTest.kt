package day15

import kotlin.*

fun dfs(input: Map<String, List<Pair<String, String>>>, node: String): Int {
    if (!input.contains(node)) {
        return 0
    }
    var result = 0
    input[node]!!.forEach { child ->
        val childValue = dfs(input, child.first)
        result += childValue
        if (childValue == 0 && child.second == "r") {
            ++result
        }
    }
    return result
}

fun solution(input: Int): Int {
    val zeros = Integer.toBinaryString(input).split("1")
    val gaps = mutableListOf<Int>()
    for (i in zeros.indices) {
        val previousGap = if (i - 1 >= 0) zeros[i - 1] else null
        val nextGap = if (i + 1 < zeros.size) zeros[i + 1] else null
        if (previousGap != null && nextGap != null) {
            gaps.add(zeros[i].count())
        }
    }
    return if (gaps.size == 0) 0 else gaps.maxOf { it }
}

fun main() {
    println(solution(32))
    println(solution(9))
    println(solution(529))
}

