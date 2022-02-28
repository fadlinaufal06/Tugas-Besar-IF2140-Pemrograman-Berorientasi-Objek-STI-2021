/*
 * Interface CardMethod
 */

package kartu;

public interface CardMethod {
    String toString();
    boolean isEqual(Card card);
    boolean isLegalMove(Card card);
}
