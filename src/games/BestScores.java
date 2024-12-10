package games;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class BestScores {
    private final int max_scores;     // Nb scores max
    public static int DEFAULT_MAX_SCORES = 10; // Nb scores max par défaut
    Score[] scores; // Les scores
    private final int level; // Le niveau de difficulté
    private final String game; // Le jeu concerné
    int nb_scores = 0; // Nb scores courant
    private final E_ORDER_BY order_by; // Ordre des scores : croissant, décroissant

    public enum E_ORDER_BY {
        ASC, DESC
    }

    /**
     * Constructeur par défaut avec un niveau et un nom de jeu.
     *
     * @param level le niveau de difficulté
     * @param game  le nom du jeu
     */
    public BestScores(int level, String game) throws Exception {
        this(E_ORDER_BY.ASC, DEFAULT_MAX_SCORES, level, game);
    }

    /**
     * Constructeur avec un ordre spécifié, un niveau et un jeu.
     *
     * @param order_by l'ordre des scores : croissant ou décroissant
     * @param level    le niveau de difficulté
     * @param game     le nom du jeu
     */
    public BestScores(E_ORDER_BY order_by, int level, String game) throws Exception {
        this(order_by, DEFAULT_MAX_SCORES, level, game);
    }

    /**
     * Constructeur principal avec tous les paramètres.$
     *
     * @param order_by   l'ordre des scores : croissant ou décroissant
     * @param max_scores le nombre maximum de scores
     * @param level      le niveau de difficulté
     * @param game       le nom du jeu
     */
    public BestScores(E_ORDER_BY order_by, int max_scores, int level, String game) throws Exception {
        this.order_by = order_by;
        this.max_scores = max_scores;
        this.level = level;
        this.game = game;
        this.scores = new Score[this.max_scores];
        this.nb_scores = 0;
        this.load();
    }

    /**
     * Récupère le nombre maximum de scores
     *
     * @return int : le nombre maximum de scores
     */
    public int getMax_scores() {
        return max_scores;
    }

    /**
     * Compare deux scores A et B
     *
     * @param valueA : valeur du score A
     * @param valueB : valeur du score B
     * @return boolean : true si le score A est meilleur que le score B, false sinon
     */
    private boolean is_better(int valueA, int valueB) {
        if (this.order_by == E_ORDER_BY.ASC && valueA < valueB) return true;
        return this.order_by == E_ORDER_BY.DESC && valueA > valueB;
    }

    /**
     * Position du score dans la liste
     *
     * @param new_score : valeur du score
     * @return int
     */
    private int get_position(int new_score) {
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            if (this.is_better(new_score, score.value)) {
                return i;
            }
        }
        return this.nb_scores;
    }

    /**
     * Définit si un score fait partie des meilleurs scores
     *
     * @param new_score : valeur du score
     * @return boolean : true si le score fait partie des meilleurs scores, false sinon
     */
    public boolean is_scoring(int new_score) {
        return this.nb_scores < this.max_scores || this.is_better(new_score, this.scores[this.nb_scores - 1].value);
    }

    /**
     * Ajout d'un score dans la liste des scores
     *
     * @param value : valeur du score
     * @param who   : qui a réalisé le score
     * @param when  : quand le score a été réalisé
     */
    public void add_score(int value, String who, String when) {
        int pos = this.get_position(value);
        for (int i = this.max_scores - 1; i > pos; i--) {
            this.scores[i] = this.scores[i - 1];
        }
        if (pos < this.max_scores) {
            this.scores[pos] = new Score(value, who, when);
            if (this.nb_scores < this.max_scores)
                this.nb_scores++;
        }

        try {
            this.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajout d'un score dans la liste des scores sans la date fournie
     *
     * @param value : valeur du score
     * @param who   : qui a réalisé le score
     */
    public void add_score(int value, String who) {
        this.add_score(value, who, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    /**
     * Ajout d'un score dans la liste des scores
     *
     * @param score : le score à ajouter
     */
    public void add_score(Score score) {
        this.add_score(score.value, score.who, score.when);
    }

    /**
     * Affiche les différents scores sous forme de chaîne de caractères
     *
     * @return String
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            s.append((i + 1)).append(" - ").append(score.value).append(", ").append(score.who).append(", ").append(score.when).append("\n");
        }
        return s.toString();
    }

    /**
     * Affichage des scores sur console
     */
    public void write() {
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            System.out.println((i + 1) + " - " + score.value + ", " + score.who + ", " + score.when);
        }
    }

    /**
     * Récupère les meilleurs scores correspondant à un jeu et un niveau
     */
    public void load() throws Exception {
        // Connexion à la base de données
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {

            // Récupérer la base de données et la collection
            MongoDatabase database = mongoClient.getDatabase("games");
            MongoCollection<Document> collection = database.getCollection("games_best_scores");

            // Construction de la requête
            Document query = new Document("game", this.game);

            if (this.level != -1) { // Inclure le niveau uniquement si existant (Pas le Craps)
                query.append("level", this.level);
            }

            // Récupérer le document du jeu
            Document gameDocument = collection.find(query).first();

            // Si le document n'existe pas, on sort, ca indiquera qu'il n'y a pas de scores et qu'il faute en créer un
            if (gameDocument == null) {
                return;
            }

            // Récupérer les scores
            List<Document> scoreDocuments = (List<Document>) gameDocument.get("scores");
            this.nb_scores = 0; // Réinitialiser le nombre de scores

            // Parcourir les scores de la base de données
            for (Document scoreDoc : scoreDocuments) {

                // Sortir si on a atteint le nombre maximum de scores
                if (this.nb_scores >= this.max_scores) break;

                // Récupérer les informations du score
                int value = scoreDoc.getInteger("score");
                String who = scoreDoc.getString("player");
                String when = scoreDoc.getString("date");

                // Ajouter le score à la liste de la classe
                this.scores[this.nb_scores++] = new Score(value, who, when);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors du chargement des scores !");
        }
    }

    /**
     * Enregistre les meilleurs scores dans la base de données
     * @throws Exception
     */
    public void save() throws Exception {
        // Connexion à la base de données
        try (MongoClient mongoClient = new MongoClient("localhost", 27017)) {

            // Récupérer la base de données et la collection
            MongoDatabase database = mongoClient.getDatabase("games");
            MongoCollection<Document> collection = database.getCollection("games_best_scores");

            // Créer la liste des documents des scores à intégrer dans la base de données
            List<Document> scoreDocuments = new ArrayList<>();

            // Parcourir les scores de la classe
            for (int i = 0; i < nb_scores; i++) {
                // Récupérer le score
                Score score = scores[i];

                // Ajouter le score à la liste des documents si le score existe
                if (score != null) {
                    // Ajouter du score à la liste
                    scoreDocuments.add(new Document("player", score.who)
                            .append("score", score.value)
                            .append("date", score.when));
                }
            }

            // Construction du document du jeu
            Document gameDocument = new Document("game", this.game)
                    .append("scores", scoreDocuments);

            if (this.level != -1) { // Enregistrer le niveau uniquement si pertinent
                gameDocument.append("level", this.level);
            }

            // Construction de la requête
            Document query = new Document("game", this.game);
            if (this.level != -1) {
                query.append("level", this.level);
            }

            // Enregistrer les scores dans la base de données
            collection.replaceOne(query, gameDocument, new com.mongodb.client.model.ReplaceOptions().upsert(true));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'enregistrement des scores !");
        }
    }

}
