package util

import java.io.*

class Board : Serializable {
    private var puzzle: MutableList<Digit>
    private var answer: MutableList<Int>
    var boxHeight: Int = 0
    var boxWidth: Int = 0
    var numberCount: Int = 0
    var boxCount: Int = 0

    private var posX: MutableList<Int>
    private var posY: MutableList<Int>

    constructor(x: Int, y: Int) {
        this.numberCount = x * y
        this.boxCount = numberCount * numberCount
        this.puzzle = mutableListOf()
        this.boxHeight = y
        this.boxWidth = x
        for (i in 0 until boxCount) {
            puzzle.add(i, Digit())
        }
        posX = mutableListOf()
        posY = mutableListOf()
        for (i in 0 until boxCount) {
            posX.add(i, 0)
            posY.add(i, 0)
        }
        answer = mutableListOf()
    }

    constructor(x: Int, y: Int, fname: String) {
        this.numberCount = x * y
        this.boxCount = numberCount * numberCount
        this.puzzle = mutableListOf()
        this.boxHeight = y
        this.boxWidth = x
        for (i in 0 until boxCount) {
            puzzle.add(i, Digit())
        }
        posX = mutableListOf()
        posY = mutableListOf()
        for (i in 0 until boxCount) {
            posX.add(i, 0)
            posY.add(i, 0)
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
                        puzzle[count] = Digit(number, Digit.VALUE_TYPE.CLUE)
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

    fun getPosX(pos: Int): Int {
        return posX[pos]
    }

    fun setPosX(pos: Int, value: Int) {
        this.posX[pos] = value
    }

    fun getPosY(pos: Int): Int {
        return posY[pos]
    }

    fun setPosY(pos: Int, value: Int) {
        this.posY[pos] = value
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
