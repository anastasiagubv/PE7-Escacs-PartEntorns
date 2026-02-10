# 🏆 Joc d'Escacs - Chess Game

Projecte de joc d'escacs per consola desenvolupat en Java amb proves unitàries JUnit.

## 📁 Estructura del Projecte

```
PE07_Escacs/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── chess/
│   │                   ├── Board.java           # Gestió del tauler
│   │                   ├── Piece.java           # Representació de peces
│   │                   ├── MoveValidator.java   # Validació de moviments
│   │                   ├── UIConsole.java       # Interfície d'usuari
│   │                   └── GameController.java  # Controlador principal
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── chess/
│                       ├── PawnTest.java        # Tests del peó
│                       └── KnightTest.java      # Tests del cavall
├── docs/
│   └── diagrama_moviment_illegal.png            # Diagrama UML
├── pom.xml                                       # Configuració Maven
└── README.md                                     # Aquest fitxer
```

## 🚀 Com Executar el Joc

### Prerequisits
- Java 11 o superior
- Maven 3.6 o superior

### Compilar i executar

```bash
# Compilar el projecte
mvn compile

# Executar el joc
mvn exec:java

# O alternativament, compilar i executar manualment
mvn package
java -cp target/chess-game-1.0-SNAPSHOT.jar com.example.chess.GameController
```

### Instruccions de joc
1. Introdueix els noms dels dos jugadors
2. El jugador amb blanques comença
3. Introdueix moviments en format: `e2 e4` (origen destí)
4. Escriu `Abandonar` per abandonar la partida
5. Al final de cada partida, pots jugar de nou amb els mateixos o altres jugadors

## 🧪 Com Executar els Tests

```bash
# Executar tots els tests
mvn test

# Executar tests amb informació detallada
mvn test -X

# Executar només tests d'una classe específica
mvn test -Dtest=PawnTest
mvn test -Dtest=KnightTest
```

### Cobertura de Tests
- **PawnTest**: 6+ tests que validen moviments del peó
  - Moviment 1 casella endavant
  - Moviment 2 caselles des de posició inicial
  - Bloqueig per peça davant
  - Captura diagonal
  - Moviment enrere (invàlid)
  
- **KnightTest**: 6+ tests que validen moviments del cavall
  - Moviment en L vàlid
  - Captura de peça rival
  - Moviments invàlids (com torre/alfil)
  - Límits del tauler
  - Captura de peça pròpia (invàlid)
  - Salt sobre peces

## 🎯 Decisions Importants de Disseny

### 1. Representació del Tauler
- **Implementació**: Array bidimensional `char[8][8]`
- **Peces blanques**: Majúscules (P, T, C, A, Q, K)
- **Peces negres**: Minúscules (p, t, c, a, q, k)
- **Caselles buides**: Punt `.`
- **Motivació**: Permet identificar ràpidament el color i tipus de peça

### 2. Validació de Moviments
- **Arquitectura**: Classe `MoveValidator` independent
- **Flux de validació**:
  1. Validació de format (parseig)
  2. Validació de límits del tauler
  3. Validació de peça present
  4. Validació de color correcte
  5. Validació específica per tipus de peça
- **Missatges d'error**: Específics per cada tipus d'error (no genèrics)

### 3. Separació de Responsabilitats
- **Board**: Només gestiona l'estat del tauler
- **MoveValidator**: Només valida moviments
- **UIConsole**: Només gestiona entrada/sortida
- **GameController**: Coordina el flux del joc
- **Piece**: Utilitats per identificar peces

### 4. Gestió d'Errors
- Validació en múltiples capes (UI → Controller → Validator)
- Missatges d'error clars i específics
- Mai es modifica el tauler si el moviment és invàlid
- El jugador pot reintentar després d'un error

## 📝 Regles Implementades

### Peces Implementades
- ✅ Peó (moviment endavant, captura diagonal, moviment inicial de 2)
- ✅ Torre (moviment horitzontal i vertical)
- ✅ Cavall (moviment en L, salta peces)
- ✅ Alfil (moviment diagonal)
- ✅ Reina (combinació torre + alfil)
- ✅ Rei (1 casella en qualsevol direcció)

### Regles No Implementades
- ❌ Enroc
- ❌ Captura al pas
- ❌ Promoció de peó
- ❌ Escac i escac mat
- ❌ Taules

## 👥 Autors

- **Nom de l'estudiant**: [El teu nom]
- **Assignatura**: Entorns de Desenvolupament (MP0487)
- **Curs**: DAW1-DAM1
- **Data**: Gener 2026

## 📄 Llicència

Aquest projecte s'ha desenvolupat amb finalitats educatives.