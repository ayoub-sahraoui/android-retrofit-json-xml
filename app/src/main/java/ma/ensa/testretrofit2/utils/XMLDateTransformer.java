package ma.ensa.testretrofit2.utils;

import org.simpleframework.xml.transform.Transform;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XMLDateTransformer implements Transform<Date> {
    private static final String[] FORMATS = {
            "yyyy-MM-dd'T'HH:mm:ssXXX",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd"
    };

    @Override
    public Date read(String value) throws Exception {
        if (value == null) return null;

        for (String format : FORMATS) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(value);
            } catch (Exception e) {
                // Try next format
            }
        }
        throw new Exception("Unable to parse date: " + value);
    }

    @Override
    public String write(Date value) throws Exception {
        if (value == null) return null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATS[0]);
        return dateFormat.format(value);
    }
}