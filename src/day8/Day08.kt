package day8

import java.io.File

fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)
fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun main() {
    val input = File("src/day8/input.txt").readLines()
    val sampleInput1 = File("src/day8/sample.txt").readLines()
    val sampleInput2 = File("src/day8/sample02.txt").readLines()

    check(part1(sampleInput1) == 6)
    check(part2(sampleInput2).toInt() == 6)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(input: List<String>): Int {


    val rightLeft = input[0]
    val map = input.drop(2).associate { line ->
        val (p, qq) = line.split("=").map { it.trim() }
        val (ql, qr) = qq.removeSurrounding("(", ")").split(",").map { it.trim() }
        Pair(p, Pair(ql, qr))
    }
    var start = "AAA"
    var index = 0
    while (start != "ZZZ") {
        val q = map[start]!!
        val z = rightLeft[index % rightLeft.length]
        start = when (z) {
            'L' -> q.first
            'R' -> q.second
            else -> error("!")
        }
        index++
    }
    return index
}

fun part2(input: List<String>): Long {
    val map = input.asSequence().drop(2).associateBy(
        { it.substring(0, 3) },
        { Pair(it.substring(7, 10), it.substring(12, 15)) }
    )

    return map.keys.asSequence().filter { it.endsWith('A') }
        .map {
            var node = it
            var index = 0
            var steps = 0L
            while (!node.endsWith('Z')) {
                node = if (input[0][index] == 'L') map[node]!!.first else map[node]!!.second
                index = if (index < input[0].lastIndex) index + 1 else 0
                steps += 1
            }
            steps
        }
        .reduce(::lcm)
}
