package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat
class PointsListActivity : AppCompatActivity() {

    private lateinit var repository: PointRepository
    private lateinit var searchField: EditText
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_list)

        repository = PointRepository(this)

        searchField = findViewById(R.id.editSearchPoints)
        container = findViewById(R.id.layoutSavedPoints)

        val btnLocation1 = findViewById<Button>(R.id.btnLocation1)
        val btnLocation2 = findViewById<Button>(R.id.btnLocation2)
        val btnLocation3 = findViewById<Button>(R.id.btnLocation3)
        val btnAddPoint = findViewById<Button>(R.id.btnAddPoint)
        val btnBack = findViewById<Button>(R.id.btnBack)

        fun openLocation(locationId: Int) {
            val intent = Intent(this, PointDetailsActivity::class.java)
            intent.putExtra("locationId", locationId)
            startActivity(intent)
        }

        btnLocation1.setOnClickListener { openLocation(1) }
        btnLocation2.setOnClickListener { openLocation(2) }
        btnLocation3.setOnClickListener { openLocation(3) }

        btnAddPoint.setOnClickListener {
            startActivity(Intent(this, AddPointActivity::class.java))
        }

        btnBack.setOnClickListener {
            finish()
        }

        searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                displayPoints(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        findViewById<Button>(R.id.btnBack).post {
            val width = findViewById<Button>(R.id.btnBack).width
            android.util.Log.d("QuestHuntWidth", "Back width: $width")
        }
    }

    override fun onResume() {
        super.onResume()
        displayPoints(searchField.text.toString())
    }

    private fun displayPoints(query: String) {
        val search = query.trim().lowercase()

        val originalLocations = listOf(
            "City Park",
            "Museum",
            "Library"
        )

        val originalButtons = listOf(
            R.id.btnLocation1,
            R.id.btnLocation2,
            R.id.btnLocation3
        )

        originalLocations.forEachIndexed { index, name ->
            val button = findViewById<Button>(originalButtons[index])

            button.visibility = if (
                search.isEmpty() ||
                (!search.contains(",") && name.lowercase().contains(search))
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        lifecycleScope.launch {
            val points = repository.getAllPoints()

            val filteredPoints = points.filter { point ->
                if (search.isEmpty()) {
                    true
                } else if (search.contains(",")) {
                    val searchTags = search.split(",")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                    searchTags.isNotEmpty() &&
                            searchTags.all { tag ->
                                point.tags.any {
                                    it.equals(tag, ignoreCase = true)
                                }
                            }
                } else {
                    point.name.contains(search, ignoreCase = true) ||
                            point.tags.any {
                                it.contains(search, ignoreCase = true)
                            }
                }
            }

            if (searchField.text.toString().trim().lowercase() != search) {
                return@launch
            }

            container.removeAllViews()

            filteredPoints.forEach { point ->
                val button = AppCompatButton(this@PointsListActivity)

                button.text = point.name
                button.setTextColor(android.graphics.Color.WHITE)
                button.textSize = 24f
                button.isAllCaps = false
                button.typeface = ResourcesCompat.getFont(this@PointsListActivity, R.font.qager)
                button.setBackgroundResource(R.drawable.retro_button_background)
                button.backgroundTintList = null
                button.minHeight = (64 * resources.displayMetrics.density).toInt()


                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                params.topMargin =
                    (20 * resources.displayMetrics.density).toInt()

                button.layoutParams = params

                button.setOnClickListener {
                    val intent = Intent(
                        this@PointsListActivity,
                        PointDetailsActivity::class.java
                    )
                    intent.putExtra("databasePointId", point.id)
                    startActivity(intent)
                }

                container.addView(button)
            }
        }
    }
}
