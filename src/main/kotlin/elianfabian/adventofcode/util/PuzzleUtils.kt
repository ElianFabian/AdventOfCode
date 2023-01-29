package elianfabian.adventofcode.util

data class Vector2(val x: Int, val y: Int)

operator fun Vector2.plus(other: Vector2) = Vector2(this.x + other.x, this.y + other.y)

typealias Matrix2D<T> = Array<Array<T>>

operator fun <T> Matrix2D<T>.get(row: Int, column: Int) = this[row][column]
operator fun <T> Matrix2D<T>.set(row: Int, column: Int, value: T)
{
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

// These functions are inspired from
// https://github.com/sschuberth/stan/blob/main/lib/src/main/kotlin/utils/PatternMatching.kt
fun <R : Any> String.whenMatchDestructured(
    vararg matchingInfo: MatchingInfo<R>,
    maxMatchesCount: Int = 1,
): R?
{
    if (maxMatchesCount < 1) error("maxMatchesCount of value '$maxMatchesCount' can't be less than 1.")

    var returnedValue: R? = null
    var currentMatchesCount = 0

    for (info in matchingInfo)
    {
        info.regex.matchEntire(this)?.also { result ->

            returnedValue = info.resultBlock(result.destructured)

            currentMatchesCount++
        }

        if (currentMatchesCount >= maxMatchesCount) break
    }

    return returnedValue
}

@Suppress("FunctionName")
fun <R : Any> Matching(regex: Regex, block: (MatchResult.Destructured) -> R) = MatchingInfo(
    regex = regex,
    resultBlock = block,
)

data class MatchingInfo<out R : Any>(
    val regex: Regex,
    inline val resultBlock: (MatchResult.Destructured) -> R,
)


// Since in the JVM Shorts are converted to Int there's not shl and shr for Short.
// That's why we define them in here when they are required.
infix fun UShort.shl(bitCount: UShort): UShort = (this.toInt() shl bitCount.toInt()).toUShort()
infix fun UShort.shr(bitCount: UShort): UShort = (this.toInt() shr bitCount.toInt()).toUShort()
