package org.blog.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;


//фильтр для создания равнозначности страниц со слешем на конце и без
@Component
public class EndSlashRedirectFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (!(req instanceof HttpServletRequest) || !(resp instanceof HttpServletResponse)) {
            chain.doFilter(req, resp);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String reqUri = request.getRequestURI();

        if (reqUri.length() > 1 && reqUri.endsWith("/")) {

            String newUri = reqUri.substring(0, reqUri.length() - 1);

            String query = request.getQueryString();

            if (query != null) {
                newUri +=  "?" + query;
            }

            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("location", newUri);
            return;
        }
        chain.doFilter(req, resp);

    }
}
