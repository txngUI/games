package games;

import java.util.ArrayList;
import java.util.List;

public class Craps {
    // Constante de la classe
    private final int NB_DES = 2;

    // Attributs privés de la classe
    private final Dice[] des = new Dice[NB_DES];
    private List<Integer> valuesWon; // Liste des valeurs gagnantes
    private List<Integer> valuesLose; // Liste des valeurs perdantes

    /**
     * Constructeur de la classe Craps
     */
    public Craps() {
        initialize(); // Initialisation des attributs
    }

    /**
     * Méthode permettant de jouer au jeu de Craps
     * @return le nombre de tours joués
     */
    public int play() {
        // Initialisation des variables
        int score = 0;
        int point = 0;
        boolean gameOver = false;

        // Boucle de jeu
        while (!gameOver) {

            // initialisation de la valeur des dés
            int sum = 0;

            // Lancer des dés
            for (int i = 0; i < NB_DES; i++) {
                des[i].roll();
                System.out.println("Lancé du dé " + (i + 1) + " : " + des[i].get_value());
                sum += des[i].get_value();
            }

            // Affichage du total
            System.out.printf("Vous avez fait un total de %d\n", sum);

            // Incrémentation du score
            score++;

            // Étape 1 : Come-Out Roll
            if (point == 0) {
                // Vérification des valeurs gagnantes et perdantes
                if (valuesLose.contains(sum)) {
                    System.out.println("Tu as perdu !");
                    score = -1;
                    gameOver = true;
                } else if (valuesWon.contains(sum)) {
                    System.out.println("Tu as gagné !");
                    gameOver = true;
                } else { // Établissement du point
                    point = sum;
                    System.out.println("Tu as établi un point : " + point);
                }
            }
            // Étape 2 : Phase de point
            else {
                // Vérification des valeurs gagnantes et perdantes
                if (sum == point) {
                    System.out.println("Tu as gagné en refaisant ton point !");
                    gameOver = true;
                } else if (sum == 7) {
                    System.out.println("Tu as perdu en obtenant un 7 avant de faire ton point !");
                    gameOver = true;
                    score = -1;
                }
            }

            // Demande de continuer
            if (!gameOver) {
                System.out.println("Souhaitez-vous continuer ? (o/n)");

                if (Lire.c() == 'n') {
                    gameOver = true;
                }
            }
        }

        // Retour du score
        return score;
    }

    /**
     * Méthode permettant d'initialiser les attributs de la classe
     */
    private void initialize() {
        // Initialisation des dés
        for (int i = 0; i < NB_DES; i++) {
            des[i] = new Dice(6);
        }

        // Initialisation des valeurs gagnantes et perdantes
        valuesLose = new ArrayList<>();
        valuesLose.add(2);
        valuesLose.add(3);
        valuesLose.add(12);

        valuesWon = new ArrayList<>();
        valuesWon.add(7);
        valuesWon.add(11);
    }
}
