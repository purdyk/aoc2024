import java.io.File
import java.nio.charset.Charset

fun main() {
    val lines: List<String> = File("input.txt").readText(Charset.forName("UTF8")).split("\n")
    val bits = lines.mapNotNull { it.takeIf { it.isNotEmpty() }?.split("   ") }
//println("$bits")
    val left: List<Int> = bits.map { it[0].toInt() }.sorted()
    val right: List<Int> = bits.map { it[1].toInt() }.sorted()
    val diff = left.zip(right) { a, b -> maxOf(a, b) - minOf(a, b) }
//println("$diff")
    println("${diff.sum()}")

    val counts = mutableMapOf<Int, Int>()
    right.forEach { counts[it] = counts.getOrDefault(it, 0) + 1 }

    val scores = left.map { it * counts.getOrDefault(it, 0) }
    println("${scores.sum()}")
}