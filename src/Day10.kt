fun main() {

    fun seekUp(map: Map<Int>, position: Position): Set<Position> {
        val cur = map.get(position) ?: return emptySet()
        if (cur == 9) return setOf(position)
        return Position.Direction.entries.map {
            position.move(it)
        }.filter {
            map.get(it) == cur + 1
        }.map {
            seekUp(map, it)
        }.flatten().toSet()
    }

    fun part1(input: List<String>): String {
        val map = input.map { it.map { it.toString().toInt() } }
        val starts = map.allPositions().filter { map.get(it) == 0 }

        val scores = starts.map { seekUp(map, it).size }

        return scores.sum().toString()
    }

    fun seekUp2(map: Map<Int>, position: Position): List<List<Position>>? {
        val cur = map.get(position) ?: return null
        if (cur == 9) return listOf(listOf(position))
        return Position.Direction.entries
            .map { position.move(it) }
            .filter { map.get(it) == cur + 1 }
            .takeIf { it.isNotEmpty() }
            ?.map {
                seekUp2(map, it)?.map { listOf(position) + it }
            }
            ?.filterNotNull()?.flatten()
    }

    fun part2(input: List<String>): String {
        val map = input.map { it.map { it.toString().toInt() } }
        val starts = map.allPositions().filter { map.get(it) == 0 }

        val paths = starts.mapNotNull {
            seekUp2(map, it)?.toSet()
        }

        return paths.map{ it.size }.sum().toString()
    }

    val day = "10"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "36")
    check(part2(testInput) == "81")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
