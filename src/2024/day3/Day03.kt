package `2024`.day3

import java.io.File

fun main() {
    val input = File("src/2024/day3/input.txt").readLines()
    val sample = File("src/2024/day3/sample.txt").readLines()
    if (part1(sample) == 161) {
        println(part1(input))
    } else {
        println("Part 1 fehlerhaft")
    }

    if (part2(sample) == 48) {
        println(part2(input))
    } else {
        println("Part 2 fehlerhaft")
    }
}

fun part1(lines: List<String>): Int {
    val regex = Regex("""mul\(\d+,\d+\)""")
    val list = lines.flatMap { line ->
        regex.findAll(line).map { it.value }.toList()
    }
    val totalCalculation = list.sumOf { calculate(it) }
    return totalCalculation
}

fun part2(lines: List<String>): Int {
    val mulRegex = Regex("""mul\((\d+),(\d+)\)""")
    val doRegex = Regex("""do\(\)""")
    val dontRegex = Regex("""don't\(\)""")

    var mulEnabled = true
    var totalSum = 0
    for (line in lines) {
        val commands = Regex("""do\(\)|don't\(\)|mul\(\d+,\d+\)""").findAll(line)
        for (command in commands) {
            val text = command.value
            when {
                doRegex.matches(text) -> {
                    mulEnabled = true
                }
                dontRegex.matches(text) -> {
                    mulEnabled = false
                }
                mulRegex.matches(text) -> {
                    if (mulEnabled) {
                        totalSum += calculate(text)
                    }
                }
            }
        }
    }
    return totalSum
}

fun calculate(seq: String): Int {
    val regex = Regex("""mul\((\d+),(\d+)\)""")
    return regex.find(seq)?.destructured?.let { (num1, num2) ->
        num1.toInt() * num2.toInt()
    } ?: 0
}