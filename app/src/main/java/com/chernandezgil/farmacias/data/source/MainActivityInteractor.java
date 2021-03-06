package com.chernandezgil.farmacias.data.source;

import android.content.Context;
import android.content.Intent;

import com.chernandezgil.farmacias.MyApplication;
import com.chernandezgil.farmacias.model.ColorMap;
import com.chernandezgil.farmacias.services.DownloadFarmacias;
import com.chernandezgil.farmacias.ui.adapter.PreferencesManager;

/**
 * Created by Carlos on 12/08/2016.
 */
public class MainActivityInteractor {
    private PreferencesManager mPreferencesManager;
    private Context mContext;

    public MainActivityInteractor(PreferencesManager preferencesManager) {
        mPreferencesManager=preferencesManager;
        mContext= MyApplication.getContext();
    }

    public void loadData(){
        if(mPreferencesManager.isFirstExecution()) {
            launchDownloadService();
            ColorMap colorMap = new ColorMap();
            colorMap.generate();
            mPreferencesManager.saveColorMap(colorMap.getColorHashMap());
            mPreferencesManager.setFirstExecutionFalse();
        }
    }

    private void launchDownloadService() {
        Intent intent = new Intent(mContext, DownloadFarmacias.class);
        mContext.startService(intent);
    }
}
