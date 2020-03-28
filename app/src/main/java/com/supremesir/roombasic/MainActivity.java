package com.supremesir.roombasic;

import androidx.appcompat.app.AppCompatActivity;
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
        updateView();


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好");
                Word word2 = new Word("World", "世界");
                wordDao.insertWords(word1);
                wordDao.insertWords(word2);
                updateView();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordDao.deleteAllWords();
                updateView();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(12);
                wordDao.updateWords(word);
                updateView();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(13);
                wordDao.deleteWords(word);
                updateView();
            }
        });


    }

    void updateView() {
        List<Word> list = wordDao.getAllWords();
        String text = "";
        for (Word i : list) {
            text += i.getId() + ":" + i.getWord() + "=" + i.getChineseMeaning() + "\n";
        }
        textView.setText(text);
    }
}
