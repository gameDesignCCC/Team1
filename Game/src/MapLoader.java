/*
 * Author(s):
 * Date: 6/10/2018
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class MapLoader {

    public static void main( String args[] ) {
        MapLoader m = new MapLoader();

        File file = new File("/home/campus06/csseat36/IdeaProjects/Team1/Game/src/level-1");
        System.out.println(file.canRead());


        m.load("/home/campus06/csseat36/IdeaProjects/Team1/Game/src/level-1");
    }


    public void load(String file) {
        File f = new File(file);
        try {
            Scanner scan = new Scanner(f);

            int height = scan.nextInt();
            int width = scan.nextInt();

            char array[][] = new char[height][width];
            int i = 0;
            int j = 0;

            while (scan.hasNext()) {
                char nextChar = (char) scan.nextByte();

                if (nextChar != '\n') {
                    array[i][j] = nextChar;
                    i++;
                    j++;
                }
            }

            System.out.println(Arrays.toString(array));

            scan.close();
        }catch(FileNotFoundException e ) {

        }

      //  return array;

    }

}
