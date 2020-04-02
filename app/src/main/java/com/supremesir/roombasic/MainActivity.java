package com.supremesir.roombasic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button buttonInsert, buttonUpdate, buttonClear, buttonDelete;
    Switch aSwitch;
    WordViewModel wordViewModel;
    WordDao wordDao;
    WordDatabase wordDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter_normal, myAdapter_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textViewNumber);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonClear = findViewById(R.id.buttonClear);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        aSwitch = findViewById(R.id.switch_cardview);
        recyclerView = findViewById(R.id.recyclerView);

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        myAdapter_normal = new MyAdapter(false, wordViewModel);
        myAdapter_card = new MyAdapter(true, wordViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter_normal);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setAdapter(myAdapter_card);
                } else {
                    recyclerView.setAdapter(myAdapter_normal);
                }
            }
        });

        wordDatabase = Room.databaseBuilder(this, WordDatabase.class, "word_database")
                .build();
        wordDao = wordDatabase.getWordDao();
        wordViewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                // 在 MyAdapter 中 switch lister 中写了视图改变的代码
                // 但更新数据库后，此处又会通知数据改变，再次刷新视图，易造成卡顿
                // 因此此处排除，修改数据的操作
                int tmp = myAdapter_normal.getItemCount();
                myAdapter_normal.setAllWords(words);
                myAdapter_card.setAllWords(words);
                if (tmp != words.size()) {
                    myAdapter_normal.notifyDataSetChanged();
                    myAdapter_card.notifyDataSetChanged();
                }
            }
        });


        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] english = {
                        "Hello",
                        "World",
                        "Android",
                        "Google",
                        "Studio",
                        "Project",
                        "Database",
                        "Recycler",
                        "View",
                        "String",
                        "Value",
                        "Integer"
                };
                String[] chinese = {
                        "你好",
                        "世界",
                        "安卓系统",
                        "谷歌公司",
                        "工作室",
                        "项目",
                        "数据库",
                        "回收站",
                        "视图",
                        "字符串",
                        "价值",
                        "整数类型"
                };
                for(int i = 0;i<english.length;i++) {
                    wordViewModel.insertWords(new Word(english[i],chinese[i]));
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWords();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(64);
                wordViewModel.updateWords(word);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("Test", "测试");
                word.setId(66);
                wordViewModel.deleteWords(word);
            }
        });
    }


}
