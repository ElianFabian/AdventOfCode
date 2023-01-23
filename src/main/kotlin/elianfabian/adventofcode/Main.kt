package elianfabian.adventofcode

import elianfabian.adventofcode.util.showPuzzleResults
import elianfabian.adventofcode.year2015.Year2015Day1Puzzle
import elianfabian.adventofcode.year2015.Year2015Day2Puzzle

fun main()
{
    //showLastPuzzle()
    showAllPuzzlesOfAllTheYears()
}


val puzzlesGroupByYearAndDay = mapOf(
    2015 to mapOf(
        1 to Year2015Day1Puzzle,
        2 to Year2015Day2Puzzle,
    ),
)

private fun showLastPuzzle()
{
    val lastPuzzle = puzzlesGroupByYearAndDay.values.last().values.last()

    showPuzzleResults(lastPuzzle)
}

private fun showPuzzleFromYearAndDay(
    year: Int,
    day: Int,
) = runCatching()
{
    showPuzzleResults(puzzlesGroupByYearAndDay[year]!![day]!!)
}.onFailure()
{
    println("ERROR: the puzzle from year '$year' and day '$day' does not exist.")
}

private fun showAllPuzzlesOfAllTheYears()
{
    for ((year, days) in puzzlesGroupByYearAndDay)
    {
        println("------------------ Year: $year ------------------\n")

        for ((day, puzzle) in days)
        {
            println("\t--------- Day: $day ---------")
            showPuzzleResults(puzzle, indentationCount = 1)
        }
    }
}
