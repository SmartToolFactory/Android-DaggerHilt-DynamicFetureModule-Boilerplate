package com.smarttoolfactory.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smarttoolfactory.data.model.IEntity

@Entity
data class SampleDataEntity(@PrimaryKey val id: Int) : IEntity
