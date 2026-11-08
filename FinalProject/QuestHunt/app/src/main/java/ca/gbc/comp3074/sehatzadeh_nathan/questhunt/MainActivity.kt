package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnStartHunt = findViewById<Button>(R.id.btnStartHunt)

        btnStartHunt.setOnClickListener {
            val intent = Intent(this, PointsListActivity::class.java)
            startActivity(intent)
        }

        val btnProgress = findViewById<Button>(R.id.btnProgress)

        btnProgress.setOnClickListener {
            val intent = Intent(this, ProgressActivity::class.java)
            startActivity(intent)
        }


        val btnTeam = findViewById<Button>(R.id.btnTeam)

        btnTeam.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
        }

        val btnAbout = findViewById<Button>(R.id.btnAbout)

        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            val padding = (24 * resources.displayMetrics.density).toInt()
            v.setPadding(
                systemBars.left + padding,
                systemBars.top + padding,
                systemBars.right + padding,
                systemBars.bottom + padding
            )

            insets
        }
        findViewById<Button>(R.id.btnAbout).post {
            val width = findViewById<Button>(R.id.btnAbout).width
            android.util.Log.d("QuestHuntWidth", "About width: $width")
        }
    }
}
