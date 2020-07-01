package Lab.Commands;

import Lab.Objects.MusicBand;

import java.io.Serializable;

class Element implements Serializable {
    private static final long serialVersionUID=582376438772L;
    private int num;
    private Integer id;
    private MusicBand elem;
    private Element(){}

    int getNum() {
        return num;
    }
    Integer getId(){
        return id;
    }
    MusicBand getElem(){
        return elem;
    }
}
