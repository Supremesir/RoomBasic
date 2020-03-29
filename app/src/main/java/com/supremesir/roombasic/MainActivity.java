package com.supremesir.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WordDao wordDao;
    WordDatabase wordDatabase;

    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;

    LiveData<List<Word>> allWordsLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database")
                .build();
        wordDao = wordDatabase.getWordDao();
        allWordsLive = wordDao.getAllWordsLive();
        allWordsLive.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for (Word i : words) {
                    text.append(i.getId()).append(":").append(i.getWord()).append("=").append(i.getChineseMeaning()).append("\n");
                }
                textView.setText(text.toString());
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                new InsertAsyncTask(wordDao).execute(word1, word2);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAllAsyncTask(wordDao).execute();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(38);
                new UpdateAsyncTask(wordDao).execute(word);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(39);
                new DeleteAsyncTask(wordDao).execute(word);
            }
        });
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
