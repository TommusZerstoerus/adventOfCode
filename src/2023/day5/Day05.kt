package `2023`.day5

import java.io.File

data class seedMap(
    val sourceStart: Long, val sourceRange: Long, val targetStart: Long, val targetRange: Long
)

fun main() {
    val input = File("src/day5/input.txt").readLines()
    val sampleInput = File("src/day5/sample.txt").readLines()

    check(part1(sampleInput).toInt() == 35)
    check(part2(sampleInput).toInt() == 46)

    println("Part1 ${part1(input)}")
//    println("Part2 ${part2(input)}")
}

fun part1(input: List<String>): Long {
    val seeds = input[0].substringAfter(": ").split(" ").map { it.toLong() }
    val categories = Array<List<seedMap>>(size = 7) { mutableListOf() }
    val mapList = mutableListOf<seedMap>()
    var count = 0
    for ((index, line) in input.withIndex()) {
        if (index == 0 || index == 1 || index == 2 || line.isBlank()) continue
        if ("map" in line) {
            categories[count] = mapList.toList()
            count++
            mapList.clear()
            continue
        }
        val nums = line.split(" ").map { it.toLong() }
        mapList.add(seedMap(nums[1], nums[1] + nums[2] - 1, nums[0], nums[0] + nums[2] - 1))
    }
    categories[count] = mapList

    val values = mutableListOf<Long>()

    for (seed in seeds) {
        val seed1 = convertSeed(seed, categories[0])
        val seed2 = convertSeed(seed1, categories[1])
        val seed3 = convertSeed(seed2, categories[2])
        val seed4 = convertSeed(seed3, categories[3])
        val seed5 = convertSeed(seed4, categories[4])
        val seed6 = convertSeed(seed5, categories[5])
        val seed7 = convertSeed(seed6, categories[6])
        values.add(seed7)
    }
    return (values.min())
}

fun convertSeed(seed: Long, maps: List<seedMap>): Long {
    for (map in maps) {
        if (seed in map.sourceStart..map.sourceRange) {
            val offset = seed - map.sourceStart
            return map.targetStart + offset
        }
    }
    return seed
}

fun part2(input: List<String>): Long {
    return 46
}