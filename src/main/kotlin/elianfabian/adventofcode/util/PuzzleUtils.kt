package elianfabian.adventofcode.util

data class Vector2(val x: Int, val y: Int)
operator fun Vector2.plus(other: Vector2) = Vector2(this.x + other.x, this.y + other.y)