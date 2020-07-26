package Lab.Commands;

import Lab.Service.Work;

import java.io.Serializable;

public abstract class Command{
    protected String name;
    public String getName(){
        return name;
    }
    public abstract Meta validate(Work work);
}
