package elianfabian.adventofcode.util

import elianfabian.adventofcode.main
import org.reflections.Reflections
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

abstract class AocPuzzle(val year: Int, val day: Int)
{
    abstract val partOneQuestion: String
    abstract fun getResultOfPartOne(): Int

    abstract val partTwoQuestion: String
    abstract fun getResultOfPartTwo(): Int

    abstract val input: String


    override fun toString(): String
    {
        return "AocPuzzle(year=$year, day=$day, partOneQuestion='$partOneQuestion', partTwoQuestion='$partTwoQuestion')"
    }
}

private fun getAllAocPuzzles(): List<AocPuzzle>
{
    val reflections = Reflections(
        ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage(::main.javaClass.`package`.name))
    )

    return reflections.getSubTypesOf(AocPuzzle::class.java).mapNotNull { it.kotlin.objectInstance }
}

fun getAllAocPuzzlesGroupByYearAndDay() = getAllAocPuzzles()
    .sortedBy { "${it.year}-${it.day}" }
    .groupBy { it.year }
    .mapValues { it.value.associateBy { p -> p.day } }

fun showPuzzleResults(puzzle: AocPuzzle, indentationTabCount: Int = 0)
{
    val indentation = "\t".repeat(indentationTabCount)

    println("$indentation 1. ${puzzle.partOneQuestion}")
    println("$indentation - Your answer is: ${puzzle.getResultOfPartOne()}\n")

    println("$indentation 2. ${puzzle.partTwoQuestion}")
    println("$indentation - Your answer is: ${puzzle.getResultOfPartTwo()}\n")
}