import jdk.internal.util.xml.impl.Input

fun main() {
    val right = 1 to 0
    val left = -1 to 0
    val up =  0 to -1
    val down = 0 to 1

    val dirs = listOf(up, right, down, left)
    val faces = listOf(
        '^' to up,
        '>' to right,
        'v' to down,
        '<' to left
    )

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
        (first + other.first) to (second + other.second)

    fun findGuard(input: List<String>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                faces.firstOrNull { it.first == cell }.let { face ->
                    if (face != null) {
                        return (x to y) to face.second
                        println("Guard found at $x, $y")
                    }
                }
            }
        }
        return (0 to 0) to (0 to 0)
    }

    fun part1(input: List<String>): String {
        val visited = mutableSetOf<Pair<Int, Int>>()
        var (pos, direction) = findGuard(input)

        while (true) {
            visited.add(pos)
            val nextPos = pos + direction
            val test = input.getOrNull(nextPos.second)?.getOrNull(nextPos.first)

            when (test) {
                '#' -> {
                    direction = dirs[(dirs.indexOf(direction) + 1) % dirs.size]
                }
                null -> {
                    break
                }
                else -> {
                    pos = nextPos
                }
            }
        }

        return visited.size.toString()
    }

    fun isLoop(input: List<String>, override: Pair<Int, Int>): Boolean {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val turnedAt = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        var (pos, direction) = findGuard(input)

        while (true) {
            visited.add(pos)
            val nextPos = pos + direction
            val test = if (nextPos == override) { '#' } else {
                input.getOrNull(nextPos.second)?.getOrNull(nextPos.first)
            }

            when (test) {
                '#' -> {
                    val turnPair = pos to nextPos
                    if (turnPair in turnedAt) {
                        return true
                    }

                    turnedAt.add(turnPair)
                    direction = dirs[(dirs.indexOf(direction) + 1) % dirs.size]
                }

                null -> {
                    return false
                }

                else -> {
                    pos = nextPos
                }
            }
        }
    }

    fun part2(input: List<String>): String {
        var loops = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell == '.') {
                    if (isLoop(input, x to y)) {
                        loops++
                    }
                }
            }
        }
        return loops.toString()
    }

    val day = "06"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "41")
    check(part2(testInput) == "6")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
