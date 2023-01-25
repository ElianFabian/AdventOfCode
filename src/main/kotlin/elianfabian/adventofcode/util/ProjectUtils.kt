package elianfabian.adventofcode.util

interface AocPuzzle
{
    val partOneQuestion: String
    fun getResultOfPartOne(input: String): Int

    val partTwoQuestion: String
    fun getResultOfPartTwo(input: String): Int

    val input: String
}

fun showPuzzleResults(puzzle: AocPuzzle, indentationTabCount: Int = 0)
{
    val indentation = "\t".repeat(indentationTabCount)

    val input = puzzle.input

    println("$indentation 1. ${puzzle.partOneQuestion}")
    println("$indentation - Your answer is: ${puzzle.getResultOfPartOne(input)}\n")

    println("$indentation 2. ${puzzle.partTwoQuestion}")
    println("$indentation - Your answer is: ${puzzle.getResultOfPartTwo(input)}\n")
}