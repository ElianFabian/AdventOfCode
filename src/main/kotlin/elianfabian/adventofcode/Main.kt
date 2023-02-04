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
    showLastPuzzle()
    //showPuzzleFromYearAndDay(year = 2015, day = 5)
    //showAllPuzzlesOfAllTheYears(aocPuzzles)
}


private fun showLastPuzzle()
{
    val aocPuzzles = getAllAocPuzzlesGroupByYearAndDay()
    
    val lastPuzzle = aocPuzzles.values.last().values.sortedBy { it.year }.maxByOrNull { it.day }!!

    showPuzzleResults(lastPuzzle)
}

private fun showPuzzleFromYearAndDay(
    year: Int,
    day: Int,
) = runCatching()
{
    val aocPuzzles = getAllAocPuzzlesGroupByYearAndDay()
    
    showPuzzleResults(aocPuzzles[year]!![day]!!)
}.onFailure()
{
    println("ERROR: the puzzle from year '$year' and day '$day' does not exist.")
}

private fun showAllPuzzlesOfAllTheYears()
{
    val aocPuzzles = getAllAocPuzzlesGroupByYearAndDay()
    
    for ((year, days) in aocPuzzles)
    {
        println("------------------ Year: $year ------------------\n")

        for ((day, puzzle) in days)
        {
            println("\t--------- Day: $day ---------")
            showPuzzleResults(puzzle, indentationTabCount = 1)
        }
    }
}
