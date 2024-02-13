package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.ignoreReversed
import elianfabian.adventofcode.util.permutationsWithoutReplacement
import elianfabian.adventofcode.util.putOrUpdate

/**
 * --- Day 13: Knights of the Dinner Table --- https://adventofcode.com/2015/day/13
 */
object PuzzleYear2015Day13 : AocPuzzle(2015, 13) {

	override val partOneQuestion = "What is the total change in happiness for the optimal seating arrangement of the actual guest list?"

	/**
	 * In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you resolve, will be different. You're going to find the optimal seating arrangement and avoid all those awkward conversations.
	 *
	 * You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they were to find themselves sitting next to each other person. You have a circular table that will be just big enough to fit everyone comfortably, and so each person will have exactly two neighbors.
	 *
	 * For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:
	 *
	 * - Alice would gain 54 happiness units by sitting next to Bob.
	 * - Alice would lose 79 happiness units by sitting next to Carol.
	 * - Alice would lose 2 happiness units by sitting next to David.
	 * - Bob would gain 83 happiness units by sitting next to Alice.
	 * - Bob would lose 7 happiness units by sitting next to Carol.
	 * - Bob would lose 63 happiness units by sitting next to David.
	 * - Carol would lose 62 happiness units by sitting next to Alice.
	 * - Carol would gain 60 happiness units by sitting next to Bob.
	 * - Carol would gain 55 happiness units by sitting next to David.
	 * - David would gain 46 happiness units by sitting next to Alice.
	 * - David would lose 7 happiness units by sitting next to Bob.
	 * - David would gain 41 happiness units by sitting next to Carol.
	 * - Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), but David would gain 46 happiness units (because Alice is such a good listener), for a total change of 44.
	 *
	 * If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54). Finally, seat Carol, who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). The arrangement looks like this:
	 *
	 * - | +41 +46
	 * - | +55    David    -2
	 * - | Carol          Alice
	 * - | +60    Bob    +54
	 * - |     -7     +83
	 *
	 * After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, with a total change in happiness of 330.
	 *
	 * What is the total change in happiness for the optimal seating arrangement of the actual guest list?
	 */
	override fun getResultOfPartOne(): Int {
		val listOfGuestInfo = input
			.lineSequence()
			.map { line -> fromLineToHappinessInfo(line) }
			.toList()

		val allGuestNames = listOfGuestInfo.map { info ->
			info.selfName
		}.distinct()

		val happinessUnitsVariationByGuestByGuest = getHappinessUnitsVariationByGuestByGuest(listOfGuestInfo)

		val allPossibleGuestArrangements = allGuestNames.permutationsWithoutReplacement().ignoreReversed()

		val maxHappinessUnitVariation = allPossibleGuestArrangements.maxOfOrNull { arrangement ->
			getTotalHappinessUnitsFromArrangement(
				arrangement = arrangement,
				happinessUnitsVariationByGuestByGuest = happinessUnitsVariationByGuestByGuest,
			)
		} ?: 0

		return maxHappinessUnitVariation
	}

	override val partTwoQuestion = "What is the total change in happiness for the optimal seating arrangement that actually includes yourself?"

	/**
	 * In all the commotion, you realize that you forgot to seat yourself. At this point, you're pretty apathetic toward the whole thing, and your happiness wouldn't really go up or down regardless of who you sit next to. You assume everyone else would be just as ambivalent about sitting next to you, too.
	 *
	 * So, add yourself to the list, and give all happiness relationships that involve you a score of 0.
	 *
	 * What is the total change in happiness for the optimal seating arrangement that actually includes yourself?
	 */
	override fun getResultOfPartTwo(): Int {
		val listOfGuestInfo = input
			.lineSequence()
			.map { line -> fromLineToHappinessInfo(line) }
			.toList()

		val allGuestNames = listOfGuestInfo.map { it.selfName }.distinct() + "Me"

		val happinessUnitsVariationByGuestByGuest = getHappinessUnitsVariationByGuestByGuest(listOfGuestInfo)

		val allPossibleGuestArrangements = allGuestNames.permutationsWithoutReplacement()

		val maxHappinessUnitVariation = allPossibleGuestArrangements.maxOfOrNull { arrangement ->
			getTotalHappinessUnitsFromArrangement(
				arrangement = arrangement,
				happinessUnitsVariationByGuestByGuest = happinessUnitsVariationByGuestByGuest,
			)
		} ?: 0

		return maxHappinessUnitVariation
	}

	override val input = getInput()
}


//region Utils

private fun getTotalHappinessUnitsFromArrangement(
	arrangement: List<String>,
	happinessUnitsVariationByGuestByGuest: Map<String, Map<String, Int>>,
): Int {
	return arrangement.mapIndexed { index, currentGuestName ->

		val previousGuestName = arrangement.getOrNull(index - 1) ?: arrangement.last()
		val nextGuestName = arrangement.getOrNull(index + 1) ?: arrangement.first()

		val unitsOfTheCurrentAndNext = happinessUnitsVariationByGuestByGuest[currentGuestName]?.get(nextGuestName) ?: 0
		val unitsOfTheCurrentAndPrevious = happinessUnitsVariationByGuestByGuest[currentGuestName]?.get(previousGuestName) ?: 0

		unitsOfTheCurrentAndNext + unitsOfTheCurrentAndPrevious
	}.sum()
}

