package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 90;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static TETile[][] Initialize() {
        TETile[][] world = new TETile[WIDTH][WIDTH];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /**
     * Drawing A Single Hexagon
     * @param world
     * @param px Position_X
     * @param py Position_Y
     * @param n Size of this hexagon
     */
    public static void addHexagon(TETile[][] world, int px, int py, int n) {
        TETile current_tile = randomTile();
        // To iterate through each row:
        for(int start_row = 0; start_row<n; start_row++) {
            // To drow each row:
            for(int i=n-start_row; i<2*n+start_row; i++) {
                // Bottom:
                world[i+px][start_row+py] = current_tile;
                // Top:
                world[i+px][2*n-start_row-1+py] = current_tile;
            }
        }
    }

    /**
     * Use different tile background
     * @return
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        // initialize tiles
        TETile[][] world = Initialize();
        // Define the position to put
        int px = 20;
        int py = 30;
        int size = 4;
        // Drawing A Tesselation of Hexagons: (I should not use this kind of for loop, but build help method...)
        // Iterate through each column:
        for(int start_col = 0; start_col<2*size-1; start_col++) {
            // Draw each hexagon of this column:
            for(int i=0; i<2*size-1-Math.abs(size-1 - start_col); i++) {
                addHexagon(world, px, py, size);
                py = py + 2*size;
            }
            px = px + 2*size-1;
            int PlusOrMinus = Integer.signum(size-1 - start_col);
            if (PlusOrMinus == 0) { PlusOrMinus = -1;}
            py = py - 2*size*(2*size-1-Math.abs(size-1 - start_col)) - size*PlusOrMinus;
        }

        ter.renderFrame(world);
    }
}
