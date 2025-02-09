package ru.itis.androiddevelopment.data.db.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.androiddevelopment.data.db.dao.UserDao
import ru.itis.androiddevelopment.data.db.entities.UserEntity

class UserRepository (
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun saveUserData(user: UserEntity) {
        return withContext(ioDispatcher) {
            userDao.saveUserData(user = user)
        }
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.getUserByEmail(email)
        }
    }

    suspend fun authenticateUser(email: String, password: String): UserEntity? {
        return withContext(ioDispatcher) {
            userDao.authenticateUser(email, password)
        }
    }

    suspend fun deleteUserByEmail(email: String) {
        return withContext(ioDispatcher) {
            userDao.deleteUserByEmail(email)
        }
    }

    suspend fun updateUser(user: UserEntity) {
        return withContext(ioDispatcher) {
            userDao.updateUser(user)
        }
    }
}