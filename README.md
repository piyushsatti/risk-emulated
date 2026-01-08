# Risk Emulated

Risk Emulated is a strategy game where players plan moves, manage resources, and compete to capture territories. It focuses on thinking ahead and making smart decisions to win.

## Prerequisites

- Java 21 (see Maven compiler configuration in `WARZONE/pom.xml`)
- Maven

## Build

```bash
cd WARZONE
mvn clean package
```

## Run

```bash
cd WARZONE
mvn exec:java -Dexec.mainClass=controller.GameEngine
```

## Test

```bash
cd WARZONE
mvn test
```

## Repository layout

- `WARZONE/src/main/java`: main application source tree.
  - `controller.GameEngine`: primary game flow entry point and phase manager.
  - `views.TerminalRenderer`: terminal UI rendering helpers.

## Gameplay overview

`GameEngine` runs a loop that moves between the main menu, map editor, and gameplay phases, prompting for input, loading maps, assigning players, and starting the play session based on the current phase.
