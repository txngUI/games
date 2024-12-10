package games;

public class Play {

    public static void main(String[] args) throws Exception {
        // Initialisation du nom du joueur
        String player;

        // Initialisation de la variable de fin de jeu
        boolean bEnd = false;

        // Tant que le joueur souhaite jouer
        while (!bEnd) {

            // Saisie du nom du joueur
            System.out.println("Qui êtes-vous ? (q pour quitter)");
            player = Lire.S();

            // Si le joueur souhaite quitter
            if (player.equals("q")) {
                bEnd = true;
                break;
            }

            // Affichage du message de bienvenue
            System.out.println("Bienvenue " + player + " !");

            // Affichage du menu des jeux
            System.out.println("A quel jeu voulez-vous jouer ?");
            System.out.println("1 - Nombre mystère");
            System.out.println("2 - Jeu du 421");
            System.out.println("3 - Craps");
            System.out.println("q - Quitter");

            // Saisie du choix du jeu
            char game_choice = Lire.c();

            // En fonction du choix du jeu
            switch (game_choice) {
                case '1':
                    playMysteryNumber(player);
                    break;
                case '2':
                    play421(player);
                    break;
                case '3':
                    playCraps(player);
                    break;
                case 'q':
                    bEnd = true;
                    break;
                default:
                    System.out.println("Saisie incorrecte !!!");
                    break;
            }

            // Si le joueur souhaite rejouer
            if (!bEnd) {
                System.out.println("<Enter> pour revenir au menu principal");
                Lire.c();
            }
        }

        // Message de fin de jeu
        System.out.println("Bye !");
    }

    /**
     * Fonction de lancement du jeu de Craps
     * @param player : Nom du joueur
     * @throws Exception : Exception levée lors de l'enregistrement du score
     */
    public static void playCraps(String player) throws Exception {
        // Initialisation des variables
        int score;
        Craps craps;
        BestScores craps_sc = new BestScores(-1, "Craps");

        // Lancement du jeu
        craps = new Craps();

        // Récupération du score
        score = craps.play();

        // Affichage du score
        System.out.println("Votre score est de : " + score);

        try {
            // Enregistrement du score et consultation des meilleurs scores
            if (score != -1) craps_sc.add_score(score, player);
            else System.out.println("Vous avez perdu ! Votre score n'est pas enregistré.");

            consultBestScores(craps_sc, score);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
        }
    }

    /**
     * Fonction de lancement du jeu 421
     * @param player : Nom du joueur
     * @throws Exception : Exception levée lors de l'enregistrement du score
     */
    public static void play421(String player) throws Exception {
        // Initialisation des variables
        int score;
        Game421 game421;

        // Initialisation des meilleurs scores pour chaque niveau
        BestScores game421_level1_sc = new BestScores(1, "Game421");
        BestScores game421_level2_sc = new BestScores(2, "Game421");
        BestScores game421_level3_sc = new BestScores(3, "Game421");

        // Initialisation de la variable de fin de partie
        boolean bEnd = false;

        // Tant que la partie n'est pas terminée
        while (!bEnd) {
            // Choix du niveau de difficulté
            char sChoice = chooseLevel();

            // En fonction du choix du niveau, on lance la partie
            switch (sChoice) {
                case '1':
                    // Initialisation du jeu
                    game421 = new Game421();

                    // Lancement du jeu et récupération du score
                    score = game421.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        game421_level1_sc.add_score(score, player);
                        consultBestScores(game421_level1_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case '2':
                    // Initialisation du jeu
                    game421 = new Game421(10);

                    // Lancement du jeu et récupération du score
                    score = game421.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        game421_level2_sc.add_score(score, player);
                        consultBestScores(game421_level2_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case '3':
                    // Initialisation du jeu
                    game421 = new Game421(15);

                    // Lancement du jeu et récupération du score
                    score = game421.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        game421_level3_sc.add_score(score, player);
                        consultBestScores(game421_level3_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case 'q':
                    // Fin de la partie
                    bEnd = true;
                    break;
                default:
                    System.out.println("Saisie incorrecte !!!");
                    break;
            }
        }
    }

    /**
     * Fonction de lancement du jeu du Nombre Mystère
     * @param player : Nom du joueur
     * @throws Exception : Exception levée lors de l'enregistrement du score
     */
    public static void playMysteryNumber(String player) throws Exception {
        // Initialisation des variables
        int score;
        MysteryNumber mysteryNumber;

        // Initialisation des meilleurs scores pour chaque niveau
        BestScores mystery_level1_sc = new BestScores(1, "MysteryNumber");
        BestScores mystery_level2_sc = new BestScores(2, "MysteryNumber");
        BestScores mystery_level3_sc = new BestScores(3, "MysteryNumber");

        // Initialisation de la variable de fin de partie
        boolean bEnd = false;

        // Tant que la partie n'est pas terminée
        while (!bEnd) {
            // Choix du niveau de difficulté
            char sChoice = chooseLevel();

            // En fonction du choix du niveau, on lance la partie
            switch (sChoice) {
                case '1':
                    // Initialisation du jeu
                    mysteryNumber = new MysteryNumber(10);

                    // Lancement du jeu et récupération du score
                    score = mysteryNumber.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        mystery_level1_sc.add_score(score, player);
                        consultBestScores(mystery_level1_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case '2':
                    // Initialisation du jeu
                    mysteryNumber = new MysteryNumber(100);

                    // Lancement du jeu et récupération du score
                    score = mysteryNumber.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        mystery_level2_sc.add_score(score, player);
                        consultBestScores(mystery_level2_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case '3':
                    // Initialisation du jeu
                    mysteryNumber = new MysteryNumber(1000);

                    // Lancement du jeu et récupération du score
                    score = mysteryNumber.play();

                    try {
                        // Enregistrement du score et consultation des meilleurs scores
                        mystery_level3_sc.add_score(score, player);
                        consultBestScores(mystery_level3_sc, score);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'enregistrement du score : " + e.getMessage());
                    }
                    break;
                case 'q':
                    // Fin de la partie
                    bEnd = true;
                    break;
                default:
                    System.out.println("Saisie incorrecte !!!");
                    break;
            }
        }
    }

    /**
     * Fonction de choix du niveau de difficulté
     * @return : Niveau de difficulté choisi
     */
    public static char chooseLevel() {
        System.out.println("\n\nNiveaux de difficultés:\n");
        System.out.println("1 - Niveau débutant");
        System.out.println("2 - Niveau normal");
        System.out.println("3 - Niveau expert");
        System.out.println("q - Quitter");

        System.out.print("Votre choix : ");

        return Lire.c();
    }

    /**
     * Fonction de consultation des meilleurs scores
     * @param bestScores : Meilleurs scores
     * @param new_score : Nouveau score
     */
    public static void consultBestScores(BestScores bestScores, int new_score) {
        try {
            if (new_score == -1) {
                System.out.println("Vous avez perdu ! Votre score n'est pas enregistré.");
            } else {
                // Si le score est dans les meilleurs scores
                if (bestScores.is_scoring(new_score)) {
                    System.out.println("Bravo, vous avez fait un des meilleurs scores !"); // Message de félicitations
                } else {
                    System.out.println("Désolé, vous ne faites pas partie du top " + bestScores.getMax_scores() + " des meilleurs scores.");
                }
            }

            // Affichage des meilleurs scores
            System.out.println("Voici les meilleurs scores :");
            bestScores.write();
        } catch (Exception e) {
            System.out.println("Erreur lors de la consultation des scores : " + e.getMessage());
        }
    }
}
