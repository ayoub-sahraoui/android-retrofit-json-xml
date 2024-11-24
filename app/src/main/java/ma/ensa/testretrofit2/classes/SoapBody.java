package ma.ensa.testretrofit2.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "soap:Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapBody<T> {
    @Element(name = "Response", required = false)
    private T response;

    @Element(name = "Fault", required = false)
    private SoapFault fault;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public SoapFault getFault() {
        return fault;
    }

    public void setFault(SoapFault fault) {
        this.fault = fault;
    }
}
