package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import java.math.BigInteger
import java.security.MessageDigest

/**
 * --- Day 4: The Ideal Stocking Stuffer --- https://adventofcode.com/2015/day/4
 */
object PuzzleYear2015Day4 : AocPuzzle(2015, 4) {

	override val partOneQuestion = "What is the decimal number that whose concatenation with the key and applying the md5 produces a string that starts with 5 zeros?"

	/**
	 * Santa needs help mining some AdventCoins (very similar to bitcoins) to use as gifts for all the economically forward-thinking little girls and boys.
	 *
	 * To do this, he needs to find MD5 hashes which, in hexadecimal, start with at least five zeroes. The input to the MD5 hash is some secret key (your puzzle input, given below) followed by a number in decimal. To mine AdventCoins, you must find Santa the lowest positive number (no leading zeroes: 1, 2, 3, ...) that produces such a hash.
	 *
	 * For example:
	 *
	 * - If your secret key is abcdef, the answer is 609043, because the MD5 hash of abcdef609043 starts with five zeroes (000001dbbfa...), and it is the lowest such number to do so.
	 * - If your secret key is pqrstuv, the lowest number it combines with to make an MD5 hash starting with five zeroes is 1048970; that is, the MD5 hash of pqrstuv1048970 looks like 000006136ef....
	 */
	override fun getResultOfPartOne(): Int {
		val fiveZeros = "0".repeat(5)

		for (number in 1..Int.MAX_VALUE) {
			val keyAndDecimalNumber = "$input$number"

			if (md5(keyAndDecimalNumber).startsWith(fiveZeros)) return number
		}

		return -1
	}

	override val partTwoQuestion = "Now find one that starts with six zeroes."

	/**
	 * Now find one that starts with six zeroes.
	 */
	override fun getResultOfPartTwo(): Int {
		val sixZeros = "0".repeat(6)

		for (number in 1..Int.MAX_VALUE) {
			val keyAndDecimalNumber = "$input$number"

			if (md5(keyAndDecimalNumber).startsWith(sixZeros)) return number
		}

		return -1
	}

	override val input = getInput()

}


//region Utils

private fun md5(input: String): String {
	val md = MessageDigest.getInstance("MD5")
	return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

//endregion


private fun getInput() = "bgvyzdsv"
