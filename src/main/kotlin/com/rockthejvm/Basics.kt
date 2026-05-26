package com.rockthejvm

object Basics {
    @JvmStatic
    fun main(args: Array<String>) {
        println("basics")
        val meaningOfLife: Int = 42 // const int meaningOfLife
        val meaningOfLife_v2 = 42 //  type inference

        // meaningOfLife = 1 // ERROR

        var objectiveInLife = 32
        objectiveInLife = 45

        // standard: Int, Boolean, Char, Short, Long, Float, Double

        // string
        val aString = "I love Kotlin"
        // val ll = 'some string' // ' only for a Char
        val aChar = 'c'
        val aCombinedString = "I" + "love!"
        val aTemplate = "I love Kotlin $aString"

        val anExpression = 2 + 3
        val aCondition = 1 > 2

        if(aCondition) {
            println("Condition is true")
        } else {
            println("Condition is false")
        }

        val anIfExpression = if(aCondition) 42 else 999 // an expression

        when (meaningOfLife) {
            42-> println("Meaning of life from HGG")
            43 -> println("Meaning of life from almost HGG")
            else -> println("Meaning of life NOT from HGG")
        }

        val anWhenExpression = when(meaningOfLife) {
            42 -> println("Meaning of life from HGG")
            else -> println("Meaning of life NOT from HGG")
        }

        println("inclusive range 1-10")
        for(i in 1 .. 10) {
            println(i)
        }
// 1..10, 1..<10, 1until 10, 1.. 10 step 2, 10 downTo 1
        for(i in 1 until 10) {
            println(i)
        }

        val aList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val aList2 = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        for(element in aList) {
            println(element)
        }

        var i = 1

        while(i <= 10) {
            println(i)
            i += 1
        }

        // also do-while, do
        do {
            println(i)
        } while(i <= 10)

        fun concatenateString(aString: String): String {
            var result = ""
            for(i in 1 .. aString.length) {
                result += aString[i-1]
            }
            return result
        }

        println(concatenateString("Hello World!"))

        fun combine(strA: String, strB: String): String =
            "$strA----$strB"

        println(combine("Hello World!", "Abe!"))


    }
}