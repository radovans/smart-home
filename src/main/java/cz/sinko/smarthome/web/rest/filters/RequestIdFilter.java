package cz.sinko.smarthome.web.rest.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(1)
public class RequestIdFilter extends OncePerRequestFilter {

	private static final String REQUEST_HEADER_NAME = "X-Request-Id";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain)
			throws ServletException, IOException {
		if (!SkipLoggingAndRequestId.skip(request)) {
			String requestId = request.getHeader(REQUEST_HEADER_NAME);

			if (requestId == null || "".equals(requestId)) {
				requestId = UUID.randomUUID().toString();
			}
			log.debug("RequestId: " + requestId);
			try {
				MDC.put("requestId", requestId);
				response.addHeader(REQUEST_HEADER_NAME, requestId);
				filterChain.doFilter(request, response);
			} finally {
				MDC.clear();
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
