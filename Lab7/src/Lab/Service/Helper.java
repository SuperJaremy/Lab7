package Lab.Service;


import Lab.Objects.MusicBand;


import java.time.LocalDate;
import java.util.Date;

public class Helper {
    public static LocalDate getLocalDateFromDate(Date d){
        String es = MusicBand.sdf.format(d);
        return LocalDate.parse(es, MusicBand.formatter);
    }
    public static String getHexString(byte[] b){
        String result = "";
        for (int i=0; i < b.length; i++) {
            result +=
                    Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
}
