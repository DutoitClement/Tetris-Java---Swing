package com.supinfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by cleme on 2/14/2017.
 */

// Classe permettant l'affichage de la fenetre pop up d'entré de nombre dans une case
public class NumberChoiceWindow extends JFrame implements ActionListener
{
    private JComboBox choiceNumberBox;
    private boolean actionFinished = false;

    public JComboBox getChoiceNumberBox(){
        return choiceNumberBox;
    }

    public boolean getActionFinished(){
        return actionFinished;
    }

    public String numberChoiceAction() // Fonction réalisant l'affichage de la fenetre
    {
        this.setSize(500,300);
        this.setLocationRelativeTo(null);

        // Création du panel contenant les boutons
        JPanel choiceNumberPanel = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridwidth = 2;

        // Affichage du texte
        JLabel text = new JLabel("Enter a number : ");
        text.setFont(new Font("Arial", Font.PLAIN, 24));
        grid.gridy = 0;
        grid.insets.set(0,0,30,0);
        choiceNumberPanel.add(text, grid);

        // Afichage du menu déroulant permettant de choisir le nombre a jouer
        Object[] listChoices = {1,2,3,4,5,6,7,8,9};
        choiceNumberBox = new JComboBox(listChoices);
        grid.ipady = 20;
        grid.ipadx = 20;
        grid.gridy = 1;
        choiceNumberPanel.add(choiceNumberBox, grid);

        // Création des boutons 'yes' et 'no', et ajout des ActionListeners pour ces boutons
        JButton No = new JButton("No");
        grid.gridy = 2;
        choiceNumberPanel.add(No, grid);
        JButton Yes = new JButton("Yes");
        choiceNumberPanel.add(Yes, grid);
        No.addActionListener(this);
        Yes.addActionListener(this);

        this.setContentPane(choiceNumberPanel);

        this.setVisible(true);

        return "n";
    }

    // ActionListener, il permet de confirmer ou non l'action du joueur, et de fermer la fenetre
    @Override
    public void actionPerformed(ActionEvent ae){
        Object button = ae.getSource();

        // Si le joueur appuie sur 'yes', le nombre choisi dans le menu déroulant s'affiche dans le bouton choisi
        if (((JButton) button).getText() == "Yes")
        {
            Object newButtonTitle = this.choiceNumberBox.getSelectedItem();
            GUI.button.setText("" + newButtonTitle);
            GUI.grilleToConsole();
            GUI.grille.verifVictoire();
            if(GUI.grille.getVictory())GUI.victoryPanel.setVisible(true);
        }

        // Dans tous les cas la fenetre se ferme
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

    }

}
