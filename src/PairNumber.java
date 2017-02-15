/**
 * Created by JoseLlorens on 14/02/2017.
 */
public class PairNumber implements Comparable {
  int despX,despY;

  public PairNumber(int despX, int despY) {
    this.despX = despX;
    this.despY = despY;
  }

  public int getDespX() {
    return despX;
  }

  public void setDespX(int despX) {
    this.despX = despX;
  }

  public int getDespY() {
    return despY;
  }

  public void setDespY(int despY) {
    this.despY = despY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PairNumber that = (PairNumber) o;

    if (despX != that.despX) return false;
    return despY == that.despY;

  }

  @Override
  public int hashCode() {
    int result = despX;
    result = 31 * result + despY;
    return result;
  }

  @Override
  public int compareTo(Object o) {
    PairNumber that = (PairNumber)o;
    if(this.despX*this.despY < that.despX*that.despY){
      return -1;
    }else{
      if(this.despX*this.despY > that.despX*that.despY){
        return 1;
      }else{
        if(despX<that.despX){
          return -1;
        }else{
          if (despX>that.despX){
            return 1;
          }else{
            return 0;
          }
        }
      }
    }
  }
}
