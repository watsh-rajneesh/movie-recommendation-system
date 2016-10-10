package edu.sjsu.cohort6.rest;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by rwatsh on 9/22/15.
 */
public class EndpointUtils {
    public static final String ENDPOINT_VERSION_STRING= "v1.0";
    public static final String ENDPOINT_ROOT = "/api/" + ENDPOINT_VERSION_STRING;

    public static URI getCreatedResourceURI (UriInfo info,
                                             URI resourcePath, Long resourceId) {
        URI uri = info.getAbsolutePathBuilder()
                .uri(resourcePath).path(info.getPath())
                .path(resourceId.toString())
                .build();

        return uri;
    }
}