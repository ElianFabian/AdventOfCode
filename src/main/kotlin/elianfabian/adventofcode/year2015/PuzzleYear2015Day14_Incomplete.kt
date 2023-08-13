package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.clamp

/**
 * --- Day 14: Reindeer Olympics --- https://adventofcode.com/2015/day/14
 */
object PuzzleYear2015Day14_Incomplete : AocPuzzle(2015, 14) {

	override val partOneQuestion = "Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the winning reindeer traveled?"

	/**
	 * This year is the Reindeer Olympics! Reindeer can fly at high speeds, but must rest occasionally to recover their energy. Santa would like to know which of his reindeer is fastest, and so he has them race.
	 *
	 * Reindeer can only either be flying (always at their top speed) or resting (not moving at all), and always spend whole seconds in either state.
	 *
	 * For example, suppose you have the following Reindeer:
	 *
	 * - Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
	 * - Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
	 * - After one second, Comet has gone 14 km, while Dancer has gone 16 km. After ten seconds, Comet has gone 140 km, while Dancer has gone 160 km. On the eleventh second, Comet begins resting (staying at 140 km), and Dancer continues on for a total distance of 176 km. On the 12th second, both reindeer are resting. They continue to rest until the 138th second, when Comet flies for another ten seconds. On the 174th second, Dancer flies for another 11 seconds.
	 *
	 * In this example, after the 1000th second, both reindeer are resting, and Comet is in the lead at 1120 km (poor Dancer has only gotten 1056 km by that point). So, in this situation, Comet would win (if the race ended at 1000 seconds).
	 *
	 * Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the winning reindeer traveled?
	 */
	override fun getResultOfPartOne(): Int {
		val raceTimeInSeconds = 2503

		val reindeerRaceInfoList = input.lines().map { line ->
			fromLineToReindeerRaceInfo(line)
		}

		val totalDistanceByReindeerName = reindeerRaceInfoList.associate { info ->
			info.reindeerName to info.getTotalDistanceInKmTraveled(totalTimeInSeconds = raceTimeInSeconds)
		}

		return totalDistanceByReindeerName.values.max()
	}

	override val partTwoQuestion = "Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, how many points does the winning reindeer have?"

	/**
	 * Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
	 *
	 * Instead, at the end of each second, he awards one point to the reindeer currently in the lead. (If there are multiple reindeer tied for the lead, they each get one point.) He keeps the traditional 2503 second time limit, of course, as doing otherwise would be entirely ridiculous.
	 *
	 * Given the example reindeer from above, after the first second, Dancer is in the lead and gets one point. He stays in the lead until several seconds into Comet's second burst: after the 140th second, Comet pulls into the lead and gets his first point. Of course, since Dancer had been in the lead for the 139 seconds before that, he has accumulated 139 points by the 140th second.
	 *
	 * After the 1000th second, Dancer has accumulated 689 points, while poor Comet, our old champion, only has 312. So, with the new scoring system, Dancer would win (if the race ended at 1000 seconds).
	 *
	 * Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, how many points does the winning reindeer have?
	 */
	override fun getResultOfPartTwo(): Int {
		val raceTimeInSeconds = 2503

		val infoByReindeerName = input.lines().associate { line ->
			val info = fromLineToReindeerRaceInfo(line)

			info.reindeerName to info
		}

		val reindeerNames = infoByReindeerName.keys

		val stateByReindeerName = infoByReindeerName.map { (reindeerName, _) ->

			reindeerName to ReindeerRaceState(
				distanceTraveledInKm = 0,
				accumulatedPoints = 0,
			)
		}.toMap().toMutableMap()

		for (currentSecond in 1..140) {
			for (reindeerName in reindeerNames) {
				val state = stateByReindeerName.getValue(reindeerName)
				val info = infoByReindeerName.getValue(reindeerName)

				val cycleSeconds = info.run { secondsThatCanFly + secondsThatNeedToRest }
				val cycledSecond = currentSecond % cycleSeconds

				val isCurrentSecondInFlyTime = cycledSecond <= info.secondsThatCanFly
				if (isCurrentSecondInFlyTime) {
					stateByReindeerName[reindeerName] = state.copy(
						distanceTraveledInKm = state.distanceTraveledInKm + info.speedInKmPerSecond,
					)
				}
			}

			val maxTraveledDistanceInKm = stateByReindeerName.values.maxOfOrNull { it.distanceTraveledInKm } ?: 0

			val leadReindeerByName = stateByReindeerName.filter { (_, state) -> state.distanceTraveledInKm == maxTraveledDistanceInKm }

			leadReindeerByName.forEach { (name, state) ->

				stateByReindeerName[name] = state.copy(
					accumulatedPoints = state.accumulatedPoints + 1
				)
			}
			//println("$currentSecond --- ${stateByReindeerName.map { (name, state) -> "-name=$name, distance=${state.distanceTraveledInKm}, points=${state.accumulatedPoints}" }}")
		}

		println(stateByReindeerName)

		return -1
	}

	override val input = getFakeInput()
}


//region Utils

/**
 * This version is adapted from a ChatGPT solution.
 */
private fun ReindeerRaceInfo.getTotalDistanceInKmTraveled(totalTimeInSeconds: Int): Int {
	val cycleTime = secondsThatCanFly + secondsThatNeedToRest
	val cycleCount = totalTimeInSeconds / cycleTime

	val remainingTime = totalTimeInSeconds % cycleTime
	val remainingTimeFlying = clamp(remainingTime, 0, secondsThatCanFly)

	return cycleCount * secondsThatCanFly * speedInKmPerSecond + remainingTimeFlying * speedInKmPerSecond
}

private fun fromLineToReindeerRaceInfo(line: String): ReindeerRaceInfo {
	val result = reindeerRaceInfoRegex.matchEntire(line) ?: error("Error when trying to match this line: '$line'.")

	return result.destructured.let { (reindeerName, speedInKmPerSecond, secondsThatCanFly, secondsThatNeedToRest) ->

		ReindeerRaceInfo(
			reindeerName = reindeerName,
			speedInKmPerSecond = speedInKmPerSecond.toInt(),
			secondsThatCanFly = secondsThatCanFly.toInt(),
			secondsThatNeedToRest = secondsThatNeedToRest.toInt(),
		)
	}
}

private data class ReindeerRaceInfo(
	val reindeerName: String,
	val speedInKmPerSecond: Int,
	val secondsThatCanFly: Int,
	val secondsThatNeedToRest: Int,
)

private data class ReindeerRaceState(
	val distanceTraveledInKm: Int,
	val accumulatedPoints: Int,
)

private val reindeerRaceInfoRegex = "(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds\\.".toRegex()

//endregion

private fun getFakeInput() = """
    Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
    Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
""".trimIndent()

private fun getInput() = """
    Dancer can fly 27 km/s for 5 seconds, but then must rest for 132 seconds.
    Cupid can fly 22 km/s for 2 seconds, but then must rest for 41 seconds.
    Rudolph can fly 11 km/s for 5 seconds, but then must rest for 48 seconds.
    Donner can fly 28 km/s for 5 seconds, but then must rest for 134 seconds.
    Dasher can fly 4 km/s for 16 seconds, but then must rest for 55 seconds.
    Blitzen can fly 14 km/s for 3 seconds, but then must rest for 38 seconds.
    Prancer can fly 3 km/s for 21 seconds, but then must rest for 40 seconds.
    Comet can fly 18 km/s for 6 seconds, but then must rest for 103 seconds.
    Vixen can fly 18 km/s for 5 seconds, but then must rest for 84 seconds.
""".trimIndent()
