package org.go_game.go_game_1_0;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class RoundButtonsGrid {

    private final Color[][] buttonColors;
    private final double lineSize;

    public RoundButtonsGrid(Color[][] buttonColors) {
        this.buttonColors = buttonColors;
        this.lineSize = 25;
    }

    private class ButtonWithLines extends javafx.scene.layout.StackPane {
        public ButtonWithLines(Button button, Line lineV, Line lineH) {
            getChildren().addAll(lineV, lineH, button);
        }
    }

    public GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        double buttonSize = 2 * lineSize; // Rozmiar przycisku równy dwukrotności rozmiaru linii
        double gap = 3 * lineSize; // Odstęp między przyciskami równy trzykrotnej wielkości linii
        double margin = 0.0;

        // Tworzymy przyciski w centrum komórek GridPane z odstępami i marginesami
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button.setMinSize(buttonSize, buttonSize);
                button.setStyle("-fx-background-radius: 50%; -fx-base: #" + toHex(buttonColors[i][j]));

                // Linie pionowa
                Line lineV = new Line();
                lineV.setStartX(buttonSize / 2);
                lineV.setStartY(0);
                lineV.setEndX(buttonSize / 2);
                lineV.setEndY(buttonSize);

                // Linie pozioma
                Line lineH = new Line();
                lineH.setStartX(0);
                lineH.setStartY(buttonSize / 2);
                lineH.setEndX(buttonSize);
                lineH.setEndY(buttonSize / 2);

                // Ustawianie grubości linii
                lineV.setStrokeWidth(5);
                lineH.setStrokeWidth(5);

                ButtonWithLines buttonWithLines = new ButtonWithLines(button, lineV, lineH);
                gridPane.add(buttonWithLines, j, i);

                GridPane.setHalignment(buttonWithLines, javafx.geometry.HPos.CENTER);
                GridPane.setValignment(buttonWithLines, javafx.geometry.VPos.CENTER);
                GridPane.setMargin(buttonWithLines, new javafx.geometry.Insets(margin));
            }
        }

        return gridPane;
    }

    private String toHex(Color color) {
        return String.format("%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}











