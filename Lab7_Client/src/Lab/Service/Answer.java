package Lab.Service;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 463274289482L;
    private String answer;
    private boolean exit;
    private boolean success;

    private Answer(){}

    public String getAnswer() {
        return answer;
    }

    public boolean isExit(){
        return exit;
    }

    public boolean isSuccess(){
        return success;
    }

}
