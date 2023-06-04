package com.demo.appforcoursera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String[] questionList;
    String[][] options;
    int[] answers = {1, 3, 3, 2, 2, 3, 3, 1, 1, 3};

    TextView question;
    TextView[] optionList = new TextView[4];
    Button next, previous;

    int currentQuestion = -1;
    HashMap<Integer, Integer> saved = new HashMap<>();
    int userCorrectAnswers = 0;
    int attemptedQuestion = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.content_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(null);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        question = findViewById(R.id.question);
        optionList[0] = findViewById(R.id.option1);
        optionList[1] = findViewById(R.id.option2);
        optionList[2] = findViewById(R.id.option3);
        optionList[3] = findViewById(R.id.option4);

        questionList = getResources().getStringArray(R.array.question_list);
        options = new String[][]{getResources().getStringArray(R.array.optionsQ1), getResources().getStringArray(R.array.optionsQ2), getResources().getStringArray(R.array.optionsQ3), getResources().getStringArray(R.array.optionsQ4), getResources().getStringArray(R.array.optionsQ5), getResources().getStringArray(R.array.optionsQ6), getResources().getStringArray(R.array.optionsQ7), getResources().getStringArray(R.array.optionsQ8), getResources().getStringArray(R.array.optionsQ9), getResources().getStringArray(R.array.optionsQ10)};

        previous = findViewById(R.id.previous_button);
        next = findViewById(R.id.next_button);
        for(int i = 0;i <4;i++){
            optionList[i].setOnClickListener(this);
        }
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        next.performClick();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.building_blocks) {
            startActivity(new Intent(this, BuildingBlocksActivity.class));
            return true;
        }
        else if (item.getItemId() == R.id.add_questions) {
            Toast.makeText(this, "All Questions have been sourced from the web", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveSelectedOptionAndDisplayCorrectOrIncorrectOnClick(int option) {
        //if answer is correct
        if (answers[currentQuestion] == option) {
            optionList[option].setBackgroundColor(getResources().getColor(R.color.AnswerCorrect));
            userCorrectAnswers++;
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.ting);
            mp.start();
        }
        //else answer is wrong
        else {
            optionList[option].setBackgroundColor(getResources().getColor(R.color.AnswerIncorrect));
            Vibrator vibrator = (Vibrator) getSystemService(MainActivity.VIBRATOR_SERVICE);
            assert vibrator != null;
            vibrator.vibrate(100);
        }
        attemptedQuestion++;
        Toast.makeText(this, "Correct Answers: " + userCorrectAnswers + "/10", Toast.LENGTH_SHORT).show();
        //save chosen option for current question
        saved.put(currentQuestion, option);
        setAllOptionsClickable(false);
    }
    private void updateQuestionsAndOptions() {
        System.out.print("heifh");
        question.setText(questionList[currentQuestion]);
        optionList[0].setText(options[currentQuestion][0]);
        optionList[1].setText(options[currentQuestion][1]);
        optionList[2].setText(options[currentQuestion][2]);
        optionList[3].setText(options[currentQuestion][3]);

//        YoYo.with(Techniques.FadeIn).duration(300).playOn(question);
//        YoYo.with(Techniques.FadeIn).duration(600).playOn(optionList[0]);
//        YoYo.with(Techniques.FadeIn).duration(900).playOn(optionList[1]);
//        YoYo.with(Techniques.FadeIn).duration(1200).playOn(optionList[2]);
//        YoYo.with(Techniques.FadeIn).duration(1500).playOn(optionList[3]);

        optionList[0].setBackgroundColor(getResources().getColor(R.color.colorOptions));
        optionList[1].setBackgroundColor(getResources().getColor(R.color.colorOptions));
        optionList[2].setBackgroundColor(getResources().getColor(R.color.colorOptions));
        optionList[3].setBackgroundColor(getResources().getColor(R.color.colorOptions));

        //showing previously attempted questions status as red or green
        Integer markedAnswer = saved.get(currentQuestion);
        if (markedAnswer != null) {
            if (answers[currentQuestion] == markedAnswer)
                optionList[markedAnswer].setBackgroundColor(getResources().getColor(R.color.AnswerCorrect));
            else
                optionList[markedAnswer].setBackgroundColor(getResources().getColor(R.color.AnswerIncorrect));
        } else
            setAllOptionsClickable(true);

//        checkButtonsVisibility();
    }
    private void checkButtonsVisibility() {
        if (currentQuestion == 0)
            YoYo.with(Techniques.FadeOut).duration(200).playOn(previous);
        else if (currentQuestion == 9)
            YoYo.with(Techniques.FadeOut).duration(200).playOn(next);
        else {
            YoYo.with(Techniques.FadeIn).duration(0).playOn(previous);
            YoYo.with(Techniques.FadeIn).duration(0).playOn(next);
        }
    }

    private void setAllOptionsClickable(boolean b) {
        optionList[0].setClickable(b);
        optionList[1].setClickable(b);
        optionList[2].setClickable(b);
        optionList[3].setClickable(b);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.option1)
            saveSelectedOptionAndDisplayCorrectOrIncorrectOnClick(0);
        if(view.getId() == R.id.option2)
            saveSelectedOptionAndDisplayCorrectOrIncorrectOnClick(1);
        if(view.getId() == R.id.option3)
            saveSelectedOptionAndDisplayCorrectOrIncorrectOnClick(2);
        if(view.getId() == R.id.option4)
            saveSelectedOptionAndDisplayCorrectOrIncorrectOnClick(3);
        if(view.getId() == R.id.previous_button){
            if (currentQuestion > 0) {
                currentQuestion--;
                updateQuestionsAndOptions();
            }
        }
        if(view.getId() == R.id.next_button){
            if (currentQuestion < 9) {
                currentQuestion++;
                updateQuestionsAndOptions();
            }
        }
    }
}
