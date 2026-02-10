package com.example.chess;

/**
 * Classe responsable de validar els moviments de les peces d'escacs.
 * Conté la lògica específica per cada tipus de peça.
 */
public class MoveValidator {
    
    private Board board;
    
    /**
     * Constructor que rep el tauler per poder validar moviments.
     * 
     * @param board Tauler d'escacs
     */
    public MoveValidator(Board board) {
        this.board = board;
    }
    
    /**
     * Valida un moviment complet des d'origen fins a destí.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @param isWhiteTurn true si és el torn de les blanques
     * @return true si el moviment és vàlid, false altrament
     */
    public boolean validateMove(int fromRow, int fromCol, int toRow, int toCol, boolean isWhiteTurn) {
        
        // Validar que les posicions estan dins del tauler
        if (!board.isValidPosition(fromRow, fromCol) || !board.isValidPosition(toRow, toCol)) {
            System.out.println("ERROR: Posició fora del tauler.");
            return false;
        }
        
        // Validar que hi ha una peça a l'origen
        char piece = board.getPiece(fromRow, fromCol);
        if (Piece.isEmpty(piece)) {
            System.out.println("ERROR: No hi ha cap peça a aquesta posició.");
            return false;
        }
        
        // Validar que la peça és del color correcte
        boolean isPieceWhite = Piece.isWhite(piece);
        if (isPieceWhite != isWhiteTurn) {
            String expectedColor = isWhiteTurn ? "blanques" : "negres";
            System.out.println("ERROR: No pots moure peces " + (isPieceWhite ? "blanques" : "negres") + 
                             ". Ara toca moure " + expectedColor + ".");
            return false;
        }
        
        // Validar que l'origen i el destí no són iguals
        if (fromRow == toRow && fromCol == toCol) {
            System.out.println("ERROR: L'origen i el destí són la mateixa posició.");
            return false;
        }
        
        // Validar que al destí no hi ha una peça del mateix color
        char destPiece = board.getPiece(toRow, toCol);
        if (!Piece.isEmpty(destPiece) && Piece.isSameColor(piece, destPiece)) {
            System.out.println("ERROR: No pots capturar les teves pròpies peces.");
            return false;
        }
        
        // Validar moviment específic segons el tipus de peça
        return validatePieceMove(piece, fromRow, fromCol, toRow, toCol);
    }
    
    /**
     * Valida el moviment segons el tipus de peça.
     * 
     * @param piece Peça a moure
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment és vàlid segons les regles de la peça
     */
    private boolean validatePieceMove(char piece, int fromRow, int fromCol, int toRow, int toCol) {
        switch (Piece.getType(piece)) {
            case Piece.PAWN:
                return validatePawnMove(fromRow, fromCol, toRow, toCol);
            case Piece.ROOK:
                return validateRookMove(fromRow, fromCol, toRow, toCol);
            case Piece.KNIGHT:
                return validateKnightMove(fromRow, fromCol, toRow, toCol);
            case Piece.BISHOP:
                return validateBishopMove(fromRow, fromCol, toRow, toCol);
            case Piece.QUEEN:
                return validateQueenMove(fromRow, fromCol, toRow, toCol);
            case Piece.KING:
                return validateKingMove(fromRow, fromCol, toRow, toCol);
            default:
                System.out.println("ERROR: Peça desconeguda.");
                return false;
        }
    }
    
    /**
     * Valida el moviment d'un peó.
     * Un peó pot:
     * - Avançar 1 casella endavant si està buida
     * - Avançar 2 caselles des de la posició inicial si el camí està lliure
     * - Capturar en diagonal 1 casella si hi ha una peça rival
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment del peó és vàlid
     */
    public boolean validatePawnMove(int fromRow, int fromCol, int toRow, int toCol) {
        char piece = board.getPiece(fromRow, fromCol);
        char destPiece = board.getPiece(toRow, toCol);
        boolean isWhite = Piece.isWhite(piece);
        
        // Direcció del moviment (blanques cap amunt = -1, negres cap avall = +1)
        int direction = isWhite ? -1 : 1;
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);
        
        // Moviment normal (1 casella endavant)
        if (colDiff == 0 && rowDiff == direction && Piece.isEmpty(destPiece)) {
            return true;
        }
        
        // Moviment inicial (2 caselles endavant)
        int startingRow = isWhite ? 6 : 1;
        if (fromRow == startingRow && colDiff == 0 && rowDiff == 2 * direction) {
            // Comprovar que ambdues caselles estan buides
            if (Piece.isEmpty(board.getPiece(fromRow + direction, fromCol)) && 
                Piece.isEmpty(destPiece)) {
                return true;
            }
        }
        
