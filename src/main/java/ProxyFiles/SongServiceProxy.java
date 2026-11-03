package ProxyFiles;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class SongServiceProxy implements SongService {

    private SongService realService;

    private Map<Integer, Song> idCache;
    private Map<String, List<Song>> titleCache;
    private Map<String, List<Song>> albumCache;

    public SongServiceProxy(SongService realService) {

        this.realService = realService;

        idCache = new HashMap<>();
        titleCache = new HashMap<>();
        albumCache = new HashMap<>();
    }

    @Override
    public Song searchById(Integer songID) {

        if (idCache.containsKey(songID)) {

            System.out.println("Cache HIT: Song ID " + songID);

            return idCache.get(songID);
        }

        System.out.println("Cache MISS: Song ID " + songID);

        Song song = realService.searchById(songID);

        if (song != null) {
            idCache.put(songID, song);
        }

        return song;
    }

    @Override
    public List<Song> searchByTitle(String title) {

        String key = title.toLowerCase().trim();

        if (titleCache.containsKey(key)) {

            System.out.println("Cache HIT: Title " + title);

            return new ArrayList<>(titleCache.get(key));
        }

        System.out.println("Cache MISS: Title " + title);

        List<Song> results = realService.searchByTitle(title);

        titleCache.put(key, new ArrayList<>(results));

        return new ArrayList<>(results);
    }

    @Override
    public List<Song> searchByAlbum(String album) {

        String key = album.toLowerCase().trim();

        if (albumCache.containsKey(key)) {

            System.out.println("Cache HIT: Album " + album);

            return new ArrayList<>(albumCache.get(key));
        }

        System.out.println("Cache MISS: Album " + album);

        List<Song> results = realService.searchByAlbum(album);

        albumCache.put(key, new ArrayList<>(results));

        return new ArrayList<>(results);
    }

    // Returns the number of cached songs searched by ID
    public int getIdCacheSize() {
        return idCache.size();
    }

    // Returns the number of cached title searches
    public int getTitleCacheSize() {
        return titleCache.size();
    }

    // Returns the number of cached album searches
    public int getAlbumCacheSize() {
        return albumCache.size();
    }
}