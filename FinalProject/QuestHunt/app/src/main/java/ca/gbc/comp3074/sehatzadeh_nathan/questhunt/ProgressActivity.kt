
package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProgressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val btnBack = findViewById<Button>(R.id.btnBackFromProgress)

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        updateProgress()
    }

    private fun updateProgress() {
        val preferences = getSharedPreferences(
            "QuestHuntProgress",
            MODE_PRIVATE
        )

        val cityPark = preferences.getBoolean("location_1", false)
        val museum = preferences.getBoolean("location_2", false)
        val library = preferences.getBoolean("location_3", false)

        val completedCount = listOf(
            cityPark,
            museum,
            library
        ).count { it }

        val percentage = completedCount * 100 / 3

        findViewById<TextView>(R.id.txtCompletedCount).text =
            "$completedCount of 3 Challenges Completed"

        findViewById<TextView>(R.id.txtPercentage).text =
            "$percentage% Complete"

        findViewById<ProgressBar>(R.id.progressBar).progress =
            percentage

        findViewById<TextView>(R.id.txtCityParkStatus).text =
            "City Park: ${if (cityPark) "Completed" else "Not Completed"}"

        findViewById<TextView>(R.id.txtMuseumStatus).text =
            "Museum: ${if (museum) "Completed" else "Not Completed"}"

        findViewById<TextView>(R.id.txtLibraryStatus).text =
            "Library: ${if (library) "Completed" else "Not Completed"}"
    }
}
