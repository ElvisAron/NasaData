package br.com.easolutions.nasadada;

import android.app.LoaderManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.easolutions.nasadada.utils.CustomTypefaceSpan;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<AstronomyPictureDay>{

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String URL_NASA_APOD = "https://api.nasa.gov/planetary/apod?api_key=G7RLGXo6imZXJJeYC8OfpCRF9cstJH9NS6ISYlG4";
    /**
     * Valor constante para o ID do carregador de terremotos. Podemos escolher qualquer número inteiro.
     * Isso realmente só entra em jogo se você estiver usando vários carregadores.
     */
    private static final int ASTRO_PICTURE_DAY_LOADER_ID = 1;
    private TextView mTvTitle;
    private ImageView mIvPictureDay;
    private TextView mTvExplanation;
    private TextView mTvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mIvPictureDay = (ImageView)findViewById(R.id.iv_picture);
        mTvExplanation = (TextView)findViewById(R.id.tv_explanation);
        mTvCopyright = (TextView)findViewById(R.id.tv_copyright);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(ASTRO_PICTURE_DAY_LOADER_ID, null, this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.pic_of_day));

    }

    @Override
    public android.content.Loader<AstronomyPictureDay> onCreateLoader(int id, Bundle args) {
        return new AstronomyPictureDayLoader(this, URL_NASA_APOD);
    }

    @Override
    public void onLoadFinished(android.content.Loader<AstronomyPictureDay> loader, AstronomyPictureDay data) {

        //Log.d(LOG_TAG, "data: " + data.getExplanation());

        if (data != null){
           mTvTitle.setText(data.getTitle());
            Picasso.with(this).load(data.getUrl()).into(mIvPictureDay);
            String explanation = getString(R.string.explanation);
            explanation+= " ";
            Spannable spannable = new SpannableString(explanation+data.getExplanation());
            spannable.setSpan( new CustomTypefaceSpan("sans-serif",Typeface.DEFAULT_BOLD), 0, explanation.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan( new CustomTypefaceSpan("monospace", Typeface.DEFAULT), explanation.length(), explanation.length() + data.getExplanation().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvExplanation.setText( spannable );

            String copyright = getString(R.string.copyright);
            copyright+=" ";
            String getCopyright = data.getCopyright().replaceAll("\\n"," ");
            Spannable spanCopyright = new SpannableString(copyright + getCopyright);
            spanCopyright.setSpan( new CustomTypefaceSpan("sans-serif",Typeface.DEFAULT_BOLD), 0, copyright.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanCopyright.setSpan( new CustomTypefaceSpan("monospace",Typeface.DEFAULT), copyright.length(), copyright.length() + getCopyright.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mTvCopyright.setText(spanCopyright);

        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<AstronomyPictureDay> loader) {
        mTvExplanation.setText("");
    }
}
