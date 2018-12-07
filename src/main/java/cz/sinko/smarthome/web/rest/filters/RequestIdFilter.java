package cz.sinko.smarthome.web.rest.filters;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestIdFilter implements Filter {

	private static final String REQUEST_HEADER_NAME = "X-Request-Id";
	private final Logger logger = LoggerFactory.getLogger(RequestIdFilter.class);

	@Override public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (!SkipLoggingAndRequestId.skip(req)) {
			String requestId = req.getHeader(REQUEST_HEADER_NAME);

			if (requestId == null || "".equals(requestId)) {
				requestId = UUID.randomUUID().toString();
			}

			logger.debug("RequestId: " + requestId);
			try {
				MDC.put("requestId", requestId);
				res.addHeader(REQUEST_HEADER_NAME, requestId);
				chain.doFilter(request, response);
			} finally {
				MDC.clear();
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override public void destroy() {
	}

}
