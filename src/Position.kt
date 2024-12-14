data class Position(val x: Int, val y: Int) {
    enum class Direction(val dx: Int, val dy: Int) {
        Right(1, 0),
        Left(-1, 0),
        Up(0, -1),
        Down(0, 1);
    }

    operator fun plus(direction: Direction): Position {
        return Position(x + direction.dx, y + direction.dy)
    }

    operator fun minus(direction: Direction): Position {
        return Position(x - direction.dx, y - direction.dy)
    }

    fun move(direction: Direction): Position {
        return this + direction
    }

    val neighbors get() =
        Direction.entries.map { this + it }

    val neigborsWithDirection get() =
        Direction.entries.map { it to this + it }

    fun touches(other: Position): Boolean {
        return this.neighbors.contains(other)
    }
}

typealias Map<T> = List<List<T>>

fun Map<*>.contains(position: Position): Boolean {
    return position.y in this.indices && position.x in this[0].indices
}

fun <T> Map<T>.get(pos: Position): T? {
    return this.getOrNull(pos.y)?.getOrNull(pos.x)
}

fun Map<*>.allPositions(): List<Position> {
    return this.flatMapIndexed { y, row ->
        row.mapIndexed { x, _ ->
            Position(x, y)
        }
    }
}