package Lab.Commands;

import Lab.Service.Answer;
import Lab.Service.Work;


public abstract class Command{
    protected String name;
    protected String description;
    protected boolean rewrite=false;
    protected boolean exit=false;
    public String getName(){
        return name;
    }
    public abstract String describe();
    public abstract Answer act(Meta meta, Work work);
    public boolean getRewrite(){
        return rewrite;
    }
}
