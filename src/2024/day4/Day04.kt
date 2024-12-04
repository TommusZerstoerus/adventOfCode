package `2024`.day4

import java.io.File

fun main() {
    val input = File("src/2024/day4/input.txt").readLines()
    val sample = File("src/2024/day4/sample.txt").readLines()
    if (part1(sample) == 18) {
        println(part1(input))
    } else {
        println("Part 1 fehlerhaft")
    }

    if (part2(sample) == 9) {
        println(part2(input))
    } else {
        println("Part 2 fehlerhaft")
    }
}

fun part1(lines: List<String>): Int {
    val directions = listOf(
        Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, -1),
        Pair(0, -1), Pair(-1, 0), Pair(-1, -1), Pair(-1, 1)
    )
    val rows = lines.size
    val cols = lines[0].length
    var count = 0

    for (row in 0 until rows) {
        for (col in 0 until cols) {
            for (direction in directions) {
                if (checkWord(lines, row, col, direction.first, direction.second)) {
                    count++
                }
            }
        }
    }
    return count
}

fun part2(lines: List<String>): Int {
    val crosses = listOf(
        listOf('M', 'M', 'S', 'S'), listOf('S', 'M', 'M', 'S'),
        listOf('S', 'S', 'M', 'M'), listOf('M', 'S', 'S', 'M')
    )
    val rows = lines.size
    val cols = lines[0].length
    var count = 0

    for (row in 1 until rows - 1) {
        for (col in 1 until cols - 1) {
            val diagonals = listOf(
                lines[row - 1][col + 1], lines[row + 1][col + 1],
                lines[row + 1][col - 1], lines[row - 1][col - 1]
            )
            if (lines[row][col] == 'A' && crosses.contains(diagonals)) {
                count++
            }
        }
    }
    return count
}

fun checkWord(grid: List<String>, row: Int, col: Int, rowDir: Int, colDir: Int): Boolean {
    val word = "XMAS"
    val rows = grid.size
    val cols = grid[0].length
    for (index in word.indices) {
        val newRow = row + index * rowDir
        val newCol = col + index * colDir
        if (newRow !in 0 until rows || newCol !in 0 until cols || grid[newRow][newCol] != word[index]) {
            return false
        }
    }
    return true
}