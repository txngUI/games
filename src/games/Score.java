package games;

public class Score {

	String who ;
	String when ;
	int value;
	
	// Constructeur par défaut, sans argument
	public Score() {
		super();
	}

	/**
	 * Constructeur avec les 3 arguments
	 *
	 * @param value : la valeur du score
	 * @param who : le nom du joueur
	 * @param when : la date du score
	 */
	public Score(int value, String who, String when) {
		super();
		this.who = who;
		this.when = when;
		this.value = value;
	}

	/**
	 * Affichage du score
	 */
	public String toString() {
		return this.who + " : " + this.value + " <" + this.when + ">";
	}
}