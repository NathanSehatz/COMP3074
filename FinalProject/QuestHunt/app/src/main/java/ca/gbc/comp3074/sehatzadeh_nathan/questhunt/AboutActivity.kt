
package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnBack =
            findViewById<Button>(R.id.btnBackFromAbout)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
