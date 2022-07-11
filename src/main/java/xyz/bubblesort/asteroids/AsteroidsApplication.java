package xyz.bubblesort.asteroids;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AsteroidsApplication extends Application {

  public static int WIDTH = 480;
  public static int HEIGHT = 360;

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(AsteroidsApplication.class.getResource("hello-view.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    stage.setTitle("Asteroids!");
    stage.setScene(scene);
    stage.show();
    root.requestFocus();
    stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
      if (KeyCode.ESCAPE == event.getCode()) {
        stage.close();
      }
    });
  }

  public static void main(String[] args) {
    launch();
  }
}