package util

import java.awt.Color
import java.util.ArrayList

class Mechanics(private val sudokuBoard: Board) {
    var scanned: Boolean = false
        private set
    var helpCount: Int = 0
        private set

    init {
        scanned = false
        helpCount = 0
    }

    fun checkRowAt(x: Int, y: Int, pos: Int): MutableList<Int> {
        if (sudokuBoard.getDigit(pos).type == Digit.ValueType.EMPTY || sudokuBoard.getDigit(pos).type == Digit.ValueType.GUESS) {
            val check = ArrayList<Int>()
            for (i in 1 until sudokuBoard.numberCount + 1)
                check.add(i)
            for (i in 0 until sudokuBoard.numberCount) {
                val current = sudokuBoard.getDigit(i, y)
                if (i != x && current.size() == 1 && current.type != Digit.ValueType.GUESS) {
                    check.remove(current.get(0))
                }
            }
            sudokuBoard.getDigit(pos).type = Digit.ValueType.GUESS
            return check
        } else
            return sudokuBoard.getDigit(x, y).value
    }

    fun checkColumnAt(x: Int, y: Int, pos: Int): MutableList<Int> {
        if (sudokuBoard.getDigit(pos).type == Digit.ValueType.EMPTY || sudokuBoard.getDigit(pos).type == Digit.ValueType.GUESS) {
            val check = ArrayList<Int>()
            for (i in 1 until sudokuBoard.numberCount + 1) check.add(i)

            var i = x
            while (i < sudokuBoard.boxCount) {
                val current = sudokuBoard.getDigit(i)
                if (i != pos && current.size() == 1 && current.type != Digit.ValueType.GUESS) {
                    check.remove(current[0])
                }
                i += sudokuBoard.numberCount
            }
            sudokuBoard.getDigit(pos).type = Digit.ValueType.GUESS
            return check
        } else
            return sudokuBoard.getDigit(x, y).value
    }

    fun checkBoxAt(x: Int, y: Int, pos: Int): MutableList<Int> {
        if (sudokuBoard.getDigit(pos).type == Digit.ValueType.EMPTY || sudokuBoard.getDigit(pos).type == Digit.ValueType.GUESS) {
            val check = ArrayList<Int>()
            for (i in 1 until sudokuBoard.numberCount + 1) check.add(i)

            var fromPos = sudokuBoard.getPos(
                x / sudokuBoard.boxCountInLine * sudokuBoard.boxCountInLine,
                y / sudokuBoard.boxCountInColumn * sudokuBoard.boxCountInColumn
            )
            for (i in 0 until sudokuBoard.numberCount) {
                val current = sudokuBoard.getDigit(fromPos)
                if (fromPos != pos && current.size() == 1 && current.type != Digit.ValueType.GUESS) {
                    check.remove(current.get(0))
                }
                if ((1 + i) % sudokuBoard.boxCountInLine == 0)
                    fromPos = fromPos - (sudokuBoard.boxCountInLine - 1) + sudokuBoard.numberCount
                else
                    fromPos++
            }
            sudokuBoard.getDigit(pos).type = Digit.ValueType.GUESS
            return check
        } else
            return sudokuBoard.getDigit(x, y).value
    }

    fun checkAt(x: Int, y: Int): Boolean {
        val pos = sudokuBoard.getPos(x, y)
        val before = sudokuBoard.getDigit(pos).clone() as ArrayList<Int>
        sudokuBoard.getDigit(pos).value = this.checkRowAt(x, y, pos)
        sudokuBoard.getDigit(pos).intersection(this.checkColumnAt(x, y, pos))
        sudokuBoard.getDigit(pos).intersection(this.checkBoxAt(x, y, pos))
        return before != sudokuBoard.getDigit(pos).value
    }

    fun checkAll(): Boolean {
        var modded = false
        for (i in 0 until sudokuBoard.numberCount) {
            for (j in 0 until sudokuBoard.numberCount) {
                if (this.checkAt(j, i))
                    modded = true
            }
        }
        helpCount++
        return modded
    }

    fun solvedAt(pos: Int): Boolean {
        val current = sudokuBoard.getDigit(pos)
        if (current.size() == 1 && current.type == Digit.ValueType.GUESS) {
            current.type = Digit.ValueType.NUMBER
            val posX = sudokuBoard.getX(pos)
            val posY = sudokuBoard.getY(pos)
            sudokuBoard.highlight.add(Highlight(posX, posY, Color.GREEN))
            return true
        } else
            return false
    }

    fun solvedAll(): Boolean {
        var found = false
        var i = 0
        while (i < sudokuBoard.boxCount && !found) {
            found = solvedAt(i)
            i++
        }
        helpCount++
        return found
    }

