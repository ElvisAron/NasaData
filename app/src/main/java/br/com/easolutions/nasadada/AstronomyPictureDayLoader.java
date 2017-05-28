package br.com.easolutions.nasadada;


import android.content.AsyncTaskLoader;
import android.content.Context;

import br.com.easolutions.nasadada.network.QueryUtils;

/**
 * Created by Elvis Aron Andrade on 27/05/2017.
 */

public class AstronomyPictureDayLoader extends AsyncTaskLoader<AstronomyPictureDay> {

    private String mUrl;

    public AstronomyPictureDayLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public AstronomyPictureDay loadInBackground() {

        if (mUrl == null){
            return null;
        }

        return QueryUtils.fetchAstronomyPictureDay(mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
