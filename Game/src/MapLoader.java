/*
 * Author(s):
 * Date: 6/10/2018
 */


import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public static final int GRID_SIZE = 30;
    public int playerX = -1;
    public int playerY = -1;

    public static void main( String args[] ) {
        MapLoader m = new MapLoader();

        File file = new File("./Game/src/level-1");
        System.out.println(file.canRead());


        m.load("./Game/src/level-1");
    }

    public ArrayList<Object> load(String file) {
        ArrayList<Object> result = new ArrayList<Object>();
        File f = new File(file);
        try {
            Scanner scan = new Scanner(f);

            int height = scan.nextInt();
            int width = scan.nextInt();

            char array[][] = new char[height][width];
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
                    char c = array[y][x];

                    // AIR
                    if ( c == '.') {
                        // Do Nothing

                    // Ground
                    } else if ( c == 'G' ) {
                        result.add( new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/ground_placeholder.png")));
                    // Player
                    } else if ( c == 'P') {
                        playerX = x * GRID_SIZE;
                        playerY = y * GRID_SIZE + 2;
                    // Block
                    } else if ( c == 'B') {
                        result.add( new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/block_placeholder.png")));
                    // Lava
                    } else if ( c == '~') {
                        result.add( new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/lava_placeholder.png")));
                    }
                    // Spike
                    else if ( c == '^') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/spike_placeholder.png")));

                    // Enemy
                    } else if ( c == 'e') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/enemy_placeholder.png")));

                    // Exit
                    } else if ( c == 'E') {
                        result.add( new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/exit_placeholder.png")));
                    }
                }
                System.out.println(" ");
            }

            //System.out.println( String.format("Player: x:%d  y:%d", player.getX(), player.getY()));
            scan.close();
        }catch(FileNotFoundException e ) {

        }
      return result;

    }

}
