/*
 * Author(s):
 * Date: 6/10/2018
 */

import java.io.File;
import java.util.Scanner;

public class MapLoader {

    public static void loadMap(String map){

        try{
            File mapFile = new File("/assets/maps/" + map + "/map.dat");
            Scanner sc = new Scanner(mapFile);
            while(sc.hasNextLine()){
                System.out.println(sc.nextLine());
            }
        } catch (Exception e){
            System.out.println();
            e.printStackTrace();
        }

    }

}
