package hantaro.com.news;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    String  startingUrl = "http://content.guardianapis.com/search?q=games&api-key=29357165-0e91-4f8c-953e-587d84f45d33&show-fields=thumbnail";

    final String BASE_URL = "http://content.guardianapis.com/search?";
    final String QUERY_PARAM = "q";
    final String API_KEY = "api-key";
    final String KEY = "29357165-0e91-4f8c-953e-587d84f45d33";
    final String SHOW_FIELDS = "show-fields";
    ViewPager viewPager;
    ImageView errorImage;
    ProgressBar progressBar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search){
            final AlertDialog.Builder buider = new AlertDialog.Builder(this);
            buider.setTitle(getResources().getString(R.string.search_dialog_title));
            buider.setMessage(getResources().getString(R.string.search_dialog_message));
            final EditText editText = new EditText(this);
            buider.setView(editText);
             buider.setPositiveButton(getResources().getString(R.string.search_button_positive), new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                     String text = editText.getText().toString();
                     Uri buidUri = Uri.parse(BASE_URL)
                             .buildUpon()
                             .appendQueryParameter(QUERY_PARAM, text)
                             .appendQueryParameter(API_KEY, KEY)
                             .appendQueryParameter(SHOW_FIELDS, "thumbnail").build();

                     startingUrl = buidUri.toString();
                     progressBar.setVisibility(View.VISIBLE);
                     errorImage.setVisibility(View.GONE);
                     viewPager.setVisibility(View.GONE);
                     getSupportLoaderManager().restartLoader(0, null, MainActivity.this);
                 }
             });
             buider.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         viewPager = findViewById(R.id.view_pager);
         errorImage = findViewById(R.id.pag_not_found);
         progressBar = findViewById(R.id.pb_searching);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, @Nullable Bundle bundle) {
        NewsLoader newsLoader = new NewsLoader(this, startingUrl);
        newsLoader.forceLoad();
        return newsLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        progressBar.setVisibility(View.GONE);
        if(news.size() <= 0 || news == null){
            errorImage.setVisibility(View.VISIBLE);
        }else {
            viewPager.setVisibility(View.VISIBLE);
            NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(this, news);
            viewPager.setAdapter(newsPagerAdapter);

        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {

    }
}
