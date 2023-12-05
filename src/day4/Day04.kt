package day4

import java.io.File

private fun getPlayedNumbers(str: String) =
    str.substringAfter("|").trim().split(" ").filterNot { it.isEmpty() }.map { it.toInt() }

private fun getWinningNumbers(str: String) =
    str.substringAfter(":").substringBefore("|").trim().split(" ").filterNot { it.isEmpty() }.map { it.toInt() }

data class Game(val id: Int, val winningNumbers: List<Int>, val playedNumbers: List<Int>, var count: Int) {
    fun getWinningSet(): List<Int> {
        return this.playedNumbers.filter { winningNumbers.contains(it) }
    }
}

fun main() {
    val sampleInput = File("src/day4/sample.txt").readText().lines()
    val input = File("src/day4/input.txt").readText().lines()


    check((part1(sampleInput)) == 13)
    check((part2(sampleInput)) == 30)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(input: List<String>): Int {
    val winningSum = input.sumOf { game ->
        var gameTotal = 0
        val winningNumbers = getWinningNumbers(game)
        val playedNumbers = getPlayedNumbers(game)
        playedNumbers.forEach { number ->
            if (winningNumbers.contains(number)) {
                when (gameTotal) {
                    0 -> gameTotal = 1
                    1 -> gameTotal = 2
                    else -> gameTotal += gameTotal
                }
            }
        }
        gameTotal
    }
    return winningSum
}


fun part2(lines: List<String>): Int {

    val gameList = lines.map { str ->
        val gameId = str.substringBefore(":").split(" ").filterNot { it.isEmpty() }[1].toInt()
        val winningNumbers = getWinningNumbers(str)
        val playedNumbers = getPlayedNumbers(str)

        Game(gameId, winningNumbers, playedNumbers, 1)
    }
    var winningSum = 0

    gameList.forEachIndexed { i, game ->
        winningSum += game.count
        val winnings = game.getWinningSet().count()
        for (c in 1..<winnings + 1) {
            gameList[i + c].count += game.count
        }
    }
    return winningSum
}