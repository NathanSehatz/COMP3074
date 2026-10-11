
package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PointDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_details)

        val txtLocationName =
            findViewById<TextView>(R.id.txtLocationName)
        val txtLocationDescription =
            findViewById<TextView>(R.id.txtLocationDescription)
        val txtChallenge =
            findViewById<TextView>(R.id.txtChallenge)
        val txtStatus =
            findViewById<TextView>(R.id.txtStatus)

        val btnComplete =
            findViewById<Button>(R.id.btnComplete)
        val btnBack =
            findViewById<Button>(R.id.btnBackToPoints)

        // Receive the selected location
        val locationId = intent.getIntExtra("locationId", 0)

        // Get saved game progress
        val preferences = getSharedPreferences(
            "QuestHuntProgress",
            MODE_PRIVATE
        )

        // Display information for the selected location
        when (locationId) {
            1 -> {
                txtLocationName.text = "City Park"
                txtLocationDescription.text =
                    "Explore the park and discover its landmarks."
                txtChallenge.text =
                    "Find a large tree and identify its species."
            }

            2 -> {
                txtLocationName.text = "Museum"
                txtLocationDescription.text =
                    "Discover interesting historical exhibits."
                txtChallenge.text =
                    "Find an exhibit and record one interesting fact."
            }

            3 -> {
                txtLocationName.text = "Library"
                txtLocationDescription.text =
                    "Explore the library and its collection."
                txtChallenge.text =
                    "Find a book about local history."
            }

            else -> {
                txtLocationName.text = "Unknown Location"
                txtLocationDescription.text =
                    "No location information available."
                txtChallenge.text = "No challenge available."
                btnComplete.isEnabled = false
            }
        }

        val progressKey = "location_$locationId"

        // Update the UI according to saved progress
        fun updateStatus() {
            val completed = preferences.getBoolean(
                progressKey,
                false
            )

            if (completed) {
                txtStatus.text = "Status: Completed!"
                btnComplete.text = "Completed"
                btnComplete.isEnabled = false
            } else {
                txtStatus.text = "Status: Not Completed"
                btnComplete.text = "Mark as Completed"
                btnComplete.isEnabled = locationId in 1..3
            }
        }

        updateStatus()

        // Save completion when the player finishes
        btnComplete.setOnClickListener {
            preferences.edit()
                .putBoolean(progressKey, true)
                .apply()

            updateStatus()

            Toast.makeText(
                this,
                "Challenge completed!",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return to the Points List
        btnBack.setOnClickListener {
            finish()
        }
    }
}
