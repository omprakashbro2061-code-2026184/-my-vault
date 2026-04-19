package com.example.vault

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var shouldReset = false
    private val SECRET_PIN = "290605"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        display = findViewById(R.id.display)

        val buttons = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )

        buttons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener { appendNumber(value) }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { setOperator("/") }
        findViewById<Button>(R.id.btnPercent).setOnClickListener { setOperator("%") }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { toggleSign() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculate() }
    }

    private fun appendNumber(value: String) {
        if (shouldReset) {
            currentInput = ""
            shouldReset = false
        }
        if (value == "." && currentInput.contains(".")) return
        if (currentInput.length >= 10) return
        currentInput += value
        display.text = currentInput
    }

    private fun setOperator(op: String) {
        if (currentInput.isEmpty()) return
        firstNumber = currentInput.toDouble()
        operator = op
        shouldReset = true
    }

    private fun toggleSign() {
        if (currentInput.isEmpty()) return
        val value = currentInput.toDouble()
        currentInput = if (value > 0) "-$currentInput" else currentInput.substring(1)
        display.text = currentInput
    }

    private fun clear() {
        currentInput = ""
        operator = ""
        firstNumber = 0.0
        display.text = "0"
    }

    private fun calculate() {
        if (currentInput == SECRET_PIN) {
            openVault()
            return
        }

        if (operator.isEmpty() || currentInput.isEmpty()) return

        val secondNumber = currentInput.toDouble()
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else 0.0
            "%" -> firstNumber % secondNumber
            else -> 0.0
        }

        val resultStr = if (result == result.toLong().toDouble()) {
            result.toLong().toString()
        } else {
            result.toString()
        }

        display.text = resultStr
        currentInput = resultStr
        operator = ""
        shouldReset = true
    }

    private fun openVault() {
        display.text = "0"
        currentInput = ""
        startActivity(Intent(this, VaultActivity::class.java))
    }
}
