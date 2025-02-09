package ru.itis.androiddevelopment.data.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.itis.androiddevelopment.data.db.InceptionDatabase

class Migration_1_2: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS `wishes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_email` TEXT NOT NULL, `wish_name` TEXT NOT NULL, `price` REAL NOT NULL, `photo_path` TEXT, FOREIGN KEY(`user_email`) REFERENCES `users`(`email`) ON UPDATE NO ACTION ON DELETE CASCADE )"
            )
        }catch (ex: Exception) {
            Log.e(InceptionDatabase.DB_LOG_KEY, "Error while 1_2 migration: ${ex.message}")
        }
    }

}