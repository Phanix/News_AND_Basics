package hantaro.com.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String mURl = "";
    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        this.mURl = url;
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        List<News> news = new ArrayList<>();
        try {
            URL url = new URL(this.mURl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            String result = Helper.convertInputStream(inputStream);
            news = Helper.jsonParseNews(result);
            Log.i("Result", result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return news;
    }

    @Override
    public void deliverResult(@Nullable List<News> data) {
        super.deliverResult(data);

    }
}
