//Full Name: Golsa Momeni
//Student number: 501202209


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class AudioContentNotFoundException extends RuntimeException {
    public AudioContentNotFoundException(String message) {
        super(message);
    }
}

class ChapterNotFoundException extends RuntimeException {
    public ChapterNotFoundException(String message) {
        super(message);
    }
}

class PlaylistNotFoundException extends RuntimeException {
    public PlaylistNotFoundException(String message) {
        super(message);
    }
}

class SongAlreadyDownloadedException extends RuntimeException {
    public SongAlreadyDownloadedException(String message) {
        super(message);
    }
}

public class Library {
    private ArrayList<Song> songs;
    private ArrayList<AudioBook> audiobooks;
    private ArrayList<Playlist> playlists;

    //private ArrayList<Podcast> 	podcasts;


    public Library() {
        songs = new ArrayList<Song>();
        audiobooks = new ArrayList<AudioBook>();
        playlists = new ArrayList<Playlist>();
        //podcasts		= new ArrayList<Podcast>(); ;
    }

    /*
     * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
     * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
     * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
     * to determine which list it belongs to above
     *
     * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
     * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
     * See the video
     */
    public void download(AudioContent content) throws SongAlreadyDownloadedException {
        String type = content.getType();
        if (type.equals(Song.TYPENAME)) {
            if (songs.contains((Song) content)) {
                throw new SongAlreadyDownloadedException("SONG " + content.getTitle() + " already downloaded");
            } else {
                songs.add((Song) content);
                System.out.println("SONG " + content.getTitle() + " Added to Library");
            }
        } else if (type.equals(AudioBook.TYPENAME)) {
            if (audiobooks.contains((AudioBook) content)) {
                throw new SongAlreadyDownloadedException("AUDIOBOOK " + content.getTitle() + " already downloaded");
            } else {
                audiobooks.add((AudioBook) content);
                System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");
            }
        }
//        } else if type.equals("PODCAST") {
//            if podcasts.contains(Podcast content) {
//                errorMsg = "Podcast already downloaded";
//                return false;
//            } else {
//                podcasts.add((Podcast) content);
//            }
//        }
    }

    // Print Information (printInfo()) about all songs in the array list
    public void listAllSongs() {
        for (int i = 0; i < songs.size(); i++) {
            int index = i + 1;
            System.out.print("" + index + ". ");
            songs.get(i).printInfo();
            System.out.println();
        }
    }

    // Print Information (printInfo()) about all audiobooks in the array list
    public void listAllAudioBooks() {
        for (int i = 0; i < audiobooks.size(); i++) {
            int index = i + 1;
            System.out.print("" + index + ". ");
            audiobooks.get(i).printInfo();
            System.out.println();
        }
    }

    // Print Information (printInfo()) about all podcasts in the array list
    public void listAllPodcasts() {
        /*for (int i = 0; i < podcasts.size(); i++)
        {
            int index = i + 1;
            System.out.print("" + index + ". ");
            audiobooks.get(i).printInfo();
            System.out.println();
        }*/
    }

    // Print the name of all playlists in the playlists array list
    // First print the index number as in listAllSongs() above
    public void listAllPlaylists() {
        for (int i = 0; i < playlists.size(); i++) {
            int index = i + 1;
            System.out.print("" + index + ". ");
            System.out.println(playlists.get(i).getTitle());
            System.out.println();
        }
    }



    // Print the name of all artists.
    public void listAllArtists() {
        // First create a new (empty) array list of string
        // Go through the songs array list and add the artist name to the new arraylist only if it is
        // not already there. Once the artist arrayl ist is complete, print the artists names
        ArrayList<String> artists = new ArrayList<String>();
        for (int i = 0; i < songs.size(); i++) {
            String artist = songs.get(i).getArtist();
            if (!artists.contains(artist)) {
                artists.add(artist);
            }
        }

        for (int i = 0; i < artists.size(); i++) {
            int index = i + 1;
            System.out.print("" + index + ". ");
            System.out.println(artists.get(i));
            System.out.println();
        }
    }

    // Delete a song from the library (i.e. the songs list) -
    // also go through all playlists and remove it from any playlist as well if it is part of the playlist
    public void deleteSong(int index) throws AudioContentNotFoundException {
        if (index > songs.size()) {
            throw new AudioContentNotFoundException("Song not found");
        }

        for (Playlist playlist : playlists) {
            if (playlist.getContent().contains(songs.get(index - 1))) {
                // add 1 to the index to account for the 1 subtracted in the deleteContent method
                playlist.deleteContent(playlist.getContent().indexOf(songs.get(index - 1)) + 1);
            }
        }

        songs.remove(index - 1);
    }

    //Sort songs in library by year
    public void sortSongsByYear() {
        Collections.sort(songs, new SongYearComparator());
    }

    // Write a class SongYearComparator that implements
    // the Comparator interface and compare two songs based on year
    private class SongYearComparator implements Comparator<Song> {
        // Compare two songs based on their release year
        public int compare(Song first, Song second) {
            return first.getYear() - (second.getYear());
        }
    }

    // Sort songs by length
    public void sortSongsByLength() {
        Collections.sort(songs, new SongLengthComparator());
    }

