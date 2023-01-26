package elianfabian.adventofcode


import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.getAllAocPuzzlesGroupByYearAndDay
import elianfabian.adventofcode.util.showPuzzleResults

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
    val aocPuzzles = getAllAocPuzzlesGroupByYearAndDay()

    showLastPuzzle(aocPuzzles)
    //showAllPuzzlesOfAllTheYears(aocPuzzles)
}


private fun showLastPuzzle(puzzlesGroupByYearAndDay: Map<Int, Map<Int, AocPuzzle>>)
{
    val lastPuzzle = puzzlesGroupByYearAndDay.values.last().values.last()

    showPuzzleResults(lastPuzzle)
}

private fun Map<Int, Map<Int, AocPuzzle>>.showPuzzleFromYearAndDay(
    year: Int,
    day: Int,
) = runCatching()
{
    showPuzzleResults(this[year]!![day]!!)
}.onFailure()
{
    println("ERROR: the puzzle from year '$year' and day '$day' does not exist.")
}

private fun showAllPuzzlesOfAllTheYears(puzzlesGroupByYearAndDay: Map<Int, Map<Int, AocPuzzle>>)
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
