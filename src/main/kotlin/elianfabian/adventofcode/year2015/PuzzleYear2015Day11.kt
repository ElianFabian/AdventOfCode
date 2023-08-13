package elianfabian.adventofcode.year2015

import elianfabian.adventofcode.util.AocPuzzle
import elianfabian.adventofcode.util.plusAssign

/**
 * --- Day 11: Corporate Policy --- https://adventofcode.com/2015/day/11
 */
object PuzzleYear2015Day11 : AocPuzzle(2015, 11) {

	override val partOneQuestion = " Given Santa's current password (your puzzle input), what should his next password be?"

	/**
	 * Santa's previous password expired, and he needs help choosing a new one.
	 *
	 * To help him remember his new password after the old one expires, Santa has devised a method of coming up with a password based on the previous one. Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons), so he finds his new password by incrementing his old password string repeatedly until it is valid.
	 *
	 * Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around.
	 *
	 * Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:
	 *
	 * Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. They cannot skip letters; abd doesn't count.
	 * Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
	 * Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
	 * For example:
	 *
	 * - hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement (because it contains i and l).
	 * - abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
	 * - abbcegjk fails the third requirement, because it only has one double letter (bb).
	 * - The next password after abcdefgh is abcdffaa.
	 * - The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., since i is not allowed.
	 *
	 * Given Santa's current password (your puzzle input), what should his next password be?
	 */
	override fun getResultOfPartOne(): String {
		return input.nextPassword()
	}

	override val partTwoQuestion = "Santa's password expired again. What's the next one?"

	/**
	 * Santa's password expired again. What's the next one?
	 */
	override fun getResultOfPartTwo(): String {
		return input.nextPassword().nextPassword()
	}

	override val input = getInput()
}


//region Utils

private fun String.nextPassword(): String {
	var currentPassword = this

	do {
		currentPassword = incrementStringLikeANumber(currentPassword)
	}
	while (!isValidPassword(currentPassword))

	return currentPassword
}

private fun isValidPassword(password: String): Boolean {
	return containsAtLeastOneIncreasingStraightOfThreeLetters(password) &&
		doesNotContainIOL(password) &&
		containsAtLeastTwoDifferentNonOverlappingPairsOfLetters(password)
}

private fun incrementStringLikeANumber(string: String): String {
	val resultSb = StringBuilder()

	for (char in string.reversed()) {
		val newChar = char.nextInAlphabet()

		resultSb += newChar

		if (newChar == 'a') continue
		else {
			return string.dropLast(resultSb.length) + resultSb.reversed()
		}
	}

	return resultSb.toString()
}

private fun Char.nextInAlphabet(): Char {
	val index = alphabet.indexOf(this)

	return alphabet[(index + 1) % alphabet.size]
}

private val alphabet = listOf(
	'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
	'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
	'u', 'v', 'w', 'x', 'y', 'z',
)

/**
 * First requirement
 */
private fun containsAtLeastOneIncreasingStraightOfThreeLetters(string: String): Boolean {
	string.forEachIndexed { index, currentChar ->

		val currentCharCode = currentChar.code
		val nextCharCode = string.getOrNull(index + 1)?.code ?: -1
		val nextNextCharCode = string.getOrNull(index + 2)?.code ?: -1

		if (nextCharCode - currentCharCode == 1 && nextNextCharCode - nextCharCode == 1) return true
	}

	return false
}

/**
 * Second requirement
 */
private fun doesNotContainIOL(string: String) = !("i" in string || "o" in string || "l" in string)

/**
 * Third requirement
 */
private fun containsAtLeastTwoDifferentNonOverlappingPairsOfLetters(string: String): Boolean {
	val minPairCount = 2
	val letterPairs = mutableSetOf<String>()

	string.forEachIndexed { index, currentChar ->

		val nextChar = string.getOrNull(index + 1)

		if (currentChar == nextChar) {
			letterPairs += "$currentChar$nextChar"

			if (letterPairs.size >= minPairCount) return true
		}
	}

	return false
}

//endregion


private fun getInput() = "cqjxjnds"