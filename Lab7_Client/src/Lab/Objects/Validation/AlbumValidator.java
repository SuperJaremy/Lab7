package Lab.Objects.Validation;

import Lab.Objects.Album;


public abstract class AlbumValidator {
    protected String name;
    protected long length;
    protected boolean isNull=false;
    int lines=0;
    private final String ERROR="Это значение должно быть натуральным числом";

    protected boolean checkName(String name){
        if(name!=null)
            this.name = name;
        else
            isNull=true;
        lines++;
        return true;
    }
    protected boolean checkLength(String length){
        if(length!=null&&!isNull){
            long i;
            try{
                i=Long.parseLong(length);
            }
            catch (NumberFormatException e){
                System.out.println(ERROR);
                return false;
            }
            if(i<=0) {
                System.out.println(ERROR);
                return false;
            }
            this.length=i;
            lines++;
            return true;
        }
        else if(length==null&&!isNull) {
            System.out.println(ERROR);
            return false;
        }
        else
            return true;
    }
    protected Album build(){
        if(!isNull)
            return new Album(name,length);
        else return null;
    }
    protected Album Validate(){
        return null;
    }
    protected Album Validate(String name, String length){return null;}
}
