package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.applyTransformation
import elianfabian.adventofcode.util.plusAssign

/**
 * --- Day 10: Elves Look, Elves Say --- https://adventofcode.com/2015/day/10
 */
object PuzzleYear2015Day10 : AocPuzzle(2015, 10) {

	override val partOneQuestion = "Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?"

	/**
	 * Today, the Elves are playing a game called look-and-say. They take turns making sequences by reading aloud the previous sequence and using that reading as the next sequence. For example, 211 is read as "one two, two ones", which becomes 1221 (1 2, 2 1s).
	 *
	 * Look-and-say sequences are generated iteratively, using the previous value as input for the next step. For each step, take the previous value, and replace each run of digits (like 111) with the number of digits (3) followed by the digit itself (1).
	 *
	 * For example:
	 *
	 * - 1 becomes 11 (1 copy of digit 1).
	 * - 11 becomes 21 (2 copies of digit 1).
	 * - 21 becomes 1211 (one 2 followed by one 1).
	 * - 1211 becomes 111221 (one 1, one 2, and two 1s).
	 * - 111221 becomes 312211 (three 1s, two 2s, and one 1).
	 *
	 * Starting with the digits in your puzzle input, apply this process 40 times. What is the length of the result?
	 */
	override fun getResultOfPartOne(): Int {
		val result = applyTransformation(times = 40, initialValue = input) { convertToLookAndSay(it) }

		return result.length
	}

	override val partTwoQuestion = "Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the new result?"

	/**
	 * Neat, right? You might also enjoy hearing John Conway talking about this sequence (that's Conway of Conway's Game of Life fame).

	 * Now, starting again with the digits in your puzzle input, apply this process 50 times. What is the length of the new result?
	 */
	override fun getResultOfPartTwo(): Int {
		val result = applyTransformation(times = 50, initialValue = input) { convertToLookAndSay(it) }

		return result.length
	}

	override val input = getInput()
}


//region Utils

private fun convertToLookAndSay(numberString: String): String {
	var previousNumber = Char.MIN_VALUE
	var currentNumberCount = 0

	// This is for better performance
	val resultSb = StringBuilder("")

	numberString.forEach { currentNumber ->

		if (currentNumber == previousNumber) {
			currentNumberCount++
		}
		else {
			resultSb += if (currentNumberCount != 0) "$currentNumberCount$previousNumber" else ""

			previousNumber = currentNumber
			currentNumberCount = 1
		}
	}

	resultSb += if (currentNumberCount != 0) "$currentNumberCount$previousNumber" else ""

	return resultSb.toString()
}

//endregion


private fun getInput() = "1321131112"
