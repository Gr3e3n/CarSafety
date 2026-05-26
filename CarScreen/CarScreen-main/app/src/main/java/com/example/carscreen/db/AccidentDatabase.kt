package com.example.carscreen.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        AccidentEventEntity::class,
        TelemetryEntity::class,
        ResponsibilityEntity::class,
        EvidenceEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class AccidentDatabase : RoomDatabase() {

    abstract fun accidentDao(): AccidentDao

    companion object {
        @Volatile
        private var INSTANCE: AccidentDatabase? = null

        fun getInstance(context: Context): AccidentDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AccidentDatabase::class.java,
                    "accident_edr.db",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                db
            }
        }
    }
}
