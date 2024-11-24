package ma.ensa.testretrofit2.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import ma.ensa.testretrofit2.api.CompteJsonApiService;
import ma.ensa.testretrofit2.api.CompteXmlApiService;
import ma.ensa.testretrofit2.api.CompteSoapApiService;
import ma.ensa.testretrofit2.utils.XMLDateTransformer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.1.9:8082/";
    private static Retrofit jsonRetrofit = null;
    private static Retrofit xmlRetrofit = null;
    private static Retrofit soapRetrofit = null;

    private static OkHttpClient getHttpClient(String contentType) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request original = chain.request();
                    okhttp3.Request request = original.newBuilder()
                            .header("Accept", contentType)
                            .header("Content-Type", contentType)
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    private static OkHttpClient getSoapHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request original = chain.request();
                    // Get the SOAPAction from the request header if it exists
                    String soapAction = original.header("SOAPAction");

                    okhttp3.Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "text/xml; charset=utf-8")
                            .header("Content-Type", "text/xml; charset=utf-8");

                    // Add SOAPAction header if it exists
                    if (soapAction != null && !soapAction.isEmpty()) {
                        requestBuilder.header("SOAPAction", soapAction);
                    }

                    okhttp3.Request request = requestBuilder
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                })
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS) // Longer timeout for SOAP
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static synchronized Retrofit getJsonClient() {
        if (jsonRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                    .setLenient()
                    .create();

            jsonRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient("application/json"))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return jsonRetrofit;
    }

    public static synchronized Retrofit getXmlClient() {
        if (xmlRetrofit == null) {
            // Create registry matcher for custom date handling
            RegistryMatcher matcher = new RegistryMatcher();
            matcher.bind(Date.class, new XMLDateTransformer());

            // Create persister with custom matcher and annotation strategy
            Persister persister = new Persister(new AnnotationStrategy(), matcher);

            xmlRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient("application/xml"))
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(persister))
                    .build();
        }
        return xmlRetrofit;
    }

    public static synchronized Retrofit getSoapClient() {
        if (soapRetrofit == null) {
            // Create registry matcher for custom date handling
            RegistryMatcher matcher = new RegistryMatcher();
            matcher.bind(Date.class, new XMLDateTransformer());

            // Create persister with custom matcher and annotation strategy
            Persister persister = new Persister(new AnnotationStrategy(), matcher);

            soapRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getSoapHttpClient())
                    .addConverterFactory(ScalarsConverterFactory.create()) // Add support for String responses
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(persister))
                    .build();
        }
        return soapRetrofit;
    }

    public static CompteJsonApiService getJsonApi() {
        return getJsonClient().create(CompteJsonApiService.class);
    }

    public static CompteXmlApiService getXmlApi() {
        return getXmlClient().create(CompteXmlApiService.class);
    }

    public static CompteSoapApiService getSoapApi() {
        return getSoapClient().create(CompteSoapApiService.class);
    }
}