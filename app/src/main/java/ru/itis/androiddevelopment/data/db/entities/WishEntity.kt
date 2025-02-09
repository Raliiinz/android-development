package ru.itis.androiddevelopment.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "wishes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["user_email"],
            onDelete = ForeignKey.CASCADE,
        )]
)
data class WishEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "user_email")
    val userEmail: String,
    @ColumnInfo(name = "wish_name")
    val wishName: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "photo_path")
    val photo: ByteArray? = null
)