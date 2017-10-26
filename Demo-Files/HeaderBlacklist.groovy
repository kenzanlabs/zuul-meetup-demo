package filters.pre


import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

import javax.servlet.http.HttpServletRequest
import java.util.function.Consumer


class HeaderBlacklist extends ZuulFilter {


    private static Collection<String> HEADER_BLACKLIST = [ "X-BAD-HEADER" ]
    @Override
    int filterOrder() {
        return 2
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
                if (HEADER_BLACKLIST.contains(headerName)) {
                    System.out.println("Preventing request due to header: " + headerName)
                    ctx.setSendZuulResponse(false)
                    ctx.response.setStatus(400)
                }
            }
        })
    }

}
