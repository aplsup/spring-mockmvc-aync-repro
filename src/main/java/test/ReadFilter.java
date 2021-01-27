package test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class ReadFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(ReadFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            LOG.info("Reading response {}", response);
            Collection<String> headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                LOG.info("Read Header {}", headerName);
            }
        }
    }
}
