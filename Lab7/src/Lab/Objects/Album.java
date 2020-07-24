package Lab.Objects;






import java.io.Serializable;
import java.util.Objects;


public class Album implements Serializable {
    private final static long serialVersionUID=98537287843L;
    private String name;
    private long length;

    public String getName() {
        return name;
    }

    public long getLength() {
        return length;
    }

    private Album(String name, long length){
        this.name=name;
        this.length=length;
    }
    public Album(){}

    public static Album getAlbum(String name, Long length){
        if(name!=null&length!=null)
            return new Album(name,length);
        return null;
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

