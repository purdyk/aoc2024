fun main() {

    fun eval(target: Long, tail: List<Long>, cat: Boolean = false): Int {
        val (a, b) = tail.take(2)
        val end = tail.size == 2

        val plus = (a + b).let {
            if (end) {
                if (it == target) 1 else 0
            } else {
                eval(target, listOf(it) + tail.drop(2), cat)
            }
        }

        val mult = (a * b).let {
            if (end) {
                if (it == target) 1 else 0
            } else {
                eval(target, listOf(it) + tail.drop(2), cat)
            }
        }

        val concatenated = if (cat) {
            (a.toString() + b.toString()).toLong().let {
                if (end) {
                    if (it == target) 1 else 0
                } else {
                    eval(target, listOf(it) + tail.drop(2), cat)
                }
            }
        } else 0

        return plus + mult + concatenated
    }

    fun part1(input: List<String>): String {
        val tests = input.map { it.split(" ") }

        val results = tests.map {
            val target = it.first().replace(":", "").toLong()
            val tail = it.drop(1).map { it.toLong() }
            (target to eval(target, tail))
        }

        return results.sumOf { it.first * it.second.coerceAtMost(1) }.toString()
    }

    fun part2(input: List<String>): String {
        val tests = input.map { it.split(" ") }

        val results = tests.map {
            val target = it.first().replace(":", "").toLong()
            val tail = it.drop(1).map { it.toLong() }
            (target to eval(target, tail, true))
        }

        return results.sumOf { it.first * it.second.coerceAtMost(1) }.toString()
    }

    val day = "07"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "3749")
    check(part2(testInput) == "11387")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
