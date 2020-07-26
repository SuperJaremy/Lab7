package Lab.Commands;

import Lab.Objects.MusicBand;

import java.io.Serializable;

class Element implements Serializable {
    private static final long serialVersionUID=582376438772L;
    Element(Integer id, MusicBand elem){
        this.id=id;
        this.elem=elem;
    }
    Element(int num){
        this.num=num;
    }
    private int num;
    private Integer id;
    private MusicBand elem;
}
