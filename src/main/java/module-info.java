module org.go_game.go_game_1_0 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.go_game.go_game_1_0 to javafx.fxml;
    exports org.go_game.go_game_1_0;
}