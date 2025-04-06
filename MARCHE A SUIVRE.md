# Marche à suivre

## Affichage

1er Affichage : Prenom + nom + userId + demander pswd  --> bouton se connecter --> Si correct : SUIVANT // Si pas correct msg erreur + refresh la page.

2ème Affichage : Liste des commandes de la journée (les non cloturée) + bouton créer commande (SOUTH) + bouton modifier commande (Une seule instance) + bouton valider commande (Une seule instance) + radio pour chaque commande

- Bouton se connecter / se deconnecter
- Orders management :
    * Ajout bouton dans le menu (chercher liste des commandes non cloturées).

- paymentMethod management :
    * 3 instances (Carte, cash, pas payé)
 

 ## CORRECTION

Barre de menu : Accueil - Commande - Produits - DB

Commande >>>> Liste des commandes  >>Quand sélectionné>> BOUTONS modifier/Clôturer : 
                                                                                      >>modifier>> liste des orderLines + Bouton Ajouter orderLine + >>Quand sélectionné>> Bouton + && -
                                                                                      >>cloturer>> setEnabledFalse + reafficher panel
                                    >>Creer Commande>>

Produits IDEM


DB : ????


# À demander à la prof :
- Changer le fait de payer plus tard à payer directement après chaque commande


# À faire :
- Les boutons supprimer et modifier ne seront accessibles que quand une commande est sélectionnée
- Bouton supprimer --> petite pop up "Vouslez vous supprimer" --> Si oui, remove de la liste des commandes
- Bouton modifier

- Pour la JList du panel :"
   * Faire un toString de la commande --> Commande + id + prixTotal de la commande euros + "prise par " + nomUser + "le" + date  
