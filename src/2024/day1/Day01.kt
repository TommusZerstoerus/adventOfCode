package `2024`.day1

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("src/2024/day1/input.txt").readLines()
    val sample = File("src/2024/day1/sample.txt").readLines()
    val part1 = part1(input)
    println(part1)
    val part2 = part2(input)
    println(part2)
}

fun part1(lines: List<String>): Int {
    val pairs = lines.map { line ->
        val (a, b) = line.split("\\s+".toRegex()).map { it.toInt() }
        a to b
    }

    val sortedLeft = pairs.map { it.first }.sorted()
    val sortedRight = pairs.map { it.second }.sorted()

    val differences = sortedLeft.zip(sortedRight).map { (left, right) ->
        abs(left - right)
    }

    return differences.sum()
}

fun part2(lines: List<String>): Int {
    val pairs = lines.map { line ->
        val (a, b) = line.split("\\s+".toRegex()).map { it.toInt() }
        a to b
    }

    val leftList = pairs.map { it.first }
    val rightList = pairs.map { it.second }

    val resultList = leftList.map { left ->
        val count = rightList.count { it == left }
        left * count
    }

    return resultList.sum()

}