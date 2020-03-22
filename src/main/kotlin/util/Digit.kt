package util

import java.io.Serializable
import java.util.*

class Digit: Serializable {
    var value: MutableList<Int> = mutableListOf()
    var type: ValueType

    enum class ValueType {
        EMPTY,
        CLUE,
        NUMBER,
        GUESS
    }

    constructor(value: Int, type: ValueType) {
        this.value.add(value)
        this.type = type
    }

    constructor() {
        this.value = ArrayList()
        this.type = ValueType.EMPTY
    }

    operator fun get(at: Int): Int {
        return value[at]
    }

    fun size(): Int {
        return value.size
    }

    fun clone(): MutableList<Int> {
        return value
    }

    fun add(toAdd: Int, type: ValueType) {
        if (this.type != ValueType.CLUE) {
            if (type == ValueType.NUMBER) {
                value.clear()
                value.add(toAdd)
                this.type = type
            } else if (type == ValueType.GUESS) {
                try {
                    value.remove(toAdd)
                } catch (e: IndexOutOfBoundsException) {
                }

                value.add(toAdd)
                this.type = type
            }
        }
        value.sort()
    }

    fun remove(toRemove: Int) {
        if (type != ValueType.CLUE) {
            try {
                value.remove(toRemove)
            } catch (e: NullPointerException) {
            }

            if (value.isEmpty())
                type = ValueType.EMPTY
        }
    }

    fun remove() {
        if (type != ValueType.CLUE) {
            value.clear()
            type = ValueType.EMPTY
        }
    }

    fun addClue(toAdd: Int) {
        format()
        value.add(toAdd)
        type = ValueType.CLUE
    }

    fun format() {
        value.clear()
        type = ValueType.EMPTY
    }

    fun intersection(toUnite: MutableList<Int>) {
        var i = 0
        while (i < value.size) {
            if (!toUnite.contains(value[i])) {
                value.removeAt(i)
            } else
                i++
        }
    }

    companion object {
        /**
         *
         */
        private const val serialVersionUID = 1L
    }
}
