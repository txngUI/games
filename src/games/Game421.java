package games;

public class Game421 {
    // Constantes de la classe
    private static final int NB_DES = 3;
    private static final int NB_FACES = 6;

    // Attributs de la classe
    private final int faces;
    private final Dice[] des = new Dice[NB_DES];

    /**
     * Constructeur de la classe
     * @param faces : nombre de faces des dés
     */
    public Game421(int faces) {
        this.faces = faces;
    }

    /**
     * Constructeur de la classe par défaut
     */
    public Game421() {
        this(NB_FACES);
    }

    /**
     * Méthode pour jouer au jeu 421
     * @return le score obtenu
     */
    public int play() {

        // Initialisation des dés
        initialize();

        // Variables locales
        int score = 0;
        int locked_dices = 0;

        // Boucle de jeu
        boolean reroll = false;
        while (!reroll) {

            // Affichage des dés
            System.out.println("Lancé de dés : ");

            // Pour chaque dé, on lance le dé et on affiche sa valeur
            for (int i = 0; i < NB_DES; i++) {
                // Si le dé n'est pas verrouillé, on le lance
                if (!des[i].isLocked()) {
                    des[i].roll();
                    // On affiche la valeur du dé
                    System.out.println("Dé " + (i + 1) + " : " + des[i].get_value());
                } else {
                    // Si le dé est verrouillé, on rappel sa valeur
                    System.out.println("Le dé " + (i + 1) + " a été verrouillé durant les manches précédentes, sa valeur est : " + des[i].get_value());
                }
            }

            // On demande à l'utilisateur s'il veut relancer un ou plusieurs dés
            System.out.println("Voulez-vous relancer un ou plusieurs dés ? (o/n)");
            char choice = Lire.c();

            // Si l'utilisateur ne veut pas relancer de dés, on sort de la boucle
            if (choice == 'n') reroll = true;
            else {
                // Sinon, on demande à l'utilisateur quels dés il veut relancer
                for (int i = 0; i < NB_DES; i++) {

                    // Si le dé est verrouillé, on passe à l'itération suivante
                    if (des[i].isLocked()) continue;

                    // Sinon, on demande à l'utilisateur s'il veut relancer le dé
                    System.out.println("Voulez-vous relancer le dé " + (i + 1) + " ? (o/n)");
                    choice = Lire.c();

                    // Si l'utilisateur veut relancer le dé, on le relance
                    if (choice == 'n') {
                        des[i].setLocked(true);
                        locked_dices++;
                    }
                }
            }

            // Si tous les dés sont verrouillés, on sort de la boucle
            if (locked_dices == NB_DES) {
                System.out.println("Vous avez verrouillé tous les dés, fin de la partie.");
                reroll = true;
            }

            // On incrémente le score
            score++;
        }

        // On retourne le score
        return score;
    }

    /**
     * Méthode pour initialiser les dés
     */
    public void initialize() {
        // pour chaque dé, on crée un objet Dice et l'enregistre dans la liste
        for (int i = 0; i < NB_DES; i++) {
            this.des[i] = new Dice(faces);
        }
    }
}
