package games;
/**
 * Classe simulant un lancé de dés
 */
public class Dice {
	// Attributs de la classe
	private static final int DEFAULT_NB_FACES = 6 ;
	private final int nb_faces;
	private int value;
	private boolean locked;

	/**
	 * Constructeur par défaut
	 */
	public Dice () {
		// Constructeur, 6 faces par défaut
		this(Dice.DEFAULT_NB_FACES);
	}

	/**
	 * Constructeur
	 * @param nb_faces Nombre de faces du dé
	 */
	public Dice (int nb_faces) {
		// Constructeur
		super();
		this.nb_faces = nb_faces;
		this.locked = false;
	}

	/**
	 * Verrouille le dé : empêche de le relancer
	 * @param locked true pour verrouiller, false pour déverrouiller
	 */
	public void setLocked(boolean locked){
		this.locked = locked;
	}

	/**
	 * Retourne l'état de verrouillage du dé
	 * @return : true si le dé est verrouillé, false sinon
	 */
	public boolean isLocked(){
		return this.locked;
	}

	/**
	 * Retourne la valeur du dé
	 * @return : la valeur du dé
	 */
	public int get_value(){
		// Retourne la valeur du dé
		return this.value;
	}

	/**
	 * Retourne le nombre de faces du dé
	 */
	public void roll(){
		// Lancé de dé, retourne un nombre entre 1 et la valeur maximale
		this.value = (int) (this.nb_faces * Math.random() + 1);
	}
}