package chucknorris

// function to encode a text string: parameter: string to be encoded
fun encode(text: String): String {
    // transform input string to binary string
    val interim = buildString {
        for (item in text) {
            for (i in 1..(7 - Integer.toBinaryString(item.code).length)) append("0")
            append(Integer.toBinaryString(item.code))
        }
    }
    // transform binary to coded output
    val result = buildString {
        var index = 0
        while (index < interim.length) {
            if (interim[index] == '1') append("0")
            else append("00")
            append(" ")
            var itemCount = 1
            while (index < interim.length - 1 && interim[index] == interim[index + 1]) {
                itemCount++
                index++
            }
            for (i in 0 until itemCount) {
                append("0").repeat(itemCount)
            }
            append(" ")
            index++
        }
    }
    return result
}

// function to decode a text string: parameter: coded string to decode
fun decode(code: String): String {
    val step1: List<MutableList<String>> = code.split(" ").chunked(2) { it.toMutableList() }

    // convert data pair to bit string
    for (step in step1) {
        if (step[0] == "0") {
            step[0] = "1"
        } else {
            step[0] = "0"
        }
        if (step[0] == "1") {
            step[1] = buildString {
                for (i in 0 until step[1].length) {
                    append("1")
                } // for (i
            } // buildString
        } // if (step
    } // for (step
    var step2: String = ""
    val step3: MutableList<String> = emptyList<String>().toMutableList()
    if (step1.size == 1) {
        step2 = step1.map { it[1] }.toString().toInt(2).toChar().toString()
        println(step2)
    } else {
        step2 = step1.map { it[1] }.joinToString("")

        // break up string into list of 7 bit chucks
        var index = 0
        while (index < step2.length) {
            step3.add(step2.substring(index, index + 7))
            index += 7
        }
    }
    val step4 = step3.map { it.toInt(2).toChar() }
    return step4.joinToString("")
}

// function to get input: parameters are encode, decode, and main
fun getInput(type: String): String {
    var message: String = ""
    var input: String = ""
    when (type) {
        "encode" -> message = "Input string:"
        "decode" -> message = "Input encoded string:"
        "main" -> message = "Please input operation (encode/decode/exit):"
    }

    while (type == "main") {
        do {
            println(message)
            input = readln().trim()
        } while (input == "")

        when (input) {
            "encode" -> return "encode"
            "decode" -> return "decode"
            "exit" -> return "exit"
            else -> println("There is no '$input' operation")
        }
    }

    if (type == "encode") {
        do {
            println(message)
            input = readln().trim()
        } while (input == "")
        return input
    }

    if (type == "decode") {
        do {
            println(message)
            input = readln().trim()
        } while (input == "")
        val testInput = input.split(" ")
        val testInput2 = testInput.chunked(2)
        val testInput3 = testInput.filterIndexed() { i, _ -> i % 2 != 0 }.map { it.length }.sum()
        if (testInput.size % 2 == 0 && Regex("[0\\s]+").matches(input) && testInput2.all { "0|00".toRegex() matches it[0] } && testInput3 % 7 == 0) return input
        println("'$input' not valid")
        return "-1"

    }
    return ""
}

fun main() {
    var input = ""

    while (input != "exit") {
        input = getInput("main")

        if (input == "encode") {
            input = getInput("encode")
            println("Encoded string:")
            println(encode(input))

        }

        if (input == "decode") {
            input = getInput("decode")
            if (input != "-1") {
                println("Decoded string:")
                println(decode(input))
            }
        }
    }
    println("Bye!")
}