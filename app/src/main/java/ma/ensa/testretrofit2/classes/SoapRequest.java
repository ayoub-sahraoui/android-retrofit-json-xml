package ma.ensa.testretrofit2.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "soap:Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapRequest<T> {
    @Element(name = "Body")
    private SoapRequestBody<T> body;

    public SoapRequest(T request) {
        this.body = new SoapRequestBody<>(request);
    }

    public SoapRequestBody<T> getBody() {
        return body;
    }
}
