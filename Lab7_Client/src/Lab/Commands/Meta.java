package Lab.Commands;

import Lab.Service.Authorizator;

import java.io.Serializable;

public class Meta implements Serializable {
    private static final long serialVersionUID = 4562371567263178L;
    public Meta(){
        username= Authorizator.getUsername();
        password=Authorizator.getPassword();
    }
    Meta(String name, Element element){
        this.name=name;
        this.element=element;
        username= Authorizator.getUsername();
        password=Authorizator.getPassword();
    }
    private String username;
    private String password;
    String name;
    Element element;
}
