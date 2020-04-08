package com.supremesir.WordsApp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.supremesir.WordsApp.databinding.FragmentAddBinding;

/**
 * A simple {@link Fragment} subclass.
 * @author fang
 */
public class AddFragment extends Fragment {

    private WordViewModel wordViewModel;
    private FragmentAddBinding binding;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ViewModel 的作用域是整个 activity 内
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        binding.buttonSubmit.setEnabled(false);
        // 进入 Fragment 键盘自动弹出
        binding.editTextEnglish.requestFocus();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(binding.editTextEnglish, 0);
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // trim() 删掉前后的空格
                String english = binding.editTextEnglish.getText().toString().trim();
                String chinese = binding.editTextChinese.getText().toString().trim();
                binding.buttonSubmit.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.editTextEnglish.addTextChangedListener(textWatcher);
        binding.editTextChinese.addTextChangedListener(textWatcher);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = binding.editTextEnglish.getText().toString().trim();
                String chinese = binding.editTextChinese.getText().toString().trim();
                Word word = new Word(english, chinese);
                wordViewModel.insertWords(word);
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_addFragment_to_wordsFragment);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }
}
