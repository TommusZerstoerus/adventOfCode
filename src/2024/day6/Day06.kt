package `2024`.day6

import java.io.File

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

data class Coordinates(val x: Int, val y: Int)
data class VisitedEntry(val previousPosition: Coordinates, val direction: Direction)

fun main() {
    val input = File("src/2024/day6/input.txt").readLines()
    val sample = File("src/2024/day6/sample.txt").readLines()

    if (part1(sample) == 41) println("part1 ${part1(input)}")
    else println("Part 1 fehlerhaft")

    if (part2(sample) == 6) println("part2 ${part2(input)}")
    else println("Part 2 fehlerhaft")
}

fun part1(lines: List<String>): Int = predictGuardPath(lines)

fun part2(lines: List<String>): Int = makeGuardStuckInLoop(lines)

fun makeGuardStuckInLoop(lines: List<String>): Int {
    val (visited, visitedEntry) = makeGuardMove(lines)
    val guardStartPos = getPlayerCoordinates(lines)
    val mutableVisited = visited.toMutableSet()
    mutableVisited.remove(guardStartPos)
    var loopCount = 0

    val mapDump = lines.map { it.toCharArray() }

    for (visitedCoordinate in mutableVisited) {
        val mapCopy = mapDump.map { it.copyOf() }
        mapCopy[visitedCoordinate.x][visitedCoordinate.y] = '#'

        val (pos, direction) = visitedEntry[visitedCoordinate]!!
        val (visitedCopy, _) = makeGuardMove(mapCopy.map { it.joinToString("") }, pos, direction)
        if (visitedCopy.isEmpty()) {
            loopCount++
        }
    }

    return loopCount
}

fun makeGuardMove(
    map: List<String>, startPos: Coordinates? = null, startDirection: Direction? = null
): Pair<Set<Coordinates>, Map<Coordinates, VisitedEntry>> {
    val fieldHeight = map.size
    val fieldWidth = map[0].length
    var position = startPos ?: getPlayerCoordinates(map)
    var direction = startDirection ?: Direction.UP

    val visited = mutableSetOf<Coordinates>()
    val visitedEntries = mutableMapOf<Coordinates, VisitedEntry>()

    while (true) {
        visited.add(position)
        val nextPosition = getNextPosition(position.x, position.y, direction)

        if (nextPosition.x !in 0 until fieldHeight || nextPosition.y !in 0 until fieldWidth) {
            return visited to visitedEntries
        }

        if (isObstacle(nextPosition, map)) {
            direction = turnRight(direction)
        } else {
            visitedEntries[nextPosition]?.let {
                if (it == VisitedEntry(position, direction)) return emptySet<Coordinates>() to emptyMap<_, _>()
            } ?: run {
                visitedEntries[nextPosition] = VisitedEntry(position, direction)
            }
            position = nextPosition
        }
    }
}

fun predictGuardPath(lines: List<String>): Int {
    val visited = mutableSetOf<Coordinates>()
    val fieldHeight = lines.size
    val fieldWidth = lines[0].length
    var (row, col) = getPlayerCoordinates(lines)
    var direction = Direction.UP

    while (true) {
        visited.add(Coordinates(row, col))
        val nextPosition = getNextPosition(row, col, direction)

        if (nextPosition.x !in 0 until fieldHeight || nextPosition.y !in 0 until fieldWidth) {
            break
        }

        if (isObstacle(nextPosition, lines)) {
            direction = turnRight(direction)
        } else {
            row = nextPosition.x
            col = nextPosition.y
        }
    }

    return visited.size
}

fun isObstacle(position: Coordinates, map: List<String>): Boolean {
    val (row, col) = position
    return map.getOrNull(row)?.getOrNull(col) == '#'
}

fun turnRight(direction: Direction): Direction {
    return when (direction) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }
}

fun getNextPosition(row: Int, col: Int, direction: Direction): Coordinates {
    return when (direction) {
        Direction.UP -> Coordinates(row - 1, col)
        Direction.RIGHT -> Coordinates(row, col + 1)
        Direction.DOWN -> Coordinates(row + 1, col)
        Direction.LEFT -> Coordinates(row, col - 1)
    }
}

fun getPlayerCoordinates(map: List<String>): Coordinates {
    for (row in map.indices) {
        for (col in map[row].indices) {
            if (map[row][col] == '^') {
                return Coordinates(row, col)
            }
        }
    }
    throw IllegalArgumentException("Player not found")
}
