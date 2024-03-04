# Script d'execution

Ce script, disponible sous le fichier `run.sh` à la racine du dépôt, permet de compiler et executer des projets Java complexes.

## Compilation

La compilation requiert une class, le chemin de travail (non-obligatoire) et l'argument `-b` ou `--build`.

Pour compiler le `framework-example`:
```shell
./run.sh -p framework-example/src -c HoleConsole -b
```
OU
```shell
./run.sh --path framework-example/src --class HoleConsole --build
```

De la même façon, build le code dans le dossier `src` se fait de cette manière:
```shell
./run.sh  -c HoleConsole -b
```
OU
```shell
./run.sh --class HoleConsole --build
```

## Execution

Pour executer un programme Java, il suffit de reprendre la commande de compilation et d'ajouter l'argument `-r` ou `--run`.

Veuillez noter qu'on peut executer le code sans le compiler (si toutefois il a été compilé au moins une fois) ou bien le compiler sans l'executer. Nous pouvons également faire les deux.

### Passer des arguments pendant l'execution

Pour passer des arguments pendant l'execution, utilisez cette syntaxe :
```shell
./run.sh  -c HoleConsole -b -r -- arg1 arg2 arg3
```
OU
```shell
./run.sh --class HoleConsole --build --run -- arg1 arg2 arg3
```

## Bonus

Vous pouvez activer les messages de logs en ajoutant l'argument `-v` ou `--verbose` à la commande. (avant les arguments d'execution)