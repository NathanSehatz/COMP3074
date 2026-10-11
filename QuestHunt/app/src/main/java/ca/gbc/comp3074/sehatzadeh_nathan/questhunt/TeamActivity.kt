
package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TeamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val btnAddMember =
            findViewById<Button>(R.id.btnAddMember)

        val btnBack =
            findViewById<Button>(R.id.btnBackFromTeam)

        btnAddMember.setOnClickListener {
            Toast.makeText(
                this,
                "Team management coming in a future prototype!",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
