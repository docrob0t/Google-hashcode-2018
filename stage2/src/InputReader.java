import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * InputReader reads text input from the file. The text input is then chopped into lines,
 * and create new objects based on each line.
 */
public class InputReader
{
    public InputReader() {
        // Do nothing
    }

    /**
     * Read input from the file and store each line as an int array, and put the int array in an array list
     *
     * @param worldAndRidesFileName The name of the input file
     * @return An array list of integer array that stores each line in the file
     */
    public ArrayList<int[]> parseInput(String worldAndRidesFileName) {
        ArrayList<int[]> file = new ArrayList<>();
        // Parse input for Input file
        try (BufferedReader reader = new BufferedReader(new FileReader(worldAndRidesFileName))) {
            // Reading every line in the fine, until the last line is null
            for (String line; (line = reader.readLine()) != null; ) {
                // Split each line with " " and store it in a String array
                String[] fileArray = line.split(" ");
                // Convert each number in the string arr to an integer
                int[] array = Arrays.stream(fileArray).mapToInt(Integer::parseInt).toArray();
                file.add(array);
            }
        } catch (FileNotFoundException e) {
            System.err.print("Unable to open file.");
        } catch (IOException e) {
            System.err.print("A problem was encountered reading the file");
        }
        return file;
    }
}
