package Lab.Service;

import java.io.Serializable;

public class Answer implements Serializable {
    private static final long serialVersionUID = 463274289482L;
    private String answer;
    private boolean success;
    private boolean exit;
    public Answer(String answer, boolean success, boolean exit){
        this.answer=answer;
        this.success=success;
        this.exit=exit;
    }
    boolean isSuccess(){
        return success;
    }
}
