package com.smarttoolfactory.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import androidx.room.Update
import com.smarttoolfactory.data.model.IEntity

@Dao
interface BaseDao<T : IEntity> {

    /*
         Insert Single entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    /*
         Insert Multiple entities
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>): List<Long>

    /*
        Update
     */
    @Update
    suspend fun update(entity: T): Int

    /*
        Delete
     */
    @Delete
    suspend fun delete(entity: T): Int
}
