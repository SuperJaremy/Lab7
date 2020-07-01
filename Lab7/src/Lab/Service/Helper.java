package Lab.Service;


import Lab.Objects.MusicBand;

import java.time.LocalDate;
import java.util.Date;

public class Helper {
    public static LocalDate localDateFromDate(Date d){
        String es = MusicBand.sdf.format(d);
        return LocalDate.parse(es, MusicBand.formatter);
    }
}
