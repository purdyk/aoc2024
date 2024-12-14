import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

fun main() {

    fun blink(input: Sequence<Long>, start: Long, count: Int): Sequence<Long> {
        return sequence {
            input.iterator().also { iter ->
                while (iter.hasNext()) {
                    val next = iter.next()
                    when {
                        next == 0L -> yield(1)
                        next.toString().length % 2 == 0 -> next.splitInHalf().also { yieldAll(it) }
                        else -> yield(next * 2024)
                    }
                }

                if (count > 20) {
                    println("blink for $start complete at $count")
                }
            }
        }
    }

    suspend fun handleStarter(starter: Long, count: Int): Long {
        println("Started with $starter")
        var sequence = sequenceOf(starter)
        repeat(count) {
            sequence = blink(sequence, starter, it)
        }
        var final = 0L
        sequence.forEach {
            final++
        }

        println("Started with $starter and counted $final")

        return final
    }

    fun part1(input: List<String>): String {
        val starts = input.first().split(" ").map { it.toLong() }

        val count: Long

        val time = measureTime {
            count = runBlocking {
                val jobs = starts.map { async(Dispatchers.Default) { handleStarter(it, 25) } }
                jobs.sumOf { it.await() }
            }
        }

        println("Took: $time")

        return count.toString()
    }

    fun part2(input: List<String>): String {
        val starts = input.first().split(" ").map { it.toLong() }

        val count = runBlocking {
            val jobs = starts.map { async(Dispatchers.Default) { handleStarter(it, 75) } }
            jobs.sumOf { it.await() }
        }

        return count.toString()
    }

    val day = "11"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
//    println(part2(testInput))

    check(part1(testInput) == "55312")
//    check(part2(testInput) == "")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}

fun Long.splitInHalf(): List<Long> {
    val str = this.toString()
    val half = str.length / 2
    return listOf(str.substring(0, half).toLong(), str.substring(half).toLong())
}
