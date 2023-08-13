package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.ignoreReversed
import elianfabian.adventofcode.util.permutationsWithoutReplacement

object PuzzleYear2015Day9 : AocPuzzle(2015, 9) {

	override val partOneQuestion = "What is the distance of the shortest route?"

	/**
	 * Every year, Santa manages to deliver all of his presents in a single night.

	 * This year, however, he has some new locations to visit; his elves have provided him the distances between every pair of locations. He can start and end at any two (different) locations he wants, but he must visit each location exactly once. What is the shortest distance he can travel to achieve this?

	 * For example, given the following distances:

	 * - London to Dublin = 464
	 * - London to Belfast = 518
	 * - Dublin to Belfast = 141
	 *
	 * The possible routes are therefore:
	 *
	 * - Dublin -> London -> Belfast = 982
	 * - London -> Dublin -> Belfast = 605
	 * - London -> Belfast -> Dublin = 659
	 * - Dublin -> Belfast -> London = 659
	 * - Belfast -> Dublin -> London = 605
	 * - Belfast -> London -> Dublin = 982
	 *
	 * The shortest of these is London -> Dublin -> Belfast = 605, and so the answer is 605 in this example.
	 *
	 * What is the distance of the shortest route?
	 */
	override fun getResultOfPartOne(): Int {
		val info = input.lines().map { fromLineToCountriesDistanceInfo(it) }

		val allCountries = info.map { listOf(it.firstCountryName, it.secondCountryName) }.flatten().distinct()
		val distanceByPairOfCountries = info.associate { "${it.firstCountryName}-${it.secondCountryName}" to it.distance }

		fun getDistanceBetweenCountries(
			firstCountryName: String,
			secondCountryName: String,
		): Int {
			return distanceByPairOfCountries["$firstCountryName-$secondCountryName"] ?: distanceByPairOfCountries["$secondCountryName-$firstCountryName"] ?: 0
		}

		val allPossibleRoutes = allCountries.permutationsWithoutReplacement().ignoreReversed()

		val distanceByRoute = allPossibleRoutes.associateWith { route ->
			val routeDistance = route.mapIndexed { index, currentCountryName ->

				val nextCountryName = route.getOrNull(index + 1) ?: ""
				val distance = getDistanceBetweenCountries(currentCountryName, nextCountryName)

				distance
			}.sum()

			routeDistance
		}

		return distanceByRoute.values.min()
	}

	override val partTwoQuestion = "What is the distance of the longest route?"


	/**
	 * The next year, just to show off, Santa decides to take the route with the longest distance instead.
	 *
	 * He can still start and end at any two (different) locations he wants, and he still must visit each location exactly once.
	 *
	 * For example, given the distances above, the longest route would be 982 via (for example) Dublin -> London -> Belfast.
	 *
	 * What is the distance of the longest route?
	 */
	override fun getResultOfPartTwo(): Int {
		val info = input.lines().map { fromLineToCountriesDistanceInfo(it) }

		val allCountries = info.map { listOf(it.firstCountryName, it.secondCountryName) }.flatten().distinct()
		val distanceByPairOfCountries = info.associate { "${it.firstCountryName}-${it.secondCountryName}" to it.distance }

		fun getDistanceBetweenCountries(
			firstCountryName: String,
			secondCountryName: String,
		): Int {
			return distanceByPairOfCountries["$firstCountryName-$secondCountryName"] ?: distanceByPairOfCountries["$secondCountryName-$firstCountryName"] ?: 0
		}

		val allPossibleRoutes = allCountries.permutationsWithoutReplacement().ignoreReversed()

		val distanceByRoute = allPossibleRoutes.associateWith { route ->
			val routeDistance = route.mapIndexed { index, currentCountryName ->

				val nextCountryName = route.getOrNull(index + 1) ?: ""
				val distance = getDistanceBetweenCountries(currentCountryName, nextCountryName)

				distance
			}.sum()

			routeDistance
		}

		return distanceByRoute.values.max()
	}

	override val input = getInput()
}


//region Utils

private fun fromLineToCountriesDistanceInfo(line: String): CountriesDistanceInfo {
	val result = fromCountryToCountryDistanceRegex.matchEntire(line) ?: error("Error when trying to match this line: '$line'.")

	return result.destructured.let { (firstCountryName, secondCountryName, distance) ->

		CountriesDistanceInfo(
			firstCountryName = firstCountryName,
			secondCountryName = secondCountryName,
			distance = distance.toInt(),
		)
	}
}

private data class CountriesDistanceInfo(
	val firstCountryName: String,
	val secondCountryName: String,
	val distance: Int,
)

private val fromCountryToCountryDistanceRegex = "(\\w+) to (\\w+) = (\\d+)".toRegex()

//endregion


private fun getInput() = """
    Tristram to AlphaCentauri = 34
    Tristram to Snowdin = 100
    Tristram to Tambi = 63
    Tristram to Faerun = 108
    Tristram to Norrath = 111
    Tristram to Straylight = 89
    Tristram to Arbre = 132
    AlphaCentauri to Snowdin = 4
    AlphaCentauri to Tambi = 79
    AlphaCentauri to Faerun = 44
    AlphaCentauri to Norrath = 147
    AlphaCentauri to Straylight = 133
    AlphaCentauri to Arbre = 74
    Snowdin to Tambi = 105
    Snowdin to Faerun = 95
    Snowdin to Norrath = 48
    Snowdin to Straylight = 88
    Snowdin to Arbre = 7
    Tambi to Faerun = 68
    Tambi to Norrath = 134
    Tambi to Straylight = 107
    Tambi to Arbre = 40
    Faerun to Norrath = 11
    Faerun to Straylight = 66
    Faerun to Arbre = 144
    Norrath to Straylight = 115
    Norrath to Arbre = 135
    Straylight to Arbre = 127
""".trimIndent()
