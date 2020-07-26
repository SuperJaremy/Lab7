package Lab.Objects;




import java.io.Serializable;
import java.nio.channels.FileLock;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public class Coordinates implements Serializable {
    private final static long serialVersionUID=436274532780L;
    private Float x;
    private long y;
    public Coordinates(Float x, long y) {
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
