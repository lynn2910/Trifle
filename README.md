# TrifleConsole

The final goal is to create an application where we can play at the game Kamisado.

## Why the name of the project

This project code is `Trifle`, here is a little definition:
> Trifle is a layered dessert of English origin. The usual ingredients are a thin layer of sponge fingers or sponge cake soaked in sherry or another fortified wine, a fruit element (fresh or jelly), custard and whipped cream layered in that order in a glass dish. The contents of a trifleConsole are highly variable and many varieties exist, some forgoing fruit entirely and instead using other ingredients, such as chocolate, coffee or vanilla. The fruit and sponge layers may be suspended in fruit-flavored jelly, and these ingredients are usually arranged to produce three or four layers. The assembled dessert can be topped with whipped cream or, more traditionally, syllabub.
> 
> The name trifleConsole was used for a dessert like a fruit fool in the sixteenth century; by the eighteenth century, Hannah Glasse records a recognisably modern trifleConsole, with the inclusion of a gelatin jelly. 

Source: [en.wikipedia.org](https://en.wikipedia.org/wiki/Trifle)

## Project members

The project members are:
- [Cédric Colin](https://github.com/lynn2910)
- [Marvyn Levin](https://github.com/marvynlevin)
- Hugues Estrade
- [Timothée Meyer](https://github.com/Spaceiii)
- [Baptiste Dulieux](https://github.com/baptisteDULIEUX)

## Installation

### Requirements

- Java Development Kit (JDK):
  - **Version:** 17 (or later, unless compatibility issues exist)
- Hardware/Terminal:
  - **TTY:** Pseudo-terminal device (provides basic functionalities for the terminal)
  - **Colored Bash:** Your terminal shall display colors, as elsewhere you will not be able to see the case and pawn colors.
- OS:
  - **Linux** (Tested on Debian 12)
  - **Windows 10/11** (Both tested)
  - **MacOS** (Tested by a third-party)
- Tests:
  - **mockito** (lib for junit)

### Instructions

> ⚠️ The instructions below are only if the game is in the `main` branch.

Clone the game:
```bash
git clone https://gitlab.iut-bm.univ-fcomte.fr/ccolin2/trifleConsole.git
```

Build the game:
```bash
javac -d out src/**/*.java
```

You can choose whether you want to run or not the game with an interactive menu at the start.

Run with the TUI:
```bash
java -cp out trifleConsole.TrifleConsole
```

Run without the TUI:
```bash
java -cp out trifleConsole.TrifleConsole 0
# 0 = Human    vs Human
# 1 = Human    vs Computer
# 2 = Computer vs Computer
```

Run and register the moves done:
```bash
java -cp out trifleConsole.TrifleConsole --output-moves ./move_file.in
# Or
java -cp out trifleConsole.TrifleConsole 0 --output-moves ./move_file.in
```
> **Note:** The TUI lets you configure things such as the bot.s strategy.ies and player name.s, while if you run it without the TUI, default values will be set for you.

> **Note bis:** For JUNIT, you should run with the ide IDEA.

> **Tip:** If you want to slow the bot, add `WAIT_BEFORE_END=5000` before the command (in the env variables), the game will automatically read it and wait for 5s if you or the bot have been faster

## Useful resources

All resources used are stored in the `resources` folder.

Here is a quick summary for each resource:

- `board.png`: The default board of the Kamisado at the start of a game
- `draw_table.py`: A python3 script used to test which board display should be used.
- `kamisado.text`: The ASCII-styled name, here only for storage purposes.
- `run_game.sh`: A bash script which can run a specific number of games, allowing for massive benchmarking along the API (at `stat-api`)
- `profiling`: A folder which contains profiling information from the IDEA profiler, allowing to watch the memory and cpu usage.
- `efficiency_games`: A folder which contains games used to determine the efficiency of the bots.

> **Note:** The game can use some resources which are stored under the following directory: `src/trifleConsole/resources/`
