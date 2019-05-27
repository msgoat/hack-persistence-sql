package group.msg.at.cloud.cloudtrain.adapter.rest;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * {@code Factory} for {@code UriBuilder}s which are aware of routers that forwarded the current request.
 * <p>
 * Use this class instead of the factory methods provided by {@link UriInfo} or {@link UriBuilder}, if your service
 * is running behind a router that uses path-based routing.
 * </p>
 * @TODO: clarify if this factory can be transformed into a @RequestScoped bean with injectable UriInfo and HttpHeaders
 */
public class RouterAwareUriBuilderFactory {

    public static UriBuilder from(UriInfo uriInfo, HttpHeaders httpHeaders) {
        URI actualUri = null;
        URI requestUri = uriInfo.getRequestUri();
        String forwardedPrefix = httpHeaders.getHeaderString("x-forwarded-prefix");
        if (forwardedPrefix == null || forwardedPrefix.isEmpty()) {
            forwardedPrefix = httpHeaders.getHeaderString("X-FORWARDED-PREFIX");
        }
        if (forwardedPrefix != null && !forwardedPrefix.isEmpty()) {
            try {
                actualUri = new URI(requestUri.getScheme(), requestUri.getAuthority(), forwardedPrefix + requestUri.getPath(), requestUri.getQuery(), requestUri.getFragment());
            } catch (URISyntaxException ex) {
                throw new IllegalStateException(String.format("failed to create a external URI from request URI [%s] and router path prefix [%s]", requestUri, forwardedPrefix));
            }
        }
        return UriBuilder.fromUri(actualUri != null ? actualUri : requestUri);
    }
}
