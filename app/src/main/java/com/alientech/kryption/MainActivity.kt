package com.alientech.kryption

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toUpperCase
import com.alientech.kryption.ui.theme.KryptionTheme
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KryptionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var quote by remember {
                        mutableStateOf("")
                    }

                    var keyPorta by remember {
                        mutableStateOf("")
                    }

                    var reveal by remember {
                        mutableStateOf(false)
                    }

                    // Column with everything
                    Column {
                        // Row for title
                        Row {

                        }

                        // Row for quote display
                        Row {

                        }

                        // Row for details
                        Row {
                            if (reveal) {
                                Text(
                                    text = "You completed this cipher in "
                                )
                            } else {
                                Text(
                                    text = "Decode this Porta cipher using the key word $keyPorta"
                                )
                            }
                        }

                        // Row for controls
                        Row {

                        }

                        // Button for each control
                        Row {

                        }
                    }
                }
            }
        }
    }

    private fun sanitize(quote: String) : String {
        return quote.uppercase().replace(Regex("[^A-Z]"), "")
    }

    private fun generateRandomQuote(prev: String) : String {
        val quote: String = ""

        do {
            resources.getStringArray(R.array.quotes_array).random()
        } while (quote == prev)

        return quote
    }

    private fun generateRandomKey(min: Int = 2, max: Int = 5) : String {
        val key: String = ""
        return key
    }

    private fun generateRandomPorta(prevQuote: String) : Triple<String, String, String> {
        val plaintext = sanitize(generateRandomQuote(prevQuote))
        val plaintextNumeric = plaintext.map {
            if (it == ' ') {
                Alphabet.toNumeral(it).toString()
            } else {
                " "
            }
        }

        val key = sanitize(generateRandomKey(4, 6))
        val keyLength = key.length
        val keyNumeric = key.map {
            floor(Alphabet.toNumeral(it).toDouble() / 2.00).toInt()
        }

        var ciphertext = ""

        var k = 0
        plaintextNumeric.forEach {
            ciphertext +=
                if (it == " ") {
                    " "
                } else {
                    Alphabet.toAlphabet(
                        if (it.toInt() < 13) {
                            (it.toInt() + keyNumeric[k % keyLength]) % 13 + 13
                        } else {
                            (it.toInt() - keyNumeric[k % keyLength]) % 13
                        }
                    )
                }

            k++
        }

        return Triple(plaintext, key, ciphertext)
    }

    object Alphabet {
        val lowercase = "abcdefghijklmnopqrstuvwxyz"
        val uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        fun toAlphabet(numeral: Int, upper: Boolean = true) : Char {
            return (if (upper) uppercase else lowercase)[numeral]
        }

        fun toNumeral(alphabet: Char) : Int {
            return uppercase.indexOf(alphabet.uppercase())
        }
    }
}
