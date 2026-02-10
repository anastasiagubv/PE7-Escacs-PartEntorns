package com.example.chess;

/**
 * Classe que gestiona el tauler d'escacs.
 * Responsable de crear, inicialitzar i manipular el tauler de joc.
 */
public class Board {
    
    private static final int BOARD_SIZE = 8;
    private char[][] board;
    
    /**
     * Constructor que inicialitza el tauler amb les peces en posició inicial.
     */
    public Board() {
        initializeBoard();
    }
    
    /**
     * Inicialitza el tauler d'escacs amb totes les peces en posició inicial.
     * Les peces blanques són majúscules i les negres minúscules.
     */
    public void initializeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        
        // Omplir espais buits
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = '.';
            }
        }
        
        // Inicialitzar les peces blanques (fila 7 i 6)
        board[7][0] = 'T'; // Torre blanca
        board[7][7] = 'T'; // Torre blanca
        board[7][1] = 'C'; // Cavall blanc
        board[7][6] = 'C'; // Cavall blanc
        board[7][2] = 'A'; // Alfil blanc
        board[7][5] = 'A'; // Alfil blanc
        board[7][3] = 'Q'; // Reina blanca
        board[7][4] = 'K'; // Rei blanc
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[6][i] = 'P'; // Peons blancs
        }
        
        // Inicialitzar les peces negres (fila 0 i 1)
        board[0][0] = 't'; // Torre negre
        board[0][7] = 't'; // Torre negre
        board[0][1] = 'c'; // Cavall negre
        board[0][6] = 'c'; // Cavall negre
        board[0][2] = 'a'; // Alfil negre
        board[0][5] = 'a'; // Alfil negre
        board[0][3] = 'q'; // Reina negre
        board[0][4] = 'k'; // Rei negre
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[1][i] = 'p'; // Peons negres
        }
    }
    
    /**
     * Imprimeix el tauler per consola.
     */
    public void printBoard() {
        System.out.println("\n--------Tauler d'escacs--------");
        System.out.println("  a b c d e f g h");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println(i + 1);
        }
        System.out.println("  a b c d e f g h");
    }
    
    /**
     * Obté la peça en una posició específica del tauler.
     * 
     * @param row Fila (0-7)
     * @param col Columna (0-7)
     * @return El caràcter que representa la peça o '.' si està buida
     */
    public char getPiece(int row, int col) {
        if (isValidPosition(row, col)) {
            return board[row][col];
        }
        return '.';
    }
    
    /**
     * Col·loca una peça en una posició específica del tauler.
     * 
     * @param row Fila (0-7)
     * @param col Columna (0-7)
     * @param piece Caràcter que representa la peça
     */
    public void setPiece(int row, int col, char piece) {
        if (isValidPosition(row, col)) {
            board[row][col] = piece;
        }
    }
    
    /**
     * Mou una peça d'una posició a una altra.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     */
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = '.';
    }
    
    /**
     * Comprova si una posició està dins dels límits del tauler.
     * 
     * @param row Fila a comprovar
     * @param col Columna a comprovar
     * @return true si la posició és vàlida, false altrament
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
    
    /**
     * Comprova si una casella està buida.
     * 
     * @param row Fila
     * @param col Columna
     * @return true si la casella està buida, false altrament
     */
    public boolean isEmpty(int row, int col) {
        return getPiece(row, col) == '.';
    }
    
    /**
     * Obté la mida del tauler.
     * 
     * @return Mida del tauler (8)
     */
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}