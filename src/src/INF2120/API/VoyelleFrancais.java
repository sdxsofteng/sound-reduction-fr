package INF2120.API;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Décrit le son d'un groupe de voyelle en français.
 *
 * Cette description permet de contenir une ou deux voyelles afin de décrire le son des voyelles dans une syllabe.
 * S'il y a deux voyelles, alors la première voyelle du groupe devrait être une semi-voyelle.
 *
 * @see API_Consonne
 * @see SyllabeFrancais
 * @see <a href="https://fr.wiktionary.org/wiki/Annexe:Prononciation/fran%C3%A7ais">référence</a>
 */
public class VoyelleFrancais {

    /**
     * calcule la distance entre les deux phonemes de voyelles ensemble
     * @param voyelleComparee voyelle comparee a this
     * @return distance totale de la voyelle [0-9]
     */
    public int distancePhonemesVoyelles(VoyelleFrancais voyelleComparee){

        return distancePhonemeUnique(this.semiVoyelle, voyelleComparee.semiVoyelle)
                + distancePhonemeUnique(this.voyelle, voyelleComparee.voyelle)
                + calculerDistanceBooleens(this.estNasal(), voyelleComparee.estNasal());
    }

    /**
     * Calcule la distance entre deux phonemes de voyelles
     * @param phonemeOriginal phoneme du this
     * @param phonemeComparer phoneme de la voyelle comparee
     * @return distance [0-4]
     */
    private int distancePhonemeUnique(API_Voyelle phonemeOriginal, API_Voyelle phonemeComparer){

        int distance;

        if (phonemeOriginal == null && phonemeComparer == null){
            distance = Constantes.DISTANCE_ZERO;
        }else if (phonemeOriginal == null ^ phonemeComparer == null){
            distance = Constantes.DISTANCE_MAXIMALE_PHONEME_VOYELLE;
        }else{
            distance = calculerDistanceBooleens(phonemeOriginal.estArriere(), phonemeComparer.estArriere())
                    + calculerDistanceBooleens(phonemeOriginal.estHaut(), phonemeComparer.estHaut())
                    + calculerDistanceBooleens(phonemeOriginal.estArrondi(), phonemeComparer.estArrondi())
                    + calculerDistanceBooleens(phonemeOriginal.estOuverte(), phonemeComparer.estOuverte());
        }
        return distance;
    }

    /**
     * Calcule la distance entre deux booleens
     * @param booleenOriginal booleen du this
     * @param booleenComparer booleen comparer
     * @return distance [0-1]
     */
    private int calculerDistanceBooleens(boolean booleenOriginal, boolean booleenComparer){
        int distance;

        if (booleenOriginal == booleenComparer){
            distance = 0;
        }else {
            distance = 1;
        }

        return distance;
    }


    /**
     * Methode permettant de verifier l'egalitee entre deux voyelles. Permet d'eviter les null pointer dans la methode
     * egaliteLettre
     * @param voyelleAVerifier voyelle a comparer avec this
     * @return si les deux voyelles sont egales
     */
    public boolean egaliteLettresVoyelles(VoyelleFrancais voyelleAVerifier ) {

        boolean semiVoyelleEgale;
        boolean voyelleEgale;
        boolean voyelleCompleteEgale;

        if (!(( this.semiVoyelle == null ^ voyelleAVerifier.semiVoyelle == null)
                || ( this.voyelle == null ^ voyelleAVerifier.voyelle == null))){
            semiVoyelleEgale = verifierEgaliteeLettre( this.semiVoyelle, voyelleAVerifier.semiVoyelle );
            voyelleEgale = verifierEgaliteeLettre( this.voyelle, voyelleAVerifier.voyelle );
            voyelleCompleteEgale = semiVoyelleEgale && voyelleEgale;

        }else{
            voyelleCompleteEgale = false;
        }

        return voyelleCompleteEgale;
    }

    /**
     * Permet de verifier l'egalite entre deux API_Consonne
     * @param lettreOriginale lettre du this
     * @param lettreAVerifier lettre de la consonne comparee
     * @return valeur d'egalite entre les deux API_Consonne
     */
    private boolean verifierEgaliteeLettre(API_Voyelle lettreOriginale, API_Voyelle lettreAVerifier) {

        boolean lettreEgale;

        if (lettreOriginale == null && lettreAVerifier == null){
            lettreEgale = true;
        }else{
            lettreEgale = lettreOriginale.equals(lettreAVerifier);
        }

        return lettreEgale;
    }

    /**
     * code utf-16 pour le tilde utilisé pour indiquer les voyelles nasales selon l'API.
     */
    public static final int TILDE_CODE_POINT = 0x0303;

