package com.example.chess;

/**
 * Classe que representa una peça d'escacs.
 * Conté utilitats per identificar tipus i color de peces.
 */
public class Piece {
    
    // Constants per tipus de peces
    public static final char PAWN = 'P';
    public static final char ROOK = 'T';
    public static final char KNIGHT = 'C';
    public static final char BISHOP = 'A';
    public static final char QUEEN = 'Q';
    public static final char KING = 'K';
    public static final char EMPTY = '.';
    
    /**
     * Determina si una peça és blanca.
     * Les peces blanques són majúscules.
     * 
     * @param piece Caràcter que representa la peça
     * @return true si la peça és blanca, false altrament
     */
    public static boolean isWhite(char piece) {
        return Character.isUpperCase(piece) && piece != EMPTY;
    }
    
    /**
     * Determina si una peça és negra.
     * Les peces negres són minúscules.
     * 
     * @param piece Caràcter que representa la peça
     * @return true si la peça és negra, false altrament
     */
    public static boolean isBlack(char piece) {
        return Character.isLowerCase(piece) && piece != EMPTY;
    }
    
    /**
     * Comprova si una posició està buida.
     * 
     * @param piece Caràcter que representa la peça
     * @return true si la casella està buida, false altrament
     */
    public static boolean isEmpty(char piece) {
        return piece == EMPTY;
    }
    
    /**
     * Obté el tipus de peça (sense tenir en compte el color).
     * 
     * @param piece Caràcter que representa la peça
     * @return El tipus de peça en majúscula
     */
    public static char getType(char piece) {
        return Character.toUpperCase(piece);
    }
    
    /**
     * Comprova si dues peces són del mateix color.
     * 
     * @param piece1 Primera peça
     * @param piece2 Segona peça
     * @return true si són del mateix color, false altrament
     */
    public static boolean isSameColor(char piece1, char piece2) {
        return (isWhite(piece1) && isWhite(piece2)) || 
               (isBlack(piece1) && isBlack(piece2));
    }
    
    /**
     * Obté el nom complet de la peça.
     * 
     * @param piece Caràcter que representa la peça
     * @return Nom de la peça en català
     */
    public static String getPieceName(char piece) {
        char type = getType(piece);
        String color = isWhite(piece) ? "blanc" : "negre";
        
        switch (type) {
            case PAWN:
                return "Peó " + color;
            case ROOK:
                return "Torre " + color + "a";
            case KNIGHT:
                return "Cavall " + color;
            case BISHOP:
                return "Alfil " + color;
            case QUEEN:
                return "Reina " + color + "a";
            case KING:
                return "Rei " + color;
            case EMPTY:
                return "Casella buida";
            default:
                return "Peça desconeguda";
        }
    }
}