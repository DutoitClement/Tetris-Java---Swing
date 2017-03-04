package com.supinfo;

import java.io.IOException;

public class Main {  // Methode principale du programme, instancie les objets

    public static void main(String[] args)throws IOException
    {
        /* On instancie un objet grille de la classe Grille, on remplit les cases selon l'algorithme,
         et on chache un nombre de cases définit par le niveau de difficulté*/

        Grille grille = new Grille();
        grille.createBoard(grille.getCases(), 0, grille);
        grille.hideNumbers();

        // On crée un nouvel objet screen de la classe GUI (JFrame), affiche la grille en interface graphique
        GUI screen = new GUI();
        screen.createGUI(grille);
    }
}
