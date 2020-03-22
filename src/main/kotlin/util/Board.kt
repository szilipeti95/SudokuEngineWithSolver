package util

import java.io.*

class Board : Serializable {
    private var puzzle: MutableList<Digit>
    private var answer: MutableList<Int>

    var highlight: MutableList<Highlight> = mutableListOf()
    var boxCountInColumn: Int = 0
    var boxCountInLine: Int = 0
    var numberCount: Int = 0
    var boxCount: Int = 0

    constructor(x: Int, y: Int) {
        this.numberCount = x * y
        this.boxCount = numberCount * numberCount
        this.puzzle = mutableListOf()
        this.boxCountInColumn = y
        this.boxCountInLine = x
        for (i in 0 until boxCount) {
            puzzle.add(i, Digit())
        }
        answer = mutableListOf()
    }

    constructor(x: Int, y: Int, fname: String) {
        this.numberCount = x * y
        this.boxCount = numberCount * numberCount
        this.puzzle = mutableListOf()
        this.boxCountInColumn = y
        this.boxCountInLine = x
        for (i in 0 until boxCount) {
            puzzle.add(i, Digit())
        }
        answer = mutableListOf()
        try {
            val br = BufferedReader(FileReader(fname))
            var read: String
            var numbers: Array<String>
            var number: Int
            var count = 0
            for (j in 0 until numberCount) {
                read = br.readLine()
                numbers = read.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in 0 until numberCount) {
                    number = Integer.parseInt(numbers[i])
                    if (number != 0) {
                        puzzle[count] = Digit(number, Digit.ValueType.CLUE)
                    }
                    count++
                }
            }
            count = 0
            for (j in 0 until numberCount) {
                read = br.readLine()
                numbers = read.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (i in 0 until numberCount) {
                    this.answer.add(count, Integer.parseInt((numbers[i])))
                    count++
                }
            }
            br.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    fun getX(pos: Int): Int {
        return pos % numberCount
    }

    fun getY(pos: Int): Int {
        return pos / numberCount
    }

    fun getPos(x: Int, y: Int): Int {
        return y * this.numberCount + x
    }

    fun getDigit(x: Int, y: Int): Digit {
        return puzzle[getPos(x, y)]
    }

    fun getDigit(pos: Int): Digit {
        return puzzle[pos]
    }

    fun setDigit(toSet: Digit, x: Int, y: Int) {
        puzzle[getPos(x, y)] = toSet
    }

    fun setDigit(toSet: Digit, pos: Int) {
        puzzle[pos] = toSet
    }

    fun getAnswer(pos: Int): Int {
        return this.answer[pos]
    }

    fun setAnswer(answer: Int, pos: Int) {
        this.answer[pos] = answer
    }

    fun copy(): Board? {
        var obj: Board? = null
        try {
            val bos = ByteArrayOutputStream()
            val out = ObjectOutputStream(bos)
            out.writeObject(this)
            out.flush()
            out.close()

            val `in` = ObjectInputStream(
                ByteArrayInputStream(bos.toByteArray())
            )
            obj = `in`.readObject() as Board
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return obj
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }
}
