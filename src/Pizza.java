import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by JoseLlorens on 14/02/2017.
 */
public class Pizza {
  Cell[][] cells;
  int rows, columns, minIngredients, maxSizeSlice;
  Set<PairNumber> posibleSlices;
  List<Slice> sliceList;

  public Pizza() {
  }

  public void init(String s) throws IOException {

    BufferedReader bf = null;
    try {
      bf = new BufferedReader(new FileReader("resources/"+s));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    String line;

    line = bf.readLine();
    String[] numbers  = line.split(" ");
    rows = Integer.parseInt(numbers[0]);
    columns = Integer.parseInt(numbers[1]);
    minIngredients = Integer.parseInt(numbers[2]);
    maxSizeSlice = Integer.parseInt(numbers[3]);

    cells = new Cell[rows][columns];

    sliceList = new ArrayList();

    posibleSlices = getPosibleSlices(maxSizeSlice);
    for(int i = 0 ; i<rows;i++){
      line = bf.readLine();
      for (int j = 0; j<columns;j++){
        cells[i][j] = new Cell(line.charAt(j));
      }
    }

    for(int i = 0; i<rows;i++){
      for(int j = 0; j<columns;j++){
        if(cells[i][j].getSlice() == null){
          doSliceIfPosibleIn(i,j);
        }
      }
    }

    for(Slice slice: sliceList){
      int x1,x2,y1,y2;
      x1 = slice.getX1();
      x2 = slice.getX2();
      y1 = slice.getY1();
      y2 = slice.getY2();


      //Expand top
      boolean possible = true;
      if(x1-1 >=0) {
        while (possible) {
          for (int j = y1; j < y2; j++) {
            if (cells[x1 - 1][j].getSlice() != null) {
              possible = false;
              break;
            }
          }

          if (possible && (x2 - x1) * (y2 - y1) + (y2 - y1) <= maxSizeSlice) {
            for (int j = y1; j < y2; j++) {
              cells[x1 - 1][j].setSlice(slice);
            }
            slice.setX1(x1 - 1);
          }else{
            possible = false;
          }
        }
      }

      x1 = slice.getX1();
      x2 = slice.getX2();
      y1 = slice.getY1();
      y2 = slice.getY2();
      //Expand bot
      possible = true;
      if(x2<rows){
        while (possible){
          for (int j = y1; j < y2; j++) {
            if (cells[x2][j].getSlice() != null) {
              possible = false;
              break;
            }
          }

          if (possible && (x2 - x1) * (y2 - y1) + (y2 - y1) <= maxSizeSlice) {
            for (int j = y1; j < y2; j++) {
              cells[x2][j].setSlice(slice);
            }
            slice.setX2(x2 +1);
          }else{
            possible = false;
          }

        }
      }

      x1 = slice.getX1();
      x2 = slice.getX2();
      y1 = slice.getY1();
      y2 = slice.getY2();
      //Expand left
      possible = true;
      if(y1-1>=0){
        while(possible){
          for(int i = x1; i<x2;i++){
            if(cells[i][y1-1].getSlice()!=null){
              possible = false;
              break;
            }
          }

          if(possible && (x2 - x1) * (y2 - y1)+(x2-x1)<=maxSizeSlice){
            for(int i = x1; i<x2;i++){
              cells[i][y1-1].setSlice(slice);
            }
            slice.setY1(y1-1);
          }else{
            possible = false;
          }
        }
      }

      x1 = slice.getX1();
      x2 = slice.getX2();
      y1 = slice.getY1();
      y2 = slice.getY2();
      //Expand right
      possible = true;
      if(y2<columns){
        while(possible){
          for(int i = x1; i<x2;i++){
            if(cells[i][y2].getSlice()!=null){
              possible = false;
              break;
            }
          }
          if(possible && (x2 - x1) * (y2 - y1)+(x2-x1)<=maxSizeSlice){
            for(int i = x1; i<x2;i++){
              cells[i][y2].setSlice(slice);
            }
            slice.setY2(y2+1);
          }else{
            possible = false;
          }
        }
      }

    }
    int count = 0;
    for(int i = 0; i<rows;i++) {
      for (int j = 0; j < columns; j++) {
        if(cells[i][j].getSlice()!=null){
          count++;
        }
      }
    }
    System.out.println(s+"\tPuntuaction: "+count+"\tof: "+rows*columns +"\t("+((double)count/(rows*columns))*100+"%)");
  }

  public void writeResults(String s){
    try{
      PrintWriter writer = new PrintWriter("resources/"+s, "UTF-8");
      writer.println(sliceList.size());
      for(Slice slice: sliceList){
        int x2 = slice.getX2()-1;
        int y2 = slice.getY2()-1;
        writer.println(""+slice.getX1()+""+slice.getY1()+""+x2+""+y2);
      }
      writer.close();
    } catch (IOException e) {
      // do something(or not)
    }
  }

  private void doSliceIfPosibleIn(int i, int j) {
    int tomatoes=0, mushroms = 0;
    boolean possible = true;
    for(PairNumber pair: posibleSlices){
      for(int x=i;x<i+pair.getDespX() && i+pair.getDespX()<rows+1 && possible;x++){
        for (int y=j; y<j+pair.getDespY() && j+pair.getDespY() < columns+1 && possible ;y++){
          if(cells[x][y].getSlice()!=null){
            possible = false;
            break;
          }
          if(cells[x][y].getIngredient()=='T'){
            tomatoes++;
          }else{
            mushroms++;
          }
        }
      }
      if(possible){
        if(tomatoes >= minIngredients && mushroms >= minIngredients){
          Slice slice = new Slice(i,i+pair.getDespX(),j,j+pair.getDespY());
          for(int x=i;x<i+pair.getDespX();x++) {
            for (int y = j; y < j + pair.getDespY(); y++) {
              cells[x][y].setSlice(slice);
            }
          }
          sliceList.add(slice);
          return;
        }
      }
      possible = true;
      tomatoes = 0; mushroms = 0;
    }
  }

  public Set<PairNumber> getPosibleSlices(int maxSizeSlice) {
    Set posSlices = new TreeSet<PairNumber>();
    for(int i =  2; i<=maxSizeSlice; i++){
      for(int j = 1; j<= i;j++){
        posSlices.add(new PairNumber(i/j,j));
      }
    }
    return posSlices;
  }
}
