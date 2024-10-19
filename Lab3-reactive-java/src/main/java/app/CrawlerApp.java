package app;

import java.io.IOException;
import java.util.List;

public class CrawlerApp {

    public static final String SCRAPER_API_KEY = "7811b91612435e2e05f8839ad2d3e13d";

    private static final List<String> TOPICS = List.of("Airplane", "BigBen", "Agent Cooper", "Sherlock", "Poirot", "Detective Monk");


    public static void main(String[] args) throws IOException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
//        photoCrawler.downloadPhotoExamples();
//        photoCrawler.downloadPhotosForQuery(TOPICS.get(0));
        photoCrawler.downloadPhotosForMultipleQueries(TOPICS);
    }
}