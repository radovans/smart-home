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

import cz.sinko.smarthome.web.rest.filters.utils.SkipLoggingAndRequestId;

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

			if (requestId == null || requestId.isEmpty() || requestId.isBlank()) {
				requestId = UUID.randomUUID().toString();
			}
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
