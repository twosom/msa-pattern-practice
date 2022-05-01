package com.thoughtmechanix.licenses.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.thoughtmechanix.licenses.utils.UserContext.*;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(ORG_ID));

        log.debug("License Service Incoming Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }
}
