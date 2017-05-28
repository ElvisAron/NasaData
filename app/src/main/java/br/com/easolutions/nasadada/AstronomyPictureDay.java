package br.com.easolutions.nasadada;

/**
 * Created by Elvis Aron Andrade on 27/05/2017.
 */

public class AstronomyPictureDay {

    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String title;
    private String url;

    public AstronomyPictureDay(String copyright, String date, String explanation, String hdurl, String title, String url) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.title = title;
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
