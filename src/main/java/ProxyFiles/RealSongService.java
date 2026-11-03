package ProxyFiles;

import java.util.ArrayList;
import java.util.List;

public class RealSongService implements SongService {

    private List<Song> songs;

    public RealSongService() {

        songs = new ArrayList<>();

        // Create 8 songs

        songs.add(new Song(1, "Blinding Lights",
                "The Weeknd", "After Hours", 200));

        songs.add(new Song(2, "Save Your Tears",
                "The Weeknd", "After Hours", 215));

        songs.add(new Song(3, "Levitating",
                "Dua Lipa", "Future Nostalgia", 203));

        songs.add(new Song(4, "Don't Start Now",
                "Dua Lipa", "Future Nostalgia", 183));

        songs.add(new Song(5, "Anti-Hero",
                "Taylor Swift", "Midnights", 200));

        songs.add(new Song(6, "Lavender Haze",
                "Taylor Swift", "Midnights", 202));

        songs.add(new Song(7, "As It Was",
                "Harry Styles", "Harry's House", 167));

        songs.add(new Song(8, "Watermelon Sugar",
                "Harry Styles", "Fine Line", 174));
    }

    // Simulates a slow server request
    private void simulateServerDelay() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Song searchById(Integer songID) {

        simulateServerDelay();

        for (Song song : songs) {

            if (song.getSongID().equals(songID)) {
                return song;
            }
        }

        return null;
    }

    @Override
    public List<Song> searchByTitle(String title) {

        simulateServerDelay();

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

        simulateServerDelay();

        List<Song> results = new ArrayList<>();

        for (Song song : songs) {

            if (song.getAlbum().equalsIgnoreCase(album)) {
                results.add(song);
            }
        }

        return results;
    }
}