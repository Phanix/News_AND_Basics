package hantaro.com.news;

public class News {
    private String mTitle;
    private String mImageUrl;
    private String mNewsUrl;

    public News(String title, String imageUrl, String newsUrl) {
        mTitle = title;
        mImageUrl = imageUrl;
        mNewsUrl = newsUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getNewsUrl() {
        return mNewsUrl;
    }

    public String toString(){
        return getTitle();
    }
}
