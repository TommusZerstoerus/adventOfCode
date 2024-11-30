package `2023`.day2

import java.io.File

data class Game(val id: Int, val rgb: List<IntArray>)

fun main() {
    val sampleInput = File("src/day2/sample.txt").readText().lines().map { line ->
        val id = line.drop(5).takeWhile { it != ':' }.toInt()
        val rgb = line.substringAfter(": ").split("; ").map { cubes ->
            var red = 0
            var green = 0
            var blue = 0
            cubes.split(", ").forEach { cube ->
                val num = cube.substringBefore(" ").toInt()
                when {
                    cube.endsWith("red") -> red += num
                    cube.endsWith("green") -> green += num
                    cube.endsWith("blue") -> blue += num
                }
            }
            arrayOf(red, green, blue).toIntArray()
        }
        Game(id, rgb)
    }

    val input = File("src/day2/input.txt").readText().lines().map { line ->
        val id = line.drop(5).takeWhile { it != ':' }.toInt()
        val rgb = line.substringAfter(": ").split("; ").map { cubes ->
            var red = 0
            var green = 0
            var blue = 0
            cubes.split(", ").forEach { cube ->
                val num = cube.substringBefore(" ").toInt()
                when {
                    cube.endsWith("red") -> red += num
                    cube.endsWith("green") -> green += num
                    cube.endsWith("blue") -> blue += num
                }
            }
            arrayOf(red, green, blue).toIntArray()
        }
        Game(id, rgb)
    }
    check((part1(sampleInput)) == 8)
    check((part2(sampleInput)) == 2286)

    println("Part1 ${part1(input)}")
    println("Part2 ${part2(input)}")
}

fun part1(input: List<Game>): Int {
    return(input.filter { it.rgb.all { rgb -> rgb[0] <= 12 && rgb[1] <= 13 && rgb[2] <= 14 } }.sumOf { game -> game.id })
}

fun part2(input: List<Game>): Int {
    return(input.sumOf { game -> game.rgb.maxOf { it[0] } * game.rgb.maxOf { it[1] } * game.rgb.maxOf { it[2] } })
}

