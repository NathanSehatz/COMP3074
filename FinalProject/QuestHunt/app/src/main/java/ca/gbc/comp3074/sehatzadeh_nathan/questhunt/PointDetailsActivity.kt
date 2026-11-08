package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.view.View

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

        val btnEditPoint =
            findViewById<Button>(R.id.btnEditPoint)

        val btnDeletePoint =
            findViewById<Button>(R.id.btnDeletePoint)

        val locationId = intent.getIntExtra("locationId", 0)
        val databasePointId = intent.getIntExtra("databasePointId", 0)

        val progressRepository = QuestRepository(this)
        val pointRepository = PointRepository(this)

        fun updateStatus(progressId: Int) {
            val completed = progressRepository.isChallengeCompleted(progressId)

            if (completed) {
                txtStatus.text = "Status: Completed!"
                btnComplete.text = "Completed"
                btnComplete.isEnabled = false
            } else {
                txtStatus.text = "Status: Not Completed"
                btnComplete.text = "Mark as Completed"
                btnComplete.isEnabled = true
            }
        }

        btnBack.setOnClickListener {
            finish()
        }

        if (databasePointId > 0) {
            btnComplete.isEnabled = false
            btnEditPoint.visibility = View.VISIBLE
            btnDeletePoint.visibility = View.VISIBLE

            btnEditPoint.setOnClickListener {
                val intent = Intent(this, AddPointActivity::class.java)
                intent.putExtra("pointId", databasePointId)
                startActivity(intent)
            }


            btnDeletePoint.setOnClickListener {
                androidx.appcompat.app.AlertDialog.Builder(this, R.style.RetroDialogTheme)
                    .setTitle("Delete Point")
                    .setMessage("Are you sure you want to delete this point? This cannot be undone.")
                    .setPositiveButton("Delete") { _, _ ->
                        lifecycleScope.launch {
                            val point = pointRepository.getPointById(databasePointId)

                            if (point != null) {
                                pointRepository.deletePoint(point)

                                Toast.makeText(
                                    this@PointDetailsActivity,
                                    "Point deleted successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                finish()
                            } else {
                                Toast.makeText(
                                    this@PointDetailsActivity,
                                    "Point not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }

            lifecycleScope.launch {
                val point = pointRepository.getPointById(databasePointId)

                if (point != null) {
                    txtLocationName.text = point.name
                    txtLocationDescription.text =
                        "Address: ${point.address}\n\nTags: ${point.tags.joinToString(", ")}"
                    txtChallenge.text = point.task

                    val progressId = databasePointId + 1000

                    updateStatus(progressId)

                    btnComplete.setOnClickListener {
                        progressRepository.completeChallenge(progressId)
                        updateStatus(progressId)

                        Toast.makeText(
                            this@PointDetailsActivity,
                            "Challenge completed!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    txtLocationName.text = "Point Not Found"
                    txtLocationDescription.text =
                        "This location is no longer available."
                    txtChallenge.text = "No challenge available."
                    txtStatus.text = "Status: Unavailable"
                    btnComplete.isEnabled = false
                }
            }
        } else {
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
                    txtStatus.text = "Status: Unavailable"
                    btnComplete.isEnabled = false
                }
            }

            if (locationId in 1..3) {
                updateStatus(locationId)

                btnComplete.setOnClickListener {
                    progressRepository.completeChallenge(locationId)
                    updateStatus(locationId)

                    Toast.makeText(
                        this,
                        "Challenge completed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
