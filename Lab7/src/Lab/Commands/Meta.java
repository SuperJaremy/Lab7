package Lab.Commands;

import java.io.Serializable;

public class Meta implements Serializable {
    private static final long serialVersionUID = 4562371567263178L;
    private String username;
    private String password;
    private String name;
    private Element element;
    private Meta(){}
    public String getName() {
        return name;
    }

    Element getElement() {
        return element;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
