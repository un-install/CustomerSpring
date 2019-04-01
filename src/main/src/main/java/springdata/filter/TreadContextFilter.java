package springdata.filter;

import org.apache.logging.log4j.ThreadContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "ThreadContextFilter", urlPatterns = "/*")
public class TreadContextFilter implements Filter {
    private static final String UUID_FIELD = "uuid";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            ThreadContext.put(UUID_FIELD, UUID.randomUUID().toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ThreadContext.remove(UUID_FIELD);
        }
    }
}
