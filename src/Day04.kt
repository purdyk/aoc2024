fun main() {

    val right = 1 to 0
    val left = -1 to 0
    val up =  0 to -1
    val down = 0 to 1
    val upRight = 1 to -1
    val downRight = 1 to 1
    val upLeft = -1 to -1
    val downLeft = -1 to 1

    val directions = listOf(right, left, up, down, upRight, downRight, upLeft, downLeft)

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
        (first + other.first) to (second + other.second)

    fun test(input: List<String>, expected: Char, location: Pair<Int, Int>): Boolean =
        input.getOrNull(location.second)?.getOrNull(location.first) == expected

    fun search(input: List<String>, tail: String, location: Pair<Int, Int>, direction: Pair<Int, Int>): Boolean {

        if (test(input, tail.first(), location)) {
            if (tail.length == 1) {
                return true
            }
//            "proceed: $tail: $next".println()
            return search(input, tail.drop(1), location + direction, direction)
        }
        return false
    }

    fun part1(input: List<String>): String {
        var found = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell == 'X') {
                    directions.forEach { direction ->
                        if (search(input, "XMAS", x to y, direction)) {
                            found++
                        }
                    }
                }
            }
        }

        return found.toString()
    }

    val crosses =
        listOf(
            upLeft to downRight,
            upRight to downLeft,
            downLeft to upRight,
            downRight to upLeft
        )

    fun part2(input: List<String>): String {
        var found = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell == 'A') {
                    if (crosses.count { (start, dir) ->
                        val pos = (x to y) + start
                        search(input, "MAS", pos, dir)
                    } == 2) found++
                }
            }
        }

        return found.toString()
    }

    val day = "04"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "18")
    check(part2(testInput) == "9")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
