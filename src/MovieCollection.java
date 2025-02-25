import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)ighest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<>();

        // search through ALL movies in collection
        for (Movie m : movies)
        {
            String movieTitle = m.getTitle().toLowerCase();
            if (movieTitle.contains(searchTerm))
            {
                results.add(m);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        if (results.size() > 0)
        {
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear the newline

            Movie selectedMovie = results.get(choice - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No matching titles found.\n");
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    /**
     * Sorts a list of movies alphabetically by title, using a simple insertion sort.
     * You could replace this with Collections.sort(...) if desired.
     */
    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 &&
                    tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    /**
     * Search the cast similarly to how we search titles or keywords:
     * - Prompt user for a search term
     * - Compare against each movie's cast (lowercase)
     * - Collect, sort, display
     */
    private void searchCast()
    {
        System.out.print("Enter a cast search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        ArrayList<Movie> results = new ArrayList<>();
        for (Movie m : movies)
        {
            String cast = m.getCast().toLowerCase();
            if (cast.contains(searchTerm))
            {
                results.add(m);
            }
        }

        sortResults(results);

        // Display results
        for (int i = 0; i < results.size(); i++)
        {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        if (results.size() > 0)
        {
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear the newline

            Movie selectedMovie = results.get(choice - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No matching cast members found.\n");
        }
    }

    private void searchKeywords()
    {
        System.out.println("Enter a keyword to search by: ");
        String keyword = scanner.nextLine().toLowerCase();

        ArrayList<Movie> results = new ArrayList<>();

        for (Movie m : movies)
        {
            String keywords = m.getKeywords().toLowerCase();
            if (keywords.contains(keyword))
            {
                results.add(m);
            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        if (results.size() > 0)
        {
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = results.get(choice - 1);

            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No matching keywords found.\n");
        }
    }

    /**
     * Lists all distinct genres, sorted alphabetically, lets the user choose one,
     * then shows all movies in that genre (also sorted), and lets the user pick a movie to see info.
     */
    private void listGenres()
    {
        // Gather all distinct genres
        Set<String> genresSet = new HashSet<>();
        for (Movie m : movies)
        {
            String[] splitGenres = m.getGenres().split("\\|");
            for (String g : splitGenres)
            {
                genresSet.add(g.trim());
            }
        }

        // Convert to a list and sort
        ArrayList<String> allGenres = new ArrayList<>(genresSet);
        Collections.sort(allGenres);

        // Display genres
        System.out.println("Available genres:");
        for (int i = 0; i < allGenres.size(); i++)
        {
            System.out.println((i + 1) + ". " + allGenres.get(i));
        }

        // Let the user pick a genre
        System.out.print("Which genre would you like to see movies for? Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = allGenres.get(choice - 1);

        // Collect all movies in that genre
        ArrayList<Movie> results = new ArrayList<>();
        for (Movie m : movies)
        {
            // A movie can have multiple genres separated by "|"
            // We only add it if it matches the selected genre
            String[] splitGenres = m.getGenres().split("\\|");
            for (String g : splitGenres)
            {
                if (g.trim().equalsIgnoreCase(selectedGenre))
                {
                    results.add(m);
                    break; // no need to keep checking once matched
                }
            }
        }

        // Sort them
        sortResults(results);

        // Display
        for (int i = 0; i < results.size(); i++)
        {
            System.out.println((i + 1) + ". " + results.get(i).getTitle());
        }

        if (results.size() > 0)
        {
            System.out.print("Which movie would you like to learn more about? Enter number: ");
            int movieChoice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = results.get(movieChoice - 1);
            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No movies found in genre: " + selectedGenre);
        }
    }

    /**
     * Lists the top 50 rated movies (or as many as we have if < 50).
     */
    private void listHighestRated()
    {
        // Make a copy
        ArrayList<Movie> sortedList = new ArrayList<>(movies);
        // Sort by rating descending
        sortedList.sort((m1, m2) -> Double.compare(m2.getUserRating(), m1.getUserRating()));

        int limit = Math.min(50, sortedList.size());
        for (int i = 0; i < limit; i++)
        {
            Movie m = sortedList.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " -- Rating: " + m.getUserRating());
        }

        if (limit > 0)
        {
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = sortedList.get(choice - 1);
            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No movies found in the collection.");
        }
    }

    /**
     * Lists the top 50 highest revenue movies (or fewer if we have fewer than 50).
     */
    private void listHighestRevenue()
    {
        // Make a copy
        ArrayList<Movie> sortedList = new ArrayList<>(movies);
        // Sort by revenue descending
        sortedList.sort((m1, m2) -> Integer.compare(m2.getRevenue(), m1.getRevenue()));

        int limit = Math.min(50, sortedList.size());
        for (int i = 0; i < limit; i++)
        {
            Movie m = sortedList.get(i);
            System.out.println((i + 1) + ". " + m.getTitle() + " -- Revenue: $" + m.getRevenue());
        }

        if (limit > 0)
        {
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            Movie selectedMovie = sortedList.get(choice - 1);
            displayMovieInfo(selectedMovie);

            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
        }
        else
        {
            System.out.println("No movies found in the collection.");
        }
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine(); // read header line, ignore

            movies = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline,
                                            keywords, overview, runtime,
                                            genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}