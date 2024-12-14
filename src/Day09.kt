fun main() {
    fun expand(input: String): MutableList<Int?> {
        val output = mutableListOf<Int?>()
        var e = false
        var cur = 0
        input.forEach {
            val len = it.toString().toInt()
            val put = if (e) null else cur
            repeat(len) {
                output.add(put)
            }

            if (!e) cur++
            e = !e
        }
        return output
    }

    fun isCompact(disk: List<Int?>): Boolean =
        disk.indexOfFirst { it == null } > disk.indexOfLast { it != null }

    fun shift(disk: MutableList<Int?>) {
        val to = disk.indexOfFirst { it == null }
        val from = disk.indexOfLast { it != null }

        disk[to] = disk[from]
        disk[from] = null
    }

    fun checksum(disk: List<Int?>): Long {
        var output = 0L
        disk.forEachIndexed { idx, it ->
            output += (it ?: 0) * idx
        }

        return output
    }

    fun part1(input: List<String>): String {
        var disk = expand(input[0])

        while (!isCompact(disk)) {
            shift(disk)
        }

        return checksum(disk).toString()
    }

    fun findSpaceBefore(disk: List<Int?>, size: Int, idx: Int): Int? {
        var count = 0
        var ptr = 0
        while (ptr < idx) {
            if (disk[ptr] == null) {
                count++
            } else {
                count = 0
            }

            ptr++

            if (count == size) {
                return ptr - size
            }
        }

        return null
    }

    fun part2(input: List<String>): String {
        var disk = expand(input[0])

        val blocks = sequence<Pair<Int, List<Int>>> {
            var ptr = disk.lastIndex
            var cur: MutableList<Int>? = null
            while (ptr > 0) {
                while (disk[ptr] == null) {
                    ptr--
                }

                cur = mutableListOf(disk[ptr]!!)
                ptr--

                while(disk.getOrNull(ptr) == cur.first()) {
                    cur.add(disk[ptr]!!)
                    ptr--
                }

                yield(ptr + 1 to cur)
                cur = null
            }
        }

        blocks.forEach { (idx, block) ->
            val space = findSpaceBefore(disk, block.size, idx)
            if (space != null) {
                block.forEachIndexed { i, it ->
                    disk[space + i] = it
                    disk[idx + i] = null
                }
            }
        }

        return checksum(disk).toString()
    }

    val day = "09"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "1928")
    check(part2(testInput) == "2858")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
