package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PointOfInterestDao {

    @Insert
    suspend fun insert(point: PointOfInterest)

    @Update
    suspend fun update(point: PointOfInterest)

    @Delete
    suspend fun delete(point: PointOfInterest)

    @Query("SELECT * FROM points_of_interest ORDER BY name ASC")
    suspend fun getAllPoints(): List<PointOfInterest>

    @Query("SELECT * FROM points_of_interest WHERE id = :id LIMIT 1")
    suspend fun getPointById(id: Int): PointOfInterest?

    @Query(
        "SELECT * FROM points_of_interest " +
                "WHERE name LIKE '%' || :query || '%' " +
                "OR tags LIKE '%' || :query || '%' " +
                "ORDER BY name ASC"
    )
    suspend fun searchPoints(query: String): List<PointOfInterest>
}
