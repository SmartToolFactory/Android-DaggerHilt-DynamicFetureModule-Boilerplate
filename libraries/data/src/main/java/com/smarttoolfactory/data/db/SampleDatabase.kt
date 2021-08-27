package com.smarttoolfactory.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smarttoolfactory.data.constant.DATABASE_VERSION
import com.smarttoolfactory.data.db.dao.SampleDao
import com.smarttoolfactory.data.model.local.SampleDataEntity

@Database(
    entities = [
        SampleDataEntity::class,
    ],
    version = DATABASE_VERSION,
    exportSchema = true
)
abstract class SampleDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}
