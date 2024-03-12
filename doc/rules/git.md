# Règles d'utilisation de Git

## 1. Les branches

Les branches suivront un format précis :
- **\*-stable** : Ces branches sont "stables", le code dessus a été vérifié, testé et peut fonctionner.
- **\*-unstable** : Ces branches sont "instables", le code dessus est en train d'être travaillé et est séparé de la branche principale.

On pourra ainsi avoir, par exemple:
- master (la branche avec le code stable)
- plateau-unstable: Développement du plateau
- nn-unstable: Développement du réseau neuronal
- 3d-unstable: Développement de la version 3D
- etc...

Lorsqu'une branche de développement est terminée, on peut créer un pull request qui devra être revu par au minimum une personne avant de pouvoir être déployé dans la branche stable.
Cette étape de vérification permettra de s'assurer que le code est compréhensible, documenté et testé correctement.

## 2. Normes de codage des commits

Les commits devront suivre un format précis :
```
Titre

- Info n°1
- Info n°2
- Info n°3
```
OU
```
Titre

Ajouts:
- Info n°1

Changements:
- Info n°1

Retraits:
- Info n°1
```
OU
```
Titre

Explication de la raison du commit
```

Cela permet, entre autre, de pouvoir comprendre rapidement pourquoi ce commit a été fait et qu'est-ce qui a été fait.