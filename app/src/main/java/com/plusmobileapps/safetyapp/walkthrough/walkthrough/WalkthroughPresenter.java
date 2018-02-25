package com.plusmobileapps.safetyapp.walkthrough.walkthrough;

import com.plusmobileapps.safetyapp.data.entity.Question;
import com.plusmobileapps.safetyapp.data.entity.Response;
import com.plusmobileapps.safetyapp.walkthrough.walkthrough.question.WalkthroughContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehanna2 on 2/24/2018.
 */

public class WalkthroughPresenter implements WalkthroughContract.Presenter {

    private WalkthroughContentFragment walkthroughFragment;
    private WalkthroughContract.View view;
    private List<Question> questions = new ArrayList<>(0);
    private List<Response> responses = new ArrayList<>(0);
    private int currentIndex = 0;

    public WalkthroughPresenter(WalkthroughContract.View view) {
        this.view = view;
    }

    @Override
    public void start(int locationId) {
       loadQuestions(locationId);
    }

    @Override
    public void loadQuestions(int locationId) {
       new WalkthroughActivityModel(locationId, view, this).execute();
    }

    @Override
    public void previousQuestionClicked() {
        Response lastResponse = view.getCurrentResponse();
        responses.set(currentIndex, lastResponse);
        currentIndex--;

        if(currentIndex < 0) {
            saveResponses();
            return;
        }

        view.showPreviousQuestion();
    }

    @Override
    public void nextQuestionClicked() {
        //if youre at the last question
        if(currentIndex == questions.size()) {
            saveResponses();
            return;
        }

        Response response = view.getCurrentResponse();
        responses.set(currentIndex, response);
        currentIndex++;
        view.showNextQuestion(questions.get(currentIndex));

    }

    @Override
    public void confirmationExitClicked() {
        view.closeWalkthrough();
    }

    @Override
    public void backButtonPressed() {
        view.showConfirmationDialog();
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    private void saveResponses() {
        //TODO create async task to save responses
    }
}
