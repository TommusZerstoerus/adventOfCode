package `2023`.day6

import java.io.File

fun main() {
    val input = File("src/day6/input.txt").readLines()
    val sampleInput = File("src/day6/sample.txt").readLines()

    check(part1(sampleInput).toInt() == 288)
    check(part2(sampleInput) == 71503)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(input: List<String>): Long {
    val timeList = input[0].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
    val distanceList = input[1].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toLong() }
    var result = 1L
    for (i in timeList.indices) {
        val time = timeList[i]
        val distance = distanceList[i]
        var numberOfWays = 0
        for (x in 1L..<time) {
            val y = (time - x) * x
            if (y > distance) numberOfWays++
        }
        result *= numberOfWays
    }
    return(result)
}

fun part2(input: List<String>): Int {
    val time = input[0].substringAfter(":").filter { it != ' ' }.toCharArray().concatToString().toLong()
    val distance = input[1].substringAfter(":").filter { it != ' ' }.toCharArray().concatToString().toLong()
    var numberOfWays = 0
    for (x in 1L..<time) {
        val y = (time - x) * x
        if (y > distance) numberOfWays++
    }
    return(numberOfWays)
}