package ru.itis.androiddevelopment.data.db.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.androiddevelopment.data.db.dao.WishDao
import ru.itis.androiddevelopment.data.db.entities.WishEntity

class WishRepository (
    private val wishDao: WishDao,
    private val ioDispatcher: CoroutineDispatcher,
    ) {
    suspend fun saveWish(wish: WishEntity) {
        return withContext(ioDispatcher) {
            wishDao.saveWish(wish)
        }
    }

    suspend fun getWishesByEmail(email: String): MutableList<WishEntity> {
        return withContext(ioDispatcher) {
            wishDao.getWishesByEmail(email)
        }
    }

    suspend fun deleteWish(wish: WishEntity) {
        return withContext(ioDispatcher) {
            wishDao.deleteWish(wish)
        }
    }
}