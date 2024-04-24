package com.dicoding.submissiongithub.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "favorite")
@Parcelize
class FavoriteUser (
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean = false
) : Parcelable