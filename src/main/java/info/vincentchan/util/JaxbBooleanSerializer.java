package info.vincentchan.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Jaxb 将boolean类型转化为>0和0
 *
 * @author Vincent.Chan
 * @since 2012.04.15 13:47
 */
public class JaxbBooleanSerializer extends XmlAdapter<Integer, Boolean> {
    @Override
    public Boolean unmarshal(Integer i) {
        return i == null ? null : i > 0;
    }

    @Override
    public Integer marshal(Boolean b) {
        return b == null ? null : b ? 1 : 0;
    }
}
