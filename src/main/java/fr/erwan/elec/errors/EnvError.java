package fr.erwan.elec.errors;

/**
 * classe d'erreur si l'environnement n'est pas dev
 * les méthodes pour définir l'environnement sont dans la classe Config
 * l'environnement est défini dans le main
 */
public class EnvError extends Exception {
    public EnvError(String message) {
        super(message);
    }
}
