package ca.gbc.comp3074.sehatzadeh_nathan.questhunt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PointOfInterest::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TagConverter::class)
abstract class QuestDatabase : RoomDatabase() {

    abstract fun pointOfInterestDao(): PointOfInterestDao

    companion object {
        @Volatile
        private var INSTANCE: QuestDatabase? = null

        fun getDatabase(context: Context): QuestDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuestDatabase::class.java,
                    "quest_hunt_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
