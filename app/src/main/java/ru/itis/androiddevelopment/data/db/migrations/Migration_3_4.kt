package ru.itis.androiddevelopment.data.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.itis.androiddevelopment.data.db.InceptionDatabase

class Migration_3_4: Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("ALTER TABLE 'users' ADD COLUMN 'photo_path_user' BLOB")
        }catch (ex: Exception) {
            Log.e(InceptionDatabase.DB_LOG_KEY, "Error while 3_4 migration: ${ex.message}")
        }
    }
}
