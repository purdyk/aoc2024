fun main() {

    fun contiguousRegion(map: Map<*>, start: Position): Set<Position> {
        val region = mutableSetOf(start)
        val queue = mutableListOf(start)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            for (neighbor in current.neighbors) {
                if (neighbor in region) continue
                if (map.get(neighbor) == map.get(start)) {
                    region.add(neighbor)
                    queue.add(neighbor)
                }
            }
        }

        return region
    }

    fun buildRegions(map: Map<*>): List<Set<Position>> {
        val pulled = mutableSetOf<Position>()
        val regions = mutableListOf<Set<Position>>()

        map.allPositions().forEach {
            if (it !in pulled) {
                val region = contiguousRegion(map, it)
                pulled.addAll(region)
                regions.add(region)
            }
        }

        return regions
    }

    fun regionPerimeter(region: Set<Position>): Int {
        return region.map {
            it.neighbors
                .filter { it !in region }
                .size
        }.sum()
    }

    // Builds a list of all the sides of a region, which are
    // contiguous border positions with directional facing
    fun regionSides(region: Set<Position>): Int {
        val allSides = region.flatMap {
            it.neigborsWithDirection
                .filter { it.second !in region }
        }

        // Build contiguous sides, that is, facing the same direction and touching each other

        // List of contigious sides
        val sides = mutableListOf<MutableList<Pair<Position.Direction, Position>>>()

        // pull a block, match it to a contiguous side, or start a new one
        // Check if any of the previously built sides are contiguous with the current side
        allSides.forEach { block ->
            val touchingSides = sides.filter { side ->
                side.first().first == block.first && side.any { it.second.touches(block.second) }
            }

            touchingSides.forEach { sides.remove(it) }

            val replacement = touchingSides.takeIf { it.size > 0 }?.reduce { acc, side ->
                acc.addAll(side)
                acc
            } ?: mutableListOf()

            replacement.add(block)

            sides.add(replacement)
        }

        return sides.size
    }

    fun part1(input: List<String>): String {
        val map = input.map { it.toList() }
        val regions = buildRegions(map)
        val costs = regions.map { it.size * regionPerimeter(it) }
        return costs.sum().toString()
    }

    fun part2(input: List<String>): String {
        val map = input.map { it.toList() }
        val regions = buildRegions(map)
        val costs = regions.map { it.size * regionSides(it) }
        return costs.sum().toString()
    }

    val day = "12"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test").split("\n")

    println("Test: ")
    println(part1(testInput))
    println(part2(testInput))

    check(part1(testInput) == "1930")
    check(part2(testInput) == "1206")

    val input = readInput("Day${day}").split("\n")

    println("\nProblem: ")
    println(part1(input))
    println(part2(input))
}
