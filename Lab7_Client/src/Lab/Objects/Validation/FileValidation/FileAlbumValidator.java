package Lab.Objects.Validation.FileValidation;

import Lab.Objects.Album;
import Lab.Objects.Validation.AlbumValidator;



class FileAlbumValidator extends AlbumValidator {

    @Override
    protected Album Validate(String name, String length) {
       String line1=name.length()==0?null:name;
       String line2=length.length()==0?null:length;
       if(checkName(line1)&&checkLength(line2))
            return build();
       throw new NullPointerException();
    }
}
