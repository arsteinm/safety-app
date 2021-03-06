package com.plusmobileapps.safetyapp.signup;

import android.os.AsyncTask;

import com.plusmobileapps.safetyapp.MyApplication;
import com.plusmobileapps.safetyapp.data.AppDatabase;
import com.plusmobileapps.safetyapp.data.dao.SchoolDao;
import com.plusmobileapps.safetyapp.data.entity.School;

/**
 * Created by Robert Beerman on 2/19/2018.
 */

public class SaveSchoolTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "SaveSchoolTask";
    private AppDatabase db;
    private School school;

    SaveSchoolTask(School school) {
        this.school = school;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        db = AppDatabase.getAppDatabase(MyApplication.getAppContext());
        SchoolDao dao = db.schoolDao();
        dao.insert(school);

        return true;
    }

    @Override
    protected void onPostExecute(Boolean finished) {

    }
}
