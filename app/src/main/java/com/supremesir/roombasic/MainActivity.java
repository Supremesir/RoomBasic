package com.supremesir.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

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
                // 数据库操作不允许在主线程中进行，此处为了简单，临时允许
                .allowMainThreadQueries()
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
//        updateView();


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                wordDao.insertWords(word1);
                wordDao.insertWords(word2);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(34);
                wordDao.updateWords(word);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(36);
                wordDao.deleteWords(word);
            }
        });


    }

//    void updateView() {
//        List<Word> list = wordDao.getAllWords();
//        String text = "";
//        for (Word i : list) {
//            text += i.getId() + ":" + i.getWord() + "=" + i.getChineseMeaning() + "\n";
//        }
//        textView.setText(text);
//    }
}
