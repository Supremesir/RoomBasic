package com.supremesir.WordsApp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @author HaoFan Fang
 * @date 2020/3/29 15:51
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application.getApplicationContext());
    }

    LiveData<List<Word>> getAllWordsLive() {
        return wordRepository.getAllWordsLive();
    }

    LiveData<List<Word>> findWordsWithPatten(String patten) {
        return wordRepository.findWordsWithPatten(patten);
    }

    void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    void updateWords(Word... words) {
        wordRepository.updateWords(words);
    }

    void deleteWords(Word... words) {
        wordRepository.deleteWords(words);
    }

    void deleteAllWords() {
        wordRepository.deleteAllWords();
    }
}
