package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ProgressActivity : AppCompatActivity() {

    private lateinit var progressRepository: QuestRepository
    private lateinit var pointRepository: PointRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        progressRepository = QuestRepository(this)
        pointRepository = PointRepository(this)

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
        lifecycleScope.launch {
            val points = pointRepository.getAllPoints()
            val originalCompleted = progressRepository.getCompletedCount()
            val databaseCompleted = points.count {
                progressRepository.isChallengeCompleted(it.id + 1000)
            }

            val totalChallenges = 3 + points.size
            val completedCount = originalCompleted + databaseCompleted
            val percentage = completedCount * 100 / totalChallenges

            findViewById<TextView>(R.id.txtCompletedCount).text =
                "$completedCount of $totalChallenges Challenges Completed"

            findViewById<TextView>(R.id.txtPercentage).text =
                "$percentage% Complete"

            findViewById<ProgressBar>(R.id.progressBar).progress =
                percentage

            findViewById<TextView>(R.id.txtCityParkStatus).text =
                "City Park: ${if (progressRepository.isChallengeCompleted(1)) "Completed" else "Not Completed"}"

            findViewById<TextView>(R.id.txtMuseumStatus).text =
                "Museum: ${if (progressRepository.isChallengeCompleted(2)) "Completed" else "Not Completed"}"

            findViewById<TextView>(R.id.txtLibraryStatus).text =
                "Library: ${if (progressRepository.isChallengeCompleted(3)) "Completed" else "Not Completed"}"

            val container =
                findViewById<LinearLayout>(R.id.layoutAdditionalProgress)

            container.removeAllViews()

            points.forEach { point ->
                val completed =
                    progressRepository.isChallengeCompleted(point.id + 1000)

                val status = TextView(this@ProgressActivity)
                status.text =
                    "${point.name}: ${if (completed) "Completed" else "Not Completed"}"
                status.setTextColor(android.graphics.Color.WHITE)
                status.textSize = 17f

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.bottomMargin =
                    (20 * resources.displayMetrics.density).toInt()

                status.layoutParams = params
                container.addView(status)
            }
        }
    }
}
