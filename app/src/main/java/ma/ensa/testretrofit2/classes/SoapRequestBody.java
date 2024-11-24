package ma.ensa.testretrofit2.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "soap:Body")
@Namespace(prefix = "soap", reference = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapRequestBody<T> {
    @Element(name = "Request")
    private T request;

    public SoapRequestBody(T request) {
        this.request = request;
    }

    public T getRequest() {
        return request;
    }
}
