package com.supinfo;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by cleme on 2/11/2017.
 */
public class GUI extends JFrame implements ActionListener {

    public static Grille grille;
    private static JButton[] buttonList = new JButton[81];
    private String[] textButtonList = new String[81];
    private boolean[] playableButtonList = new boolean[81];
    public boolean modification = true;
    public static JButton button;
    private JComboBox choiceLevel;
    public static JPanel victoryPanel;

    public JButton getButton(){
        return button;
    }

    public JButton[] getButtonList()
    {
        return buttonList;
    }

    public String[] getTextButtonList()
    {
        return textButtonList;
    }

    public void setButtonList(JButton[] buttonList)
    {
        GUI.buttonList = buttonList;
    }

    public void setTextButtonList(String[] textButtonList)
    {
        this.textButtonList = textButtonList;
    }

    public void setButton(JButton button){
        this.button = button;
    }

    public void createGUI(Grille grille)  // Fonction créant la frame principale, réalise l'affichage du jeu
    {
        GUI.grille = grille;

        // Propriétés de la frame principale
        this.setSize(700,900);
        this.setTitle("SupSudoku");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Création du panel affichant l'option de génération d'une grille
        JPanel generatePanel = new JPanel();
        JLabel text = new JLabel("Choose your level : ");
        generatePanel.add(text);
        Object listLevels[] = {"Easy", "Medium", "Hard"};
        this.choiceLevel = new JComboBox(listLevels);
        generatePanel.add(choiceLevel);
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(this);
        generatePanel.add(generateButton);

        // Création du panel affichant la grille de jeu
        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        float bordureX = 0, bordureY = 0;

        // Boucle for permettant d'afficher chaque bouton de la grille dans le panel de jeu
        for (int i =0; i<81; i++)
        {
            // Permet d'afficher les chiffres dans les boutons, ou des '.' si le chiffre est un 0 (case vide)
            if (GUI.grille.getCases()[i/9][i%9] != 0) this.textButtonList[i] = "" + GUI.grille.getCases()[i/9][i%9];
            else this.textButtonList[i] = ".";

            // On crée chaque bouton
            buttonList[i] = new JButton(this.textButtonList[i]);

            // On ajoute un ActionListener uniquement aux cases jouables
            if (buttonList[i].getText() == ".") playableButtonList[i] = true;
            else playableButtonList[i] = false;

            // On modifie l'affichage de chaque bouton
            buttonList[i].setPreferredSize(new Dimension(30,30));
            buttonList[i].setBackground(Color.white);
            buttonList[i].setFont(new Font("Arial", Font.PLAIN, 24));

            // On change la couleur du texte des cases jouables
            if(playableButtonList[i]) {
                buttonList[i].addActionListener(this);
                buttonList[i].setForeground(Color.BLUE);
            }

            // On affiche les boutons pour former une grille de sudoku
            grid.gridx = (i%9);
            grid.gridy = (i/9);
            grid.ipady = 30;
            grid.ipadx = 30;

            // On espace les carrés de 3*3 cases les uns des autres
            if (i%3 == 2 && (i/9)%3 == 2)grid.insets.set(0,0,10,10);
            else if (i%3 == 2 && (i/9)%3 != 2)grid.insets.set(0,0,0,10);
            else if (i%3 != 2 && (i/9)%3 == 2)grid.insets.set(0,0,10,0);
            else grid.insets.set(0,0,0,0);

            // On ajoute chaque bouton a la liste de boutons passée en attribut de l'objet screen
            gridPanel.add(buttonList[i], grid);
        }

        // Création du panel affichant le texte de victoire du joueur
        GUI.victoryPanel = new JPanel();
        JLabel victory = new JLabel("VICTORY !");
        victory.setFont(new Font("Arial", Font.PLAIN,24));
        GUI.victoryPanel.add(victory);
        GUI.victoryPanel.setVisible(false);

        // On ajoute les 3 panels au panel principal
        mainPanel.add(generatePanel, BorderLayout.PAGE_START);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(victoryPanel, BorderLayout.PAGE_END);
        this.setContentPane(mainPanel);

        //______________________________________________________________________________________________________________
        this.setVisible(true);
    }

    // Méthode permettant d'envoyer le contenu des boutons dans les cases de l'objet grille, en temps réel
    public static void grilleToConsole(){
        int[][] updatedGrille = new int[9][9];
        for (int i = 0; i < 81; i++){
            int line = i/9; int column = i%9;
            if (buttonList[i].getText() == ".")updatedGrille[line][column] = 0;
            else {
                int number = Integer.parseInt(buttonList[i].getText());
                updatedGrille[line][column] = number;
            }
        }
        GUI.grille.setCases(updatedGrille);
    }

    /* ActionListener, il permet d'ppeler les méthodes d'affichage de fenetre d'action pour chaque case,
     ou d'appeler les méthodes de génération de la grille, selon le bouton utilisé  */
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        Object button = arg0.getSource();

        if (((JButton)button).getText() == "Generate"){
            GUI.grille= new Grille();
            GUI.grille.createBoard(GUI.grille.getCases(), 0, GUI.grille);
            if (this.choiceLevel.getSelectedItem() == "Easy")GUI.grille.setDifficulty("Easy");
            else if (this.choiceLevel.getSelectedItem() == "Medium")GUI.grille.setDifficulty("Normal");
            else if (this.choiceLevel.getSelectedItem() == "Hard")GUI.grille.setDifficulty("Hard");
            GUI.grille.hideNumbers();
            GUI.buttonList = new JButton[81];
            createGUI(GUI.grille);
        }
        else {
            this.button = ((JButton)button);
            NumberChoiceWindow numberChoiceWindow = new NumberChoiceWindow();
            numberChoiceWindow.numberChoiceAction();
        }

    }


}
