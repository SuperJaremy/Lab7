package Lab.Objects;



import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private final static long serialVersionUID=436274532780L;
    private Float x;
    private long y;
    public Float getX(){
        return x;
    }
    public long getY(){
        return y;
    }

    private Coordinates(Float x, long y){
        this.x=x;
        this.y=y;
    }

    public Coordinates(){}

    public static Coordinates getCoordinates(Float x, Long y){
        if(x!=null&&y!=null)
            if(x.compareTo(-801f)>0)
                return new Coordinates(x,y);
      return null;
    }

    @Override
    public String toString() {
        return "x=" + x +
                "\n y=" + y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return y == that.y &&
                x.equals(that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
