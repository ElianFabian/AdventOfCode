package elianfabian.adventofcode.util

data class Vector2(val x: Int, val y: Int)

operator fun Vector2.plus(other: Vector2) = Vector2(this.x + other.x, this.y + other.y)

typealias Matrix2D<T> = Array<Array<T>>

operator fun <T> Matrix2D<T>.get(row: Int, column: Int) = this[row][column]
operator fun <T> Matrix2D<T>.set(row: Int, column: Int, value: T) {
    this[row][column] = value
}

@Suppress("FunctionName")
inline fun <reified T> Matrix2D(width: Int, height: Int, noinline init: (index: Int) -> T): Matrix2D<T> = Array(height) { Array(width, init) }

@Suppress("FunctionName")
inline fun <reified T> Matrix2D(width: Int, height: Int): Matrix2D<T?> = Array(height) { arrayOfNulls<T>(width) }

fun <T> Array<Array<T>>.toNiceString() = this.joinToString("\n") { "${it.toList()}" }

fun clamp(value: Int, min: Int, max: Int) = when
{
    value < min -> min
    value > max -> max
    else        -> value
}