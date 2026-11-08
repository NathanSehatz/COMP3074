package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddPointActivity : AppCompatActivity() {

    private lateinit var repository: PointRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_point)

        repository = PointRepository(this)

        val pointId = intent.getIntExtra("pointId", 0)

        val editName = findViewById<EditText>(R.id.editPointName)
        val editAddress = findViewById<EditText>(R.id.editPointAddress)
        val editTask = findViewById<EditText>(R.id.editPointTask)
        val editTags = findViewById<EditText>(R.id.editPointTags)

        val btnSave = findViewById<Button>(R.id.btnSavePoint)
        val btnCancel = findViewById<Button>(R.id.btnCancelPoint)

        if (pointId > 0) {
            btnSave.text = "Update Point"

            lifecycleScope.launch {
                val point = repository.getPointById(pointId)

                if (point != null) {
                    editName.setText(point.name)
                    editAddress.setText(point.address)
                    editTask.setText(point.task)
                    editTags.setText(point.tags.joinToString(", "))
                } else {
                    Toast.makeText(
                        this@AddPointActivity,
                        "Point not found",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }


        btnSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val address = editAddress.text.toString().trim()
            val task = editTask.text.toString().trim()
            val tags = editTags.text.toString()
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            if (name.isEmpty() || address.isEmpty() || task.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please fill in all required fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val point = PointOfInterest(
                name = name,
                address = address,
                task = task,
                tags = tags
            )


            lifecycleScope.launch {
                if (pointId > 0) {
                    val existingPoint = repository.getPointById(pointId)

                    if (existingPoint != null) {
                        repository.updatePoint(
                            existingPoint.copy(
                                name = name,
                                address = address,
                                task = task,
                                tags = tags
                            )
                        )

                        Toast.makeText(
                            this@AddPointActivity,
                            "Point updated successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@AddPointActivity,
                            "Point not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    repository.addPoint(point)

                    Toast.makeText(
                        this@AddPointActivity,
                        "Point added successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                finish()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}
