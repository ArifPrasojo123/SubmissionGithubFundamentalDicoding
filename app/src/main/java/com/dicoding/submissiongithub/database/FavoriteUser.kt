package com.dicoding.submissiongithub.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUser (
    @field:PrimaryKey(autoGenerate = false)
    val username: String = "",
    var avatarUrl: String? = null,
) : Parcelable