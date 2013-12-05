package info.vincentchan.util;

/**
 * @author Vincent.Chan
 * @since 2012.04.09 16:33
 */

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JaxbDateSerializer extends XmlAdapter<String, Date> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String marshal(Date date) throws Exception {
        return dateFormat.format(date);
    }

    @Override
    public Date unmarshal(String date) throws Exception {
        return dateFormat.parse(date);
    }
}
