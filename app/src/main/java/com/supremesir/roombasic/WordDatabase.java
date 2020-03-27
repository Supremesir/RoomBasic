package com.supremesir.roombasic;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author HaoFan Fang
 * @date 2020/3/27 16:53
 */

@Database(entities = {Word.class}, version = 1, exportSchema = false)

public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao getWordDao();
}
