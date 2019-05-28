package com.ASUPEACLab.safetyapp.walkthrough.landing;

import android.os.AsyncTask;
import android.util.Log;

import com.ASUPEACLab.safetyapp.MyApplication;
import com.ASUPEACLab.safetyapp.data.AppDatabase;
import com.ASUPEACLab.safetyapp.data.dao.WalkthroughDao;
import com.ASUPEACLab.safetyapp.data.entity.Walkthrough;

import java.util.List;

/**
 * Created by kneil on 2/21/2018.
 */

public class LoadWalkthroughs extends AsyncTask<Void, Void, List<Walkthrough>> {
    private static final String TAG = "LoadWalkthroughs";
    private AppDatabase db;
    private List<Walkthrough> walkthroughs;
    private WalkthroughLandingPresenter.WalkthroughListLoadingListener listener;

    public LoadWalkthroughs(WalkthroughLandingPresenter.WalkthroughListLoadingListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Walkthrough> doInBackground(Void... voids) {
        Log.d(TAG, "Loading walkthroughs...");
        db = AppDatabase.getAppDatabase((MyApplication.getAppContext()));
        WalkthroughDao dao = db.walkthroughDao();
        walkthroughs = dao.getAll();

        return walkthroughs;
    }

    protected void onPostExecute(List<Walkthrough> walkthroughs) {
        Log.d(TAG, "Done loading walkthroughs");
        listener.onWalkthroughListLoaded(walkthroughs);
    }
}