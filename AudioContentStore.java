//Full Name: Golsa Momeni
//Student number: 501202209

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore {
    private ArrayList<AudioContent> contents;
    private HashMap<String, Integer> contentMap;
    private HashMap<String, ArrayList<Integer>> artistMap;
    private HashMap<String, ArrayList<Integer>> genreMap;

    public AudioContentStore() {
        contents = new ArrayList<AudioContent>();

        // Create some songs audiobooks and podcasts and to store
        File file = new File("src/store.txt");
        Scanner scanner = null;
        try {
            FileInputStream input = new FileInputStream(file);
            scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String type = scanner.nextLine();

                if (type.equalsIgnoreCase("SONG")){
                    String id = scanner.nextLine();
                    String title = scanner.nextLine();
                    int year = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    int length = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    String artist = scanner.nextLine();
                    String composer = scanner.nextLine();
                    String genre = scanner.nextLine();

                    int count = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    StringBuilder audioFile = new StringBuilder();
                    for (int i = 0; i < count; i++) {
                        audioFile.append(scanner.nextLine()).append("\n");
                    }
                    Song song = new Song(title, year, id, type, audioFile.toString(), length, artist, composer, Song.Genre.valueOf(genre.toUpperCase()), audioFile.toString());
                    System.out.println("Loading SONG");
                    contents.add(song);
                } else if (type.equalsIgnoreCase("AUDIOBOOK")){
                    String id = scanner.nextLine();
                    String title = scanner.nextLine();
                    int year = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    int length = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    String author = scanner.nextLine();
                    String narrator = scanner.nextLine();

                    int count = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    ArrayList<String> chapterTitles = new ArrayList<>();
                    ArrayList<String> chapters = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        chapterTitles.add(scanner.nextLine());
                    }

                    count = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                    for (int i = 0; i < count; i++) {
                        chapters.add(scanner.nextLine());
                    }

                    AudioBook book = new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters);
                    System.out.println("Loading AUDIOBOOK");
                    contents.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        contentMap = new HashMap<>();
        int counter = 0;
        for (AudioContent contact : contents) {
            contentMap.put(contact.getTitle(), counter);
            counter++;
        }

        artistMap = new HashMap<>();
        for (AudioContent content : contents) {
            if (content instanceof AudioBook) {
                AudioBook temp = (AudioBook) content;
                if (artistMap.get(temp.getAuthor()) != null) {
                    artistMap.get(temp.getAuthor()).add(contentMap.get(content.getTitle()));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(contentMap.get(content.getTitle()));
                    artistMap.put(temp.getAuthor(), arrayList);
                }
            } else if (content instanceof Song) {
                Song temp = (Song) content;
                if (artistMap.get(temp.getArtist()) != null) {
                    artistMap.get(temp.getArtist()).add(contentMap.get(content.getTitle()));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(contentMap.get(content.getTitle()));
                    artistMap.put(temp.getArtist(), arrayList);
                }
            }

        }

        genreMap = new HashMap<>();
        for (AudioContent content : contents) {
            if (content instanceof Song temp) {
                if (genreMap.get(temp.getGenre().toString()) != null) {
                    genreMap.get(temp.getGenre().toString()).add(contentMap.get(content.getTitle()));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(contentMap.get(content.getTitle()));
                    genreMap.put(temp.getGenre().toString(), arrayList);
                }
            } else {
                AudioBook temp = (AudioBook) content;
                if (genreMap.get(temp.getType().toString()) != null) {
                    genreMap.get(temp.getType().toString()).add(contentMap.get(content.getTitle()));
                } else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    arrayList.add(contentMap.get(content.getTitle()));
                    genreMap.put(temp.getType().toString(), arrayList);
                }
            }
        }
    }


    public AudioContent getContent(int index) {
        return contents.get(index);
    }

    public void listAll() {
        for (int i = 0; i < contents.size(); i++) {
            int index = i + 1;
            System.out.print("" + index + ". ");
            contents.get(i).printInfo();
            System.out.println();
        }
    }

    private ArrayList<String> makeHPChapterTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("The Riddle House");
        titles.add("The Scar");
        titles.add("The Invitation");
        titles.add("Back to The Burrow");
        return titles;
    }

    private ArrayList<String> makeHPChapters() {
        ArrayList<String> chapters = new ArrayList<String>();
        chapters.add("In which we learn of the mysterious murders\r\n"
                + " in the Riddle House fifty years ago, \r\n"
                + "how Frank Bryce was accused but released for lack of evidence, \r\n"
                + "and how the Riddle House fell into disrepair. ");
        chapters.add("In which Harry awakens from a bad dream, \r\n"
                + "his scar burning, we recap Harry's previous adventures, \r\n"
                + "and he writes a letter to his godfather.");
        chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
                + " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
                + " with her family and attend the World Quidditch Cup finals.");
        chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
                + "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
                + ", blast it open, send Fred and George after Harry's trunk,\r\n"
                + " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
                + "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
        return chapters;
    }

    private ArrayList<String> makeMDChapterTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("Loomings.");
        titles.add("The Carpet-Bag.");
        titles.add("The Spouter-Inn.");
        return titles;
    }

    private ArrayList<String> makeMDChapters() {
        ArrayList<String> chapters = new ArrayList<String>();
        chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
                + " or no money in my purse, and nothing particular to interest me on shore,\r\n"
                + " I thought I would sail about a little and see the watery part of the world.");
        chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
                + "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
                + "I duly arrived in New Bedford. It was a Saturday night in December.");
        chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
                + "low, straggling entry with old-fashioned wainscots, \r\n"
                + "reminding one of the bulwarks of some condemned old craft.");
        return chapters;
    }

    private ArrayList<String> makeSHChapterTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("Prologue");
        titles.add("Chapter 1");
        titles.add("Chapter 2");
        titles.add("Chapter 3");
        return titles;
    }

    private ArrayList<String> makeSHChapters() {
        ArrayList<String> chapters = new ArrayList<String>();
        chapters.add("The gale tore at him and he felt its bite deep within\r\n"
                + "and he knew that if they did not make landfall in three days they would all be dead");
        chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
                + "because he was ashore and the room unbelieveable");
        chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
                + "where you come from, how ou got here, and what acts of piracy you have committed.");
        chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
        return chapters;
    }

    public ArrayList<Integer> getArtistContent(String artistName) {
        return artistMap.get(artistName);
    }

    public ArrayList<Integer> getGenreContent(String genre) {
        return genreMap.get(genre);
    }

    public void getContentByTitle(String Title) throws AudioContentNotFoundException {
        if (contentMap.get(Title) == null) {
            throw new AudioContentNotFoundException("No matches for " + Title);
        } else {
            contents.get(contentMap.get(Title)).printInfo();
        }
    }

    public void getContentsByArtist (String artistName){
        if (artistMap.get(artistName) == null) {
            System.out.println("No matches for " + artistName);
        } else {
            for (int i = 0; i < artistMap.get(artistName).size(); i++) {
                contents.get(artistMap.get(artistName).get(i)).printInfo();
            }
        }
    }

    public void getContentsByGenre(String genre) {
        if (genreMap.get(genre) == null) {
            System.out.println("No matches for " + genre);
        } else {
            for (int i = 0; i < genreMap.get(genre).size(); i++) {
                contents.get(genreMap.get(genre).get(i)).printInfo();
            }
        }
    }

    // Podcast Seasons
		/*
		private ArrayList<Season> makeSeasons()
		{
			ArrayList<Season> seasons = new ArrayList<Season>();
		  Season s1 = new Season();
		  s1.episodeTitles.add("Bay Blanket");
		  s1.episodeTitles.add("You Don't Want to Sleep Here");
		  s1.episodeTitles.add("The Gold Rush");
		  s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \r\n"
		  		+ "lip-syncing, but some people believe they were used to spread\r\n"
		  		+ " smallpox and decimate entire Indigenous communities. \r\n"
		  		+ "We dive into the history of The Hudson's Bay Company and unpack the\r\n"
		  		+ " very complicated story of the iconic striped blanket.");
		  s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s1.episodeLengths.add(31);
		  s1.episodeLengths.add(32);
		  s1.episodeLengths.add(45);
		  seasons.add(s1);
		  Season s2 = new Season();
		  s2.episodeTitles.add("Toronto vs Everyone");
		  s2.episodeTitles.add("Water");
		  s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \r\n"
		  		+ "But what did the mining industry cost the original people of the territory? \r\n"
		  		+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
		  s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\r\n"
		  		+ " In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\r\n"
		  		+ " and vampires, and discuss some big concerns currently facing Canada's water."); 
		  s2.episodeLengths.add(45);
		  s2.episodeLengths.add(50);
		 
		  seasons.add(s2);
		  return seasons;
		}
		*/
}
