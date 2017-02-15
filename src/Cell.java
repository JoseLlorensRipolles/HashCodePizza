/**
 * Created by JoseLlorens on 14/02/2017.
 */
public class Cell {
  char ingredient;
  Slice slice;

  public Cell(char ingredient) {
    this.ingredient = ingredient;
    this.slice = null;
  }

  public Slice getSlice() {
    return slice;
  }

  public void setSlice(Slice slice) {
    this.slice = slice;
  }

  public char getIngredient() {
    return ingredient;
  }
}
