package INF2120.API;

import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * Représente l'unité de base pour la prononciation en français.
 *
 * Une syllabe est composé d'un groupe de syllabe (le noyau).
 * optionnelement, elle peu avoir deux groupes de consonne.
 * Le groupe de consonne avant le noyau est l'attaque de la syllabe.
 * Le groupe de consonne après le noyau est le coda de la syllabe.
 *
 * @see ConsonneFrancais
 * @see VoyelleFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class SyllabeFrancais {
    /**
     * Le groupe de consonne pour l'attaque de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais attaque = null;

    /**
     * Le groupe de voyelle pour la syllabe.  Ne doit pas être {@code null}.
     */
    protected VoyelleFrancais noyau;

    /**
     * Le groupe de consonne pour le code de la syllabe.  S'il n'est pas présent, alors la valeur est à {@code null}.
     */
    protected ConsonneFrancais coda = null;


    /**
     * Construit une syllabe avec un noyau seulement.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( VoyelleFrancais noyau) {
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque et un noyau.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     */
    public SyllabeFrancais( ConsonneFrancais attaque, VoyelleFrancais noyau) {
        this.attaque = attaque;
        this.noyau = noyau;
    }


    /**
     * Construit une syllabe avec une attaque, un noyau et un coda.
     *
     * @param attaque le groupe de consonne utilisé pour la syllabe.
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(ConsonneFrancais attaque, VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.attaque = attaque;
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Construit une syllabe avec un noyau et un coda.
     *
     * @param noyau le groupe de voyelle utilisé pour la syllabe.  Ne doit pas être {@code null}.
     * @param coda le groupe de consonne utilisé pour la syllabe.
     */
    public SyllabeFrancais(VoyelleFrancais noyau, ConsonneFrancais coda ) {
        this.noyau = noyau;
        this.coda = coda;
    }


    /**
     * Lit une syllabe dans le {@code Scanner}.
     *
     * Cherche possiblement un groupe de consonne qui servira d'attaque, ensuite un groupe de voyelle qui
     * servira de noyau et finalement un autre groupe de consonne pour le coda.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return la voyelle lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code SyllabeFrancais} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static SyllabeFrancais lire( Scanner scanner ) {
        ConsonneFrancais attaque = null;
        VoyelleFrancais noyau;
        ConsonneFrancais coda = null;

        try {
            attaque = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }

        noyau = VoyelleFrancais.lire( scanner );

        try {
            coda = ConsonneFrancais.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }


        return new SyllabeFrancais( attaque, noyau, coda );
    }


    /**
     * retourne une chaîne de caractère composée des phonèmes de la syllabe.
     *
     * @return la chaîne contenant les symboles des phonèmes de la syllabe.
     */
    @Override
    public String toString() {
        return "" + ( null == attaque ? "" : attaque )
                + noyau
                + ( null == coda ? "" : coda );
    }
}
