/**
 * Wildcard.java
 * Kelas Wildcard sebagai jenis kartu Wildcard
 */

package kartu;
public class Wildcard extends Card implements CardMethod {
    final CardColor warnaKartu = CardColor.WILD;
    CardColor nextWarna = CardColor.WILD;

    /**
     * Constructor Wildcard
     */
    public Wildcard(CardColor warnaKartu){
        super(warnaKartu);
    }

    /**
     * Getter warnaKartu
     */
    public CardColor getColor(){
        return this.warnaKartu;
    }

    /**
     * Setter nextWarna
     * Memilih warna kartu yang dapat dimainkan selanjutnya
     * @param Warna kartu selanjutnya
     */
    public void setNextWarna(CardColor nextWarna){
        this.nextWarna = nextWarna;
    }

    /**
     * toString
     * Mengubah dalam format String
     */
    public String toString() {
        return "Wildcard " + this.nextWarna;
    }

    /**
     * isEqual
     * Mengecek apakah kartu sama persis
     * Kartu sama persis jika memiliki jenis kartu yang sama
     * @param Kartu yang sedang dimiliki
     * @return true jika memiliki jenis kartu yang sama
     */
    public boolean isEqual(Card card) {
        return (card.getClass() == this.getClass());
    }

    /**
     * isLegalMove
     * Wildcard dapat dikeluarkan kapanpun
     * @param Kartu yang dimainkan sebelumnya
     */
    public boolean isLegalMove(Card card) {
        return true;
    }
}