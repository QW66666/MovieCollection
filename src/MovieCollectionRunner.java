import java.lang.management.MonitorInfo;
import java.util.ArrayList;

public class MovieCollectionRunner
{
  public static void main(String[] args)
  {
    MovieCollection movieCollection = new MovieCollection("src\\movies_data.csv");
    movieCollection.menu();


  }
}