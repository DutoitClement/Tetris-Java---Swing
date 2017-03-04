package com.supinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.RandomAccess;

/**
 * Created by cleme on 2/7/2017.
 */

public class Grille { // Classe contenant la grille et ses méthodes

    private int cases[][] = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};

    private int grilleVictoire[][] = new int[9][9];

    private boolean victory = false;

    private String difficulty;

    public Grille()
    {
        this.difficulty = "Easy";
    }

    public int[][] getCases()
    {
        return cases;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public void setCases(int[][] pCases)
    {
        cases = pCases;
    }

    public boolean getVictory(){return victory;}

    public void setGrilleVictoire(int[][] pCases) /* Setter de GrilleVictoire, il copie le contenue de la grille
    avant qu'on lui retire des nombres, elle sera utilisée pour vérifier la victoire du joueur  */
    {

        for (int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                grilleVictoire[i][j] = pCases[i][j];
            }
        }
    }

    public void setDifficulty(String pDifficulty)
    {
        difficulty = pDifficulty;
    }

    public boolean createBoard(int[][] cases, int compteur, Grille grille)  // Fonction créant la grille
    {

        // Cette fonction est récursive, elle se rappelle elle meme jusqu'à ce que a grille soit remplie
        boolean inLoop = true;
        int colonne, ligne;

        // Une liste contenant les valeurs que l'on peut encore placer dans la case
        int valeursRestantes[] = {0,1,2,3,4,5,6,7,8,9};

        // On stoppe la fonction si la grille est remplie
        if(compteur == 81) {
            grille.setCases(cases);
            grille.setGrilleVictoire(cases);
            return true;
        }

        colonne = compteur % 9;
        ligne = compteur / 9;

        for(int i = 0; i<9; i++)  /* On supprime de la liste des valeurs possibles les valeurs
            que l'on ne peut plus placer dans cette case  */
        {
            valeursRestantes[cases[i][colonne]] = 0;
            valeursRestantes[cases[ligne][i]] = 0;
            valeursRestantes[cases[(ligne/3)*3+(i/3)][(colonne/3)*3+(i%3)]] = 0;
        }

        int remplissage = 0;
        for(int i = 1; i<=9; i++)  // On compte le nombre de nombres que l'on peut placer dans cette case
        {
            if(valeursRestantes[i] != 0)remplissage++;
        }

        while (remplissage != 0)  // Tans qu'il y en a, on essaye d'y placer un nombre aléatoire parmis les valeurs possibles
        {
            int random = (int )(Math.random() * 100 + 1)%10;
            if(valeursRestantes[random] != 0)
            {
                cases[ligne][colonne] = random;
                if (this.createBoard(cases, compteur+1, grille)) return true;  // On relance la fonction jusqu'a ce que la grille soit remplie
                valeursRestantes[random] = 0;
                remplissage--;
                cases[ligne][colonne] = 0;
            }

        }

        return false;
    }

    public void hideNumbers()  // Cette fonction supprime un certain nombre de cases dans la grille, selon la difficulté chosie
    {
        int numbersToHide = 0;

        switch (this.getDifficulty())
        {
            case  "Easy":
                numbersToHide = 3;
                break;
            case  "Normal":
                numbersToHide = 45;
                break;
            case  "Hard":
                numbersToHide = 55;
                break;
        }

        int compteur = 0;

        while(compteur < numbersToHide)
        {
            for (int line = 0; line <9; line++)
            {
                if (compteur <= numbersToHide)
                {
                    for (int column = 0; column<9; column++)
                    {
                        int random = (int )(Math.random() * 100 + 1)%2;
                        if (random == 0 && cases[line][column] != 0)
                        {
                            if (compteur < numbersToHide)
                            {
                                cases[line][column] = 0;
                                compteur++;
                            }
                            else break;
                        }
                    }
                }
            }
        }

    }

    public void verifVictoire(){  // Cette fonction vérifie si le joueur a gagné ou non
        int compteur = 0;
        for (int i = 0; i<9; i++){
            for (int j = 0; j<9; j++){
                if (grilleVictoire[i][j] == cases[i][j])compteur += 1; /* Si la grille remplie par le joueur est
                   identitque a la copie de la grille de départ, le joueur a gagné  ²*/
            }
        }
        if(compteur == 81){
            this.victory = true;
        }
    }
}