    // Write a class SongLengthComparator that implements
    // the Comparator interface and compare two songs based on length
    private class SongLengthComparator implements Comparator<Song> {
        // Compare two songs based on their length
        public int compare(Song first, Song second) {
            return first.getLength() - (second.getLength());
        }
    }

    // Sort songs by title
    public void sortSongsByName() {
        // Use Collections.sort()
        // class Song should implement the Comparable interface
        // see class Song code
        Collections.sort(songs);
    }



    /*
     * Play Content
     */

    // Play song from songs list
    public void playSong(int index) throws AudioContentNotFoundException {
        if (index < 1 || index > songs.size()) {
            throw new AudioContentNotFoundException("Song not found");
        }
        songs.get(index - 1).play();
    }

    // Play podcast from list (specify season and episode)
    // Bonus
    public boolean playPodcast(int index, int season, int episode) {
        return false;
    }

    // Print the episode titles of a specified season
    // Bonus
    public boolean printPodcastEpisodes(int index, int season) {
        return false;
    }

    // Play a chapter of an audio book from list of audiobooks
    public void playAudioBook(int index, int chapter) throws AudioContentNotFoundException, ChapterNotFoundException {
        if (index < 1 || index > audiobooks.size()) {
            throw new AudioContentNotFoundException("AudioBook not found");
        }

        AudioBook audiobook = audiobooks.get(index - 1);

        if (chapter < 1 || chapter > audiobook.getNumberOfChapters()) {
            throw new ChapterNotFoundException("Chapter not found");
        }

        audiobook.selectChapter(chapter);
        audiobook.play();
    }

    // Print the chapter titles (Table Of Contents) of an audiobook
    // see class AudioBook
    public void printAudioBookTOC(int index) throws AudioContentNotFoundException {
        if (index < 1 || index > audiobooks.size()) {
            throw new AudioContentNotFoundException("AudioBook not found");
        }

        audiobooks.get(index - 1).printTOC();
    }

    /*
     * Playlist Related Methods
     */

    // Make a new playlist and add to playlists array list
    // Make sure a playlist with the same title doesn't already exist
    public void makePlaylist(String title) throws IllegalArgumentException {
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(title)) {
                throw new IllegalArgumentException("Playlist " + title + " already exists");
            }
        }
        Playlist newPlaylist = new Playlist(title);
        playlists.add(newPlaylist);
    }

    // Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
    public void printPlaylist(String title) throws PlaylistNotFoundException {
        Playlist desiredPlaylist = new Playlist(title);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(title)) {
                desiredPlaylist = playlist;
                desiredPlaylist.printContents();
                return;
            }
        }

        throw new PlaylistNotFoundException("Playlist not found");
    }

    // Play all content in a playlist
    public void playPlaylist(String playlistTitle) throws PlaylistNotFoundException {
        Playlist desiredPlaylist = new Playlist(playlistTitle);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(playlistTitle)) {
                desiredPlaylist = playlist;
                desiredPlaylist.playAll();
                return;
            }
        }

        throw new PlaylistNotFoundException("Playlist not found");
    }

    // Play a specific song/audiobook in a playlist
    public void playPlaylist(String playlistTitle, int indexInPL) throws PlaylistNotFoundException {
        Playlist desiredPlaylist = new Playlist(playlistTitle);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(playlistTitle)) {
                desiredPlaylist = playlist;
                desiredPlaylist.play(indexInPL);
                return;
            }
        }

        throw new PlaylistNotFoundException("Playlist not found");
    }

    // Add a song/audiobook/podcast from library lists at top to a playlist
    // Use the type parameter and compare to Song.TYPENAME etc
    // to determine which array list it comes from then use the given index
    // for that list
    public void addContentToPlaylist(String type, int index, String playlistTitle) throws IndexOutOfBoundsException, IllegalArgumentException {
        Playlist desiredPlaylist = new Playlist(playlistTitle);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(playlistTitle)) {
                desiredPlaylist = playlist;
            }
        }

        if (type.equalsIgnoreCase(Song.TYPENAME)) {
            if (index < 1 || index > songs.size()) {
                throw new IndexOutOfBoundsException("Index out of bounds. Song does not exist");
            }
            desiredPlaylist.addContent(songs.get(index - 1));
        } else if (type.equalsIgnoreCase(AudioBook.TYPENAME)) {
            if (index < 1 || index > audiobooks.size()) {
                throw new IndexOutOfBoundsException("Index out of bounds. AudioBook does not exist");
            }
            desiredPlaylist.addContent(audiobooks.get(index - 1));
        } else {
            throw new IllegalArgumentException("Invalid type");
        }
    }

    // Delete a song/audiobook/podcast from a playlist with the given title
    // Make sure the given index of the song/audiobook/podcast in the playlist is valid
    public void delContentFromPlaylist(int index, String title) throws IndexOutOfBoundsException {

        Playlist desiredPlaylist = new Playlist(title);
        for (Playlist playlist : playlists) {
            if (playlist.getTitle().equals(title)) {
                desiredPlaylist = playlist;
            }
        }
        if (index < 1 || index > desiredPlaylist.getContent().size()) {
            throw new IndexOutOfBoundsException("Index out of bounds. Content does not exist");
        }
        desiredPlaylist.deleteContent(index);
    }
}

