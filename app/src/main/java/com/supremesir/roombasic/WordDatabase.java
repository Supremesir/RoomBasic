package com.supremesir.roombasic;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author HaoFan Fang
 * @date 2020/3/27 16:53
 */

// singleton，单例模式，database实例化耗资源，只允许生成一个实例

@Database(entities = {Word.class}, version = 2, exportSchema = false)

public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase INSTANCE;

    // synchronized加强singleton的强度
    static synchronized WordDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // context.getApplicationContext() 返回应用程序根结点的context，全局且唯一
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
    public abstract WordDao getWordDao();
}
