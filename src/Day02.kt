import kotlin.math.absoluteValue

fun main() {

    fun isSafe(list: List<Int>): Boolean {
        val allNeg = list.all { it < 0 }
        val allPos = list.all { it > 0 }
        val allSmall = list.all { it.absoluteValue in (1..3)}
        return ((allNeg or allPos) and allSmall)
    }

    fun listDelta(from: List<Int>) =
        from.zipWithNext { a, b -> a - b }

    fun toInts(from: List<String>): List<List<Int>> =
        from.map { it.split(" ").map { it.toInt() } }

    fun test(reading: List<Int>): Boolean =
        isSafe(listDelta(reading))

    fun buffer(raw: List<Int>): Boolean {
        return raw.indices.any { idx ->
            val buffered = raw.slice(0 until idx) + raw.slice(idx+1 .. raw.lastIndex)
            test(buffered)
        }
    }

    fun part1(input: List<String>): String {
        val raw = toInts(input.filter { it.isNotEmpty() })
        val safes = raw.map { test(it) }
        return safes.count { it == true }.toString()
    }

    fun part2(input: List<String>): String {
        val raw = toInts(input.filter { it.isNotEmpty() })

        val (safe, toTest) = raw.partition { test(it) }
        val afterBuffer = toTest.map { buffer(it) }

        val good = afterBuffer.count { it } + safe.size

        return good.toString()
    }

    val day = "02"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "2")
    check(part2(testInput) == "4")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
