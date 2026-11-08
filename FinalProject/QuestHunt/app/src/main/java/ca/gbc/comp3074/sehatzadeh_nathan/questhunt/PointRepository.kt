package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Context

class PointRepository(context: Context) {

    private val dao = QuestDatabase
        .getDatabase(context)
        .pointOfInterestDao()

    suspend fun addPoint(point: PointOfInterest) {
        dao.insert(point)
    }

    suspend fun updatePoint(point: PointOfInterest) {
        dao.update(point)
    }

    suspend fun deletePoint(point: PointOfInterest) {
        dao.delete(point)
    }

    suspend fun getAllPoints(): List<PointOfInterest> {
        return dao.getAllPoints()
    }

    suspend fun getPointById(id: Int): PointOfInterest? {
        return dao.getPointById(id)
    }

    suspend fun searchPoints(query: String): List<PointOfInterest> {
        return dao.searchPoints(query)
    }
}
