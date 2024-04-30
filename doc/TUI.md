# Tui

<!-- TOC -->
* [Tui](#tui)
  * [Objective](#objective)
  * [Api](#api)
  * [Different parts of this TUI](#different-parts-of-this-tui)
    * [Define game mode](#define-game-mode)
    * [Define the player mode (human or computer)](#define-the-player-mode-human-or-computer)
    * [Define the name of the player(s)](#define-the-name-of-the-players)
    * [Define bot strategy(ies)](#define-bot-strategyies)
    * [Display current configuration](#display-current-configuration)
    * [Start the game](#start-the-game)
<!-- TOC -->

> A TUI (Text User Interface, Textual User Interface or Terminal User Interface) is a type of user interface that uses only text to communicate with the user.


## Objective

Be able to allow the user to choose between various options, enter values, etc...

It must be a complete interface in itself.

## Api

| Method         | Signature                   | Description                                                |
|----------------|-----------------------------|------------------------------------------------------------|
| reset          | `() -> ()`                  | Reset all values registered before.                        |
| run            | `() -> ()`                  | Run the logic of the TUI. Will exit when the user want to. |
| getPlayerMode  | `() -> PlayerMode \| null ` |                                                            |
| getGameMode    | `() -> GameMode  \| null`   | Return the game mode defined, if any.                      |
| getPlayerNames | `() -> List<String>`        |                                                            |

_Internal (private) methods should not be used and values must not be overwritten._

## Different parts of this TUI

### Define game mode

This section will let the user select the game mode based on the enumerator `GameMode.java` at `trifle.rules.GameMode`.

There are three options:
- **a.** Fast
- **b.** Standard
- **c.** Marathon

The user can change this value at will each time, as this doesn't impact any other values.

If the user doesn't provide a valid choice,
the program will say `Your selection is invalid.` and ask again for the choice.

The user can exit with the `exit` word in the prompt.

When a valid choice is selected, the program will say `Game mode selected: XXX` and exit this menu.


### Define the player mode (human or computer)

This section will let the user choose between three possibilities:
- **a.** Player vs Player
- **b.** Player vs Computer
- **c.** Computer vs Computer

_These values are based on the enumerator `PlayerMode` at `trifle.rules.PlayerMode`_

Because the number of players may change, all player names registered will be cleared.

If the user doesn't provide a valid choice,
the program will say `Your selection is invalid.` and ask again for the choice.

The user can exit with the `exit` word in the prompt.

When a valid choice is selected, the program will say `Player mode selected: XXX vs XXX` and exit this menu.

### Define the name of the player(s)

This section will let the user define the names of the player.

One player will be required for the player mode `Player vs Computer`,
two for `Player vs Player` and none for `Computer vs Computer`

In case that no players are required,
the program will say `No human player is required for this player mode.` and exit this submenu.

For each player:

> The program will say `Player configuration no.N`, then ask the name of this player.
> 
> Finally, the program will ask if this is right by asking `Player name no.1 will be 'Cédric',  are you sure? (Y/n) `.

At the end, the program will say `All the players have been registered.`.

The user can exit by typing `exit`. All players already registered will be kept.

### Define bot strategy(ies)

This section will let the user choose between two possibilities for each bot strategy:
- **a.** Project 'BanoffeePie'
- **b.** Second Project

_The description is below each choice, here is an example:_
```
(a). Project 'BanoffeePie'
    MinMax algorithm with neural network
(b). Second algorithm
    Work In Progress
```

_These values are based on the enumerator `BotStrategy` at `trifle.rules.BotStrategy`_

All strategies already registered will be removed.

In case that no players are required,
the program will say `Mode XXX does not involve any bot, so there is no strategy to define.` and exit this submenu.

For each player, the program will ask the choice for this bot and register it accordingly.

At the end, the program will say `All bot strategies have been defined.`.

The user can exit by typing `exit`. All players already registered will be kept.

### Display current configuration

Here is an example of what is expected:
```
Current configuration:
  Game mode:          Fast
  Mode de joueur:     Player vs Computer
  Registered players: Lynn
  Bots strategies:    Computer <Project 'BanoffeePie'>
```

### Start the game

Before launching the game, the program will check if the player defined what he wants.

Here is an example:
```
Some elements need to be configured:
- Game mode (fast, standard, marathon)
- Player mode (Human vs Human, Human vs Computer, Computer vs Computer)
```

Each point is added or not based on the values registered.

> **Note:** Things such as the player names or the bot strategies have default values and can be kept undefined by the user.
