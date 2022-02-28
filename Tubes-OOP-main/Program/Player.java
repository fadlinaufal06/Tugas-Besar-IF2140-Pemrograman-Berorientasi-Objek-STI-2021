  
/**
 * Player.java
 * [Kelas Player] Masih banyak bingung 
 */

import java.util.*;
import kartu.*;

public class Player {
    private String nama;
    static int jumlahPlayer = 0;
    private List<Card> playerCardList = new ArrayList<Card>();
    private List<Card> tmpCardList = new ArrayList<Card>();

    public Player (String nama){
        this.nama = nama;   
        jumlahPlayer ++;
        // Jumlah kartu awal 7 (3 khusus untuk testing)
        for (int i=0; i<7; i++){
            playerCardList.add(Deck.getTopCard());
            Deck.moveCardtoPlayer();
        }
    }

    public void draw(int n){
        for (int i=0; i<n; i++){
            playerCardList.add(Deck.getTopCard());
            System.out.println("Kartu yang didraw: "+((CardMethod)Deck.getTopCard()).toString());
            Deck.moveCardtoPlayer();
        }
    }

    public void discard(Card card){
        Deck.moveCardtoDeck(Deck.getDiscardPile());
        Deck.setDiscardPile(card);
        System.out.println("Kartu yang didiscard: "+((CardMethod)card).toString());
        playerCardList.remove(card);
    }

    public void cardList(){
        int n = 1;
        System.out.println();
        System.out.println("List kartu:");
        for (Card myCard : playerCardList){
            System.out.println(n + ". "+((CardMethod)myCard).toString());
            n++;
        }
    }

    public boolean discardable(){
        boolean retVal = false;
        // cek apa ada kartu yg bisa didiscard
        for (Card myCard : playerCardList){
            if(((CardMethod)myCard).isLegalMove(Deck.getDiscardPile())){
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public int printDiscardable(){
        int n = 1;
        System.out.println();
        for (Card myCard : playerCardList){
            if(((CardMethod)myCard).isLegalMove(Deck.getDiscardPile())){
                tmpCardList.add(myCard);
                System.out.println(n + ". "+((CardMethod)myCard).toString());
                n++;
            }
        }
        return n;
    }

    public boolean multipleDiscardable(Card card){
        boolean retVal = false;

        for (Card myCard : playerCardList){
            if(((CardMethod)myCard).isEqual(card)){
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    public int printMultipleDiscardable(Card card){
        int n = 1;
        System.out.println();
        for (Card myCard : playerCardList){
            if(((CardMethod)myCard).isEqual(card)){
                tmpCardList.add(myCard);
                System.out.println(n + ". "+((CardMethod)myCard).toString());
                n++;
            }
        }
        return n;
    }

    public boolean haveDrawCard(){
        boolean retVal = false;
        for (Card myCard : playerCardList){
            if(myCard instanceof DrawFourCard || myCard instanceof DrawTwoCard){
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public int printDrawCard(){
        int n = 1;
        System.out.println();
        for (Card myCard : playerCardList){
            if(myCard instanceof DrawFourCard || myCard instanceof DrawTwoCard){
                tmpCardList.add(myCard);
                System.out.println(n + ". "+((CardMethod)myCard).toString());
                n++;
            }
        }
        return n;
    }

    public boolean discardAfterDraw(){
        return ((CardMethod)(playerCardList.get(playerCardList.size()-1))).isLegalMove(Deck.getDiscardPile());
    }

    public void discardLastCard(){
        discard(playerCardList.get(playerCardList.size()-1));
    }

    public void declareHIJI() {
        System.out.println("HIJI!!!");
    }

    public void clearTmpCardList(){
        tmpCardList.clear();
    }
    public Card getTmpCardList(int n){
        return tmpCardList.get(n);
    }

    public String getNama(){
        return nama;
    }

    public int getNumOfCard(){
        return playerCardList.size();
    }

}
