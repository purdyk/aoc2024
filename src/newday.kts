import java.io.File

fun main() {
    val last = // Pull out only digits
        File("./src/").listFiles()
            .mapNotNull { it.name.filter { it.isDigit() }.takeIf { it.isNotEmpty() } }  // Pull out only digits
            .map { it.toInt() }
            .max()

    val next = (last + 1).toString().padStart(2, '0')
    println("Last day was $last, workin on $next")

    File("./src/Day").copyTo(File("./src/Day${next}.kt"))
    File("./src/Day${next}.txt").createNewFile()
    File("./src/Day${next}_test.txt").createNewFile()
}

main()
