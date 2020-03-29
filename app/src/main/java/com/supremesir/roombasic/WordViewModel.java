package com.supremesir.roombasic;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * @author HaoFan Fang
 * @date 2020/3/29 15:51
 */

public class WordViewModel extends AndroidViewModel {

    private WordDatabase wordDatabase;
    private WordDao wordDao;
    private LiveData<List<Word>> allWordsLive;

    public LiveData<List<Word>> getAllWordsLive() {
        return allWordsLive;
    }

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordDatabase = WordDatabase.getDatabase(application);
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
    }

    public void insertWords(Word... words) {
        new InsertAsyncTask(wordDao).execute(words);
    }
    public void updateWords(Word... words) {
        new UpdateAsyncTask(wordDao).execute(words);
    }
    public void deleteWords(Word... words) {
        new DeleteAsyncTask(wordDao).execute(words);
    }
    public void deleteAllWords(Word... words) {
        new DeleteAllAsyncTask(wordDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao wordDao;

        public InsertAsyncTask(WordDao wordDao) {
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

        public UpdateAsyncTask(WordDao wordDao) {
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

        public DeleteAsyncTask (WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deleteWords( words);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        public DeleteAllAsyncTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
