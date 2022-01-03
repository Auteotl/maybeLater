package SomePack;

import java.util.Date;

public class TableBody {
    private int urlId;
    private String someURL;
    private String description;
    private int urlCat;
    private String date;

    public TableBody() {
        this.urlId = 0;
        this.someURL = "";
        this.description = "";
        this.urlCat = 0;
        this.date = "";
    }
    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public void setSomeURL(String someURL) {
        this.someURL = someURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrlCat(int urlCat) {
        this.urlCat = urlCat;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getUrlId() {
        return urlId;
    }

    public String getSomeURL() {
        return someURL;
    }

    public String getDescription() {
        return description;
    }

    public int getUrlCat() {
        return urlCat;
    }

    public String getDate() {
        return date;
    }

    public TableBody(int urlId, String someURL, String description, int urlCat, String date) {
        this.urlId = urlId;
        this.someURL = someURL;
        this.description = description;
        this.urlCat = urlCat;
        this.date = date;
    }

}
