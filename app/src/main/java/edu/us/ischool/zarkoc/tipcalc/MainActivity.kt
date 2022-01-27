package edu.us.ischool.zarkoc.tipcalc

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.text.NumberFormat
import android.icu.util.Currency
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.text.TextWatcher
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tipButton = findViewById<Button>(R.id.tipButton)
        val tipInput = findViewById<EditText>(R.id.tipInput)

        tipButton.isEnabled = false
        tipButton.isClickable = false

        var currentText = tipInput.text
        tipInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (currentText.isNotEmpty()) {
                    tipButton.isEnabled = true
                    tipButton.isClickable = true
                } else {
                    tipButton.isEnabled = false
                    tipButton.isClickable = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                tipInput.removeTextChangedListener(this);

                if (tipInput.text.isNotEmpty() && !tipInput.text.contains("$")) {
                    tipInput.setText(NumberFormat.getCurrencyInstance(Locale.US).format(tipInput.text.toString().toFloat() / 100))
                } else if (tipInput.text.isNotEmpty() && tipInput.text.contains("$")) {
                    var tipNewNum = tipInput.text.toString().substring(0, 1).toFloat()
                    var tipOldNum = tipInput.text.toString().substring(2).toFloat()
                    var tipTotal = (tipNewNum / 100) + (tipOldNum * 10)
                    tipInput.setText(NumberFormat.getCurrencyInstance(Locale.US).format(tipTotal))
                }

                tipInput.addTextChangedListener(this);
            }
        })

        tipButton.setOnClickListener {
            val tipText = tipInput.text.toString().substring(1).toFloat()
            val finalTip = NumberFormat.getCurrencyInstance(Locale.US).format(tipText * .15)

            Toast.makeText(this, finalTip, Toast.LENGTH_SHORT).show()
        }
    }
}