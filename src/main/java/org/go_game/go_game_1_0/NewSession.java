package org.go_game.go_game_1_0;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NewSession implements Runnable {

    public static int PLAYER1_WON = 1;
    public static int PLAYER2_WON = 2;
    public static int DRAW = 3;
    public static int CONTINUE = 4;

    private Socket firstPlayer;
    private Socket secondPlayer;

    public NewSession(Socket firstPlayer, Socket secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        // stworzenie okręgów na kamienie na planszy?
    }

    @Override
    public void run() {
        try {
            DataInputStream fromPlayer1 = new DataInputStream(firstPlayer.getInputStream());
            DataOutputStream toPlayer1 = new DataOutputStream(firstPlayer.getOutputStream());
            DataInputStream fromPlayer2 = new DataInputStream(secondPlayer.getInputStream());
            DataOutputStream toPlayer2 = new DataOutputStream(secondPlayer.getOutputStream());

            // notyfikacja gracza 1, że gracz 2 dołączył
            toPlayer1.writeInt(1);

            while (true) {
                // logika gry do wysylania wiadomosci o wybranym
                // miejscu na kamień
                // metoda do wybierania kamienia i zmiany jego koloru


                // LOGIKA do obsługi zasad i wygyranych
                // Sprawdzanie, czy gracz 1 wygrał swoim ruchem
//                if (isWon)



            }
        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(int row, int col) throws IOException {

    }

    private boolean isWon() {
        return false;
    }


}
