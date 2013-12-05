import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vincent.Chan
 * @since 2012.04.18 20:20
 */
@XmlRootElement
public class Result {
    Object data;

    @XmlElement(name = "user")
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