        // Captura diagonal (1 casella en diagonal)
        if (colDiff == 1 && rowDiff == direction && !Piece.isEmpty(destPiece)) {
            // Verificar que és una peça rival
            if (!Piece.isSameColor(piece, destPiece)) {
                return true;
            }
        }
        
        System.out.println("ERROR: Moviment de peó invàlid.");
        return false;
    }
    
    /**
     * Valida el moviment d'una torre.
     * Una torre es mou en línia recta (horitzontal o vertical) sense saltar peces.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment de la torre és vàlid
     */
    public boolean validateRookMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Moviment horitzontal (mateixa fila)
        if (fromRow == toRow) {
            return isPathClear(fromRow, fromCol, toRow, toCol, true);
        }
        // Moviment vertical (mateixa columna)
        else if (fromCol == toCol) {
            return isPathClear(fromRow, fromCol, toRow, toCol, false);
        }
        else {
            System.out.println("ERROR: La torre només es pot moure en línia recta.");
            return false;
        }
    }
    
    /**
     * Valida el moviment d'un cavall.
     * Un cavall es mou en forma de L (2+1 o 1+2) i pot saltar peces.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment del cavall és vàlid
     */
    public boolean validateKnightMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // Moviment en L: (2,1) o (1,2)
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return true;
        } else {
            System.out.println("ERROR: El cavall es mou en forma de L (2+1).");
            return false;
        }
    }
    
    /**
     * Valida el moviment d'un alfil.
     * Un alfil es mou en diagonal sense saltar peces.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment de l'alfil és vàlid
     */
    public boolean validateBishopMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // L'alfil es mou en diagonal (rowDiff == colDiff)
        if (rowDiff != colDiff) {
            System.out.println("ERROR: L'alfil només es pot moure en diagonal.");
            return false;
        }
        
        return isDiagonalPathClear(fromRow, fromCol, toRow, toCol);
    }
    
    /**
     * Valida el moviment d'una reina.
     * Una reina es pot moure com una torre o com un alfil.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment de la reina és vàlid
     */
    public boolean validateQueenMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Moviment en línia recta (com torre)
        if (fromRow == toRow || fromCol == toCol) {
            return validateRookMove(fromRow, fromCol, toRow, toCol);
        }
        // Moviment en diagonal (com alfil)
        else {
            return validateBishopMove(fromRow, fromCol, toRow, toCol);
        }
    }
    
    /**
     * Valida el moviment d'un rei.
     * Un rei es pot moure 1 casella en qualsevol direcció.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el moviment del rei és vàlid
     */
    public boolean validateKingMove(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);
        
        // El rei es pot moure 1 casella en qualsevol direcció
        if (rowDiff <= 1 && colDiff <= 1) {
            return true;
        } else {
            System.out.println("ERROR: El rei només es pot moure 1 casella.");
            return false;
        }
    }
    
    /**
     * Comprova si el camí està lliure en moviment horitzontal o vertical.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @param isHorizontal true si és horitzontal, false si és vertical
     * @return true si el camí està lliure
     */
    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, boolean isHorizontal) {
        if (isHorizontal) {
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);
            
            for (int col = start; col < end; col++) {
                if (!board.isEmpty(fromRow, col)) {
                    System.out.println("ERROR: Hi ha una peça en el camí.");
                    return false;
                }
            }
        } else {
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);
            
            for (int row = start; row < end; row++) {
                if (!board.isEmpty(row, fromCol)) {
                    System.out.println("ERROR: Hi ha una peça en el camí.");
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Comprova si el camí diagonal està lliure.
     * 
     * @param fromRow Fila origen
     * @param fromCol Columna origen
     * @param toRow Fila destí
     * @param toCol Columna destí
     * @return true si el camí diagonal està lliure
     */
    private boolean isDiagonalPathClear(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDirection = (toRow > fromRow) ? 1 : -1;
        int colDirection = (toCol > fromCol) ? 1 : -1;
        
        int currentRow = fromRow + rowDirection;
        int currentCol = fromCol + colDirection;
        
        while (currentRow != toRow && currentCol != toCol) {
            if (!board.isEmpty(currentRow, currentCol)) {
                System.out.println("ERROR: Hi ha una peça en el camí diagonal.");
                return false;
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }
        
        return true;
    }
}