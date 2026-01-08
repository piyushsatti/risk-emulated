# Maps

## Map storage

Map files live in `src/main/resources/maps/` (the value of `GameEngine.MAPS_FOLDER`).
When the map editor asks for a filename, it expects a `.map` file from that folder.

## Map editor prompts and command keywords

The map editor shows the following prompts in the terminal:

```
Please Enter a valid .map filename from folder:	
src/main/resources/maps/
```

```
Please Enter a valid command:	
Super Commands: savemap, laodmap, editmap, showmap
Map Edit Commands: editcountry, editneighbor, editcontinent
Type 'exit' to quit map editing.
```

> **Note:** The prompt lists `laodmap` (spelled that way) in the command summary.

## Terminal examples

Load or edit a map after supplying a valid `.map` filename:

```
loadmap world.map
editmap world.map
showmap
savemap world_updated.map
```

Make map edits using the edit commands and their `-add`/`-remove` options:

```
editcontinent -add Asia 5
editcountry -add India Asia
editneighbor -add India China
editneighbor -remove India China
editcountry -remove India
editcontinent -remove Asia
```
