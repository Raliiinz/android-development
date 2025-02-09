package ru.itis.androiddevelopment.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.androiddevelopment.data.db.entities.WishEntity

@Dao
interface WishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWish(wish: WishEntity)

    @Query("SELECT * FROM wishes WHERE user_email = :email")
    suspend fun getWishesByEmail(email: String): MutableList<WishEntity>

    @Delete
    suspend fun deleteWish(wish: WishEntity)
}