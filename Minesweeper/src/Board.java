import java.util.Random;

public class Board {
    private String difficulty;
    private int size;
    private int numBombs;
    private boolean[][] boardBomb;
    private String[][] boardDisplay;
    private Random rand;
    private boolean bombed = false;

    public Board(String difficulty) {
        this.difficulty = difficulty;
        switch (this.difficulty) {
            case("E"):
                this.size = 5;
                this.numBombs = 4;
                break;
            case("M"):
                this.size = 10;
                this.numBombs = 10;
                break;
            case("H"):
                this.size = 15;
                this.numBombs = 20;
                break;
            default:
                this.size = 1;
                this.numBombs = 0;
                break;
        }
        this.boardDisplay = new String[size][size];
        this.boardBomb = new boolean[size][size];
        this.rand = new Random();
        initializeBoard();
    }

    public Board (int size, int numBombs) {
        this.size = size;
        this.numBombs = numBombs;
        this.boardDisplay = new String[size][size];
        this.boardBomb = new boolean[size][size];
        this.rand = new Random();
        initializeBoard();
    }

    public Board() {
        this.difficulty = "E";
        this.size = 5;
        this.numBombs = 5;
        this.boardDisplay = new String[size][size];
        this.boardBomb = new boolean[size][size];
        this.rand = new Random(1);
        initializeBoard();
    }

    public void initializeBoard() {
        int bombsRemaining = numBombs;
        while (bombsRemaining > 0) {
            int row = rand.nextInt(size);
            int col = rand.nextInt(size);
            // If there is no bomb already at that tile:
            if (!boardBomb[row][col]) {
                boardBomb[row][col] = true;
                bombsRemaining--;
            }
        }
        // Initializing the untouched display board
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                boardDisplay[r][c] = "X";
            }
        }
    }

    public void destroyTile(int xCoord, int yCoord) {
        if (boardBomb[yCoord][xCoord]) {
            bombed = true;
            boardDisplay[yCoord][xCoord] = "☠";
        } else {;
            destroySurrounding(xCoord, yCoord);
        }
    }

    public int numSurroundingBombs(int xCoord, int yCoord) {
        int numSurrounding = 0;
        for (int r = yCoord - 1; r <= yCoord + 1; r++) {
            for (int c = xCoord - 1; c <= xCoord + 1; c++) {
                if (inBounds(c) && inBounds(r)) {
                    // If there's a bomb in that tile:
                    if (boardBomb[r][c]) {
                        numSurrounding++;
                    }
                }
            }
        }
        return numSurrounding;
    }

    public void destroySurrounding(int xCoord, int yCoord) {
        int surroundingBombs = numSurroundingBombs(xCoord, yCoord);
        // Recursive base case
        if (surroundingBombs > 0) {
            boardDisplay[yCoord][xCoord] = String.valueOf(surroundingBombs);
        } else {
            boardDisplay[yCoord][xCoord] = ".";
            for (int r = yCoord - 1; r <= yCoord + 1; r++) {
                for (int c = xCoord - 1; c <= xCoord + 1; c++) {
                    if (inBounds(r) && inBounds(c)) {
                        if (boardDisplay[r][c].equals("X")) {
                            destroySurrounding(c, r);
                        }
                    }
                }
            }
        }
    }

    public boolean inBounds (int num) {
        return -1 < num && num < size;
    }

    public String coordinates() {
        String board = "";
        for (int r = size - 1; r > - 1; r--) {
            for (int c = 0; c < size; c++) {
                board = board + "(" + c + ", " + r + ")  ";
            }
            board = board + "\n";
        }
        return board;
    }

    public String bombs() {
        String board = "";
        for (int r = size - 1; r > - 1; r--) {
            for (int c = 0; c < size; c++) {
                if (boardBomb[r][c]) {
                    board = board + "T ";
                } else {
                    board = board + "F ";
                }
            }
            board = board + "\n";
        }
        return board;
    }

    public String toString() {
        String board = "";
        int yAxis = size - 1;
        for (int r = size - 1; r > -1; r--) {
            // If y-value is from 0-9, add a " " buffer to have consistent spacing
            // with numbers greater than 9
            if (yAxis / 10 < 1) {
                board = board + " " + yAxis + " ";
            } else {
                board = board + yAxis + " ";
            }
            for (int c = 0; c < size; c++) {
                board = board + boardDisplay[r][c] + "  ";
            }
            board = board + "\n";
            yAxis--;
        }

        board = board + "   ";
        int xAxis = 0;
        while (xAxis < size) {
            // If x-value is from 0-9, add a " " buffer to have consistence spacing
            // with numbers greater than 9
            if (xAxis / 10 < 1) {
                board = board + xAxis + "  ";
            } else {
                board = board + xAxis + " ";
            }
            xAxis++;
        }
        return board + "\n";
    }

    // Getters
    public int size() {
        return this.size;
    }

    public boolean bombed() {
        return bombed;
    }

    public boolean won() {
        int numTilesUnchecked = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (boardDisplay[r][c].equals("X")) {
                    numTilesUnchecked++;
                }
            }
        }
        return numTilesUnchecked == numBombs && !bombed;
    }
}
