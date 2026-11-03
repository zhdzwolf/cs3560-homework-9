import ProxyFiles.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyTest {

    private FakeSongService fakeServer;
    private SongServiceProxy proxy;

    // Fake server used for testing
    private static class FakeSongService implements SongService {

        private List<Song> songs = new ArrayList<>();

        public int idCalls = 0;
        public int titleCalls = 0;
        public int albumCalls = 0;

        public FakeSongService() {

            songs.add(new Song(1, "Blinding Lights",
                    "The Weeknd", "After Hours", 200));

            songs.add(new Song(2, "Save Your Tears",
                    "The Weeknd", "After Hours", 215));

            songs.add(new Song(3, "Levitating",
                    "Dua Lipa", "Future Nostalgia", 203));
        }

        @Override
        public Song searchById(Integer songID) {

            idCalls++;

            for (Song song : songs) {
                if (song.getSongID().equals(songID)) {
                    return song;
                }
            }

            return null;
        }

        @Override
        public List<Song> searchByTitle(String title) {

            titleCalls++;

            List<Song> results = new ArrayList<>();

            for (Song song : songs) {
                if (song.getTitle().equalsIgnoreCase(title)) {
                    results.add(song);
                }
            }

            return results;
        }

        @Override
        public List<Song> searchByAlbum(String album) {

            albumCalls++;

            List<Song> results = new ArrayList<>();

            for (Song song : songs) {
                if (song.getAlbum().equalsIgnoreCase(album)) {
                    results.add(song);
                }
            }

            return results;
        }
    }

    @BeforeEach
    public void setUp() {

        fakeServer = new FakeSongService();

        proxy = new SongServiceProxy(fakeServer);
    }

    // SEARCH BY ID TESTS

    @Test
    public void testSearchById() {

        Song result = proxy.searchById(1);

        assertEquals("Blinding Lights", result.getTitle());
    }

    @Test
    public void testSearchByIdUsesCache() {

        proxy.searchById(1);
        proxy.searchById(1);

        assertEquals(1, fakeServer.idCalls);
    }

    @Test
    public void testSearchByIdNotFound() {

        Song result = proxy.searchById(999);

        assertNull(result);
    }

    @Test
    public void testIdCacheSize() {

        proxy.searchById(1);
        proxy.searchById(2);

        assertEquals(2, proxy.getIdCacheSize());
    }

    // SEARCH BY TITLE TESTS

    @Test
    public void testSearchByTitle() {

        List<Song> results =
                proxy.searchByTitle("Blinding Lights");

        assertEquals(1, results.size());
    }

    @Test
    public void testSearchByTitleUsesCache() {

        proxy.searchByTitle("Blinding Lights");
        proxy.searchByTitle("Blinding Lights");

        assertEquals(1, fakeServer.titleCalls);
    }

    @Test
    public void testSearchByTitleCaseInsensitiveCache() {

        proxy.searchByTitle("Blinding Lights");
        proxy.searchByTitle("BLINDING LIGHTS");

        assertEquals(1, fakeServer.titleCalls);
    }

    @Test
    public void testTitleCacheSize() {

        proxy.searchByTitle("Blinding Lights");
        proxy.searchByTitle("Save Your Tears");

        assertEquals(2, proxy.getTitleCacheSize());
    }

    // SEARCH BY ALBUM TESTS

    @Test
    public void testSearchByAlbum() {

        List<Song> results =
                proxy.searchByAlbum("After Hours");

        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByAlbumUsesCache() {

        proxy.searchByAlbum("After Hours");
        proxy.searchByAlbum("After Hours");

        assertEquals(1, fakeServer.albumCalls);
    }

    @Test
    public void testSearchByAlbumCaseInsensitiveCache() {

        proxy.searchByAlbum("After Hours");
        proxy.searchByAlbum("AFTER HOURS");

        assertEquals(1, fakeServer.albumCalls);
    }

    @Test
    public void testAlbumCacheSize() {

        proxy.searchByAlbum("After Hours");
        proxy.searchByAlbum("Future Nostalgia");

        assertEquals(2, proxy.getAlbumCacheSize());
    }

    // GENERAL PROXY TESTS

    @Test
    public void testCachedSongIsReturned() {

        Song first = proxy.searchById(1);

        Song second = proxy.searchById(1);

        assertSame(first, second);
    }

    @Test
    public void testCachedTitleResultsAreIndependent() {

        List<Song> first =
                proxy.searchByTitle("After Hours");

        first.clear();

        List<Song> second =
                proxy.searchByTitle("After Hours");

        assertEquals(0, second.size());
    }

    @Test
    public void testCachedAlbumResultsAreIndependent() {

        List<Song> first =
                proxy.searchByAlbum("After Hours");

        first.clear();

        List<Song> second =
                proxy.searchByAlbum("After Hours");

        assertEquals(2, second.size());
    }
}