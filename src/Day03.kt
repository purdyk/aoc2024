fun main() {

    val matcher1 = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    fun part1(input: List<String>): String {
        val pairs = input.flatMap {
            matcher1.findAll(it).map {
                it.groupValues[1].toInt() to it.groupValues[2].toInt()
            }
        }

        return pairs.map { it.first * it.second }.sum().toString()
    }

    val matcher2 = """(mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\))""".toRegex()

    fun part2(input: List<String>): String {
        val vals = input.flatMap {
            matcher2.findAll(it).map {
                when (it.groupValues[0]) {
                    "do()" -> true
                    "don't()" -> false
                    else -> it.groupValues[2].toInt() * it.groupValues[3].toInt()
                }
            }
        }

        var accumulator = 0
        var enabled = true

        vals.forEach {
            if (it is Int) {
                if (enabled) {
                    accumulator += it
                }
            } else {
                enabled = it as Boolean
            }
        }

        return accumulator.toString()
    }

    val day = "03"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "161")
    check(part2(testInput) == "48")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
