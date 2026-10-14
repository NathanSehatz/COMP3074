package ca.gbc.comp3074.sehatzadeh_nathan.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Input fields
        val editHours = findViewById<EditText>(R.id.editHours)
        val editRate = findViewById<EditText>(R.id.editRate)
        val editTax = findViewById<EditText>(R.id.editTax)

        // Result fields
        val textPay = findViewById<TextView>(R.id.textPay)
        val textOvertime = findViewById<TextView>(R.id.textOvertime)
        val textTotal = findViewById<TextView>(R.id.textTotal)
        val textTax = findViewById<TextView>(R.id.textTax)

        // Buttons
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnAbout = findViewById<Button>(R.id.btnAbout)

        btnCalculate.setOnClickListener {

            // Read the values entered by the user
            val hours = editHours.text.toString().toDoubleOrNull()
            val hourlyRate = editRate.text.toString().toDoubleOrNull()
            val taxRate = editTax.text.toString().toDoubleOrNull()

            // Validate input
            if (hours == null || hourlyRate == null || taxRate == null) {
                Toast.makeText(
                    this,
                    "Please enter valid numbers in all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!hours.isFinite() || !hourlyRate.isFinite() ||
                !taxRate.isFinite() || hours < 0 ||
                hourlyRate < 0 || taxRate < 0 || taxRate > 1) {
                Toast.makeText(
                    this,
                    "Enter non-negative hours/rate and tax between 0 and 1",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Calculate regular and overtime pay
            val pay: Double
            val overtimePay: Double

            if (hours <= 40) {
                pay = hours * hourlyRate
                overtimePay = 0.0
            } else {
                pay = 40 * hourlyRate
                overtimePay = (hours - 40) * hourlyRate * 1.5
            }

            // Calculate total pay and tax
            val totalPay = pay + overtimePay
            val tax = pay * taxRate

            // Display results
            textPay.text = String.format(
                Locale.US, "Regular Pay: $%.2f", pay
            )

            textOvertime.text = String.format(
                Locale.US, "Overtime Pay: $%.2f", overtimePay
            )

            textTotal.text = String.format(
                Locale.US, "Total Pay: $%.2f", totalPay
            )

            textTax.text = String.format(
                Locale.US, "Tax: $%.2f", tax
            )
        }

        // Navigate to AboutActivity
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}