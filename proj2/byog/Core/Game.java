package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.event.KeyListener;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        ter.initialize(WIDTH, HEIGHT);
        String inputToInputString = "N1234S";
        TETile[][] world = playWithInputString(inputToInputString);
        TETile[][] loadedWorld = loadGame("game_save.ser");

        if (loadedWorld != null) {
            world = loadedWorld;
//            System.out.println("Load the existed world");
        }
        ter.renderFrame(world);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                handleKeyInput(key, world);
                ter.renderFrame(world);
            }
            StdDraw.pause(20); // Add a pause to the loop
        }
    }





    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        // Split the "N", "S" and the seed.
        input = input.toUpperCase();
        int indexOfN = input.indexOf("N");
        int indexOfS = input.indexOf("S");
        int indexOfQUIT = input.indexOf(":Q");
        String seedString = input.substring(indexOfN+1, indexOfS);
        Long seed = Long.parseLong(seedString);
        // Use the seed to generate the world
        final Random RANDOM = new Random(seed);

        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        Initial(finalWorldFrame);

        UnionFind uf = new UnionFind(WIDTH * HEIGHT);
        // Generate some random rooms and connect floors in each room:
        int rooms_number = 12;
        ArrayList<RoomCenter> roomCenters = new ArrayList<>();
        int playerInRoom = RANDOM.nextInt(12);
        for(int n=0; n< rooms_number; n++) {
            generateRandomRooms(finalWorldFrame, RANDOM, uf, roomCenters);
        }

        // Optimize the connection between rooms:
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i < roomCenters.size(); i++) {
            for (int j = i + 1; j < roomCenters.size(); j++) {
                RoomCenter room1 = roomCenters.get(i);
                RoomCenter room2 = roomCenters.get(j);
                int weight = Math.abs(room1.x - room2.x) + Math.abs(room1.y - room2.y);
                edges.add(new Edge(room1, room2, weight));
            }
        }
        // Sort the weigh of edges
        Collections.sort(edges);
        // Find the MST using Kruskal
        int connectedRooms = 0;
        for (Edge edge : edges) {
            int srcRoot = uf.find(xyTo1D(edge.src.x, edge.src.y));
            int destRoot = uf.find(xyTo1D(edge.dest.x, edge.dest.y));

            if (srcRoot != destRoot) {
                uf.union(srcRoot, destRoot);
                connectRooms(finalWorldFrame, uf, edge.src.x, edge.src.y, edge.dest.x, edge.dest.y);
                connectedRooms++;

                if (connectedRooms == roomCenters.size() - 1) {
                    break;
                }
            }
        }
        // Put the player in the selected room after generating and connecting all rooms
        playerInitial(finalWorldFrame, roomCenters.get(playerInRoom));

        // Process the remaining move commands in the input string
        for (int i = indexOfS + 1; i < input.length(); i++) {
            char moveCommand = input.charAt(i);
            handleKeyInput(moveCommand, finalWorldFrame);
        }


        // draws the world to the screen
        ter.renderFrame(finalWorldFrame);

//        GameKeyListener keyListener = new GameKeyListener();
//        ter.addKeyListener(keyListener);


        return finalWorldFrame;
    }

    public static void generateRandomRooms(TETile[][] tiles, Random RANDOM, UnionFind uf, ArrayList<RoomCenter> roomCenters) {
        int roomSize = 7;
        int room_width = Math.max(3, RANDOM.nextInt(roomSize) + 1);
        int room_height = Math.max(3, RANDOM.nextInt(roomSize) + 1);
        int init_x = RANDOM.nextInt(WIDTH-room_width-1);
        int init_y = RANDOM.nextInt(HEIGHT-room_height-1);
        int preFloor = xyTo1D(init_x, init_y);
        int currentFloor;
        roomCenters.add(new RoomCenter(init_x+room_width/2, init_y+room_height));

        for(int x=init_x; x < init_x+room_width; x++) {
            for(int y=init_y; y<init_y+room_height; y++) {
                tiles[x][y] = Tileset.FLOOR;
                currentFloor = xyTo1D(x, y);
                uf.union(preFloor, currentFloor);
                preFloor = xyTo1D(x, y);
            }
        }
    }
    // initialize tiles
    public static void Initial(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Helper mothod: 2-D to 1-D, in order to use UnionFind.
     * @param x
     * @param y
     * @return
     */
    private static int xyTo1D(int x, int y) {
        return x * HEIGHT + y;
    }

    /**
     * Used to store the center point in each room
     */
    private static class RoomCenter {
        int x;
        int y;

        RoomCenter(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static void playerInitial(TETile[][] world, RoomCenter RC) {
        world[RC.x][RC.y] = Tileset.PLAYER;
    }

    private static void connectRooms(TETile[][] world, UnionFind uf, int x1, int y1, int x2, int y2) {
        int xStart = Math.min(x1, x2);
        int xEnd = Math.max(x1, x2);
        int yStart = Math.min(y1, y2);
        int yEnd = Math.max(y1, y2);

        int previousTile = xyTo1D(x1, y1);

        for (int x = xStart; x <= xEnd; x++) {
            world[x][y1] = Tileset.FLOOR;
            int currentTile = xyTo1D(x, y1);
            uf.union(previousTile, currentTile);
            previousTile = currentTile;
        }

        for (int y = yStart; y <= yEnd; y++) {
            world[x2][y] = Tileset.FLOOR;
            int currentTile = xyTo1D(x2, y);
            uf.union(previousTile, currentTile);
            previousTile = currentTile;
        }
    }

    private static class Edge implements Comparable<Edge> {
        RoomCenter src;
        RoomCenter dest;
        int weight;

        Edge(RoomCenter src, RoomCenter dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }

    // Listening to the keyboard(i.e., W A S D)
    private class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Not needed
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // Handle W, A, S, D key presses
        }
        @Override
        public void keyReleased(KeyEvent e) {
            // Not needed
        }
    }

    private void handleKeyInput(char key, TETile[][] world) {
        // Find the current position of the player
        int currentX = -1;
        int currentY = -1;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (world[x][y].equals(Tileset.PLAYER)) {
                    currentX = x;
                    currentY = y;
                    break;
                }
            }
            if (currentX != -1 && currentY != -1) {
                break;
            }
        }

        // If the player position was not found, return
        if (currentX == -1 || currentY == -1) {
            System.out.println("Player position not found");
            return;
        }

        // Calculate the new position based on the key pressed
        int newX = currentX;
        int newY = currentY;

        if (key == 'w' || key == 'W') {
            newY++;
        } else if (key == 's' || key == 'S') {
            newY--;
        } else if (key == 'a' || key == 'A') {
            newX--;
        } else if (key == 'd' || key == 'D') {
            newX++;
        }

        // Check if the new position is walkable
        if (world[newX][newY].equals(Tileset.FLOOR)) {
            world[currentX][currentY] = Tileset.FLOOR;
            world[newX][newY] = Tileset.PLAYER;
        }
        // Other operations
        if (key == 'l' || key == 'L') {
            // Load the game
            TETile[][] loadedWorld = loadGame("game_save.ser");
            if (loadedWorld != null) {
                System.arraycopy(loadedWorld, 0, world, 0, loadedWorld.length);
            }
        } else if (key == 'q' || key == 'Q') {
            // Save and exit
            saveGame(world, "game_save.ser");
            System.exit(0);
        }
    }


    // Save the game state
    public static void saveGame(TETile[][] world, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(world);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Load the game state
    public static TETile[][] loadGame(String fileName) {
        TETile[][] world = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            world = (TETile[][]) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }
}
