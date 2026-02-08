import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class escacs {
    static final Scanner sc = new Scanner(System.in);

    // Arrays i llistes
    private char[][] board;
    private ArrayList<String> players;
    private ArrayList<String> moveHistory;

    // Variables globals
    private String playerWhite;
    private String playerBlack;
    String lastWinner = null;

    public static void main(String[] args) {
        escacs game = new escacs();
        game.start();
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            gameBoard();

            // Gestió de noms
            if (moveHistory == null) {
                getPlayersName();
            } else {
                if (!readBoolean("\nMateixos jugadors? (si/no): ")) {
                    getPlayersName();
                    lastWinner = null;
                } else if (lastWinner != null && lastWinner.equals(playerBlack)) {
                    // Intercanvi segons guanyador
                    String temp = playerWhite;
                    playerWhite = playerBlack;
                    playerBlack = temp;
                    System.out.println("\nCanvi de torns! " + playerWhite + " (guanyador) ara porta blanques.");
                }
            }

            moveHistory = new ArrayList<>();
            lastWinner = playGame();
            showMoveHistory();

            // Control de continuïtat
            exit = !readBoolean("\nVoleu jugar una altra partida? (si/no): ");
        }
        System.out.println("\nGràcies per jugar!");
        sc.close();
    }

    public void gameBoard() {
        // Inicialitzar el tauler d'escacs
        board = new char[8][8];

        // Omplir espais buits
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '.';
            }
        }

        // Inicialitzar les peces blanques
        board[7][0] = 'T'; // Torre blanca
        board[7][7] = 'T'; // Torre blanca
        board[7][1] = 'C'; // Cavall blanc
        board[7][6] = 'C'; // Cavall blanc
        board[7][2] = 'A'; // Alfil blanc
        board[7][5] = 'A'; // Alfil blanc
        board[7][3] = 'Q'; // Reina blanca
        board[7][4] = 'K'; // Rei blanc

        for (int i = 0; i < 8; i++) {
            board[6][i] = 'P'; // Peons blancs
        }

        // Inicialitzar les peces negres
        board[0][0] = 't'; // Torre negre
        board[0][7] = 't'; // Torre negre
        board[0][1] = 'c'; // Cavall negre
        board[0][6] = 'c'; // Cavall negre
        board[0][2] = 'a'; // Alfil negre
        board[0][5] = 'a'; // Alfil negre
        board[0][3] = 'q'; // Reina negre
        board[0][4] = 'k'; // Rei negre

        for (int i = 0; i < 8; i++) {
            board[1][i] = 'p'; // Peons negres
        }
    }

    public void printBoard() {
        System.out.println("\n--------Tauler d'escacs--------");
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < 8; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(i + 1);
        }
        System.out.println("  a b c d e f g h");
    }

    public String playGame() {
        boolean gameOn = true;
        boolean whiteTurn = true;
        String winner = null;

        while (gameOn) {
            printBoard();

            String currentPlayer;
            String opponent;
            String color;

            if (whiteTurn) {
                currentPlayer = playerWhite;
                opponent = playerBlack;
                color = "blanques";
            } else {
                currentPlayer = playerBlack;
                opponent = playerWhite;
                color = "negres";
            }

            String move = readString("\n" + currentPlayer + " (" + color + "), mou (ex: 'e2 e4') o 'Abandonar': ");

            if (move.equalsIgnoreCase("Abandonar")) {
                System.out.println("\n" + currentPlayer + " ha abandonat la partida.");
                winner = opponent;
                gameOn = false;
            } else {
                // Validem i actualitzem
                if (validateMove(move, whiteTurn)) {
                    whiteTurn = !whiteTurn;
                } else {
                    System.out.println("Error. Moviment invàlid. Torni a intentar-ho.");
                }
            }
        }
        return winner;
    }

    public void getPlayersName() {
        players = new ArrayList<String>();

        // Demanar noms dels jugadors
        playerWhite = readString("\\nIntrodueix el nom del jugador 1 (blanques): ");
        players.add(playerWhite);

        playerBlack = readString("Introdueix el nom del jugador 2 (negres): ");
        players.add(playerBlack);

        System.out.println("\nJugadors registrats:");
        System.out.println(playerWhite + " (blanques)");
        System.out.println(playerBlack + " (negres)");
    }

    public String readString(String prompt) {
        boolean validInput = false;
        String input = "";

        while (!validInput) {
            try {
                System.out.print(prompt);
                input = sc.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Error. Entrada de dada incorrecta");
                sc.nextLine();
            }
        }
        return input;
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readString(prompt).trim().toLowerCase();

            if (input.equals("si") || input.equals("s")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("Error: Si us plau, respon 'si' o 'no'.");
            }
        }
    }

    public boolean validateMove(String move, boolean whiteTurn) {
        // Validar format
        String[] parts = move.split(" ");

        if (parts.length != 2) {
            System.out.println("Error. Introdueix la jugada en format 'e2 e4'.");
            return false;
        }

        // Separar origen i destí
        String origin = parts[0]; // e2
        String destination = parts[1]; // e4

        // Validar que cada part tingui 2 caràcters
        if (origin.length() != 2 || destination.length() != 2) {
            System.out.println("Error. Introdueix la jugada en format 'e2 e4'.");
            return false;
        }

        // Convertir coordenades
        int[] originCoords = convertCoordinates(origin);
        int[] destCoords = convertCoordinates(destination);

        if (originCoords == null || destCoords == null) {
            System.out.println("Error. Coordenades invàlides.");
            return false;
        }

        int originRow = originCoords[0];
        int originCol = originCoords[1];
        int destRow = destCoords[0];
        int destCol = destCoords[1];

        // Validar que hi ha una peça a l'origen
        char piece = board[originRow][originCol];
        if (piece == '.') {
            System.out.println("ERROR: No hi ha cap peça a " + origin);
            return false;
        }

        // Validar que la peça és del color correcte
        if (whiteTurn && Character.isLowerCase(piece)) {
            System.out.println("ERROR: No pots moure peces negres.");
            return false;
        }
        if (!whiteTurn && Character.isUpperCase(piece)) {
            System.out.println("ERROR: No pots moure peces blanques.");
            return false;
        }

        // Validar que no es captura una peça pròpia
        char targetPiece = board[destRow][destCol];
        if (targetPiece != '.') {
            boolean pieceIsWhite = Character.isUpperCase(piece);
            boolean targetIsWhite = Character.isUpperCase(targetPiece);

            if (pieceIsWhite == targetIsWhite) {
                System.out.println("ERROR: No pots capturar una peça del teu mateix color.");
                return false;
            }

            // Validar moviment segons la peça
            if (!validatePieceMovement(piece, originRow, originCol, destRow, destCol)) {
                return false;
            }
        }
        // Actualitzar el tauler
        updateBoard(move);

        // Guardar moviment a l'historial
        String playerName;
        String color;

        if (whiteTurn) {
            playerName = playerWhite;
            color = "blanques";
        } else {
            playerName = playerBlack;
            color = "negres";
        }

        moveHistory.add(playerName + " (" + color + "): " + move);

        return true;
    }

    public boolean validatePieceMovement(char piece, int originRow, int originCol, int destRow, int destCol) {
        // Validar moviment segons la peça
        switch (Character.toUpperCase(piece)) {
            // Peó
            case 'P':
                return validatePeo(originRow, originCol, destRow, destCol);
            // Torre
            case 'T':
                return validateTorre(originRow, originCol, destRow, destCol);
            // Cavall
            case 'C':
                return validateCavall(originRow, originCol, destRow, destCol);
            // Alfil
            case 'A':
                return validateAlfil(originRow, originCol, destRow, destCol);
            // Reina
            case 'Q':
                return validateReina(originRow, originCol, destRow, destCol);
            // Rei
            case 'K':
                return validateRei(originRow, originCol, destRow, destCol);
            default:
                System.out.println("ERROR: Peça desconeguda.");
                return false;
        }
    }

    public int[] convertCoordinates(String coord) {
        // Convertir coordenades d'escacs a índexs d'array
        char file = coord.charAt(0); // lletra
        char rank = coord.charAt(1); // número

        // Validar que la columna sigui entre 'a' i 'h'
        // Validar que la fila sigui entre '1' i '8'
        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            return null; // Coordenades invàlides
        }

        int row = Character.getNumericValue(rank) - 1; // Fila (0-7)
        int col = file - 'a'; // Columna (0-7)

        return new int[] { row, col };
    }

    public void updateBoard(String move) {
        String[] parts = move.split(" ");
        String origin = parts[0];
        String destination = parts[1];

        int[] originCoords = convertCoordinates(origin);
        int[] destCoords = convertCoordinates(destination);

        int originRow = originCoords[0];
        int originCol = originCoords[1];
        int destRow = destCoords[0];
        int destCol = destCoords[1];

        // Moure la peça
        board[destRow][destCol] = board[originRow][originCol];
        board[originRow][originCol] = '.';
    }

    public boolean validatePeo(int originRow, int originCol, int destRow, int destCol) {
        char piece = board[originRow][originCol];
        char destPiece = board[destRow][destCol];
        boolean isWhite = Character.isUpperCase(piece);

        int direction;
        // Direcció del moviment del peó
        if (isWhite) {
            direction = -1; // Les blanques es mouen cap amunt (fila disminueix)
        } else {
            direction = 1; // Les negres es mouen cap avall (fila augmenta)
        }
        int colDiff = Math.abs(destCol - originCol);

        // Movimient normal (1 casilla cap endavant)
        if (destCol == originCol && destRow == originRow + direction && destPiece == '.') {
            return true;
        }

        // Moviment inicial (2 casillas endavant)
        boolean isInStartingPosition = (isWhite && originRow == 6) || (!isWhite && originRow == 1);

        if (isInStartingPosition && destCol == originCol && destRow == originRow + 2 * direction) {
            if (board[originRow + direction][originCol] == '.' && destPiece == '.') {
                return true;
            }
        }
        // Captura diagonal (1 casilla en diagonal)
        if (colDiff == 1 && destRow == originRow + direction && destPiece != '.') {
            boolean destIsWhite = Character.isUpperCase(destPiece);
            if (isWhite != destIsWhite) {
                return true;
            }
        }

        System.out.println("Error. Moviment de peó invàlid.");
        return false;
    }

    public boolean validateTorre(int originRow, int originCol, int destRow, int destCol) {
        // Moviment en línia recta horitzontal (mateixa fila)
        if (originRow == destRow) {
            // Comprovar que el camí està lliure
            int start = Math.min(originCol, destCol) + 1;
            int end = Math.max(originCol, destCol);

            for (int col = start; col < end; col++) {
                if (board[originRow][col] != '.') {
                    System.out.println("ERROR: Hi ha una peça en el camí horitzontal.");
                    return false;
                }
            }
            return true;

            // Moviment en línia recta vertical (mateixa columna)
        } else if (originCol == destCol) {
            // Comprovar que el camí està lliure
            int start = Math.min(originRow, destRow) + 1;
            int end = Math.max(originRow, destRow);

            for (int row = start; row < end; row++) {
                if (board[row][originCol] != '.') {
                    System.out.println("ERROR: Hi ha una peça en el camí vertical.");
                    return false;
                }
            }
            return true;

        } else {
            System.out.println("ERROR: La torre només es pot moure en línea recta.");
            return false;
        }
    }

    public boolean validateCavall(int originRow, int originCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - originRow);
        int colDiff = Math.abs(destCol - originCol);

        // Moviment en L: 2 caselles en una direcció i 1 en l'altra
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return true;
        } else {
            System.out.println("ERROR: El cavall es mou en forma de L.");
            return false;
        }
    }

    public boolean validateAlfil(int originRow, int originCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - originRow);
        int colDiff = Math.abs(destCol - originCol);

        // L'alfil es mou en diagonal (rowDiff == colDiff)
        if (rowDiff != colDiff) {
            System.out.println("ERROR: L'alfil només es pot moure en diagonal.");
            return false;
        }

        // Comprovar que el camí està lliure
        int rowDirection = 0;
        if (destRow > originRow) {
            rowDirection = 1;
        } else {
            rowDirection = -1;
        }

        int colDirection = 0;
        if (destCol > originCol) {
            colDirection = 1;
        } else {
            colDirection = -1;
        }

        int currentRow = originRow + rowDirection;
        int currentCol = originCol + colDirection;

        while (currentRow != destRow && currentCol != destCol) {
            if (board[currentRow][currentCol] != '.') {
                System.out.println("ERROR: Hi ha una peça en el camí diagonal.");
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        return true;
    }

    public boolean validateReina(int originRow, int originCol, int destRow, int destCol) {
        // Moviment en línia recta (com torre)
        if (originRow == destRow || originCol == destCol) {
            return validateTorre(originRow, originCol, destRow, destCol);
        }

        // Moviment en diagonal (com alfil)
        int rowDiff = Math.abs(destRow - originRow);
        int colDiff = Math.abs(destCol - originCol);

        if (rowDiff == colDiff) {
            return validateAlfil(originRow, originCol, destRow, destCol);
        }

        System.out.println("ERROR: La reina es mou en línia recta o en diagonal.");
        return false;
    }

    public boolean validateRei(int originRow, int originCol, int destRow, int destCol) {
        int rowDiff = Math.abs(destRow - originRow);
        int colDiff = Math.abs(destCol - originCol);

        // El rei es pot moure una casella en qualsevol direcció
        if (rowDiff <= 1 && colDiff <= 1) {
            return true;
        } else {
            System.out.println("ERROR: El rei només es pot moure 1 casella.");
            return false;
        }
    }

    public void showMoveHistory() {
        System.out.println("\n=== HISTORIAL DE MOVIMENTS ===");

        if (moveHistory.isEmpty()) {
            System.out.println("No s'han fet moviments.");
        } else {
            for (int i = 0; i < moveHistory.size(); i++) {
                System.out.println((i + 1) + ". " + moveHistory.get(i));
            }
        }
    }
}