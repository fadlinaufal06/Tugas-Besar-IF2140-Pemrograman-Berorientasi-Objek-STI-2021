/**
 * Game.java
 * [Class Game]
 */

import java.util.*;
import kartu.*;

public class Game {

    private final List<Player> playerList;
    private int playerTurnIdx;
    private boolean reversed;
    private boolean harusDeclareHIJI = false;
    private boolean sudahDeclareHIJI = false;
    private boolean nextTurn = false;
    private String winner = "_NO_WINNER_";
    private boolean sudahKenaTimer = false;

    /**
     * Constructor Game
     */
    public Game (List<Player> playerList){
        this.playerList = playerList;
    }


    /**
     * Method ngacak turn awal
     */
    public void randomTurn(){
        Collections.shuffle(playerList);
    }

    /**
     * Method turn pertama / start game
     */
    public void startGame(){
        randomTurn();
        playerTurnIdx = 0;
        reversed = false;
    }


    /**
     * Method playerDraw
     */
    public void playerDraw(){

        // Print kartu di discard pile
        System.out.println();
        System.out.println("Kartu di discard pile: "+Deck.getDiscardPile().toString());

        // Cek apakah kartu di discard pile kartu draw
        // Kalau draw berarti player wajib draw sebanyak counter
        if ((Deck.getDiscardPile() instanceof DrawTwoCard || Deck.getDiscardPile() instanceof DrawFourCard)&&Deck.getIsDrawActive()){
            
            System.out.println("Kamu harus draw "+Deck.getPlusCounter()+" kartu");
            System.out.println("Draw otomatis.");
            getPlayerInTurn().draw(Deck.getPlusCounter());
            Deck.resetPlusCounter();
            Deck.setIsDrawActive(false);
            // Selesai draw

            // Pindah giliran
            nextTurn();
            System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());

        }
        // Kalau bukan 
        else {
            // Draw 1
            getPlayerInTurn().draw(1);
            Deck.setIsSkipActive(false);
            Deck.setIsReverseActive(false);

            // Cek apa bisa langsung discard
            if (getPlayerInTurn().discardAfterDraw()){
                System.out.println("Kamu bisa langsung mengeluarkan kartu yang kamu ambil!");
                System.out.println("1. Tidak discard");
                System.out.println("2. Langsung discard");
                System.out.print("Pilihan:");
                int pilihan = Scan.intScanner();

                while (pilihan<1 || pilihan>2){
                    System.out.print("Masukan pilihan salah. Pilihan:");
                    pilihan = Scan.intScanner();
                }
                if (pilihan==2){
                    getPlayerInTurn().discardLastCard();
                    // Aktifin power
                    if (Deck.getDiscardPile() instanceof DrawTwoCard || Deck.getDiscardPile() instanceof DrawFourCard) Deck.setIsDrawActive(true);
                    else if (Deck.getDiscardPile() instanceof ReverseCard) Deck.setIsReverseActive(true);
                    else if (Deck.getDiscardPile() instanceof SkipCard) Deck.setIsSkipActive(true);
                    // Khusus draw 4
                    pilihNextWarna();
                }
            }

            // Pindah giliran
            nextTurn();
            System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());
        }
    }

    /**
     * Method playerDiscard
     */
    public void playerDiscard(){

        // Print kartu di discard pile
        System.out.println();
        System.out.println("Kartu di discard pile: "+Deck.getDiscardPile().toString());

        /**
         * Flow:
         * 1. Kalo kartu di discard pile itu draw dan ga discardable wajib draw
         * 2. Kalo bukan cek apakah discardable
         * 3. Keluarin semua pilihan yang bisa didiscard
         * 4. Input, tmp, dst jangan lupa clear
         * 5. Multiple discardable???
         * 6. Kalo ga discardable ya wajib draw
         */

        if (Deck.getDiscardPile() instanceof DrawFourCard && Deck.getIsDrawActive()){
            System.out.println("Kamu harus draw.");
        }
        else if(Deck.getDiscardPile() instanceof DrawTwoCard && Deck.getIsDrawActive()){

            if (getPlayerInTurn().haveDrawCard()){

                // Print + minta pilihan discard
                System.out.println("Pilih 0 untuk balik ke menu.");
                System.out.println("List Kartu yang bisa didiscard:");
                int n = getPlayerInTurn().printDrawCard()-1;
                System.out.print("Pilihan:");
                int pilihan = Scan.intScanner();
                Deck.setMultipleDiscard(1);

                // Discard
                while (pilihan<0 || pilihan >n){
                    System.out.print("Masukan pilihan salah. Pilihan:");
                    pilihan = Scan.intScanner();
                }
                if (pilihan!=0){
                    getPlayerInTurn().discard(getPlayerInTurn().getTmpCardList(pilihan-1));
                    getPlayerInTurn().clearTmpCardList();

                    // Multiple discard
                    playerMultipleDiscard();

                    // Khusus wildcard/draw 4
                    pilihNextWarna();

                    if (getPlayerInTurn().getNumOfCard()==1) {
                        declareHIJICommand();
                    } else {
                        // Next turn
                        nextTurn();
                        System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());
                    }
                }
            }
            else {
                System.out.println("Kamu harus draw.");
            }
        }
        else if (getPlayerInTurn().discardable()&&!(Deck.getIsDrawActive())){
            // Print + minta pilihan discard
            System.out.println("Pilih 0 untuk balik ke menu.");
            System.out.println("List Kartu yang bisa didiscard:");
            int n = getPlayerInTurn().printDiscardable()-1;
            System.out.print("Pilihan:");
            int pilihan = Scan.intScanner();

            // Discard
            while (pilihan<0 || pilihan >n){
                System.out.print("Masukan pilihan salah. Pilihan:");
                pilihan = Scan.intScanner();
            }
            if (pilihan!=0){
                getPlayerInTurn().discard(getPlayerInTurn().getTmpCardList(pilihan-1));
                getPlayerInTurn().clearTmpCardList();
                Deck.setMultipleDiscard(1);

                // Multiple discard
                playerMultipleDiscard();

                // Aktifin power
                if (Deck.getDiscardPile() instanceof DrawTwoCard || Deck.getDiscardPile() instanceof DrawFourCard) Deck.setIsDrawActive(true);
                else if (Deck.getDiscardPile() instanceof ReverseCard) Deck.setIsReverseActive(true);
                else if (Deck.getDiscardPile() instanceof SkipCard) Deck.setIsSkipActive(true);

                // Khusus wildcard/draw 4
                pilihNextWarna();

                if (getPlayerInTurn().getNumOfCard()==1) {
                    declareHIJICommand();
                } else {
                    // Next turn
                    nextTurn();
                    System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());
                }
            }
        }
        else {
            System.out.println("Kamu harus draw.");
        }
    }

    /**
     * Method untuk setelah discard wildcard / draw 4 dimana butuh pilih next warna
     */
    private void pilihNextWarna(){
        if (Deck.getDiscardPile() instanceof Wildcard){
            System.out.println();
            System.out.println("Kamu mengeluarkan wildcard, pilihan warna:");
            System.out.println("1. Merah");
            System.out.println("2. Kuning");
            System.out.println("3. Hijau");
            System.out.println("4. Biru");
            System.out.print("Pilihan:");
            int n = Scan.intScanner();
            while (n<1||n>4){
                System.out.println("Pilihan salah, pilihan warna:");
                System.out.println("1. Merah");
                System.out.println("2. Kuning");
                System.out.println("3. Hijau");
                System.out.println("4. Biru");
                n = Scan.intScanner();
            }
            switch (n){
                case 1:
                    ((Wildcard)(Deck.getDiscardPile())).setNextWarna(CardColor.MERAH);
                    break;
                case 2:
                    ((Wildcard)(Deck.getDiscardPile())).setNextWarna(CardColor.KUNING);
                    break;
                case 3:
                    ((Wildcard)(Deck.getDiscardPile())).setNextWarna(CardColor.HIJAU);
                    break;
                case 4:
                    ((Wildcard)(Deck.getDiscardPile())).setNextWarna(CardColor.BIRU);
                    break;
            }
        }
        else if (Deck.getDiscardPile() instanceof DrawFourCard){
            System.out.println();
            System.out.println("Kamu mengeluarkan DrawFour, pilihan warna:");
            System.out.println("1. Merah");
            System.out.println("2. Kuning");
            System.out.println("3. Hijau");
            System.out.println("4. Biru");
            System.out.print("Pilihan:");
            int n = Scan.intScanner();
            while (n<1||n>4){
                System.out.println("Pilihan salah, pilihan warna:");
                System.out.println("1. Merah");
                System.out.println("2. Kuning");
                System.out.println("3. Hijau");
                System.out.println("4. Biru");
                n = Scan.intScanner();
            }
            switch (n){
                case 1:
                    ((DrawFourCard)(Deck.getDiscardPile())).setNextWarna(CardColor.MERAH);
                    break;
                case 2:
                    ((DrawFourCard)(Deck.getDiscardPile())).setNextWarna(CardColor.KUNING);
                    break;
                case 3:
                    ((DrawFourCard)(Deck.getDiscardPile())).setNextWarna(CardColor.HIJAU);
                    break;
                case 4:
                    ((DrawFourCard)(Deck.getDiscardPile())).setNextWarna(CardColor.BIRU);
                    break;
            }
        }
    }

    /**
     * Method untuk multiple discard
     */
    private void playerMultipleDiscard(){
        System.out.println();
        while (getPlayerInTurn().multipleDiscardable(Deck.getDiscardPile())){
            System.out.println("Kamu bisa multiple discard. Pilih 0 untuk tidak multiple discard.");
            int n = getPlayerInTurn().printMultipleDiscardable(Deck.getDiscardPile())-1;
            System.out.print("Pilihan:");
            int pilihan = Scan.intScanner();
            while (pilihan<0 || pilihan >n){
                System.out.print("Masukan pilihan salah. Pilihan:");
                pilihan = Scan.intScanner();
            }
            if (pilihan==0) break;
            else {
                getPlayerInTurn().discard(getPlayerInTurn().getTmpCardList(pilihan-1));
                getPlayerInTurn().clearTmpCardList();
                Deck.setMultipleDiscard(Deck.getMultipleDiscard()+1);
                if (Deck.getDiscardPile() instanceof DrawFourCard){
                    Deck.addPlusCounter(4);
                }
                else if (Deck.getDiscardPile() instanceof DrawTwoCard){
                    Deck.addPlusCounter(2);
                }
            }
        }
    }

    /**
     * Method turn selanjutnya
     */
    public void nextTurn() {

        // reset declare HIJI
        setNextTurn();

        // Cek ada pemenang ngga
        if (getPlayerInTurn().getNumOfCard()==0){
            winner = getPlayerInTurn().getNama();
        }

        // Cek kartu di discard Pile
        if (Deck.getDiscardPile() instanceof NumberCard){
            nextIdxNumberCard(1);
        }
        else if (Deck.getDiscardPile() instanceof SkipCard){
            if (Deck.getIsSkipActive()){
                nextIdxNumberCard(Deck.getMultipleDiscard()+1);
                Deck.setIsReverseActive(false);
            }
            else nextIdxNumberCard(1);
        }
        else if (Deck.getDiscardPile() instanceof ReverseCard){
            if (Deck.getIsReverseActive()){
                reverseIdx(Deck.getMultipleDiscard());
                Deck.setIsReverseActive(false);
            }
            nextIdxNumberCard(1);
        }
        else if (Deck.getDiscardPile() instanceof DrawFourCard){
            if (Deck.getIsDrawActive()) Deck.addPlusCounter(4);
            nextIdxNumberCard(1);
        }
        else if (Deck.getDiscardPile() instanceof DrawTwoCard){
            if (Deck.getIsDrawActive()) Deck.addPlusCounter(2);
            nextIdxNumberCard(1);
        }
        else if (Deck.getDiscardPile() instanceof Wildcard){
            nextIdxNumberCard(1);
        }
        
    }

    public void setNextTurn() {
        nextTurn = true;
    }
    public boolean getNextTurn() {
        return nextTurn;
    }
    public void resetNextTurn() {
        nextTurn = false;
    }

    /**
     * Next index, looping kalau skip
     */
    private void nextIdxNumberCard(int n){
        for (int i=0; i<n; i++){
            if (reversed) {
                if (playerTurnIdx == 0) {
                    playerTurnIdx = playerList.size() - 1;
                } else {
                    playerTurnIdx -= 1;
                }
            }
            else {
                if (playerTurnIdx == playerList.size() - 1) {
                    playerTurnIdx = 0;
                } else {
                    playerTurnIdx++;
                }
            }
        }
    }
    
    /**
     * Powernya reverse card
     */
    private void reverseIdx(int n){
        for (int i=0; i<n; i++){
            if (reversed) reversed = false;
            else reversed = true;
        }
    }

    /**
     * Getter player yang saat ini gilirannya
     */
    public Player getPlayerInTurn(){
        return playerList.get(playerTurnIdx);    
    }

    /**
     * Printer list player
     */
    public void printPlayerList(){
        int n = 1;
        System.out.println();
        for (Player myPlayer : playerList){
            System.out.println("Player "+n+ ": "+myPlayer.getNama());
            System.out.println("Jumlah Kartu: "+ myPlayer.getNumOfCard());
            if(myPlayer.equals(getPlayerInTurn())) System.out.println("Sedang giliran");
            else System.out.println("Tidak sedang giliran");
            System.out.println();
            n++;
        }
    }

    /**
     * Printer player saat ini dan setelah ini
     */
    public void printPlayerInTurn(){
        System.out.println();
        System.out.print("Saat ini giliran Player : ");
        System.out.println(getPlayerInTurn().getNama());
        System.out.print("Selanjutnya giliran Player : ");
        if (reversed) {
            if (playerTurnIdx == 0) {
                System.out.println(playerList.get(playerList.size()-1).getNama());
            }
            else {
                System.out.println(playerList.get(playerTurnIdx-1).getNama());
            }
        }
        else {
            if (playerTurnIdx == playerList.size() - 1) {
                System.out.println(playerList.get(0).getNama());
            } 
            else {
                System.out.println(playerList.get(playerTurnIdx+1).getNama());
            }
        }
    }


    /**
     * Semua fungsi untuk declare HIJI
     */
    public void playerDeclareHIJI() {
        getPlayerInTurn().declareHIJI();
        setSudahDeclareHIJI();
        if (getPlayerInTurn().getNumOfCard()==1) {
            System.out.println();
            System.out.println("Satu kartu lagi menuju kemenangan.");
            nextTurn();
            System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());
        } else {
            System.out.println();
            System.out.println("Kartu kamu masih banyak!!! Kamu otomatis draw 2 kartu.");
            getPlayerInTurn().draw(2);
        }
    }

    public void declareHIJICommand(){
        harusDeclareHIJI = true;
        sudahDeclareHIJI = false;
    }
    
    public boolean getDeclareHIJICommand(){
        return harusDeclareHIJI && !sudahDeclareHIJI;
    }

    public boolean getharusDeclareHIJI() {
        return harusDeclareHIJI;
    }

    public void setSudahDeclareHIJI() {
        sudahDeclareHIJI = true;
    }

    public boolean getSudahDeclareHIJI() {
        return sudahDeclareHIJI;
    }

    public void resetDeclareHIJI() {
        harusDeclareHIJI = false;
        sudahDeclareHIJI = false;
        sudahKenaTimer = false;
    }

    public boolean isSudahKenaTimer(){
        return sudahKenaTimer;
    }

    public class TimeOutHIJI extends TimerTask {
        private Thread t;
        private Timer timer;
    
        TimeOutHIJI(Thread t, Timer timer){
            this.t = t;
            this.timer = timer;
        }
     
        public void run() {
            if (t != null && t.isAlive()) {
                t.interrupt();
                timer.cancel();
            }
        }
    }
    
    public class CheckRunningHIJI implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Thread.sleep(10);
                    if (getSudahDeclareHIJI()) {
                        resetDeclareHIJI();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                // log error
                if (!getSudahDeclareHIJI()) {
                    System.out.println();
                    System.out.println("Waktu kamu untuk declare HIJI habis!!! Kamu otomatis draw 2 kartu.");
                    getPlayerInTurn().draw(2);
                    nextTurn();
                    resetDeclareHIJI();
                    System.out.println("Giliran kamu selesai, giliran selanjutnya: "+getPlayerInTurn().getNama());
                    System.out.println("Tekan enter untuk proses selanjutnya.");
                }
            }
        }
    }
    public void startTimerHIJI() {
        Thread t = new Thread(new CheckRunningHIJI());
        Timer timer = new Timer();
        //System.out.println("Start timer");
        timer.schedule(new TimeOutHIJI(t, timer), 3*1000);
        t.start();
        sudahKenaTimer = true;
    }

    /**
     * Getter nama pemenang
     */
    public String getWinner(){
        return winner;
    }
}
