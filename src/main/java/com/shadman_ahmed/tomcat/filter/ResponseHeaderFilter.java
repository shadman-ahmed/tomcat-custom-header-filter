package com.shadman_ahmed.tomcat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Shadman Ahmed
 * @since 2020-08-14
 */

public class ResponseHeaderFilter implements Filter {

    private Map<String, String> headers;

    @Override
    public void init(FilterConfig filterConfig) {
        this.headers = new HashMap<>();

        Enumeration<String> headerNames = filterConfig.getInitParameterNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();

            String headerValue = filterConfig.getInitParameter(headerName);

            try {
                if (headerValue.equals("")) throw new IllegalArgumentException();

                headers.put(headerName, headerValue);

            } catch (NullPointerException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, final ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Set<String> headerNames = this.headers.keySet();

        headerNames.forEach(headerName -> {
            String headerValue = this.headers.get(headerName);

            response.addHeader(headerName, headerValue);
        });

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
