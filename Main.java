import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static char[][] map;
    static final int MAP_SIZE = 3;
    static final char EMPTY_CELL = '*';
    static final char X_FIELD = 'X';
    static final char O_FIELD = 'O';

    public static void main(String[] args) {
        initGame();
        printMap();
        while (true) {
            playersTurn();
            checkGameStatus(X_FIELD, "You win.");
            aiTurn();
            checkGameStatus(O_FIELD, "Computer wins.");
        }
    }

    public static void initGame() {
        map = new char[MAP_SIZE][MAP_SIZE];
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = EMPTY_CELL;
            }
        }
        scanner = new Scanner(System.in);
    }

    public static void printMap() {
        // Map markup
        for (int i = 0; i <= MAP_SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < MAP_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void playersTurn() {
        int x, y;
        do {
            System.out.println("Your turn. Enter cell coordinates 'X Y'");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            if (!isCellEmpty(x, y)) {
                System.out.println("The cell is already taken");
            }
        } while (!isCellEmpty(x, y));
        map[x][y] = X_FIELD;
    }

    public static void aiTurn() {
        int x, y;
        System.out.println("Computer`s turn");
        do {
            x = (int) (Math.random() * MAP_SIZE);
            y = (int) (Math.random() * MAP_SIZE);
        } while (!isCellEmpty(x, y));
        map[x][y] = O_FIELD;
    }

    public static boolean isCellEmpty(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) {
            return false;
        }
        if (map[x][y] != EMPTY_CELL) {
            return false;
        }
        return true;
    }

    public static boolean isDraft() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean showWinner(char playerField) {
        // Check rows
        for (int i = 0; i < MAP_SIZE; i++) {
            boolean rowWin = true;
            for (int j = 0; j < MAP_SIZE; j++) {
                if (map[i][j] != playerField) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < MAP_SIZE; j++) {
            boolean colWin = true;
            for (int i = 0; i < MAP_SIZE; i++) {
                if (map[i][j] != playerField) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                return true;
            }
        }

        // Check diagonals
        boolean diag1Win = true;
        boolean diag2Win = true;
        for (int i = 0; i < MAP_SIZE; i++) {
            if (map[i][i] != playerField) {
                diag1Win = false;
            }
            if (map[i][MAP_SIZE - 1 - i] != playerField) {
                diag2Win = false;
            }
        }
        if (diag1Win || diag2Win) {
            return true;
        }

        return false;
    }

    public static void checkGameStatus(char playerField, String message) {
        printMap();
        if (showWinner(playerField)) {
            System.out.println("Game over. " + message);
            System.exit(0);
        }
        if (isDraft()) {
            System.out.println("Game over. Draft.");
            System.exit(0);
        }
    }
}
