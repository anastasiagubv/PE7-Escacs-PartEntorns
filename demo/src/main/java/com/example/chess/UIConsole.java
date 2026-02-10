package com.example.chess;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Classe responsable de la interfície d'usuari en consola.
 * Gestiona l'entrada/sortida i el parseig de comandes.
 */
public class UIConsole {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Llegeix una cadena de text de l'usuari.
     * 
     * @param prompt Missatge a mostrar a l'usuari
     * @return La cadena introduïda per l'usuari
     */
    public String readString(String prompt) {
        boolean validInput = false;
        String input = "";
         
        while (!validInput) {
            try {
                System.out.print(prompt);
                input = scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Entrada de dada incorrecta");
                scanner.nextLine();
            }
        }
        return input;
    }
    
    /**
     * Llegeix una resposta booleana (si/no) de l'usuari.
     * 
     * @param prompt Missatge a mostrar a l'usuari
     * @return true si l'usuari respon "si", false si respon "no"
     */
    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readString(prompt).trim().toLowerCase();
            
            if (input.equals("si") || input.equals("s")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            } else {
                System.out.println("ERROR: Si us plau, respon 'si' o 'no'.");
            }
        }
    }
    
    /**
     * Parseja un moviment en format d'escacs (ex: "e2 e4").
     * 
     * @param moveString Cadena amb el moviment
     * @return Array amb les coordenades [fromRow, fromCol, toRow, toCol] o null si és invàlid
     */
    public int[] parseMove(String moveString) {
        // Validar format bàsic
        String[] parts = moveString.trim().split("\\s+");
        
        if (parts.length != 2) {
            System.out.println("ERROR: Format invàlid. Utilitza format 'e2 e4'.");
            return null;
        }
        
        String origin = parts[0].toLowerCase();
        String destination = parts[1].toLowerCase();
        
        // Validar que cada part tingui 2 caràcters
        if (origin.length() != 2 || destination.length() != 2) {
            System.out.println("ERROR: Format invàlid. Cada posició ha de tenir 2 caràcters (ex: 'e2').");
            return null;
        }
        
        // Convertir coordenades
        int[] originCoords = convertCoordinates(origin);
        int[] destCoords = convertCoordinates(destination);
        
        if (originCoords == null || destCoords == null) {
            System.out.println("ERROR: Coordenades invàlides. Utilitza lletres a-h i números 1-8.");
            return null;
        }
        
        return new int[] { 
            originCoords[0], originCoords[1], 
            destCoords[0], destCoords[1] 
        };
    }
    
    /**
     * Converteix una coordenada d'escacs (ex: "e2") a índexs d'array.
     * 
     * @param coord Coordenada en format escacs (ex: "e2")
     * @return Array amb [fila, columna] o null si és invàlid
     */
    private int[] convertCoordinates(String coord) {
        if (coord.length() != 2) {
            return null;
        }
        
        char file = coord.charAt(0); // lletra (columna)
        char rank = coord.charAt(1); // número (fila)
        
        // Validar que la columna sigui entre 'a' i 'h'
        // Validar que la fila sigui entre '1' i '8'
        if (file < 'a' || file > 'h' || rank < '1' || rank > '8') {
            return null;
        }
        
        int row = Character.getNumericValue(rank) - 1; // Fila (0-7)
        int col = file - 'a'; // Columna (0-7)
        
        return new int[] { row, col };
    }
    
    /**
     * Mostra un missatge a la consola.
     * 
     * @param message Missatge a mostrar
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
    
    /**
     * Mostra un missatge d'error a la consola.
     * 
     * @param errorMessage Missatge d'error
     */
    public void showError(String errorMessage) {
        System.out.println("ERROR: " + errorMessage);
    }
    
    /**
     * Mostra l'historial de moviments.
     * 
     * @param moveHistory Llista amb l'historial de moviments
     */
    public void showMoveHistory(java.util.ArrayList<String> moveHistory) {
        System.out.println("\n=== HISTORIAL DE MOVIMENTS ===");
        
        if (moveHistory.isEmpty()) {
            System.out.println("No s'han fet moviments.");
        } else {
            for (int i = 0; i < moveHistory.size(); i++) {
                System.out.println((i + 1) + ". " + moveHistory.get(i));
            }
        }
    }
    
    /**
     * Tanca el scanner quan ja no es necessita.
     */
    public void close() {
        scanner.close();
    }
}