package Lab.Objects;

import java.io.Serializable;

public enum MusicGenre implements Serializable {
    JAZZ,
    PUNK_ROCK,
    BRIT_POP;
    public static String list(){
        return "JAZZ\nPUNK_ROCK\nBRIT_POP";
    }
    private final static long serialVersionUID=32456738276545267L;
}
