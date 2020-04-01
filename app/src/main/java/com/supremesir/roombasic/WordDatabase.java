package com.supremesir.roombasic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author HaoFan Fang
 * @date 2020/3/27 16:53
 */

// singleton，单例模式，database实例化耗资源，只允许生成一个实例

@Database(entities = {Word.class}, version = 3, exportSchema = false)

public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase INSTANCE;

    // synchronized加强singleton的强度
    static synchronized WordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // context.getApplicationContext() 返回应用程序根结点的context，全局且唯一
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
                    // 把旧版本的数据全部清空，创建一个新的数据库
                    .addMigrations(MIGRATION_2_3)
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE word ADD COLUMN bar_data INTEGER NOT NULL DEFAULT 1");
        }
    };
}
