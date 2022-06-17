package day15

fun main() {
    fun <E : Comparable<E>> merge(left: List<E>, right: List<E>): List<E> {
        return when {
            left.isEmpty() -> right
            right.isEmpty() -> left
            left[0] <= right[0] -> listOf(left[0]) + merge(left.drop(1), right)
            else -> listOf(right[0]) + merge(left, right.drop(1))
        }
    }

    fun <E : Comparable<E>> List<E>.mergeSort(): List<E> {
        if (size <= 1) return this
        return merge(
            subList(0, size / 2),
            subList(size / 2, size).mergeSort()
        )
    }

    fun trivialExamples() {
        check(emptyList<Int>() == emptyList<Int>().mergeSort())
        check(listOf(1) == listOf(1).mergeSort())
    }

    fun sortTwoElementsLists() {
        check(listOf(1, 2) == listOf(1, 2).mergeSort())
        check(listOf(1, 2) == listOf(2, 1).mergeSort())
    }

    fun sortThreeElementsLists() {
        check(listOf(1, 2, 3) == listOf(1, 2, 3).mergeSort())
        check(listOf(1, 2, 3) == listOf(2, 1, 3).mergeSort())
        check(listOf(1, 2, 3) == listOf(3, 2, 1).mergeSort())
        check(listOf(1, 2, 3) == listOf(3, 1, 2).mergeSort())
    }

    fun sortFourElementsLists() {
        check(listOf(1, 2, 3, 4) == listOf(1, 2, 3, 4).mergeSort())
        check(listOf(1, 2, 3, 4) == listOf(1, 3, 2, 4).mergeSort())
        check(listOf(1, 2, 3, 4) == listOf(1, 3, 4, 2).mergeSort())
        check(listOf(1, 2, 3, 4) == listOf(3, 1, 4, 2).mergeSort())
        check(listOf(1, 2, 3, 4) == listOf(3, 4, 1, 2).mergeSort())
        check(listOf(1, 2, 3, 4) == listOf(3, 4, 2, 1).mergeSort())
    }

    fun sumsPairs(list: List<Int>, sum: Int): Set<Set<Int>> {
        return list.flatMapIndexed { idx, n ->
            (idx + 1 until list.size).filter { it + n == sum }.map { setOf(it, n) }
        }.toSet()
    }

    fun List<Pair<Int, Int>>.createAdjacentList(): Map<Int, List<Int>> = groupingBy { it.first }.fold(listOf()) { acc, element -> acc + element.second }

    fun fibonacci(n: Int): Int {
        val results = Array(n + 1) { 0 }
        results[1] = 1
        results[2] = 1
        for (i in 3..n) {
            results[i] = results[i - 1] + results[i - 2]
        }
        return results[n]
    }

    fun bfs(nodes: List<List<Int>>, start: Int) {
        print("bfs(): ")
        val visited = BooleanArray(nodes.size) { false }
        val queue: MutableList<Int> = mutableListOf(start)
        while (queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            if (!visited[node]) {
                print(node)
                nodes[node].forEach {
                    queue.add(it)
                }
                visited[node] = true
            }
        }
        println("")
    }

    fun helper(nodes: List<List<Int>>, visited: BooleanArray, node: Int) {
        visited[node] = true
        print(node)
        nodes[node].forEach {
            if (!visited[it]) {
                helper(nodes, visited, it)
            }
        }
    }

    fun dfs(nodes: List<List<Int>>, start: Int) {
        print("dfs(): ")
        val visited = BooleanArray(nodes.size) { false }
        helper(nodes, visited, start)
        println("")
    }

    fun test() {
        val nodes = listOf(
            Pair(1, 2),
            Pair(1, 3),
            Pair(1, 4),
            Pair(2, 4),
            Pair(2, 5),
            Pair(3, 6),
            Pair(4, 3),
            Pair(4, 6),
            Pair(4, 7),
            Pair(5, 4),
            Pair(5, 7),
            Pair(7, 6)
        )
        val nodesLists = nodes.map { listOf(it.first,it.second) }
        val adjacentList = nodes.createAdjacentList()
        println(fibonacci(3))
        Array(5) { it * 2 }.forEach { print("$it ") }
        println()
        println(adjacentList)
        bfs(nodesLists, 1)
        dfs(nodesLists, 1)
    }

    test()
}
