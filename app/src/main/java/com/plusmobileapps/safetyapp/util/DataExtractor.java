package com.plusmobileapps.safetyapp.util;

import com.plusmobileapps.safetyapp.MyApplication;
import com.plusmobileapps.safetyapp.actionitems.landing.ActionItemPresenter;
import com.plusmobileapps.safetyapp.data.AppDatabase;
import com.plusmobileapps.safetyapp.data.dao.LocationDao;
import com.plusmobileapps.safetyapp.data.dao.QuestionDao;
import com.plusmobileapps.safetyapp.data.dao.ResponseDao;
import com.plusmobileapps.safetyapp.data.entity.Location;
import com.plusmobileapps.safetyapp.data.entity.Question;
import com.plusmobileapps.safetyapp.data.entity.Response;

import java.util.List;

public class DataExtractor {

    private AppDatabase db;
    //ActionItemPresenter presenter = new ActionItemPresenter();

    public List<Response> getlist(){
        db = AppDatabase.getAppDatabase(MyApplication.getAppContext());


        ResponseDao responseDao = db.responseDao();
        LocationDao locationDao = db.locationDao();
        QuestionDao questionDao = db.questionDao();
        List<Response> items = responseDao.getAllActionItems(1);

        for (Response actionItem : items) {
            int locationId = actionItem.getLocationId();
            Location location = locationDao.getByLocationId(locationId);
            actionItem.setLocationName(location.getName());

            int questionId = actionItem.getQuestionId();
            Question question = questionDao.getByQuestionID(questionId);
            String title = question.getShortDesc();
            actionItem.setTitle(title);

        }


        //find where walkthrough data is stored return it as a string.


        return items;

    }
}