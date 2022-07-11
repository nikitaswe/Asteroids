module xyz.bubblesort.asteroids {
  requires javafx.controls;
  requires javafx.fxml;


  opens xyz.bubblesort.asteroids to javafx.fxml;
  exports xyz.bubblesort.asteroids;
}