package day1

import java.io.File

fun main() {
    val input = File("src/day1/input.txt").readText().lines()
    println("Part1 : ${part1(input)}")
    println("Part2 : ${part2(input)}")
}

fun part1(lines: List<String>) =
    lines.sumOf { string -> string.filter { word -> word.isDigit() }.let { it.first() + it.last().toString() }.toInt() }

fun part2(lines: List<String>) = lines.map { str ->
    str.windowed(5, 1, true) {
        when {
            it[0].isDigit() -> it[0].toString()
            it.startsWith("one") -> "1"
            it.startsWith("two") -> "2"
            it.startsWith("three") -> "3"
            it.startsWith("four") -> "4"
            it.startsWith("five") -> "5"
            it.startsWith("six") -> "6"
            it.startsWith("seven") -> "7"
            it.startsWith("eight") -> "8"
            it.startsWith("nine") -> "9"
            else -> ""
        }
    }
}.map {
    it.filter { c -> c.isNotBlank() }
}.sumOf { (it.first() + it.last()).toInt() }
