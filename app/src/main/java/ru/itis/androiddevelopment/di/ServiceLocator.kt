package ru.itis.androiddevelopment.di

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import ru.itis.androiddevelopment.data.db.InceptionDatabase
import ru.itis.androiddevelopment.data.db.migrations.Migration_1_2
import ru.itis.androiddevelopment.data.db.migrations.Migration_2_3
import ru.itis.androiddevelopment.data.db.migrations.Migration_3_4
import ru.itis.androiddevelopment.data.db.repository.UserRepository
import ru.itis.androiddevelopment.data.db.repository.WishRepository

object ServiceLocator {
    private const val DATABASE_NAME = "InceptionDB"

    private var dbInstance: InceptionDatabase? = null

    private var userRepository: UserRepository? = null
    private var wishRepository: WishRepository? = null

    private fun initDatabase(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, InceptionDatabase::class.java, DATABASE_NAME)
            .addMigrations(Migration_1_2(),
                Migration_2_3(),
                Migration_3_4())
            .build()
    }


    fun initDataLayerDependencies(ctx: Context) {
        if (dbInstance == null) {
            initDatabase(ctx)
            dbInstance?.let {
                userRepository = UserRepository(
                    userDao = it.userDao,
                    ioDispatcher = Dispatchers.IO
                )
                wishRepository = WishRepository(
                    wishDao = it.wishDao,
                    ioDispatcher = Dispatchers.IO
                )
            }
        }
    }

    fun getUserRepository() : UserRepository = userRepository ?: throw IllegalStateException("User repository not initialized")

    fun getWishRepository() : WishRepository = wishRepository ?: throw IllegalStateException("Wish repository not initialized")


}