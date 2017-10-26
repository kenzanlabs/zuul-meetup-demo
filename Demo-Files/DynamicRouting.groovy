package filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.model.RouteConfig
import com.netflix.zuul.util.FilterConstants
import com.netflix.zuul.context.RequestContext

class DynamicRouting extends ZuulFilter {

    @Override
    int filterOrder() {
        return 10
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
        RouteConfig routeConfig = RequestContext.getCurrentContext().get(FilterConstants.ROUTE_CONFIG_KEY)
        def ctx = RequestContext.getCurrentContext()

        if (routeConfig.getNewPath()) {
            RequestContext.getCurrentContext().requestURI = routeConfig.getNewPath()

        }

    }
}
