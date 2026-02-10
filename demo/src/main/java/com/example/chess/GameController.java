package com.example.chess;

import java.util.ArrayList;

/**
 * Classe controladora principal del joc d'escacs.
 * Gestiona el flux de joc, torns i partides.
 */
public class GameController {
    
    private Board board;
    private MoveValidator validator;
    private UIConsole ui;
    private ArrayList<String> moveHistory;
    
    private String playerWhite;
    private String playerBlack;
    private String lastWinner;
    
    /**
     * Constructor que inicialitza els components del joc.
     */
    public GameController() {
        this.board = new Board();
        this.validator = new MoveValidator(board);
        this.ui = new UIConsole();
        this.lastWinner = null;
    }
    
    /**
     * Inicia el joc i gestiona el bucle principal.
     */
    public void start() {
        boolean exit = false;
        
        ui.showMessage("=== JOC D'ESCACS ===");
        
        while (!exit) {
            // Reiniciar tauler
            board.initializeBoard();
            
            // Gestió de noms dels jugadors
            if (moveHistory == null) {
                getPlayerNames();
            } else {
                if (!ui.readBoolean("\nMateixos jugadors? (si/no): ")) {
                    getPlayerNames();
                    lastWinner = null;
                } else if (lastWinner != null && lastWinner.equals(playerBlack)) {
                    // Intercanvi segons guanyador
                    swapPlayers();
                }
            }
            
            // Iniciar nova partida
            moveHistory = new ArrayList<>();
            lastWinner = playGame();
            ui.showMoveHistory(moveHistory);
            
            // Preguntar si volen continuar
            exit = !ui.readBoolean("\nVoleu jugar una altra partida? (si/no): ");
        }
        
        ui.showMessage("\nGràcies per jugar!");
        ui.close();
    }
    
    /**
     * Obté els noms dels jugadors.
     */
    private void getPlayerNames() {
        playerWhite = ui.readString("\nIntrodueix el nom del jugador 1 (blanques): ");
        playerBlack = ui.readString("Introdueix el nom del jugador 2 (negres): ");
        
        ui.showMessage("\nJugadors registrats:");
        ui.showMessage(playerWhite + " (blanques)");
        ui.showMessage(playerBlack + " (negres)");
    }
    
    /**
     * Intercanvia els jugadors després d'una partida.
     */
    private void swapPlayers() {
        String temp = playerWhite;
        playerWhite = playerBlack;
        playerBlack = temp;
        ui.showMessage("\nCanvi de torns! " + playerWhite + " (guanyador) ara porta blanques.");
    }
    
    /**
     * Executa una partida completa.
     * 
     * @return El nom del guanyador
     */
    private String playGame() {
        boolean gameOn = true;
        boolean whiteTurn = true;
        String winner = null;
        
        while (gameOn) {
            board.printBoard();
            
            String currentPlayer = whiteTurn ? playerWhite : playerBlack;
            String opponent = whiteTurn ? playerBlack : playerWhite;
            String color = whiteTurn ? "blanques" : "negres";
            
            String moveInput = ui.readString("\n" + currentPlayer + " (" + color + 
                                            "), mou (ex: 'e2 e4') o 'Abandonar': ");
            
            if (moveInput.equalsIgnoreCase("Abandonar")) {
                ui.showMessage("\n" + currentPlayer + " ha abandonat la partida.");
                winner = opponent;
                gameOn = false;
            } else {
                // Processar moviment
                if (processMove(moveInput, whiteTurn)) {
                    whiteTurn = !whiteTurn; // Canviar torn
                }
                // Si el moviment no és vàlid, no canvia el torn i es torna a demanar
            }
        }
        
        return winner;
    }
    
    /**
     * Processa un moviment introduït per l'usuari.
     * 
     * @param moveString Cadena amb el moviment
     * @param whiteTurn true si és el torn de les blanques
     * @return true si el moviment s'ha processat correctament
     */
    private boolean processMove(String moveString, boolean whiteTurn) {
        // Parsejar el moviment
        int[] coords = ui.parseMove(moveString);
        
        if (coords == null) {
            return false; // Error de format
        }
        
        int fromRow = coords[0];
        int fromCol = coords[1];
        int toRow = coords[2];
        int toCol = coords[3];
        
        // Validar el moviment
        if (validator.validateMove(fromRow, fromCol, toRow, toCol, whiteTurn)) {
            // Executar el moviment
            board.movePiece(fromRow, fromCol, toRow, toCol);
            
            // Afegir a l'historial
            String move = convertToChessNotation(fromRow, fromCol) + " " + 
                         convertToChessNotation(toRow, toCol);
            moveHistory.add(move);
            
            return true;
        } else {
            // El missatge d'error ja s'ha mostrat en validateMove
            return false;
        }
    }
    
    /**
     * Converteix coordenades d'array a notació d'escacs.
     * 
     * @param row Fila (0-7)
     * @param col Columna (0-7)
     * @return Coordenada en format escacs (ex: "e2")
     */
    private String convertToChessNotation(int row, int col) {
        char file = (char) ('a' + col);
        char rank = (char) ('1' + row);
        return "" + file + rank;
    }
    
    /**
     * Punt d'entrada del programa.
     * 
     * @param args Arguments de línia de comandes
     */
    public static void main(String[] args) {
        GameController game = new GameController();
        game.start();
    }
}