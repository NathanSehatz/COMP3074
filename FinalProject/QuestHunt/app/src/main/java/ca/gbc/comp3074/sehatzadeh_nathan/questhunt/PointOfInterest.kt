package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_of_interest")
data class PointOfInterest(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val task: String,
    val tags: List<String>,
    val rating: Float = 0f,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
