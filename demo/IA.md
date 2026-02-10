# Traçabilitat de l'ús d'Intel·ligència Artificial

Aquest document descriu com s'ha utilitzat la Intel·ligència Artificial durant el desenvolupament del projecte PE07 - Joc d'Escacs.

## Resum

S'ha utilitzat IA (Claude) per:
- Refactoritzar el codi inicial en una arquitectura modular
- Generar esquelets de tests JUnit
- Redactar documentació Javadoc
- Crear el diagrama UML de seqüència

## Prompts utilitzats i canvis realitzats

### 1. Refactorització del codi original

**Prompt utilitzat:**
```
Refactoritza el codi d'escacs en 4 mòduls separats:
- Board: gestió del tauler
- Piece: representació de peces
- MoveValidator: validació de moviments
- UIConsole: entrada/sortida
- GameController: control del joc

Aplica clean code: noms descriptius, funcions petites, constants, DRY.
```

**Canvis aplicats:**
- **Abans**: Tot el codi en una sola classe `escacs.java` de 512 línies
- **Després**: 5 classes separades amb responsabilitats clares
- **Motivació**: Millora la mantenibilitat, facilita els tests, i segueix els principis SOLID
- **Modificacions manuals**: 
  - Ajustos en la coordinació entre classes
  - Millora de missatges d'error per ser més específics
  - Afegir constants per valors màgics (WHITE_PAWN_START_ROW, etc.)

### 2. Generació de tests automatitzats

**Prompt utilitzat:**
```
Genera tests JUnit 5 per validar:
- Peó: mínim 6 tests (moviment 1 casella, 2 caselles inici, bloqueig, captura diagonal, etc.)
- Cavall: mínim 6 tests (moviment L, captura, salt de peces, límits tauler, etc.)

Cada test ha de tenir:
- Nom descriptiu amb @DisplayName
- Comentaris explicant el cas
- Assertions clares amb missatges personalitzats
```

**Canvis aplicats:**
- **Generats**: 20 tests en total (10 per Peó, 10 per Cavall)
- **Modificacions manuals**:
  - Afegir casos addicionals no generats per IA:
    - Test 10 de PawnTest: Peó no pot avançar 2 fora d'inici
    - Test 9 de KnightTest: Moviments des de cantonada
  - Ajustar assertions per usar els mètodes reals del `MoveValidator`
  - Millorar els missatges d'error esperats en els tests

**Resultats d'execució:**
```bash
mvn test
# Tots els 20 tests passen correctament ✓
```

### 3. Documentació Javadoc

**Prompt utilitzat:**
```
Afegeix Javadoc complet a tots els mètodes públics:
- Descripció del què fa
- @param per cada paràmetre
- @return si retorna valor
- Mencionar errors o condicions especials
```

**Canvis aplicats:**
- **Generats**: Javadoc per tots els mètodes públics de les 5 classes
- **Modificacions manuals**:
  - Ampliar descripcions per fer-les més clares
  - Afegir exemples concrets en alguns mètodes (ex: `parseCoordinate`)
  - Corregir algunes descripcions de paràmetres per ser més precises

**Exemple de millora manual:**

```java
// Generat per IA:
/**
 * Converteix coordenades.
 */

// Millorat manualment:
/**
 * Converteix una coordenada d'escacs (ex: "e2") a índexs d'array.
 * 
 * @param coord Coordenada en format d'escacs (lletra a-h + número 1-8)
 * @return Array amb [fila, columna] (índexs 0-7) o null si invàlid
 */
```

### 4. Diagrama UML

**Prompt utilitzat:**
```
Crea un diagrama de seqüència UML (PlantUML) per l'escenari:
"Validació d'un moviment il·legal"

Ha d'incloure:
- Participants: UIConsole, GameController, MoveValidator, Board
- Flux: parseig → validació → retorn error → reintent
- Mostrar els diferents tipus d'error possibles
```

**Canvis aplicats:**
- **Generat**: Fitxer PlantUML amb estructura base
- **Modificacions manuals**:
  - Afegir blocs [alt] per mostrar diferents camins d'error
  - Millorar etiquetes de les fletxes amb paràmetres reals
  - Afegir notes explicatives sobre "NO modifica tauler"
  - Crear versió PNG amb Python quan PlantUML no estava disponible

## Verificació i validació

### Proves realitzades

1. **Compilació**: `mvn clean compile` ✓
2. **Tests**: `mvn test` - 20/20 tests passen ✓
3. **Execució**: Joc funciona correctament amb validacions ✓
4. **Documentació**: Javadoc genera HTML sense errors ✓

### Comprensió del codi

L'estudiant ha revisat i comprèn:
- ✅ L'arquitectura modular i per què s'ha escollit
- ✅ Com funciona la validació de cada tipus de peça
- ✅ El flux de processament d'un moviment
- ✅ Com s'executen i validen els tests

## Conclusions

L'ús d'IA ha estat útil per:
- **Accelerar** la refactorització inicial
- **Generar** una base sòlida de tests
- **Estalviar temps** en documentació repetitiva

Però ha calgut:
- **Revisar** tot el codi generat
- **Modificar** assertions i casos de test
- **Afegir** lògica addicional no contemplada per IA
- **Validar** que tot funciona correctament

**Temps estimat:**
- Sense IA: ~12 hores
- Amb IA + revisions: ~6 hores
- Estalvi: ~50% del temps, amb comprensió completa del codi final

---

**Data de revisió**: 08/02/2026