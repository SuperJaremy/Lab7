package Lab.Objects;





import java.io.Serializable;
import java.util.Objects;

public class Album implements Serializable {
    private final static long serialVersionUID=98537287843L;
    private String name;
    private long length=-1;
    String getName(){
        return name;
    }
    long getLength(){
        return length;
    }

    public Album(String name, long length){
        this.name=name;
        this.length=length;
    }

    @Override
    public String toString() {
        return "Название альбома: " + name +
                "\n Длина альбома: " + length;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return length == album.length &&
                name.equals(album.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, length);
    }


}

