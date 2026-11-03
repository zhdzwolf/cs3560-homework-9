import ProxyFiles.*;
import java.util.List;

public class ProxyDriver {

    public static void main(String[] args) {

        // Create the actual server
        SongService realService = new RealSongService();

        // Create the proxy
        SongService musicService =
                new SongServiceProxy(realService);

        System.out.println("===== MUSIC STREAMING APP =====");

        // Search by ID
        System.out.println("\n--- Search By ID ---");

        System.out.println(musicService.searchById(1));

        // Search same ID again (cached)
        System.out.println("\n--- Search Same ID Again ---");

        System.out.println(musicService.searchById(1));

        // Search by title
        System.out.println("\n--- Search By Title ---");

        List<Song> titleResults =
                musicService.searchByTitle("Blinding Lights");

        for (Song song : titleResults) {
            System.out.println(song);
        }

        // Search same title again (cached)
        System.out.println("\n--- Search Same Title Again ---");

        titleResults =
                musicService.searchByTitle("Blinding Lights");

        for (Song song : titleResults) {
            System.out.println(song);
        }

        // Search by album
        System.out.println("\n--- Search By Album ---");

        List<Song> albumResults =
                musicService.searchByAlbum("After Hours");

        for (Song song : albumResults) {
            System.out.println(song);
        }

        // Search same album again (cached)
        System.out.println("\n--- Search Same Album Again ---");

        albumResults =
                musicService.searchByAlbum("After Hours");

        for (Song song : albumResults) {
            System.out.println(song);
        }

        // Search remaining songs by ID
        System.out.println("\n--- Search Remaining Songs ---");

        for (int i = 2; i <= 8; i++) {
            System.out.println(musicService.searchById(i));
        }

        // Display cache statistics
        SongServiceProxy proxy = (SongServiceProxy) musicService;

        System.out.println("\n===== CACHE STATISTICS =====");

        System.out.println("Cached ID searches: " +
                proxy.getIdCacheSize());

        System.out.println("Cached title searches: " +
                proxy.getTitleCacheSize());

        System.out.println("Cached album searches: " +
                proxy.getAlbumCacheSize());
    }
}