import java.util.Scanner;

public class Main {
    private static Board thisBoard;
    private static boolean won = false;

    public static void main(String[] args) {
        displayMenu();
        playGame();
    }

    public static void displayMenu() {
        Scanner scanny = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("N") && !userInput.equals("Q")) {
            System.out.println("(N)ew game");
            System.out.println("(Q)uit minesweeper\n");

            while (!scanny.hasNext());
            userInput = scanny.nextLine().toUpperCase();
        }

        switch (userInput) {
            case("N"):
                displayDifficulty();
                break;
            case("Q"):
                break;
            default:
                break;
        }
    }

    public static void displayDifficulty() {
        System.out.println("\n\n");
        Scanner scanny = new Scanner(System.in);
        String userInput = "";

        while (!userInput.equals("C") &&
                !userInput.equals("E") && !userInput.equals("M") && !userInput.equals("H")) {
            System.out.println("(C)ustom");
            System.out.println("(E)asy");
            System.out.println("(M)edium");
            System.out.println("(H)ard\n");

            while (!scanny.hasNext());
            userInput = scanny.nextLine().toUpperCase();
        }
        if (userInput.equals("C")) {
            thisBoard = customGameBoard();
        } else {
            thisBoard = new Board(userInput);
        }
        System.out.print("\n\n" + thisBoard);
    }

    public static Board customGameBoard() {
        Scanner scanny = new Scanner(System.in);
        String input = "";
        int boardSize = 0;
        int numBombs = 0;

        System.out.println("Type board size between 2 and 50.\n");
        while (boardSize < 2 || boardSize > 50) {
            while (!scanny.hasNext());
            input = scanny.nextLine();
            boardSize = stringToInt(input);
        }
        System.out.println("Type number of bombs.\n");
        while (numBombs < 1 || numBombs >= Math.pow(boardSize, 2)) {
            while (!scanny.hasNext());
            input = scanny.nextLine();
            numBombs = stringToInt(input);
        }

        return new Board(boardSize, numBombs);
    }

    public static void playGame() {
        while (!thisBoard.won() && !thisBoard.bombed()) {
            Scanner scanny = new Scanner(System.in);

            System.out.println("Chose X: ");
            String xInput = "";
            while (!validCoordinate(xInput)) {
                while (!scanny.hasNext());
                xInput = scanny.nextLine();
            }
            int xCoord = Integer.parseInt(xInput);

            System.out.println("Choose Y: ");
            String yInput = "";
            while (!validCoordinate(yInput)) {
                while (!scanny.hasNext());
                yInput = scanny.nextLine();
            }
            int yCoord = Integer.parseInt(yInput);

            thisBoard.destroyTile(xCoord, yCoord);
            System.out.print(thisBoard.toString());
        }

        if (thisBoard.bombed()) {
            System.out.println("YOU LOST!");
        } else {
            System.out.println("YOU WON!");
        }
    }

    public static int stringToInt(String input) {
        try {
            int inputToInt = Integer.parseInt(input);
            return inputToInt;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean validCoordinate(String userInput) {
        try {
            int intput = Integer.parseInt(userInput);
            return -1 < intput && intput < thisBoard.size();
        } catch (NumberFormatException e) {
            return false;
        }
    }
}