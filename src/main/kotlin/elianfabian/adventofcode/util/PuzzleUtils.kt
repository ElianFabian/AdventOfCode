package elianfabian.adventofcode.util

interface Puzzle
{
    val question: String

    fun getResult(input: String): Int
}

data class PuzzleInfo(
    val input: String,
    val parts: Set<Puzzle>,
)

fun showPuzzleResults(puzzleInfo: PuzzleInfo, indentationCount: Int = 0)
{
    val indentation = "\t".repeat(indentationCount)

    puzzleInfo.parts.forEachIndexed { index, puzzle ->
        println("$indentation ${index + 1}. ${puzzle.question}")
        println("$indentation - Your answer is: ${puzzle.getResult(puzzleInfo.input)}\n")
    }
}