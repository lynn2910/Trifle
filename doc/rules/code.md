# Conventions de programmation

## 1. Noms des class et packages

La base de code sera très importante, alors l'utilisation de packages sera requise.

### Très important

Le code doit être modulaire et se base sur une high-end API.

> Une API haut de gamme en Java se caractérise par sa conception bien pensée, sa robustesse, ses performances, son extensibilité et sa facilité de maintenance.
> 
> Elle est facile à utiliser et à comprendre, avec une documentation claire et concise. Elle est stable et exempte de bogues, gère les erreurs de manière prévisible et offre des options pour optimiser les performances. Elle est conçue pour être extensible et permet d'ajouter facilement de nouvelles fonctionnalités. Le code est propre, bien organisé et simple à comprendre.

Par exemple :

```java
class Bot extends Player {
    public static final int NN_ID = 0;
    public static final int DECISION_TREE_ID = 1;
    
    private int strategyId;
    
    public Bot(String name, int strategyId) throws IllegalArgumentException {
        if (strategyId != NN_ID && strategyId != DECISION_TREE_ID)
            throw new IllegalArgumentException("Invalid strategy id");
        
        super(name);
        this.strategyId = strategyId;
    }
    
    public int getStrategy(){
        return this.strategyId;
    }
    
    public void setStrategy(int new_strategy){
        if (new_strategy != NN_ID && new_strategy != DECISION_TREE_ID)
            throw new IllegalArgumentException("Invalid strategy id");
        
        this.strategyId = new_strategy;
    }
}
```
*La documentation est omise.*

Un code tel que celui-ci pourra facilement être amélioré et changé, car les autres class doivent passer par les méthodes de `Bot` pour accéder/modifier à certaines variables.

### 1.1 Packages

Les packages doivent avoir un nom explicite.

Par exemple, pour un réseau neuronal, on mettra `NeuralNetwork`,
Alors que pour les joueurs, on mettrait `PlayerFactory`. 

### 1.2 Nom des class

Les noms de class sont en CamelCase, doivent compte entre un et 5 mots **MAXIMUM**.

Par exemple :
- `Neuron`
- `PlayerHistory`
- `Pawn`
- `3dContext`
- etc...

## 2. Langues utilisées

La langue utilisée dans le code sera l'anglais, en accord avec la langue demandée dans le rapport, mais aussi par convention avec les normes internationales de documentation dans la programmation.

## 3. Documentation

### 3.1 Documentation technique

La documentation **technique**, tel que comment fonctionne un algorithme, sera située dans des fichiers Markdown tel que ce fichier.

Cette documentation sera localisée dans le dossier `/doc/` à la racine du projet.

### 3.2 Documentation "inline"

Cette documentation sera directement dans le code.

On peut le retrouver dans des méthodes, mais aussi dans le code directement.

Exemple :
```java
/**
 * Represent a player.
 * Contain the coordinates of the player and his name.
 */
class Player {
    private Coordinate coordinates;
    // The name is protected so that we cannot change it without the associated method
    public protected String name;

    /**
     * Get the coordinates of the current player.
     * @return the coordinates
     */
    public Coordinate getCoordinates(){
        return this.coordinates;
    }

    /**
     * Set new coordinates for the current player
     * 
     * @param new_coordinates The new coordinates
     * @return The old coordinates if any
     */
    public Coordinate setCoordinates(Coordinate new_coordinates) {
        Coordinate old_coordinates = this.coordinates;
        
        this.coordinates = new_coordinates;
        return old_coordinates;
    }
}
```

Un code de ce type permettra, sur le long terme, de rendre le développement plus productif, en comprenant immédiatement ce que fait la méthode.



## 4. Nommage des variables

### 4.1 Constantes

Les constantes sont définies tel que :
```java
public static final String NAME = "Yeet";
```

Une constante doit être finale et son nom est en majuscule.

### 4.2 Variables 

Les variables utilisent le nommage en camelCase, similaire aux class et packages.

Par exemple :
- `playerScore`
- `pawnCoordinates`
- `computedNeuralNetworkWeightSum`
- etc...

Cela permet de rendre les variables plus faciles à comprendre et à utiliser. Cela devient aussi très vite intuitif de les utiliser.