    private fun scanRowAt(x: Int, y: Int, pos: Int): Int {
        var current = sudokuBoard.getDigit(pos)
        val currentList = current.value.toMutableList()
        for (i in 0 until sudokuBoard.numberCount) {
            current = sudokuBoard.getDigit(i, y)
            if (i != x && current.type == Digit.ValueType.GUESS) {
                for (j in 0 until current.size()) {
                    currentList.remove(current.get(j))
                }
            }
            sudokuBoard.highlight.add(Highlight(i, y, Color.YELLOW))
        }
        if (currentList.size == 1) {
            sudokuBoard.highlight.add(Highlight(x, y, Color.GREEN))
            return currentList[0]
        } else {
            sudokuBoard.highlight.clear()
            return 0
        }
    }

    private fun scanColumnAt(x: Int, y: Int, pos: Int): Int {
        var current = sudokuBoard.getDigit(pos)
        val currentList = current.value.toMutableList()
        for (i in 0 until sudokuBoard.numberCount) {
            current = sudokuBoard.getDigit(x, i)
            if (i != y && current.type == Digit.ValueType.GUESS) {
                for (j in 0 until current.size()) {
                    currentList.remove(current.get(j))
                }
            }
            sudokuBoard.highlight.add(Highlight(x, i, Color.YELLOW))
        }
        if (currentList.size == 1) {
            sudokuBoard.highlight.add(Highlight(x, y, Color.GREEN))
            return currentList[0]
        } else {
            sudokuBoard.highlight.clear()
            return 0
        }
    }

    private fun scanBoxAt(x: Int, y: Int, pos: Int): Int {
        var current = sudokuBoard.getDigit(pos)
        val currentList = current.value.toMutableList()

        var fromPos = sudokuBoard.getPos(
            x / sudokuBoard.boxCountInLine * sudokuBoard.boxCountInLine,
            y / sudokuBoard.boxCountInColumn * sudokuBoard.boxCountInColumn
        )
        println("pos: " + pos + "fromPos:" + fromPos)
        for (i in 0 until sudokuBoard.numberCount) {
            current = sudokuBoard.getDigit(fromPos)
            if (pos != fromPos && current.type == Digit.ValueType.GUESS) {
                for (j in 0 until current.size()) {
                    currentList.remove(current.get(j))
                }
            }
            val fromPosX = sudokuBoard.getX(fromPos)
            val fromPosY = sudokuBoard.getY(fromPos)
            sudokuBoard.highlight.add(Highlight(fromPosX, fromPosY, Color.YELLOW))
            if ((1 + i) % sudokuBoard.boxCountInLine == 0)
                fromPos = fromPos - (sudokuBoard.boxCountInLine - 1) + sudokuBoard.numberCount
            else
                fromPos++
        }
        if (currentList.size == 1) {
            sudokuBoard.highlight.add(Highlight(x, y, Color.GREEN))
            return currentList[0]
        } else {
            sudokuBoard.highlight.clear()
            return 0
        }
    }

    fun scanAt(x: Int, y: Int): Boolean {
        val pos = sudokuBoard.getPos(x, y)
        val current = sudokuBoard.getDigit(x, y)
        var scan = 1
        if (current.type == Digit.ValueType.GUESS) {
            if (scanRowAt(x, y, pos) <= 0) {
                if (scanColumnAt(x, y, pos) <= 0) {
                    if (scanBoxAt(x, y, pos) > 0) {
                        if (scanned)
                            current.add(scanBoxAt(x, y, pos), Digit.ValueType.NUMBER)
                        return true
                    }
                } else {
                    if (scanned)
                        current.add(scanColumnAt(x, y, pos), Digit.ValueType.NUMBER)
                    return true
                }
            } else {
                if (scanned)
                    current.add(scanRowAt(x, y, pos), Digit.ValueType.NUMBER)
                return true
            }
        }
        return false
    }

    fun scanAll(): Boolean {
        var found = false
        var i = 0
        while (i < sudokuBoard.numberCount && !found) {
            var j = 0
            while (j < sudokuBoard.numberCount && !found) {
                found = scanAt(j, i)
                j++
            }
            i++
        }
        if (found)
            scanned = !scanned
        helpCount++
        return found
    }

    fun solutionCheck(): Boolean {
        var finished = true
        for (i in 0 until sudokuBoard.boxCount) {
            val current = sudokuBoard.getDigit(i)
            if (current.type == Digit.ValueType.NUMBER) {
                if (current[0] != sudokuBoard.getAnswer(i)) {
                    val xPos = sudokuBoard.getX(i)
                    val yPos = sudokuBoard.getY(i)
                    sudokuBoard.highlight.add(Highlight(xPos, yPos, Color.RED))
                    finished = false
                }
            } else if (current.type != Digit.ValueType.CLUE) {
                finished = false
            }
        }
        if (!finished)
            helpCount++
        return finished
    }

    fun solutionShow() {
        for (i in 0 until sudokuBoard.boxCount) {
            if (sudokuBoard.getDigit(i).type != Digit.ValueType.CLUE) {
                sudokuBoard.getDigit(i).add(sudokuBoard.getAnswer(i), Digit.ValueType.NUMBER)
            }
        }
    }
}