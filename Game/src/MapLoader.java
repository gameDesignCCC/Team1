/*
 * Author(s):
 * Date: 6/10/2018
 */


import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class MapLoader {
    Player player;
    Object[][] arrayB;

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
            arrayB = new Object[height][width];
            int i = 0;
            int j = 0;

            // Read File into array
            while (scan.hasNext()) {
                String nextChar = scan.next();
                for ( int k = 0; k < width; k++) {
                    char n = nextChar.charAt(k);
                    array[i][k] = n;
                }
                i++;
            }

            // Create Level
            for ( int y = 0; y < height; y++) {
                for ( int x = 0; x < width; x++) {
                    System.out.print(array[y][x] + " ");
                    if (array[y][x] == 'P'){
                        //Image playerSprite = new Image("/assets/sprites/playerSprite.png");
                        //player = new Player(x, y,0, 0, playerSprite );
                        //arrayB[x][y] = player;
                    }
                }
                System.out.println(" ");
            }

            //System.out.println( String.format("Player: x:%d  y:%d", player.getX(), player.getY()));
            scan.close();
        }catch(FileNotFoundException e ) {

        }
      //  return array;

    }

}
