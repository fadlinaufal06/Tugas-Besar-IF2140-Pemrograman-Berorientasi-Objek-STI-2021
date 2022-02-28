/**
 * SkipCard.java
 * Kelas SkipCard sebagai jenis kartu Skip
 * 
 */

package kartu;

public class SkipCard extends Card implements CardMethod {

    /**
     * Constructor SkipCard
     */
    public SkipCard(CardColor warnaKartu) {
        super(warnaKartu);
    }
    
    /**
     * Getter warnaKartu
     */
    public CardColor getColor() {
        return this.warnaKartu;
    }

    /**
     * toString
     * Mengubah dalam format String
     */
    public String toString() {
        return "Skip " + getColor();
    }

    /**
     * isEqual
     * Mengecek apakah kartu sama persis
     * Kartu sama persis jika memiliki warna dan jenis kartu yang sama
     * @param Kartu yang sedang dimiliki
     * @return true jika memiliki warna dan jenis kartu yang sama
     */
    public boolean isEqual(Card card) {
        // TODO Auto-generated method stub
        return ((card instanceof SkipCard) && card.getColor() == this.getColor());
    }

    /**
     * isLegalMove
     * SkipCard dapat dikeluarkan dengan syarat: 
     * kartu yang dimainkan sebelumnya adalah kartu SkipCard 
     * atau berwarna sama
     * atau jika kartu yang dimainkan sebelumnya adalah Wildcard dengan warna pilihan yang berwarna sama.
     * @param Kartu yang dimainkan sebelumnya
     * @return true jika memenuhi syarat
     */
    public boolean isLegalMove(Card card) {
        // TODO Auto-generated method stub
        if (card instanceof SkipCard) return true;
        else if (card instanceof Wildcard){
            return ((card.getColor() == this.getColor()) || ((Wildcard) card).nextWarna == this.getColor());
        }
        else if (card instanceof DrawFourCard){
            return ((card.getColor() == this.getColor()) || ((DrawFourCard) card).nextWarna == this.getColor());
        }
        else {
            return ((card.getColor() == this.getColor()));
        }
    }
    
}