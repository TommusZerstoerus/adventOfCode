package `2024`.day5

import java.io.File

fun main() {
    val input = File("src/2024/day5/input.txt").readLines()
    val sample = File("src/2024/day5/sample.txt").readLines()

    if (part1(sample) == 143) println("part1 ${part1(input)}")
    else println("Part 1 fehlerhaft")

    if (part2(sample) == 123) println("part2 ${part2(input)}")
    else println("Part 2 fehlerhaft")
}

fun extractRulesAndUpdates(lines: List<String>): Pair<MutableList<Pair<Int, Int>>, MutableList<List<Int>>> {
    val rules = mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()
    var isRulesSection = true

    lines.forEach { line ->
        when {
            line.isBlank() -> isRulesSection = false
            isRulesSection -> {
                val (a, b) = line.split("|").map { it.toInt() }
                rules.add(a to b)
            }
            else -> {
                val update = line.split(",").map { it.toInt() }
                updates.add(update)
            }
        }
    }
    return rules to updates
}

fun part1(lines: List<String>): Int {
    val (rules, updates) = extractRulesAndUpdates(lines)
    return updates.filter { isCorrectOrder(it, rules) }
        .sumOf { it[it.size / 2] }
}

fun part2(lines: List<String>): Int {
    val (rules, updates) = extractRulesAndUpdates(lines)
    return updates.filterNot { isCorrectOrder(it, rules) }
        .sumOf { sortUpdateToCorrectOrder(it, rules)[it.size / 2] }
}

fun isCorrectOrder(update: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    val indexMap = update.withIndex().associate { it.value to it.index }
    return rules.all { (a, b) -> !(a in indexMap && b in indexMap && indexMap[a]!! > indexMap[b]!!) }
}

fun sortUpdateToCorrectOrder(update: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
    val indexMap = update.withIndex().associate { it.value to it.index }.toMutableMap()
    val sortedList = update.toMutableList()
    var changed: Boolean

    do {
        changed = false
        for ((a, b) in rules) {
            if (a in indexMap && b in indexMap) {
                val indexA = indexMap[a]!!
                val indexB = indexMap[b]!!
                if (indexA > indexB) {
                    sortedList[indexA] = b
                    sortedList[indexB] = a
                    indexMap[a] = indexB
                    indexMap[b] = indexA
                    changed = true
                }
            }
        }
    } while (changed)

    return sortedList
}