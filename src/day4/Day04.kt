package day4

import java.io.File

private fun parseLine(line: String): Pair<Set<Int>, Set<Int>> = line.substringAfter(": ").split(" | ")
    .map { it.split(' ').filterNot(String::isBlank).mapTo(hashSetOf(), String::toInt) }.let { (a, b) -> a to b }

private data class Card(val winning: Set<Int>, val mine: Set<Int>, var count: Int = 1) {
    val matches = (winning intersect mine).size
}

fun main() {
    val sampleInput = File("src/day4/sample.txt").readText().lines()
    val input = File("src/day4/input.txt").readText().lines()


    check((part1(sampleInput)) == 13)
    check((part2(sampleInput)) == 30)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(lines: List<String>): Int {
    var sum = 0
    for (line in lines) {
        val (winning, mine) = parseLine(line)
        sum += (winning intersect mine).size.let { if (it == 0) 0 else 1 shl (it - 1) }
    }
    return sum
}

fun part2(lines: List<String>): Int {
    val cards = lines.map { parseLine(it).let { (winning, mine) -> Card(winning, mine) } }
    var num = cards.size
    outer@ while (true) {
        for ((i, card) in cards.withIndex()) {
            val count = card.count
            if (count == 0) continue
            val matches = card.matches
            for (next in 1..matches) {
                cards[i + next].count += count
                num += count
            }
            card.count = 0
            continue@outer
        }
        break
    }
    return num
}