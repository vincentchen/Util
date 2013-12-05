import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Vincent.Chan
 * @since 2012.04.18 20:47
 */
@XmlRootElement
public class Wrapper {
    List test;

    public List getTest() {
        return test;
    }

    public void setTest(List test) {
        this.test = test;
    }
}
