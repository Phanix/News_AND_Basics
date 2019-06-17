package hantaro.com.news;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        NewsLoader newsLoader = new NewsLoader(this, "http://content.guardianapis.com/search?q=games&api-key=29357165-0e91-4f8c-953e-587d84f45d33&show-fields=thumbnail");
        newsLoader.forceLoad();
        return newsLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        ViewPager viewPager = findViewById(R.id.view_pager);
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(this, news);
        viewPager.setAdapter(newsPagerAdapter);



    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }
}
