package ru.itis.androiddevelopment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.androiddevelopment.data.db.converters.Converters
import ru.itis.androiddevelopment.data.db.dao.UserDao
import ru.itis.androiddevelopment.data.db.dao.WishDao
import ru.itis.androiddevelopment.data.db.entities.UserEntity
import ru.itis.androiddevelopment.data.db.entities.WishEntity

@Database(
    entities = [UserEntity::class, WishEntity::class],
    version = 4
)
@TypeConverters(Converters::class)

abstract class InceptionDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val wishDao: WishDao


    companion object {
        const val DB_LOG_KEY = "InceptionDB"
    }
}