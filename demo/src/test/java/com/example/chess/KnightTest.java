package com.example.chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests per validar el moviment del cavall.
 * Cobreix tots els casos mínims requerits:
 * - Moviment en L a casella buida
 * - Moviment en L capturant peça rival
 * - Intent de moure com alfil/torre (invàlid)
 * - Intent de sortir del tauler
 * - Intent de capturar peça pròpia
 * - Verificar que el cavall salta peces
 */
@DisplayName("Tests de validació de moviments del Cavall")
public class KnightTest {
    
    private Board board;
    private MoveValidator validator;
    
    @BeforeEach
    public void setUp() {
        board = new Board();
        validator = new MoveValidator(board);
    }
    
    @Test
    @DisplayName("Test 1: Cavall pot moure's en L a una casella buida")
    public void testKnightMoveToEmptySquare() {
        // Configuració: cavall blanc a b1
        // Acció: moure a c3 (moviment en L: 2 files amunt, 1 columna dreta)
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(7, 1, 5, 2, true);
        assertTrue(result, "El cavall hauria de poder moure's en L a una casella buida");
    }
    
    @Test
    @DisplayName("Test 2: Cavall pot capturar una peça rival movent-se en L")
    public void testKnightCanCaptureEnemyPiece() {
        // Configuració: cavall blanc a e4, peça negra a f6
        board.setPiece(4, 4, 'C'); // Cavall blanc a e4
        board.setPiece(7, 1, '.'); // Netejar posició original
        board.setPiece(2, 5, 'p'); // Peó negre a f6
        
        // Acció: capturar peça negra
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(4, 4, 2, 5, true);
        assertTrue(result, "El cavall hauria de poder capturar una peça rival movent-se en L");
    }
    
    @Test
    @DisplayName("Test 3: Cavall no pot moure's com un alfil o torre (moviment recte)")
    public void testKnightCannotMoveLikeBishopOrRook() {
        // Configuració: cavall blanc a e4
        board.setPiece(4, 4, 'C'); // Cavall blanc a e4
        board.setPiece(7, 1, '.'); // Netejar posició original
        
        // Acció: intentar moure en línia recta a e6 (com torre)
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(4, 4, 2, 4, true);
        assertFalse(result, "El cavall no hauria de poder moure's en línia recta com una torre");
    }
    
    @Test
    @DisplayName("Test 4: Cavall no pot sortir del tauler")
    public void testKnightCannotMoveOutOfBoard() {
        // Configuració: cavall blanc a a1
        board.setPiece(7, 0, 'C'); // Cavall blanc a a1
        board.setPiece(7, 1, '.'); // Netejar cavall original
        
        // Acció: intentar moure fora del tauler (posició invàlida)
        // Resultat esperat: moviment invàlid
        
        // Intent de moure a una posició que estaria fora (seria col -1)
        // Com que validateMove comprova límits, provem amb coordenades invàlides
        boolean result = validator.validateMove(7, 0, 8, 2, true);
        assertFalse(result, "El cavall no hauria de poder sortir del tauler");
    }
    
    @Test
    @DisplayName("Test 5: Cavall no pot capturar una peça pròpia")
    public void testKnightCannotCaptureOwnPiece() {
        // Configuració: cavall blanc a b1, peó blanc a c3
        board.setPiece(5, 2, 'P'); // Peó blanc a c3
        
        // Acció: intentar capturar peça blanca
        // Resultat esperat: moviment invàlid
        
        boolean result = validator.validateMove(7, 1, 5, 2, true);
        assertFalse(result, "El cavall no hauria de poder capturar una peça del mateix color");
    }
    
    @Test
    @DisplayName("Test 6: Cavall pot saltar peces (obstacles no afecten)")
    public void testKnightCanJumpOverPieces() {
        // Configuració: cavall blanc a b1, peons blancs bloquejant el camí
        // Col·locar peons en posicions intermèdies
        board.setPiece(6, 1, 'P'); // b2
        board.setPiece(6, 2, 'P'); // c2
        
        // Acció: moure de b1 a c3 saltant les peces
        // Resultat esperat: moviment vàlid (el cavall salta)
        
        boolean result = validator.validateMove(7, 1, 5, 2, true);
        assertTrue(result, "El cavall hauria de poder saltar peces");
    }
    
    @Test
    @DisplayName("Test Extra: Cavall pot fer tots els 8 moviments en L possibles")
    public void testKnightAllEightPossibleMoves() {
        // Configuració: cavall blanc a e4 (centre del tauler)
        board.setPiece(4, 4, 'C'); // Cavall blanc a e4
        board.setPiece(7, 1, '.'); // Netejar posició original
        board.setPiece(7, 6, '.'); // Netejar altre cavall
        
        // Els 8 moviments possibles del cavall des d'e4:
        // f6, g5, g3, f2, d2, c3, c5, d6
        
        assertTrue(validator.validateMove(4, 4, 2, 5, true), "e4 -> f6");
        assertTrue(validator.validateMove(4, 4, 3, 6, true), "e4 -> g5");
        assertTrue(validator.validateMove(4, 4, 5, 6, true), "e4 -> g3");
        assertTrue(validator.validateMove(4, 4, 6, 5, true), "e4 -> f2");
        assertTrue(validator.validateMove(4, 4, 6, 3, true), "e4 -> d2");
        assertTrue(validator.validateMove(4, 4, 5, 2, true), "e4 -> c3");
        assertTrue(validator.validateMove(4, 4, 3, 2, true), "e4 -> c5");
        assertTrue(validator.validateMove(4, 4, 2, 3, true), "e4 -> d6");
    }
    
    @Test
    @DisplayName("Test Extra: Cavall negre també pot moure's correctament")
    public void testBlackKnightCanMove() {
        // Configuració: cavall negre a b8
        // Acció: moure a c6
        // Resultat esperat: moviment vàlid
        
        boolean result = validator.validateMove(0, 1, 2, 2, false);
        assertTrue(result, "El cavall negre hauria de poder moure's en L");
    }
}