private fun getHappinessUnitsVariationByGuestByGuest(listOfGuestInfo: List<GuestInfo>): Map<String, Map<String, Int>> {
	val happinessUnitsByGuestByGuest = mutableMapOf<String, MutableMap<String, Int>>()

	for (guestInfo in listOfGuestInfo) {

		val happinessUnitsByGuest = mutableMapOf(
			guestInfo.neighborName to guestInfo.happinessUnitsVariation
		)

		happinessUnitsByGuestByGuest.putOrUpdate(guestInfo.selfName, happinessUnitsByGuest) { currentValue ->
			currentValue.apply {
				put(guestInfo.neighborName, guestInfo.happinessUnitsVariation)
			}
		}
	}

	return happinessUnitsByGuestByGuest
}

private fun fromLineToHappinessInfo(line: String): GuestInfo {
	val result = happinessInfoRegex.matchEntire(line) ?: error("Error when trying to match this line: '$line'.")

	return result.destructured.let { (selfName, verb, happinessUnits, neighborName) ->

		val unitsSign = when (verb) {
			"gain" -> 1
			"lose" -> -1
			else -> error("Unexpected verb '$verb' when matching line '$line'.")
		}

		GuestInfo(
			selfName = selfName,
			neighborName = neighborName,
			happinessUnitsVariation = happinessUnits.toInt() * unitsSign,
		)
	}
}

private data class GuestInfo(
	val selfName: String,
	val neighborName: String,
	val happinessUnitsVariation: Int,
)

private val happinessInfoRegex = "(\\w+) would (\\w+) (\\d+) happiness units by sitting next to (\\w+)\\.".toRegex()

//endregion

private fun getInput() = """
    Alice would gain 2 happiness units by sitting next to Bob.
    Alice would gain 26 happiness units by sitting next to Carol.
    Alice would lose 82 happiness units by sitting next to David.
    Alice would lose 75 happiness units by sitting next to Eric.
    Alice would gain 42 happiness units by sitting next to Frank.
    Alice would gain 38 happiness units by sitting next to George.
    Alice would gain 39 happiness units by sitting next to Mallory.
    Bob would gain 40 happiness units by sitting next to Alice.
    Bob would lose 61 happiness units by sitting next to Carol.
    Bob would lose 15 happiness units by sitting next to David.
    Bob would gain 63 happiness units by sitting next to Eric.
    Bob would gain 41 happiness units by sitting next to Frank.
    Bob would gain 30 happiness units by sitting next to George.
    Bob would gain 87 happiness units by sitting next to Mallory.
    Carol would lose 35 happiness units by sitting next to Alice.
    Carol would lose 99 happiness units by sitting next to Bob.
    Carol would lose 51 happiness units by sitting next to David.
    Carol would gain 95 happiness units by sitting next to Eric.
    Carol would gain 90 happiness units by sitting next to Frank.
    Carol would lose 16 happiness units by sitting next to George.
    Carol would gain 94 happiness units by sitting next to Mallory.
    David would gain 36 happiness units by sitting next to Alice.
    David would lose 18 happiness units by sitting next to Bob.
    David would lose 65 happiness units by sitting next to Carol.
    David would lose 18 happiness units by sitting next to Eric.
    David would lose 22 happiness units by sitting next to Frank.
    David would gain 2 happiness units by sitting next to George.
    David would gain 42 happiness units by sitting next to Mallory.
    Eric would lose 65 happiness units by sitting next to Alice.
    Eric would gain 24 happiness units by sitting next to Bob.
    Eric would gain 100 happiness units by sitting next to Carol.
    Eric would gain 51 happiness units by sitting next to David.
    Eric would gain 21 happiness units by sitting next to Frank.
    Eric would gain 55 happiness units by sitting next to George.
    Eric would lose 44 happiness units by sitting next to Mallory.
    Frank would lose 48 happiness units by sitting next to Alice.
    Frank would gain 91 happiness units by sitting next to Bob.
    Frank would gain 8 happiness units by sitting next to Carol.
    Frank would lose 66 happiness units by sitting next to David.
    Frank would gain 97 happiness units by sitting next to Eric.
    Frank would lose 9 happiness units by sitting next to George.
    Frank would lose 92 happiness units by sitting next to Mallory.
    George would lose 44 happiness units by sitting next to Alice.
    George would lose 25 happiness units by sitting next to Bob.
    George would gain 17 happiness units by sitting next to Carol.
    George would gain 92 happiness units by sitting next to David.
    George would lose 92 happiness units by sitting next to Eric.
    George would gain 18 happiness units by sitting next to Frank.
    George would gain 97 happiness units by sitting next to Mallory.
    Mallory would gain 92 happiness units by sitting next to Alice.
    Mallory would lose 96 happiness units by sitting next to Bob.
    Mallory would lose 51 happiness units by sitting next to Carol.
    Mallory would lose 81 happiness units by sitting next to David.
    Mallory would gain 31 happiness units by sitting next to Eric.
    Mallory would lose 73 happiness units by sitting next to Frank.
    Mallory would lose 89 happiness units by sitting next to George.
""".trimIndent()
