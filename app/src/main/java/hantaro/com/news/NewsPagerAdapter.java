package hantaro.com.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsPagerAdapter extends PagerAdapter {

    Context mContext;
    List<News> mNews;

    public NewsPagerAdapter(Context context, List<News> news) {
        mContext = context;
        mNews = news;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View itemView = layoutInflater.inflate(R.layout.news_layout, container, false);
        TextView tvTitle = itemView.findViewById(R.id.tv_title);
        ImageView imageView = itemView.findViewById(R.id.iv_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = mNews.get(position).getNewsUrl().toString();
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                mContext.startActivity(intent);

            }
        });
        String text = mNews.get(position).getTitle();
        final String textToSend = text;
        FloatingActionButton floatingActionButton = itemView.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, textToSend);
                mContext.startActivity(intent);

            }
        });
        Picasso.with(mContext).load(mNews.get(position).getImageUrl()).into(imageView);

        if(text.length() > 40){
            text = text.substring(0, 37) + "...";
        }
        tvTitle.setText(text);
        container.addView(itemView);
        return  itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public int getCount() {
        if(mNews == null){
            return 0;
        }else{
            return mNews.size();
        }
    }

    @Override
    public boolean isViewFromObject( View view, Object o) {
        return o == view;
    }
}
