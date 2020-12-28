package com.lavasoft;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * <b>A web service which performs GetIpAddress Lookups.</b>
 *
 * This class was generated by Apache CXF 3.4.1
 * 2020-12-28T23:44:26.855+03:00
 * Generated source version: 3.4.1
 *
 */
@WebServiceClient(name = "GeoIPService",
                  wsdlLocation = "file:src/main/resources/geoipservice.wsdl",
                  targetNamespace = "http://lavasoft.com/")
public class GeoIPService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://lavasoft.com/", "GeoIPService");
    public final static QName GeoIPServiceSoap = new QName("http://lavasoft.com/", "GeoIPServiceSoap");
    public final static QName GeoIPServiceSoap12 = new QName("http://lavasoft.com/", "GeoIPServiceSoap12");
    public final static QName GeoIPServiceHttpGet = new QName("http://lavasoft.com/", "GeoIPServiceHttpGet");
    public final static QName GeoIPServiceHttpPost = new QName("http://lavasoft.com/", "GeoIPServiceHttpPost");
    static {
        URL url = null;
        try {
            url = new URL("file:src/main/resources/geoipservice.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GeoIPService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:src/main/resources/geoipservice.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GeoIPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GeoIPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GeoIPService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public GeoIPService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public GeoIPService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public GeoIPService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns GeoIPServiceSoap
     */
    @WebEndpoint(name = "GeoIPServiceSoap")
    public GeoIPServiceSoap getGeoIPServiceSoap() {
        return super.getPort(GeoIPServiceSoap, GeoIPServiceSoap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GeoIPServiceSoap
     */
    @WebEndpoint(name = "GeoIPServiceSoap")
    public GeoIPServiceSoap getGeoIPServiceSoap(WebServiceFeature... features) {
        return super.getPort(GeoIPServiceSoap, GeoIPServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns GeoIPServiceSoap
     */
    @WebEndpoint(name = "GeoIPServiceSoap12")
    public GeoIPServiceSoap getGeoIPServiceSoap12() {
        return super.getPort(GeoIPServiceSoap12, GeoIPServiceSoap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GeoIPServiceSoap
     */
    @WebEndpoint(name = "GeoIPServiceSoap12")
    public GeoIPServiceSoap getGeoIPServiceSoap12(WebServiceFeature... features) {
        return super.getPort(GeoIPServiceSoap12, GeoIPServiceSoap.class, features);
    }


    /**
     *
     * @return
     *     returns GeoIPServiceHttpGet
     */
    @WebEndpoint(name = "GeoIPServiceHttpGet")
    public GeoIPServiceHttpGet getGeoIPServiceHttpGet() {
        return super.getPort(GeoIPServiceHttpGet, GeoIPServiceHttpGet.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GeoIPServiceHttpGet
     */
    @WebEndpoint(name = "GeoIPServiceHttpGet")
    public GeoIPServiceHttpGet getGeoIPServiceHttpGet(WebServiceFeature... features) {
        return super.getPort(GeoIPServiceHttpGet, GeoIPServiceHttpGet.class, features);
    }


    /**
     *
     * @return
     *     returns GeoIPServiceHttpPost
     */
    @WebEndpoint(name = "GeoIPServiceHttpPost")
    public GeoIPServiceHttpPost getGeoIPServiceHttpPost() {
        return super.getPort(GeoIPServiceHttpPost, GeoIPServiceHttpPost.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GeoIPServiceHttpPost
     */
    @WebEndpoint(name = "GeoIPServiceHttpPost")
    public GeoIPServiceHttpPost getGeoIPServiceHttpPost(WebServiceFeature... features) {
        return super.getPort(GeoIPServiceHttpPost, GeoIPServiceHttpPost.class, features);
    }

}
