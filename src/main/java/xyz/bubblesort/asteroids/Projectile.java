package xyz.bubblesort.asteroids;

import javafx.scene.shape.Polygon;

public class Projectile extends Character {

  public Projectile(int x, int y) {
    super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
  }
  @Override
  public void move() {
    Polygon projectile = this.getCharacter();
    projectile.setTranslateX(projectile.getTranslateX() + this.getMovement().getX());
    projectile.setTranslateY(projectile.getTranslateY() + this.getMovement().getY());
  }

}
