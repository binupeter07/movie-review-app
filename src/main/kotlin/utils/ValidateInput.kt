package utils

object ValidateInput {

    @JvmStatic
    fun readValidRating(prompt: String?): Int {
        var input = ScannerInput.readNextInt(prompt)
        do {
            if (Utilities.validRange(input, 1, 10)) {
                return input
            } else {
                print("Invalid rating $input.. Give rating between 1 to 10  \n")
                input = ScannerInput.readNextInt(prompt)
            }
        } while (true)
    }
}