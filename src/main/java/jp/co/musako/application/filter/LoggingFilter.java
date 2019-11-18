package jp.co.musako.application.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        Long start = System.currentTimeMillis();
        try {
            log.info("start {} {}", request.getMethod(), request.getRequestURI());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            log.info("end {}ms {} {}", (System.currentTimeMillis() - start), request.getMethod(), request.getRequestURI());
        }

    }
}