package Lab.Objects.Validation.FileValidation;

import Lab.Objects.Coordinates;
import Lab.Objects.Validation.CoordinatesValidator;



class FileCoordinatesValidator extends CoordinatesValidator {
    @Override
    public Coordinates Validate(String x, String y){
        String line1 = x.length()==0?null:x;
        String line2 = y.length()==0?null:y;
        if(checkX(line1)&&checkY(line2))
            return build();
        throw new NullPointerException();
    }
}
