fun main() {

    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
        (first + other.first) to (second + other.second)

    operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> =
        (first - other.first) to (second - other.second)

    fun makeNodes(a: Pair<Int, Int>, b: Pair<Int, Int>): List<Pair<Int, Int>> {
        val dx = b.first - a.first
        val dy = b.second - a.second

        return listOf(
            a.first - dx to a.second - dy,
            b.first + dx to b.second + dy
        )
    }

    fun genNodes(pos: Pair<Int, Int>, others: List<Pair<Int,Int>>, width: Int, height: Int): List<Pair<Int, Int>> {
        return others.flatMap {
            makeNodes(pos, it)
        }.filter {
            it.first in 0 until width && it.second in 0 until height
        }
    }

    fun part1(input: List<String>): String {
        val towers = mutableMapOf<String, List<Pair<Int, Int>>>()

        val width = input[0].length
        val height = input.size

        val nodes = mutableSetOf<Pair<Int, Int>>()

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell != '.') {
                    val key = cell.toString()
                    towers[key] = towers.getOrDefault(key, emptyList()) + (x to y)
                }
            }
        }

        towers.forEach { (key, positions) ->
//            key.println()
            positions.forEachIndexed { idx, pos ->
                val gen = genNodes(pos, positions.drop(idx + 1), width, height)
//                gen.println()
                nodes.addAll(gen)
            }
        }

        return nodes.size.toString()
    }

    fun onBoard(pos: Pair<Int, Int>, width: Int, height: Int): Boolean {
        return pos.first in 0 until width && pos.second in 0 until height
    }

    fun makeNodes2(a: Pair<Int, Int>, b: Pair<Int, Int>, width: Int, height: Int): List<Pair<Int, Int>> {
        val delta = b.first - a.first to b.second - a.second

        var start = a
        var output = mutableListOf<Pair<Int, Int>>()

        while (onBoard(start, width, height)) {
            output.add(start)
            start += delta
        }

        start = a
        while (onBoard(start, width, height)) {
            output.add(start)
            start -= delta
        }

        return output
    }

    fun genNodes2(pos: Pair<Int, Int>, others: List<Pair<Int,Int>>, width: Int, height: Int): List<Pair<Int, Int>> {
        return others.flatMap {
            makeNodes2(pos, it, width, height)
        }
    }

    fun part2(input: List<String>): String {
        val towers = mutableMapOf<String, List<Pair<Int, Int>>>()

        val width = input[0].length
        val height = input.size

        val nodes = mutableSetOf<Pair<Int, Int>>()

        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, cell ->
                if (cell != '.') {
                    val key = cell.toString()
                    towers[key] = towers.getOrDefault(key, emptyList()) + (x to y)
                }
            }
        }

        towers.forEach { (key, positions) ->
//            key.println()
            positions.forEachIndexed { idx, pos ->
                val gen = genNodes2(pos, positions.drop(idx + 1), width, height)
//                gen.println()
                nodes.addAll(gen)
            }
        }

        return nodes.size.toString()
    }

    val day = "08"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "14")
    check(part2(testInput) == "34")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
