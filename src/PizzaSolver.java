import java.io.IOException;

/**
 * Created by JoseLlorens on 14/02/2017.
 */
public class PizzaSolver {
  public static void main(String[]args) throws IOException {
    Pizza pizza = new Pizza();
    pizza.init("example.in");
    pizza.writeResults("example.out");
    pizza.init("small.in");
    pizza.writeResults("small.out");
    pizza.init("medium.in");
    pizza.writeResults("medium.out");
    pizza.init("big.in");
    pizza.writeResults("big.out");
  }
}
