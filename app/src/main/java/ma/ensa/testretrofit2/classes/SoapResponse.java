package ma.ensa.testretrofit2.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "soap:Envelope")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapResponse<T> {
    @Element(name = "Body")
    private SoapBody<T> body;

    public T getResult() {
        return body != null ? body.getResponse() : null;
    }

    public void setBody(SoapBody<T> body) {
        this.body = body;
    }

    public SoapBody<T> getBody() {
        return body;
    }
}
