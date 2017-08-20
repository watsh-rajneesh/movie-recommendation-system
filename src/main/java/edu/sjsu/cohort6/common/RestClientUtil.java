package edu.sjsu.cohort6.common;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A collection of REST client APIs using Jersey library and simple JSON.
 *
 * @author rwatsh on 10/12/16.
 */
public class RestClientUtil {
    /**
     * Perform http get and convert JSON to java object.
     *
     * @param resourceUri
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(String resourceUri, Class<T> clazz) {
        T retVal = null;
        try {

            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(resourceUri);

            Response response = webTarget
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get();
            String respStr = response.readEntity(String.class);
            System.out.println(respStr);
            retVal = CommonUtils.convertJsonToObject(respStr, clazz);
        } catch (Exception e) {

            e.printStackTrace();

        }
        return retVal;
    }
}
