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


// https://stackoverflow.com/questions/71673452/how-to-convert-hex-string-to-ascii-string-in-kotlin
fun String.decodeHex(): String
{
    require(length % 2 == 0) { "Must have an even length" }
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
        .toString(Charsets.ISO_8859_1)  // Or whichever encoding your input uses
}

inline fun <T> Iterable<T>.partitionIndexed(predicate: (index: Int, T) -> Boolean): Pair<List<T>, List<T>>
{
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()

    for ((index, element) in this.withIndex())
    {
        if (predicate(index, element))
        {
            first.add(element)
        }
        else second.add(element)
    }
    return Pair(first, second)
}

fun <T> List<T>.permutations(): List<List<T>>
{
    if (this.size == 1) return listOf(this)

    val permutations = mutableListOf<List<T>>()

    for (element in this)
    {
        for (remainingPermutation in this.filterNot { it == element }.permutations())
        {
            permutations += listOf(element) + remainingPermutation
        }
    }

    return permutations
}

// This one is faster, but the other is easier to understand, at least for me
fun <T> List<T>.permutations2(): List<List<T>>
{
    val permutations = mutableSetOf<List<T>>()

    this.ifEmpty { return permutations.apply { add(emptyList()) }.toList() }

    val firstElement = this.first()

    val elementsExceptFirst = this.drop(1)

    val elementsPermutations = elementsExceptFirst.permutations2()

    for (element in elementsPermutations)
    {
        for (i in 0..element.size)
        {
            val permutation = element.take(i) + firstElement + element.drop(i)

            permutations += permutation
        }
    }
    return permutations.toList()
}

fun <K, V> MutableMap<K, V>.putIfAbsentOr(key: K, value: V, newValue: (oldValue: V) -> V): V?
{
    val oldValue = get(key)

    if (oldValue == null)
    {
        put(key, value)
    }
    else put(key, newValue(oldValue))

    return oldValue
}

operator fun StringBuilder.plusAssign(string: String): Unit
{
    append(string)
}

inline fun <T> applyTransformation(times: Int, initialValue: T, action: (value: T) -> T): T
{
    var result = initialValue

    repeat(times)
    {
        result = action(result)
    }
    return result
}