import jdk.internal.net.http.frame.WindowUpdateFrame

fun main() {

    fun test(update: List<Int>, rule: List<Int>): Boolean {
        val indices = rule.map { update.indexOf(it) }
        if (indices.all { it >= 0 }) {
            return indices[0] < indices[1]
        }
        return true
    }

    fun <T> List<T>.middle(): T = this[(lastIndex / 2)]

    fun part1(input: List<String>): String {
        val rules = input[0].split("\n").map { it.split("|").map { it.toInt() } }
        val updates = input[1].split("\n").map { it.split(",").map { it.toInt() } }

        val pass = updates.filter { rules.all { r -> test(it, r) } }
        val mids = pass.map { it.middle() }

        return mids.sum().toString()
    }

    fun reorder(update: List<Int>, rules: List<List<Int>>): List<Int>? {
        val applies = rules
            .map { it to it.map { update.indexOf(it) } }
            .filter { it.second.all { it >= 0 } }

        val broken = applies.filter { it.second[0] > it.second[1] }

        val swapper = broken.firstOrNull()?.let { (rule, idxs) ->
            val copy = update.toMutableList()
            copy[idxs[1]] = rule[0]
            copy[idxs[0]] = rule[1]
            copy
        }

        return swapper
    }

    fun iterate(update: List<Int>, rules: List<List<Int>>): List<Int> {
        val fixed = reorder(update, rules)
        return if (fixed != null) {
            iterate(fixed, rules)
        } else {
            update
        }
    }

    fun part2(input: List<String>): String {
        val rules = input[0].split("\n").map { it.split("|").map { it.toInt() } }
        val updates = input[1].split("\n").map { it.split(",").map { it.toInt() } }

        val (pass, noPass) = updates.partition { rules.all { r -> test(it, r) } }

        val fixed = noPass.map { iterate(it, rules) }
        val mids = fixed.map { it.middle() }

        return mids.sum().toString()
    }

    val day = "05"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "143")
    check(part2(testInput) == "123")

    val input = readInput("Day${day}").split("\n\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
