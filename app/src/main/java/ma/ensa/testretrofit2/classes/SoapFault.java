package ma.ensa.testretrofit2.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Fault")
public class SoapFault {
    @Element(name = "faultcode")
    private String faultCode;

    @Element(name = "faultstring")
    private String faultString;

    @Element(name = "detail", required = false)
    private String detail;

    public String getFaultCode() {
        return faultCode;
    }

    public String getFaultString() {
        return faultString;
    }

    public String getDetail() {
        return detail;
    }
}