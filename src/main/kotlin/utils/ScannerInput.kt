package utils

import java.lang.NumberFormatException
import java.util.Scanner

object ScannerInput {

    /**
     * Read an int,boolean from the user.
     * If the entered data isn't actually an int the user is prompted again to enter the int.
     * @param prompt  The information printed to the console for the user to read
     * @return The number read from the user and verified as an int.
     */

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

    /**
     * Read a boolean from the user.  If the entered data isn't actually yes or no(yes indicates true and no indicates false),
     * the user is prompted again to enter yes or no.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The number read from the user and verified as a boolean.
     */

    fun readNextBoolean(prompt: String?): Boolean {
        do {
            try {
                print(prompt)
                val userInput = Scanner(System.`in`).next().lowercase()
                if (userInput == "yes") {
                    return true
                } else if (userInput == "no") {
                    return false
                }
            } catch (e: Exception) {
                System.err.println("\tEnter yer or no.")
            }
        } while (true)
    }

    /**
     * Read a double from the user.  If the entered data isn't actually a double,
     * the user is prompted again to enter the double.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The number read from the user and verified as a double.
     */

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

    /**
     * Read a line of text from the user.  There is no validation done on the entered data.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The String read from the user.
     */

    @JvmStatic
    fun readNextLine(prompt: String?): String {
        print(prompt)
        return Scanner(System.`in`).nextLine()
    }

    /**
     * Read a single character of text from the user.  There is no validation done on the entered data.
     *
     * @param prompt  The information printed to the console for the user to read
     * @return The char read from the user.
     */

    @JvmStatic
    fun readNextChar(prompt: String?): Char {
        print(prompt)
        return Scanner(System.`in`).next()[0]
    }
}
