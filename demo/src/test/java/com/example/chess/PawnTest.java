package com.example.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests per validar el moviment del peó.
 * Cobreix tots els casos mínims requerits:
 * - Moviment 1 casella endavant
 * - Moviment 2 caselles des de posició inicial
 * - Intent d'avançar amb peça davant
 * - Captura diagonal vàlida
 * - Intent de captura diagonal sense peça rival
 * - Intent de moure enrere
 */
@DisplayName("Tests de validació de moviments del Peó")
public class PawnTest {
    
    private Board board;
    private MoveValidator validator;
    
    @BeforeEach
    public void setUp() {
        board = new Board();
        validator = new MoveValidator(board);
    }
    
    @Test
    @DisplayName("Test 1: Peó blanc pot avançar 1 casella endavant si està buida")
    public void testPawnMoveOneSquareForward() {
        // Configuració: peó blanc a e2
        // Acció: moure a e3
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(6, 4, 5, 4, true);
        assertTrue(result, "El peó hauria de poder avançar 1 casella endavant");
    }
    
    @Test
    @DisplayName("Test 2: Peó blanc pot avançar 2 caselles des de posició inicial amb camí lliure")
    public void testPawnMoveTwoSquaresFromStart() {
        // Configuració: peó blanc a e2 (posició inicial)
        // Acció: moure a e4
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(6, 4, 4, 4, true);
        assertTrue(result, "El peó hauria de poder avançar 2 caselles des de la posició inicial");
    }
    
    @Test
    @DisplayName("Test 3: Peó no pot avançar si hi ha una peça davant")
    public void testPawnCannotMoveWithPieceInFront() {
        // Configuració: peó blanc a e2, col·locar peça negra a e3
        board.setPiece(5, 4, 'p');
        
        // Acció: intentar moure a e3
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(6, 4, 5, 4, true);
        assertFalse(result, "El peó no hauria de poder avançar si hi ha una peça davant");
    }
    
    @Test
    @DisplayName("Test 4: Peó pot capturar en diagonal una peça rival")
    public void testPawnCanCaptureDiagonally() {
        // Configuració: peó blanc a e4, peça negra a d5
        board.setPiece(4, 4, 'P'); // Peó blanc a e4
        board.setPiece(6, 4, '.'); // Netejar posició original
        board.setPiece(3, 3, 'p'); // Peça negra a d5
        
        // Acció: capturar diagonalment
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(4, 4, 3, 3, true);
        assertTrue(result, "El peó hauria de poder capturar en diagonal una peça rival");
    }
    
    @Test
    @DisplayName("Test 5: Peó no pot capturar en diagonal si no hi ha peça rival")
    public void testPawnCannotCaptureDiagonallyEmpty() {
        // Configuració: peó blanc a e4, casella d5 buida
        board.setPiece(4, 4, 'P'); // Peó blanc a e4
        board.setPiece(6, 4, '.'); // Netejar posició original
        
        // Acció: intentar capturar diagonalment casella buida
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(4, 4, 3, 3, true);
        assertFalse(result, "El peó no hauria de poder moure's en diagonal si la casella està buida");
    }
    
    @Test
    @DisplayName("Test 6: Peó no pot moure enrere")
    public void testPawnCannotMoveBackward() {
        // Configuració: peó blanc a e4
        board.setPiece(4, 4, 'P'); // Peó blanc a e4
        board.setPiece(6, 4, '.'); // Netejar posició original
        
        // Acció: intentar moure enrere a e3
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(4, 4, 5, 4, true);
        assertFalse(result, "El peó no hauria de poder moure enrere");
    }
    
    @Test
    @DisplayName("Test Extra: Peó negre pot avançar 1 casella endavant")
    public void testBlackPawnMoveOneSquareForward() {
        // Configuració: peó negre a e7
        // Acció: moure a e6
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(1, 4, 2, 4, false);
        assertTrue(result, "El peó negre hauria de poder avançar 1 casella endavant");
    }
    
    @Test
    @DisplayName("Test Extra: Peó no pot moure 2 caselles si no està a posició inicial")
    public void testPawnCannotMoveTwoSquaresNotFromStart() {
        // Configuració: moure peó blanc de e2 a e3 primer
        board.movePiece(6, 4, 5, 4);
        
        // Acció: intentar moure 2 caselles des d'e3 a e5
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(5, 4, 3, 4, true);
        assertFalse(result, "El peó no hauria de poder moure 2 caselles si no està a la posició inicial");
    }
}