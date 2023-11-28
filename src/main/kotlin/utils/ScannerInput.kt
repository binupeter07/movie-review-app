package utils

import java.lang.NumberFormatException
import java.util.Scanner


object ScannerInput {

    @JvmStatic
    fun readNextInt(prompt: String?): Int {
        do {
            try {
                print(prompt)
                return Scanner(System.`in`).next().toInt()
            } catch (e: NumberFormatException) {
                System.err.println("\tEnter a number please.")
            }
        } while (true)
    }

    fun readNextBoolean(prompt: String?): Boolean {
        do {
            try {
                print(prompt)
                val userInput = Scanner(System.`in`).next().lowercase()
                if (userInput == "yes") {
                    return true
                } else if (userInput == "no" ) {
                    return false
                }
            } catch (e: Exception) {
                System.err.println("\tEnter yer or no.")
            }
        } while (true)
    }

    fun readNextDouble(prompt: String?): Double {
        do {
            try {
                print(prompt)
                return Scanner(System.`in`).next().toDouble()
            } catch (e: NumberFormatException) {
                System.err.println("\tEnter a number please.")
            }
        } while (true)
    }

    @JvmStatic
    fun readNextLine(prompt: String?): String {
        print(prompt)
        return Scanner(System.`in`).nextLine()
    }


    @JvmStatic
    fun readNextChar(prompt: String?): Char {
        print(prompt)
        return Scanner(System.`in`).next()[0]
    }
}
