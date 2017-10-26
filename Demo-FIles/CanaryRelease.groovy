package filters.pre


import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Charsets
import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.model.RouteConfig
import com.netflix.zuul.util.FilterConstants
import org.apache.commons.io.IOUtils

import javax.servlet.http.HttpServletRequest
import java.util.function.Predicate
import java.util.function.Consumer

class CanaryRelease extends ZuulFilter {

    private static Collection<String> LOCATION_HEADER = [ "X-LOCATION" ]
    @Override
    int filterOrder() {
        return 7
    }

    @Override
    String filterType() {
        return "pre"
    }

    @Override
    boolean shouldFilter() {
        return true
    }


    @Override
    Object run() {
        def ctx = RequestContext.getCurrentContext()
        HttpServletRequest request = ctx.request

        Enumeration headerNames = request.getHeaderNames()
        Collections.list(headerNames).forEach(new Consumer() {
            @Override
            void accept(Object headerName) {
                System.out.println("Checking header: " + headerName)
                if (LOCATION_HEADER.contains(headerName)) {
                    System.out.println("Sending request to the right server: " + headerName)
                    String headerValue = request.getHeader(headerName.toString())
                    InputStream config = RouteLookup.class.getClassLoader().getResourceAsStream("routeConfig.json")
                    String redirectJson = IOUtils.toString(config, Charsets.UTF_8)

                    ObjectMapper objectMapper = new ObjectMapper()
                    RouteConfig[] routeConfigs = objectMapper.readValue(redirectJson, RouteConfig[].class)
                    Optional<RouteConfig> routeConfig = Arrays.stream(routeConfigs).filter(new Predicate<RouteConfig>() {
                        @Override
                        boolean test(RouteConfig routeConfig) {
                            System.out.println()
                            if (routeConfig.path == request.getRequestURI() && routeConfig.location == headerValue)
                                return true

                        }
                    }).findAny()

                    if (routeConfig.isPresent()) {
                        System.out.println("Config found: " + routeConfig.get())
                    }
                    ctx.set(FilterConstants.ROUTE_CONFIG_KEY, routeConfig.get())

                }

            }

        })
    }

}