    /**
     * Contient un {@code Pattern} de reconnaissance pour identifier si un caractère est un tilde.
     */
    private static final Pattern TILDE_PATTERN = Pattern.compile( Character.toString( TILDE_CODE_POINT ) );

    /**
     * La semi-voyelle du groupe de voyelle.
     * La valeur {@code null} est utilisé pour indiquer qu'elle n'est pas présente dans le groupe.
     */
    protected API_Voyelle semiVoyelle = null;

    /**
     * La voyelle de base du groupe.
     * Ne doit pas être {@code null}.
     */
    protected API_Voyelle voyelle;

    /**
     * Indique si la voyelle de base est une voyelle nasale.
     */
    protected boolean nasal = false;


    /**
     * Construit une voyelle simple.
     *
     * @param voyelle La voyelle de base du groupe.  Ne doit pas être {@code null}.
     */
    public VoyelleFrancais( API_Voyelle voyelle ) {
        this.voyelle = voyelle;
    }

    /**
     * Construit une voyelle simple, avec la possibilité de lui attribuer la caractéristique nasale.
     *
     * @param voyelle La voyelle de base du groupe.  Ne doit pas être {@code null}.
     * @param nasal {@code true} si la voyelle de base est nasale.
     */
    public VoyelleFrancais( API_Voyelle voyelle, boolean nasal ) {
        this.voyelle = voyelle;
        this.nasal = nasal;
    }

    /**
     * Construit un groupe de voyelle avec deux voyelles.
     *
     * @param semiVoyelle La semi-voyelle du groupe.
     * @param voyelle La voyelle de base du groupe.  Ne doit pas être {@code null}.
     */
    public VoyelleFrancais( API_Voyelle semiVoyelle, API_Voyelle voyelle ) {
        this.semiVoyelle = semiVoyelle;
        this.voyelle = voyelle;
    }

    /**
     * Construit un groupe de voyelle avec deux voyelles, avec la possibilité de lui attribuer la caractéristique
     * nasale.
     *
     * @param semiVoyelle La semi-voyelle du groupe.
     * @param voyelle La voyelle de base du groupe.  Ne doit pas être {@code null}.
     * @param nasal {@code true} si la voyelle de base est nasale.
     */
    public VoyelleFrancais( API_Voyelle semiVoyelle, API_Voyelle voyelle, boolean nasal ) {
        this.semiVoyelle = semiVoyelle;
        this.voyelle = voyelle;
        this.nasal = nasal;
    }


    /**
     * Consulte la caractéristique sonore 'nasale' du groupe de voyelle.
     * @return {@code true} si le groupe est nasal.
     */
    public boolean estNasal() {
        return nasal;
    }


    /**
     * Lit un groupe de voyelles dans le {@code Scanner}.
     *
     * Vérifie si les deux prochains caractères du {@code scanner} représentent des voyelles.  Si une seule voyelle est
     * trouvée, alors elle devient la voyelle de base du groupe.  Si deux voyelles sont trouvées, alors la première
     * devient la semi-voyelle et la seconde devient la voyelle de base.  Ensuite, vérifie si le prochain caractère
     * est le caractère désignant une voyelle nasale.  Si oui, alors la caractéristique nasale est ajouté au groupe
     * de voyelle.
     *
     * @param scanner le {@code Scanner} dans lequel la lecture est effectué.
     * @return le groupe de voyelle lu.
     * @exception NoSuchElementException s'il n'y a pas de {@code API_Voyelle} valide.
     * @exception IllegalStateException si le {@code Scanner} est fermé.
     */
    public static VoyelleFrancais lire(Scanner scanner ) {
        API_Voyelle voyelle = null;
        API_Voyelle voyelle2 = null;
        boolean estNasal = false;

        try {
            scanner.next( TILDE_PATTERN );
            estNasal = true;
        } catch ( NoSuchElementException e ) {
        }

        voyelle = API_Voyelle.lire( scanner );

        try {
            scanner.next( TILDE_PATTERN );
            estNasal = true;
        } catch ( NoSuchElementException e ) {
        }

        try {
            voyelle2 = API_Voyelle.lire( scanner );
        } catch ( NoSuchElementException e ) {
        }

        return null == voyelle2
                ? new VoyelleFrancais( voyelle, estNasal )
                : new VoyelleFrancais( voyelle, voyelle2, estNasal );
    }


    /**
     * retourne une chaîne de caractère composée des voyelles du groupe.
     *
     * @return la chaîne contenant les symboles des voyelles du groupe et le symbole de nasalité si c'est le cas.
     */
    @Override
    public String toString() {
        return "" + ( null == semiVoyelle ? "" : semiVoyelle )
                + ( nasal ? Character.toString( TILDE_CODE_POINT ) : "" )
                + voyelle
                ;
    }
}
