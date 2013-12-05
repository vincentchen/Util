package info.vincentchan.util;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Vincent.Chan
 * @since 2012.04.09 16:38
 */
@XmlJavaTypeAdapter(JaxbDateSerializer.class)
public @interface XmlDateAdapter {
    public String pattern() default "yyyy-MM-dd";
}
