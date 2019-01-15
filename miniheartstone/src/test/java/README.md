# Tests statiques

Pour tester notre programme, nous utilisons checkstyle & PMD. Les rapports de ces derniers peuvent être générés en utilisant
les commandes suivantes :

```
mvn site
```
```
mvn pmd:pmd -DtargetJdk=1.7   pour générer le pmd.html et   mvn checkstyle:checkstyle   pour générer le checkstyle.html
```
De nombreuses erreurs ont été corrigées, mais par soucis de manque de temps, tout ne l'a pas été. De la même manière, nous n'avons
pas réussi à retirer certaines règles (ou à les modifier) comme l'erreur des 80 caractères maximum par ligne. 

Ces rapports sont disponibles dans le dossier target/site/(checkstyle.html or pmd.html).


# Tests unitaires et mocks

Ici, quelques méthodes présentes dans certaines classes de notre moteur ont été testées. Cependant, tout n'a pas été testé pour les
raisons listées ci-dessous :

  * Besoin de créé des mock (ceci a été fait lors du test de la classe AbstractHero)
  * Nombre de cas à tester énorme (certaines méthodes avaient une complexité cyclomatique de 99!)
  * Méthode inutile à tester (getter/setter, ...)
  
  
  



