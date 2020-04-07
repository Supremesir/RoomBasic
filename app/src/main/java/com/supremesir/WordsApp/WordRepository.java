package com.supremesir.WordsApp;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @author HaoFan Fang
 * @date 2020/3/29 20:54
 */

class WordRepository {

    private LiveData<List<Word>> allWordsLive;
    private WordDao wordDao;


    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    LiveData<List<Word>> findWordsWithPatten(String patten) {
        // 前后加上%实现模糊匹配
        return wordDao.findWordsWithPatten("%" + patten + "%");
    }

    void insertWords(Word... words) {
        new InsertAsyncTask(wordDao).execute(words);
    }

    void updateWords(Word... words) {
        new UpdateAsyncTask(wordDao).execute(words);
    }

    void deleteWords(Word... words) {
        new DeleteAsyncTask(wordDao).execute(words);
    }

    void deleteAllWords() {
        new DeleteAllAsyncTask(wordDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        InsertAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWords(words);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        UpdateAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updateWords(words);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        DeleteAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords(words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
