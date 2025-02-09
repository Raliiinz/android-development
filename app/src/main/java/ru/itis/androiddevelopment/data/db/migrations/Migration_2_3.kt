package ru.itis.androiddevelopment.data.db.migrations

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.itis.androiddevelopment.data.db.InceptionDatabase

class Migration_2_3: Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS wishes_new (id INTEGER NOT NULL, user_email TEXT NOT NULL, wish_name TEXT NOT NULL, price REAL NOT NULL, photo_path BLOB, PRIMARY KEY(id), FOREIGN KEY(user_email) REFERENCES users(email) ON DELETE CASCADE)");

            db.execSQL("INSERT INTO wishes_new (id, user_email, wish_name, price, photo_path) SELECT id, user_email, wish_name, price, photo_path FROM wishes");

            db.execSQL("DROP TABLE wishes")

            db.execSQL("ALTER TABLE wishes_new RENAME TO wishes")
        }catch (ex: Exception) {
            Log.e(InceptionDatabase.DB_LOG_KEY, "Error while 2_3 migration: ${ex.message}")
        }
    }
}
