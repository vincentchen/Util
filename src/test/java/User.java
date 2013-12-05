import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vincent.Chan
 * @since 2012.04.18 21:39
 */
@XmlRootElement
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
