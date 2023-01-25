package elianfabian.adventofcode

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.showPuzzleResults
import elianfabian.adventofcode.year2015.*


/**
 * Hi there, I'm Elián and in this project it's where I complete the Advent of Code puzzles in Kotlin.
 *
 * The solutions I give in every puzzle I try to make them easy to read and understand rather than being the most efficient way to do it
 * since they're just simple puzzles.
 *
 * In case you found this repository I hope my solutions can be interesting or helpful for you.
 */
fun main()
{
    showLastPuzzle()
    //showAllPuzzlesOfAllTheYears()
}

/**
 * In here we have a variable with all the puzzles group by year and day.
 * I don't like to do it this way, but I haven't found any better way at the moment,
 * one way could be to make use of reflection and source generation, but I would prefer
 * to avoid that kind of solution, also it's kind of difficult to deal with it with my actual knowledge.
 */
val puzzlesGroupByYearAndDay = mapOf(
    2015 to mapOf(
        1 to PuzzleYear2015Day1,
        2 to PuzzleYear2015Day2,
        3 to PuzzleYear2015Day3,
        4 to PuzzleYear2015Day4,
        5 to PuzzleYear2015Day5,
        6 to PuzzleYear2015Day6,
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
            showPuzzleResults(puzzle, indentationTabCount = 1)
        }
    }
}
