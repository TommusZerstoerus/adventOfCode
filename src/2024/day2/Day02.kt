package `2024`.day2

import java.io.File

fun main() {
    val input = File("src/2024/day2/input.txt").readLines()
    val sample = File("src/2024/day2/sample.txt").readLines()
    part1(input)
    if (part1(sample) == 2) {
        println(part1(input))
    } else {
        println("Part 1 fehlerhaft")
    }

    if (part2(sample) == 4) {
        println(part2(input))
    } else {
        println("Part 2 fehlerhaft")
    }
}

fun part1(lines: List<String>): Int {
    return lines.count { line ->
        val numbers = line.split(" ").map { it.toInt() }
        checkIfSafe(numbers)
    }
}

fun part2(lines: List<String>): Int {
    val safeReportsCount = lines.count { line ->
        val numbers = line.split(" ").map { it.toInt() }
        if (!checkIfSafe(numbers)) {
            numbers.indices.any { index ->
                val modifiedList = numbers.toMutableList()
                modifiedList.removeAt(index)
                checkIfSafe(modifiedList)
            }
        } else {
            true
        }
    }
    return safeReportsCount
}

fun checkIfSafe(numbers: List<Int>): Boolean {
    val differences = numbers.zipWithNext().map { (a, b) -> b - a }
    val allIncreasing = differences.all { it in 1..3 }
    val allDecreasing = differences.all { it in -3..-1 }
    return allIncreasing || allDecreasing
}