package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class TeamActivity : AppCompatActivity() {

    private lateinit var repository: QuestRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        repository = QuestRepository(this)

        val txtMemberList =
            findViewById<TextView>(R.id.txtMemberList)
        val spinnerTeamMembers =
            findViewById<Spinner>(R.id.spinnerTeamMembers)
        val btnAddMember =
            findViewById<Button>(R.id.btnAddMember)
        val btnRemoveMember =
            findViewById<Button>(R.id.btnRemoveMember)
        val btnBack =
            findViewById<Button>(R.id.btnBackFromTeam)

        fun updateTeamList() {
            val members = repository.getTeamMembers()

            txtMemberList.text = members.mapIndexed { index, name ->
                "${index + 1}. $name"
            }.joinToString("\n")

            val removableMembers = members.filter {
                it != "Player One (Leader)"
            }

            val dropdownItems = listOf("Select Team member") +
                    removableMembers

            val adapter = ArrayAdapter(
                this,
                R.layout.spinner_member_item,
                dropdownItems
            )

            adapter.setDropDownViewResource(
                R.layout.spinner_member_item
            )

            spinnerTeamMembers.adapter = adapter
            btnRemoveMember.isEnabled = removableMembers.isNotEmpty()
        }

        updateTeamList()

        btnAddMember.setOnClickListener {

            val input = EditText(this)
            input.hint = "Enter member name"
            input.setSingleLine(true)
            input.setTextColor(android.graphics.Color.WHITE)
            input.setHintTextColor(android.graphics.Color.LTGRAY)
            input.backgroundTintList =
                android.content.res.ColorStateList.valueOf(
                    android.graphics.Color.WHITE
                )

            val container = android.widget.LinearLayout(this)
            val padding = (24 * resources.displayMetrics.density).toInt()

            container.setPadding(padding, 0, padding, padding)
            container.addView(input)

            AlertDialog.Builder(this, R.style.RetroDialogTheme)
                .setTitle("Add Team Member")
                .setView(container)
                .setPositiveButton("Add") { _, _ ->
                    val name = input.text.toString().trim()

                    if (name.isNotEmpty()) {
                        repository.addTeamMember(name)
                        updateTeamList()

                        Toast.makeText(
                            this,
                            "Team member added!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Please enter a name",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        btnRemoveMember.setOnClickListener {
            val selectedMember =
                spinnerTeamMembers.selectedItem.toString()

            if (spinnerTeamMembers.selectedItemPosition == 0) {
                Toast.makeText(
                    this,
                    "Please select a team member",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AlertDialog.Builder(this, R.style.RetroDialogTheme)
                    .setTitle("Confirm Removal")
                    .setMessage(
                        "Are you sure you want to remove $selectedMember?"
                    )
                    .setPositiveButton("Remove") { _, _ ->
                        repository.removeTeamMember(selectedMember)
                        updateTeamList()

                        Toast.makeText(
                            this,
                            "$selectedMember removed from team",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
