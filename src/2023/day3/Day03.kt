package `2023`.day3

import java.io.File

fun main() {
    val input = File("src/day3/input.txt").readLines()
    val sampleInput = File("src/day3/sample.txt").readLines()

    check(part1(sampleInput) == 4361)
    check(part2(sampleInput) == 467835)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

private val numberRegex = Regex("[0-9]+")

private fun part1(lines: List<String>): Int {
    var sum = 0
    for ((y, line) in lines.withIndex()) {
        for (match in numberRegex.findAll(line)) {
            for ((adjX, adjY) in match.range.adjacent(y)) {
                if (lines.getOrNull(adjY)?.getOrNull(adjX)?.isNotSymbol() == true) {
                    sum += match.value.toInt()
                }
            }
        }
    }
    return sum
}

private fun part2(lines: List<String>): Int {
    val gears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()
    for ((y, line) in lines.withIndex()) {
        for (match in numberRegex.findAll(line)) {
            for ((adjX, adjY) in match.range.adjacent(y)) {
                if (lines.getOrNull(adjY)?.getOrNull(adjX) == '*') {
                    gears.getOrPut(adjX to adjY) { mutableListOf() }.add(match.value.toInt())
                }
            }
        }
    }
    return gears.values.filter { it.size == 2 }.sumOf { it.reduce(Int::times) }
}

private fun IntRange.adjacent(y: Int): Set<Pair<Int, Int>> = buildSet {
    for (x in this@adjacent) {
        add(x to y - 1)
        add(x to y + 1)
    }
    for (dy in -1..1) {
        add(first - 1 to y + dy)
        add(last + 1 to y + dy)
    }
}

private fun Char.isNotSymbol() = when (this) {
    '.', in '0'..'9' -> false
    else -> true
}
