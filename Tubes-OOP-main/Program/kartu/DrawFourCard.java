/**
 * DrawFourCard.java
 * Kelas DrawFourCard sebagai jenis kartu Draw 4 (+4)
 */

package kartu;

public class DrawFourCard extends DrawTwoCard {
    final CardColor warnaKartu = CardColor.WILD;
    CardColor nextWarna;

    /**
     * Constructor DrawFourCard
     */
    public DrawFourCard(CardColor warnaKartu){
        super(warnaKartu);
    }

    /**
     * Getter warnaKartu
     */
    public CardColor getColor() {
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
        return  "Draw Four";
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
     * DrawFourCard dapat dikeluarkan kapanpun
     * @param Kartu yang dimainkan sebelumnya
     */
    public boolean isLegalMove(Card card) {
        return true;
    }
}