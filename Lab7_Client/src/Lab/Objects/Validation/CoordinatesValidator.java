package Lab.Objects.Validation;

import Lab.Objects.Coordinates;


public abstract class CoordinatesValidator {
    Float x;
    long y;
    private final String xERROR="Координата x должны быть числом больше -801";
    private final String yERROR="Координата y должна быть целым числом";

    protected boolean checkX(String x){
        if(x!=null) {
            Float i;
            try {
                i = Float.parseFloat(x);
            } catch (NumberFormatException e) {
                System.out.println(xERROR);
                return false;
            }
            if (i.compareTo(-801f) <= 0) {
                System.out.println(xERROR);
                return false;
            }
            this.x = i;
            return true;
        }
        System.out.println(xERROR);
        return false;
    }
    protected boolean checkY(String y){
        if(y!=null){
            long i;
            try{
             i=Long.parseLong(y);
            }
            catch (NumberFormatException e){
                System.out.println(yERROR);
                return false;
            }
            this.y=i;
            return true;
        }
        System.out.println(yERROR);
        return false;
    }
    protected Coordinates build(){
        return new Coordinates(x,y);
    }
    protected Coordinates Validate(){
        return null;
    }
    protected Coordinates Validate(String x, String y){
        return null;
    }
}
