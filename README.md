# TrifleConsole

The final goal is to create an application where we can play at the game Kamisado.

## Why the name of the project

This project code is `Trifle`, here is a little definition:
> Trifle is a layered dessert of English origin. The usual ingredients are a thin layer of sponge fingers or sponge cake soaked in sherry or another fortified wine, a fruit element (fresh or jelly), custard and whipped cream layered in that order in a glass dish. The contents of a trifle are highly variable and many varieties exist, some forgoing fruit entirely and instead using other ingredients, such as chocolate, coffee or vanilla. The fruit and sponge layers may be suspended in fruit-flavored jelly, and these ingredients are usually arranged to produce three or four layers. The assembled dessert can be topped with whipped cream or, more traditionally, syllabub.
> 
> The name trifle was used for a dessert like a fruit fool in the sixteenth century; by the eighteenth century, Hannah Glasse records a recognisably modern trifle, with the inclusion of a gelatin jelly. 

Source: [en.wikipedia.org](https://en.wikipedia.org/wiki/Trifle)

## Project members

The project members are:
- Cédric Colin
- Marvyn Levin

There are also three other members that didn't want to participate at all,
their name will be listed below as long as they aren't involved in the project:
- Hugues Estrade
- Timothée Meyer
- Baptiste Dulieux

## Installation

> ⚠️ The instructions below are only if the game is in the `main` branch.

### Requirements

#### Software
- Java Development Kit (JDK):
  - **Version:** 17 (or later, unless compatibility issues exist)
- Hardware/Terminal:
  - **TTY:** Pseudo-terminal device (provides basic functionalities for the terminal)
  - **Colored Bash:** Your terminal shall display colors, as elsewhere you will not be able to see the case and pawn colors.
- OS:
  - **Linux** (Tested on Debian 12)
  - **Windows 10/11** (Respectfully tested)
  - **MacOS** (Tested by a third-party)
### Instructions

Clone the game:
```bash
https://gitlab.iut-bm.univ-fcomte.fr/ccolin2/trifle.git
```

Build the game:
```bash
javac -d out src/**/*.java
```

You can choose whether you want to run or not the game with an interactive menu at the start.

Run with the TUI:
```bash
java -cp out trifle.TrifleConsole
```

Run without the TUI:
```bash
java -cp out trifle.TrifleConsole 0
```

> **Note:** The TUI lets you configure things such as the bot.s strategy.ies and player name.s, while if you run it without the TUI, default values will be set for you.

## Useful resources

All resources used are stored in the `resources` folder.

Here is a quick summary for each resource:

- `board.png`: The default board of the Kamisado at the start of a game
- `draw_table.py`: A python3 script used to test which board display should be used.
- `kamisado.text`: The ASCII-styled name, here only for storage purposes.

> **Note:** The game require some resources which are stored under the following directory: `src/trifle/resources/`
