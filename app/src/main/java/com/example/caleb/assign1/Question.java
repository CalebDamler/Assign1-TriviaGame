package com.example.caleb.assign1;

/**
 * Created by Caleb on 2/1/2018.
 * this class was also modified from the candy store example from the prevoious android class
 *
 */

public class Question {
    private String category;
    private String question;
    private String correctAns;
    private int id;

   private String incorrectAns;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncorrectAns() {
        return incorrectAns;
    }

    public void setIncorrectAns(String incorrectAns) {
        this.incorrectAns = incorrectAns;
    }

    public Question(int newId, String newCategory, String newQuestion, String newCorrectAns, String  newIncorrectAns) {
        this.id = newId;
        this.category = newCategory;
        this.question = newQuestion;
        this.correctAns = newCorrectAns;
        this.incorrectAns = newIncorrectAns;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }


    public String toString(){
        return id + " " + question + " " + correctAns ;
    }



}
