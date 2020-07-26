package Lab.Objects;

import java.io.Serializable;

public enum MusicGenre implements Serializable {
    JAZZ,
    PUNK_ROCK,
    BRIT_POP;
    static public void list(){
        for(MusicGenre i:values())
            System.out.println(i.toString());
    }
    private final static long serialVersionUID=32456738276545267L;
}
