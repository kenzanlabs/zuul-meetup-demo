/*
 * Copyright 2013 Netflix, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
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

/**
 * @author mhawthorne
 */
class RouteLookup extends ZuulFilter {

    @Override
    int filterOrder() {
        return 5
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
        def myList = []
        RequestContext ctx = RequestContext.getCurrentContext()
        HttpServletRequest request = ctx.getRequest()
        System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()))
        InputStream config = RouteLookup.class.getClassLoader().getResourceAsStream("routeConfig.json")
        String redirectJson = IOUtils.toString(config, Charsets.UTF_8)

        ObjectMapper objectMapper = new ObjectMapper()
        RouteConfig[] routeConfigs = objectMapper.readValue(redirectJson, RouteConfig[].class)
        Optional<RouteConfig> routeConfig = Arrays.stream(routeConfigs).filter(new Predicate<RouteConfig>() {
            @Override
            boolean test(RouteConfig routeConfig) {
                return routeConfig.path == request.getRequestURI()
            }
        }).findAny()

        if (routeConfig.isPresent()) {
            System.out.println("Config found: " + routeConfig.get())
            ctx.set(FilterConstants.ROUTE_CONFIG_KEY, routeConfig.get())
        } else {
            ctx.setSendZuulReponse(false)
            ctx.response.setStatus(404)
        }
    }

}
