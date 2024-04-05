package day7

import java.io.File

class Card(val strength: String, private val hands: IntArray, private val type: Int, val bid: Long) : Comparable<Card> {
    override fun compareTo(other: Card): Int {
        if (type < other.type) return -1
        if (type > other.type) return 1
        for (index in hands.indices) {
            if (hands[index] < other.hands[index]) return -1
            if (hands[index] > other.hands[index]) return 1
        }
        return 0
    }
}

fun convertType(group: List<Pair<Int, Int>>): Int {
    return when {
        group.size == 1 -> 1
        group.size == 2 && group[0].second == 4 -> 2
        group.size == 2 && group[0].second == 3 -> 3
        group.size == 3 && group[0].second == 3 -> 4
        group.size == 3 && group[0].second == 2 && group[1].second == 2 -> 5
        group.size == 4 && group[0].second == 2 -> 6
        else -> 7
    }
}

fun main() {
    val input = File("src/day7/input.txt").readLines()
    val sampleInput = File("src/day7/sample.txt").readLines()

    check(part1(sampleInput).toInt() == 6440)
    check(part2(sampleInput).toInt() == 5905)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(input: List<String>): Long {
    val stringList = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    val charList = input.map { line ->
        val (strength, bid) = line.split(" ")
        val heightList = strength.map { stringList.indexOf(it) }
        val group = heightList.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
        val type = convertType(group)
        Card(strength, heightList.toIntArray(), type, bid.toLong())
    }.sortedDescending()
    val sum = charList.withIndex().sumOf { (index, card) -> (index + 1) * card.bid }
    return (sum)
}

fun part2(input: List<String>): Long {
    val stringList = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
    val joker = stringList.indexOf('J')
    val charList = input.map { line ->
        val (strength, bid) = line.split(" ")
        val strengthMap = strength.map { stringList.indexOf(it) }
        val type = (0..<joker).minOf { joker ->
            val heightList = strengthMap.map { if (it == joker) joker else it }
            val group = heightList.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
            convertType(group)
        }
        Card(strength, strengthMap.toIntArray(), type, bid.toLong())
    }.sortedDescending()
    val sum = charList.withIndex().sumOf { (index, card) -> (index + 1) * card.bid }
    return (sum)
}