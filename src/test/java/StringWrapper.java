import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Vincent.Chan
 * @since 2012.04.18 20:22
 */
@XmlRootElement(name = "test")
public class StringWrapper {
    private String string;

    public StringWrapper() {
    }

    public StringWrapper(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
