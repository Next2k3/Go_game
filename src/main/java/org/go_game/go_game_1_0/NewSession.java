package org.go_game.go_game_1_0;

import org.go_game.go_game_1_0.Board.StoneColor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.go_game.go_game_1_0.Board.StoneColor.BLACK;
import static org.go_game.go_game_1_0.Board.StoneColor.WHITE;

public class NewSession implements Runnable {

    public static int PLAYER1_WON = 1;
    public static int PLAYER2_WON = 2;
    public static int DRAW = 3;
    public static int CONTINUE = 4;

    private Socket firstPlayer;
    private Socket secondPlayer;
    private StoneColor[][] stoneColor = new StoneColor[9][9]; // NIE JESTEM PEWIEN

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

                int row = fromPlayer1.readInt();
                int column = fromPlayer1.readInt();
                // !!! ustawienie kamienia na wybranym miejscu !!!


                // OBIEKT TYPU PRZYCISK ?? (TO CZYM SA KAMINIE)
                stoneColor[row][column] = WHITE;


                // logika gry do wysylania wiadomosci o wybranym
                // miejscu na kamień
                // metoda do wybierania kamienia i zmiany jego koloru


                // LOGIKA do obsługi zasad i wygyranych
                // Sprawdzanie, czy gracz 1 wygrał swoim ruchem
                //if (isWon)

                toPlayer2.writeInt(CONTINUE);
                sendMove(toPlayer2, row, column);

                row = fromPlayer2.readInt();
                column = fromPlayer2.readInt();
                stoneColor[row][column] = BLACK;


            }
        } catch (IOException ex) {
            System.err.println("ex");
        }
    }

    private void sendMove(DataOutputStream out, int row, int column) throws IOException {
        out.writeInt(row);
        out.writeInt(column);
    }

    private boolean isWon() {
        return false;
    }


}
