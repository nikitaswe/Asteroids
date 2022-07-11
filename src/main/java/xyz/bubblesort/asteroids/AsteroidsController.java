package xyz.bubblesort.asteroids;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class AsteroidsController  implements Initializable {
  @FXML
  private Pane root;
  @FXML
  public Pane gameArea;
  @FXML
  public Text bestScoreText;
  @FXML
  private Text scoreText;

  private AtomicInteger score;
  private int bestScore;
  Map<KeyCode, Boolean> pressedKeys;


  private Ship ship;
  private List<Asteroid> asteroids;

  List<Projectile> projectiles;


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    root.requestFocus();
    restart();
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
          ship.turnLeft();
        }

        if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
          ship.turnRight();
        }

        if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
          ship.accelerate();
        }

        if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 3) {
          Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
          projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
          projectiles.add(projectile);

          projectile.accelerate();
          projectile.setMovement(projectile.getMovement().normalize().multiply(3));

          gameArea.getChildren().add(projectile.getCharacter());
        }

        List<Projectile> projectilesToRemove = projectiles.stream().filter(projectile -> {
          if (projectile.isOutOfBounds()) {
            return true;
          }
          List<Asteroid> collisions = asteroids.stream()
                .filter(asteroid -> asteroid.collide(projectile)).toList();
          if (collisions.isEmpty()) {
            return false;
          }

          collisions.forEach(collided -> {
            asteroids.remove(collided);
            scoreText.setText("Points: " + score.addAndGet(1));
            gameArea.getChildren().remove(collided.getCharacter());
          });

          return true;
        }).toList();

        projectilesToRemove.forEach(projectile -> {
          gameArea.getChildren().remove(projectile.getCharacter());
          projectiles.remove(projectile);
        });
        asteroids.forEach(asteroid -> {
          if (ship.collide(asteroid)) {
            bestScore = Math.max(score.get(), bestScore);
            restart();
          }
        });

        ship.move();
        asteroids.forEach(Asteroid::move);
        projectiles.forEach(Character::move);

        if (Math.random() < 0.01) {
          Asteroid asteroid = new Asteroid(AsteroidsApplication.WIDTH, AsteroidsApplication.HEIGHT);
          if (!asteroid.collide(ship)) {
            asteroids.add(asteroid);
            gameArea.getChildren().add(asteroid.getCharacter());
          }
        }
      }
    }.start();
  }

  public void restart() {
    this.gameArea.getChildren().clear();
    this.score = new AtomicInteger();
    this.asteroids = new ArrayList<>();
    this.projectiles = new ArrayList<>();
    this.pressedKeys = new HashMap<>();
    bestScoreText.setText("Best Score: " + bestScore);
    for (int i = 0; i < 5; i++) {
      Random rnd = new Random();
      Asteroid asteroid = new Asteroid(rnd.nextInt(AsteroidsApplication.WIDTH / 3), rnd.nextInt(AsteroidsApplication.HEIGHT));
      this.asteroids.add(asteroid);
    }
    this.ship = new Ship(AsteroidsApplication.WIDTH / 2, AsteroidsApplication.HEIGHT / 2);
    this.asteroids.forEach(asteroid -> this.gameArea.getChildren().add(asteroid.getCharacter()));
    this.gameArea.getChildren().add(this.ship.getCharacter());
  }

  @FXML
  protected void handlePressed(KeyEvent keyEvent) {
    pressedKeys.put(keyEvent.getCode(), Boolean.TRUE);
  }

  @FXML
  protected void handleReleased(KeyEvent keyEvent) {
    pressedKeys.put(keyEvent.getCode(), Boolean.FALSE);
  }

}