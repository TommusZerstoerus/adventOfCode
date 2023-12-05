package day5

import java.io.File

data class Range(val destination: Long, val source: Long, val length: Long)

fun main() {
    val input = File("src/day5/sample.txt").readLines()
//    val sampleInput = File("src/day5/sample.txt").readLines()
//
//    check(part1(sampleInput) == 5)
//    check(part2(sampleInput) == 5)
//
//    println("Part1 ${part1(input)}")
//    println("Part2 ${part2(input)}")
    part1(input)
}

fun part1(input: List<String>): Int {
    val seeds = input[0].substringAfter(":").trim().split(" ").map { it.toLong() }
    println(seeds)
    return 5
}

fun part2(input: List<String>): Int {
    return 5
}