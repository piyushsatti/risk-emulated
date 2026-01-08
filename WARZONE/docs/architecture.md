# Architecture Overview

## Game phases and orchestration (`controller.GameEngine`)

`GameEngine` owns the top-level state machine for the application. It defines the `GAME_PHASES` enum
and holds shared state such as the current phase, loaded map, and player roster. The `main` loop
repeatedly calls `startingMenu`, which selects between the map editor and gameplay flows. Within
those flows, `mapEditor` and `playerLoop` manage interactive command processing, switching phases
as needed and delegating map commands to `CommandValidator` and map loading to `MapInterface`.
When the player chooses to assign countries, the engine validates the map and player roster before
handing control to `PlayGame.startGame`.

## Terminal UI responsibilities (`views.TerminalRenderer`)

`TerminalRenderer` contains the terminal-facing UI utilities: rendering the welcome/exit banners,
menus, prompts, errors, and map output. It is intentionally stateless, pulling the current map and
player list from `GameEngine` to format views such as `showMap` and `showCurrentGameMap`.
For interactive prompts (map editor commands, order entry), it reads from `System.in` and returns
user input to the controller layer.

## Core domain model role (`models.Player`)

`Player` represents a player in the core domain: identifiers, display name, reinforcements, assigned
countries, and an order queue. It encapsulates gameplay-specific behavior such as issuing orders,
validating deployments, and exposing helper accessors. The class bridges gameplay and UI by using
`TerminalRenderer` for prompts/messages and `GameEngine` to resolve map state when creating orders.

## Dependency outline (controller ↔ views ↔ models)

* **Controller → Views:** `GameEngine` and helpers like `MapInterface` call `TerminalRenderer` to
  render menus, prompts, messages, and maps.
* **Controller → Models:** `GameEngine` owns shared model state (`WorldMap`, `Player` list) and
  `PlayGame` manipulates players and map data.
* **Views → Models/Controller:** `TerminalRenderer` imports `GameEngine`, `Player`, and map models
  to format the current state for display.
* **Models → Controller/Views:** `Player` imports `GameEngine` for map lookups and `TerminalRenderer`
  for interactive order entry and messaging.

## Key entry points

* **`GameEngine.main`**: primary application loop and the starting point for phase orchestration.
