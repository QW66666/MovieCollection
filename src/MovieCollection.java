import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (!menuOption.equals("q")) {
        processOption(menuOption);
      }
    }
  }

  private void processOption(String option) {
    if (option.equals("t")) {
      searchTitles();
    } else if (option.equals("c")) {
      searchCast();
    } else if (option.equals("k")) {
      searchKeywords();
    } else if (option.equals("g")) {
      listGenres();
    } else if (option.equals("r")) {
      listHighestRated();
    } else if (option.equals("h")) {
      listHighestRevenue();
    } else {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++) {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie) {
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

  private void searchCast() {
    System.out.print("Enter a name: ");
    String name = scanner.nextLine();
    name = name.toLowerCase();

    ArrayList<String> casters = new ArrayList<>();
    //Search in movies and add casts to casters containing name
    for (int i = 0; i < movies.size(); i++)
    {
      String cast = movies.get(i).getCast();
      String[] casts = cast.split("\\|");
      for (int j = 0; j < casts.length; j++)
      {
        if(casts[j].toLowerCase().contains(name.toLowerCase()) && !casters.contains(casts[j]))
        {
          casters.add(casts[j]);
        }
      }
    }
    //Sort casters alphabetically
    Collections.sort(casters);
    //Print out all casts
    for(int i = 0; i < casters.size(); i++)
    {
      int choiceNum = i+1;
      System.out.println("" + choiceNum + ". " + casters.get(i));
    }
    //Ask user to pick a cast
    System.out.println("Which would you like to see all movies for? ");
    System.out.print("Enter a number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();
    //Search for all movies containing cast
    ArrayList<Movie> titles = new ArrayList<Movie>();
    for(int i = 0; i < movies.size(); i++)
    {
      if(movies.get(i).getCast().contains(casters.get(choice-1)))
      {
        titles.add(movies.get(i));
      }
    }
    //Sort titles alphabetically
    sortResults(titles);
    //Print out all movie titles
    for(int i = 0; i < titles.size(); i++)
    {
      int choiceNum = i+1;
      System.out.println("" + choiceNum + ". " + titles.get(i).getTitle());
    }
    //Ask User to pick a movie
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    int movieChoice = scanner.nextInt();
    scanner.nextLine();
    //Display movie info
    Movie selectedMovie = titles.get(movieChoice - 1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }



  private void searchKeywords() {
    System.out.print("Enter a keyword: ");
    String keywordTerm = scanner.nextLine();

    keywordTerm = keywordTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String keyword = movies.get(i).getKeywords();
      keyword = keyword.toLowerCase();

      if (keyword.indexOf(keywordTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++) {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listGenres()
  {
      //genre list
    ArrayList<String> genresList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i++)
    {
      String genre = movies.get(i).getGenres();
      String[] genres = genre.split("\\|");
      for (int j = 0; j < genres.length; j++)
      {
        if(!genresList.contains(genres[j]))
        {
          genresList.add(genres[j]);
        }
      }
    }
      Collections.sort(genresList);
      for(int i = 0; i < genresList.size(); i++)
      {
        int numChoice = i+1;
        System.out.println("" + numChoice + ". " + genresList.get(i));
      }

      //Search for movies with genre
    System.out.println("Which would you like to see all movies for?");
    System.out.print("Enter number: ");
    int choice = scanner.nextInt();

    //list of movies with genre
    ArrayList<Movie> movieWithGenre = new ArrayList<>();
    for(int i = 0; i < movies.size(); i++)
    {
      if(movies.get(i).getGenres().contains(genresList.get(choice-1)) && !movieWithGenre.contains(movies.get(i)))
      {
        movieWithGenre.add(movies.get(i));
      }
    }
    sortResults(movieWithGenre);
    // now, display them all to the user
    for (int i = 0; i < movieWithGenre.size(); i++)
    {
      String title = movieWithGenre.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movieWithGenre.get(movieChoice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRated()
  {
    ArrayList<Movie> results = new ArrayList<>();
    Movie highMovie = movies.get(0);
    double low = movies.get(0).getUserRating();

    for(int i = 1; i < movies.size(); i++)
    {
      if(movies.get(i).getUserRating() < low)
      {
        low = movies.get(i).getUserRating();
      }
    }
    double lowTemp = low;
    int counter = 0;
    for (int i = 0; i < movies.size(); i++)
    {
      for(int j = 0; j < movies.size(); j++)
      {
        if (movies.get(j).getUserRating() > low)
        {
          low = movies.get(j).getUserRating();
          highMovie = movies.get(j);
          counter = j;
        }
      }
      if (results.size() < 50)
      {
        results.add(highMovie);
        low = lowTemp;
        i--;
        movies.remove(counter);
      }
    }
    for(int i = 0; i < results.size(); i++)
    {
      double userRating = results.get(i).getUserRating();
      String title = results.get(i).getTitle();
      int choiceNum = i+1;
      System.out.println("" + choiceNum + ". " + title + ": " + userRating);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(movieChoice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void listHighestRevenue() {
    ArrayList<Movie> results = new ArrayList<>();
    Movie highMovie = movies.get(0);
    int low = movies.get(0).getRevenue();

    for(int i = 1; i < movies.size(); i++)
    {
      if(movies.get(i).getRevenue() < low)
      {
        low = movies.get(i).getRevenue();
      }
    }
    int lowTemp = low;
    int counter = 0;
    for (int i = 0; i < movies.size(); i++)
    {
      for(int j = 0; j < movies.size(); j++)
      {
        if (movies.get(j).getRevenue() > low)
        {
          low = movies.get(j).getRevenue();
          highMovie = movies.get(j);
          counter = j;
        }
      }
      if (results.size() < 50)
      {
        results.add(highMovie);
        low = lowTemp;
        i--;
        movies.remove(counter);
      }
    }
    for(int i = 0; i < results.size(); i++)
    {
      int revenue = results.get(i).getRevenue();
      String title = results.get(i).getTitle();
      int choiceNum = i+1;
      System.out.println("" + choiceNum + ". " + title + ": " + revenue);
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(movieChoice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void importMovieList(String fileName) {
    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null) {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] movieFromCSV = line.split(",");

        // pull out the data for this cereal
        String name = movieFromCSV[0];
        String title = (movieFromCSV[1]);
        String cast = (movieFromCSV[2]);
        String director = (movieFromCSV[3]);
        String keywords = (movieFromCSV[4]);
        String overview = (movieFromCSV[5]);
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = (movieFromCSV[7]);
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create Cereal object to store values
        Movie nextMovie = new Movie(name, title, cast, director, keywords, overview, runtime, genres, userRating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    } catch (IOException exception) {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }

    // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

  }
}