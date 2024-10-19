package app;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            Observable<Photo> downloadedExamples = photoDownloader.getPhotoExamples();

            downloadedExamples.blockingSubscribe(photoSerializer::savePhoto);

        } catch (IOException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForQuery(String query) throws IOException {
        try {
            photoDownloader.searchForPhotos(query)
                    .take(10)
                    .blockingSubscribe(photoSerializer::savePhoto);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
        List<Observable<Photo>> list = new ArrayList<>();

        try {
            for (String query : queries) {
                list.add(photoDownloader.searchForPhotos(query).subscribeOn(Schedulers.io()));
            }
        } catch (IOException | InterruptedException e) {
            log.log(Level.SEVERE, "Downloading multiple photos error", e);
        }

        Observable.merge(list)
                .take(15)
                .blockingSubscribe(photoSerializer::savePhoto,
                        e -> log.log(Level.SEVERE, "Error downloading photos", e));
    }
}
