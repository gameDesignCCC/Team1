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

    public static void main(String args[]) {
        MapLoader m = new MapLoader();

        File file = new File("");
        System.out.println(file.canRead());


        m.load("");
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
                for (int k = 0; k < width; k++) {
                    char n = nextChar.charAt(k);
                    array[i][k] = n;
                }
                i++;
            }

            // Create Level
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    char c = array[y][x];

                    // AIR
                    if (c == '.') {
                        // Do Nothing

                        // Ground
                    } else if (c == 'G') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/Ground.png"), StaticObject.Type.GROUND));
                        // Player
                    } else if (c == 'P') {
                        playerX = x * GRID_SIZE;
                        playerY = y * GRID_SIZE;
                        // Block
                    } else if (c == 'B') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/block_placeholder.png"), StaticObject.Type.BLOCK));
                        // Lava
                    } else if (c == '~') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/lava_placeholder.png"), StaticObject.Type.LAVA));
                    }
                    // Spike
                    else if (c == '^') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/Spikes.png"), StaticObject.Type.SPIKE));

                        // Enemy
                    } else if (c == 'e') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/enemy_placeholder.png"), StaticObject.Type.ENEMY));

                        // Item
                    } else if (c == 'I') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/pItem.png"), StaticObject.Type.ITEM));
                        // Exit
                    } else if (c == 'E') {
                        result.add(new StaticRect(x * GRID_SIZE, y * GRID_SIZE, GRID_SIZE, GRID_SIZE,
                                new Image("/assets/sprites/exit_placeholder.png"), StaticObject.Type.EXIT));
                    }
                }
            }

            //System.out.println( String.format("Player: x:%d  y:%d", player.getX(), player.getY()));
            scan.close();
        } catch (FileNotFoundException e) {

        }
        return result;

    }

}
