package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        String[] shipName = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] shipLength = {5, 4, 3, 3, 2};
        int gameFieldSize = 10;
        int xCounter = 0;
        int xCounter2 = 0;

        //for PLAYER 1   //making and printing scheme
        char[][] scheme = makeScheme(gameFieldSize, gameFieldSize);
        char[][] schemeInTheFog = makeScheme(gameFieldSize, gameFieldSize);
        System.out.println("Player 1, place your ships on the game field");
        printScheme(scheme);

        //asking for coordinates and positioning of warships
        for (int i = 0; i < shipLength.length; i++) {
            System.out.println("Enter the coordinates of the " + shipName[i] + " (" + shipLength[i] + " cells):");
            positioningScheme(scheme, shipName[i], shipLength[i]);
            printScheme(scheme);
        }
        System.out.println("Press Enter and pass the move to another player");
        pressEnterToContinue();

        //for PLAYER 2 //making and printing scheme
        char[][] scheme2 = makeScheme(gameFieldSize, gameFieldSize);
        char[][] schemeInTheFog2 = makeScheme(gameFieldSize, gameFieldSize);
        System.out.println("Player 2, place your ships on the game field");
        printScheme(scheme2);

        //asking for coordinates and positioning of warships
        for (int i = 0; i < shipLength.length; i++) {
            System.out.println("Enter the coordinates of the " + shipName[i] + " (" + shipLength[i] + " cells):");
            positioningScheme(scheme2, shipName[i], shipLength[i]);
            printScheme(scheme2);
        }
        System.out.println("Press Enter and pass the move to another player");
        pressEnterToContinue();

        //starting the game
        do {
            //PLAYER 1
            printScheme(schemeInTheFog2);
            System.out.println("---------------------");
            printScheme(scheme);
            System.out.println("Player 1, it's your turn:");
            switch (shot(scheme2, schemeInTheFog2)) {
                case 0:
                    System.out.println("You missed!");
                    break;
                case 1:
                    if (xCounter + 1 == Arrays.stream(shipLength).sum()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                    } else {
                        System.out.println("You sank a ship!");
                    }
                    xCounter++;
                    break;
                case 2:
                    System.out.println("You hit a ship!");
                    xCounter++;
                    break;
                case 3:
                    System.out.println("You've already sanked/hitted this ship!");
                    break;
            }
            System.out.println("Press Enter and pass the move to another player");
            pressEnterToContinue();

            //PLAYER 2
            printScheme(schemeInTheFog);
            System.out.println("---------------------");
            printScheme(scheme2);
            System.out.println("Player 2, it's your turn:");
            switch (shot(scheme, schemeInTheFog)) {
                case 0:
                    System.out.println("You missed!");
                    break;
                case 1:
                    if (xCounter2 + 1 == Arrays.stream(shipLength).sum()) {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                    } else {
                        System.out.println("You sank a ship!");
                    }
                    xCounter2++;
                    break;
                case 2:
                    System.out.println("You hit a ship!");
                    xCounter2++;
                    break;
                case 3:
                    System.out.println("You've already sanked/hitted this ship!");
                    break;
            }
            System.out.println("Press Enter and pass the move to another player");
            pressEnterToContinue();
        } while (xCounter != Arrays.stream(shipLength).sum() || xCounter2 != Arrays.stream(shipLength).sum());
    }

    public static char[][] makeScheme(int rows, int column) {

        char[][] warField = new char[rows + 1][column + 2];
        int rowsCounter = 65;
        warField[0][0] = ' ';

        for (int i = 0; i < rows; i++) {
            warField[i][0] = (char) rowsCounter;
            rowsCounter++;
            for (int j = 1; j < column + 1; j++) {
                warField[i][j] = '~';
            }
        }
        return warField;
    }

    public static void printScheme(char[][] warField) {

        System.out.print("\n  ");
        for (int i = 1; i < warField.length; i++) {
            if (i < 10) {
                System.out.print(i + " ");
            } else {
                System.out.print(i);
            }
        }
        for (int i = 0; i < warField.length; i++) {
            System.out.println();
            for (int j = 0; j < warField[0].length; j++) {
                System.out.print(warField[i][j] + " ");
            }
        }
        System.out.println("\n");
    }

    public static void positioningScheme(char[][] scheme, String shipName, int shipLength) {

        String coordinates;
        coordinates = scanner.nextLine().toUpperCase();
        String[] parts = coordinates.split("\\s+");
        int realShipLength;
        boolean horizontalPosition = true;

        int int1 = Integer.parseInt(parts[0].replaceAll("\\D+", ""));
        int int2 = Integer.parseInt(parts[1].replaceAll("\\D+", ""));

        char char1 = (parts[0].replaceAll("[0-9]", "")).charAt(0);
        char char2 = (parts[1].replaceAll("[0-9]", "")).charAt(0);

        if (char1 == char2) {
            realShipLength = Math.abs(int1 - int2) + 1;
        } else if (int1 == int2) {
            realShipLength = Math.abs(char1 - char2) + 1;
            horizontalPosition = false;
        } else {
            System.out.println("Error! Wrong ship location! Try again:");
            positioningScheme(scheme, shipName, shipLength);
            return;
        }

        if (realShipLength != shipLength) {
            System.out.println(realShipLength + " " + shipLength);
            System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            positioningScheme(scheme, shipName, shipLength);
            return;
        }

        for (int i = 0; i < realShipLength; i++) {
            if (horizontalPosition) {
                if (checkClosedPosition(scheme, char1 - 65, Math.min(int1, int2) + i)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    positioningScheme(scheme, shipName, shipLength);
                    return;
                }
            } else {
                if (checkClosedPosition(scheme, Math.min(char1, char2) - 65 + i, int1)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    positioningScheme(scheme, shipName, shipLength);
                    return;
                }
            }
        }

        if (horizontalPosition) {
            for (int i = 0; i < realShipLength; i++) {
                scheme[char1 - 65][Math.min(int1, int2) + i] = 'O';
            }
        } else {
            for (int i = 0; i < realShipLength; i++) {
                scheme[Math.min(char1, char2) - 65 + i][int1] = 'O';
            }
        }
    }

    public static boolean checkClosedPosition(char[][] scheme, int int1, int int2) {
        boolean result = false;

        if (int1 == 0) {
            if (scheme[int1 + 1][int2] == 'O' ||
                scheme[int1][int2 - 1] == 'O' ||
                scheme[int1][int2 + 1] == 'O' ||
                scheme[int1 + 1][int2 + 1] == 'O' ||
                scheme[int1 + 1][int2 - 1] == 'O') {
                result = true;
            }
        } else {
            if (scheme[int1 - 1][int2] == 'O' ||
                scheme[int1 + 1][int2] == 'O' ||
                scheme[int1][int2 + 1] == 'O' ||
                scheme[int1][int2 - 1] == 'O' ||
                scheme[int1 - 1][int2 - 1] == 'O' ||
                scheme[int1 + 1][int2 + 1] == 'O' ||
                scheme[int1 + 1][int2 - 1] == 'O') {
                result = true;
            }
        }
        return result;
    }

    public static int shot(char[][] scheme, char[][] schemeInTheFog) {
        boolean error;
        int int1 = 0;
        int int2 = 0;
        String coordinates;

        do {
            error = false;
            coordinates = scanner.nextLine().toUpperCase();

            try {
                int1 = coordinates.charAt(0) - 65;
                int2 = Integer.parseInt(coordinates.substring(1));
            } catch (Exception e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                error = true;
            }

            if (int1 > 10 || int1 < 0 || int2 > 10 || int2 < 0) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                error = true;
            }
        } while (error);

        if (scheme[int1][int2] == 'O') {
            scheme[int1][int2] = 'X';
            schemeInTheFog[int1][int2] = 'X';
            if (checkClosedPosition(scheme, int1, int2)) {
                return 2;
            } else {
                return 1;
            }
        } else if (scheme[int1][int2] == 'X') {
            return 3;
        } else {
            scheme[int1][int2] = 'M';
            schemeInTheFog[int1][int2] = 'M';
            return 0;
        }
    }

    public static void pressEnterToContinue() {
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }
}
