module org.go_game.go_game_1_0 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;

    opens org.go_game.go_game_1_0 to javafx.fxml;
    exports org.go_game.go_game_1_0;
    exports org.go_game.go_game_1_0.Board;
    opens org.go_game.go_game_1_0.Board to javafx.fxml;

    opens org.go_game.go_game_1_0.ClientApp to javafx.graphics;
}