
package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PointsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_list)

        val btnLocation1 =
            findViewById<Button>(R.id.btnLocation1)
        val btnLocation2 =
            findViewById<Button>(R.id.btnLocation2)
        val btnLocation3 =
            findViewById<Button>(R.id.btnLocation3)
        val btnBack =
            findViewById<Button>(R.id.btnBack)

        // Function to open details for a location
        fun openLocation(locationId: Int) {
            val intent = Intent(
                this,
                PointDetailsActivity::class.java
            )

            intent.putExtra("locationId", locationId)
            startActivity(intent)
        }

        btnLocation1.setOnClickListener {
            openLocation(1)
        }

        btnLocation2.setOnClickListener {
            openLocation(2)
        }

        btnLocation3.setOnClickListener {
            openLocation(3)
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
