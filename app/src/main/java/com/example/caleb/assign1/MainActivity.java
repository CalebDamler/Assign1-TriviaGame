package com.example.caleb.assign1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/****************************************
 *Caleb Damler
 * CSCI 428
 * ASSIGN1
 * 3/6/2018
 *
 * Database App
 *
 * this app downloads json data from the internet
 * parses it stores it in a data base then presents the question data
 * in the form of a trivia game.
 *****************************************/
public class MainActivity extends AppCompatActivity {

    private DataBaseManager dataBaseManager;

    private String TAG = MainActivity.class.getSimpleName();

    private TextView questionTx, questionTx2, numC, numI;
    private Button trueButton, falseButton, nextButton;

    int id = 0;
    int ids = 2;
    int scoreC = 0;
    int scoreI = 0;
    String answer;
    ArrayList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();

        questionTx = findViewById(R.id.questionText);
        questionTx2 = findViewById(R.id.questionText2);
        trueButton = findViewById(R.id.trueBtn);
        falseButton = findViewById(R.id.falseBtn);
        nextButton = findViewById(R.id.NextBtn);
        numC = findViewById(R.id.numC);
        numI = findViewById(R.id.numI);
        dataBaseManager = new DataBaseManager(this);


        new GetContacts().execute();
        this.updateView();

    }

    /****************************************
     * truePress()
     *
     * called when the user presses true for the question
     * if the answer is true one point will be added to
     * the score. if its not true 1 will be added to the incorrect
     * score
     *
     * buttons are disable once they are pressed
     * to avoid scoring problems
     *****************************************/
    public void truePress(View view) {
        if(answer.contains("F")) {

            scoreI += 1;
            numI.setText(String.valueOf(scoreI));
        } else {
            scoreC += 1;
            numC.setText(String.valueOf(scoreC));
        }
        questionTx2.setText(answer);
        trueButton.setEnabled(false);
        falseButton.setEnabled(false);
        nextButton.setEnabled(true);
    }
    /****************************************
     *falsePress()
     *
     * called when the false button is pressed
     * if the answer is false one point is added to
     * the correct score. if not one point is added to
     * the incorrect score
     *
     * buttons are disable once they are pressed
     * to avoid scoring problems
     *
     *****************************************/
    public void falsePress(View view) {
        if (answer.contains("F")) {
            scoreC += 1;
            numC.setText(String.valueOf(scoreC));

        } else {
            scoreI++;
            numI.setText(String.valueOf(scoreI));
        }
        questionTx2.setText(answer);
        falseButton.setEnabled(false);
        trueButton.setEnabled(false);
        nextButton.setEnabled(true);
    }
    /****************************************
     *nextPress()
     *
     * called when the next button is clicked
     * when called it gets the next question
     * and displays it, resetting the correct
     * answer text box and re-enabling the
     * true and false buttons
     *
     *****************************************/
    public void nextPress(View view) {
        Question questions = dataBaseManager.selectByID(ids);
        questionTx.setText(questions.getQuestion());
        questionTx2.setText("???");
        answer = questions.getCorrectAns();
        ids++;

        trueButton.setEnabled(true);
        falseButton.setEnabled(true);

    }
    /****************************************
     *updateView()
     *
     * called by onCreate to set the initial question
     * and answer

     *****************************************/
    public void updateView() {
        nextButton.setEnabled(false);

        Question questions = dataBaseManager.selectByID(1);
        questionTx2.setText("???");
        answer = questions.getCorrectAns();


           questionTx.setText(questions.getQuestion());



    }
    /****************************************
     *GetContacts()
     *
     * download, parses then inserts the data into
     * the database
     *
     * i used this site to base my parser off of
     * the only things i changed was instead of storing the
     * data in a hashmap I put it directly into the database
     *
     * https://www.tutorialspoint.com/android/android_json_parser.htm
     *****************************************/
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://opentdb.com/api.php?amount=10&category=9&difficulty=medium&type=boolean";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray results = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);
                        String category = c.getString("category");
                        String question = c.getString("question");
                        String correct_answer = c.getString("correct_answer");
                        String incorrect_answer = c.getString("incorrect_answers");
                        id = i;

                        Question question2 = new Question(id,category, question, correct_answer, incorrect_answer);

                        dataBaseManager.insert(question2);



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
}
