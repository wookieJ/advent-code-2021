package day15

class TimeTable<K, V> {
    private val tableHash: MutableMap<Pair<K, Long>, V> = mutableMapOf()
    private var seconds: Long = 0

    /**
     * Get the value for k at time t
     */
    fun get(key: K, time: Long): V? {
        return tableHash[Pair(key, time)]
    }

    /**
     * Insert k, v at time() - unix time
     */
    fun put(key: K, value: V): V? {
        val unixTime = getUnixTime()
        val currentValue = tableHash[Pair(key, unixTime)]
        tableHash[Pair(key, unixTime)] = value
        return currentValue
    }

    private fun getUnixTime(): Long {
//        System.currentTimeMillis() / 1000
        return seconds++
    }
}
