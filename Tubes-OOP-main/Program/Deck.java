/**
 * Deck.java
 * [Kelas Deck menyimpan list of Card]
 */

import java.util.*;
import kartu.*;

public class Deck {

    private static Card discardPile;
    private static int multipleDiscard = 0;
    private static List<Card> deckCard = new ArrayList<Card>();
    private static List<Card> playerCard = new ArrayList<Card>();
    private static int plusCounter = 0;
    private static boolean isDrawActive = false;
    private static boolean isSkipActive = false;
    private static boolean isReverseActive = false;

    /**
     * Constructor Deck
     */
    public Deck() {
        List<CardColor> colorList = new ArrayList<>();
        colorList.add(CardColor.MERAH);
        colorList.add(CardColor.HIJAU);
        colorList.add(CardColor.KUNING);
        colorList.add(CardColor.BIRU);
        for (CardColor color : colorList) {
            deckCard.add(new NumberCard(0, color));
            deckCard.add(new NumberCard(0, color));
            for (int i=0; i<4; i++) {
                deckCard.add(new NumberCard(1, color));
                deckCard.add(new NumberCard(2, color));
                deckCard.add(new NumberCard(3, color));
                deckCard.add(new NumberCard(4, color));
                deckCard.add(new NumberCard(5, color));
                deckCard.add(new NumberCard(6, color));
                deckCard.add(new NumberCard(7, color));
                deckCard.add(new NumberCard(8, color));
                deckCard.add(new NumberCard(9, color));
                deckCard.add(new SkipCard(color));
                deckCard.add(new SkipCard(color));
                deckCard.add(new ReverseCard(color));
                deckCard.add(new ReverseCard(color));
                deckCard.add(new DrawTwoCard(color));
                deckCard.add(new DrawTwoCard(color));
            }
        }
        for (int i = 0; i < 4; i++) {
            deckCard.add(new DrawFourCard(CardColor.WILD));
            deckCard.add(new Wildcard(CardColor.WILD));
        }
        randomCard();
        while (!(getTopCard() instanceof NumberCard)){
            randomCard();
        }
        discardPile = getTopCard();
        moveCardtoPlayer();
    }

    /**
     * Mengacak isi list Card dalam Deck
     */
    public static void randomCard() {
        Collections.shuffle(deckCard);
    }

    /**
     * Memindahkan Card teratas pada deckCard ke playerCard
     */
    public static void moveCardtoPlayer() {
        playerCard.add(getTopCard());
        deckCard.remove(getTopCard());
        randomCard();
    }

    /**
     * Memindahkan Card dari Player ke deckCard
     * @param movedCard objeck Card
     */
    public static void moveCardtoDeck(Card movedCard) {
        deckCard.add(movedCard);
        playerCard.remove(movedCard);
        randomCard();
    }

    /**
     * Getter topCard
     * yaitu indeks-0 dari Arraylist deckCard
     */
    public static Card getTopCard() {
        return deckCard.get(0);
    }

    /**
     * Getter&Setter DiscardPile
     */
    public static void setDiscardPile(Card card){
        discardPile = card;
    }
    public static Card getDiscardPile(){
        return discardPile;
    }
    public static int getMultipleDiscard(){
        return multipleDiscard;
    }
    public static void setMultipleDiscard(int n){
        multipleDiscard = n;
    }
    public static int getPlusCounter(){
        return plusCounter;
    }
    public static void resetPlusCounter(){
        plusCounter = 0;
    }
    public static void addPlusCounter(int n){
        plusCounter += n;
    }
    public static boolean getIsDrawActive(){
        return isDrawActive;
    }
    public static void setIsDrawActive(boolean b){
        isDrawActive = b;
    }
    public static boolean getIsSkipActive(){
        return isSkipActive;
    }
    public static void setIsSkipActive(boolean b){
        isSkipActive = b;
    }
    public static boolean getIsReverseActive(){
        return isReverseActive;
    }
    public static void setIsReverseActive(boolean b){
        isReverseActive = b;
    }
}