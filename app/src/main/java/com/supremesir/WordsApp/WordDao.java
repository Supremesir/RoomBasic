package com.supremesir.WordsApp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @author HaoFan Fang
 * @date 2020/3/27 16:45
 */

// Database Access Object
@Dao
public interface WordDao {
    @Insert
    void insertWords(Word... words);

    @Update
    void updateWords(Word... words);

    @Delete
    void deleteWords(Word... words);

    @Query("DELETE FROM WORD")
    void deleteAllWords();

    @Query("SELECT * FROM WORD ORDER BY ID DESC")
    LiveData<List<Word>> getAllWordsLive();

    @Query("SELECT * FROM WORD WHERE english_word LIKE :patten ORDER BY ID DESC")
    LiveData<List<Word>> findWordsWithPatten(String patten);
}
