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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author radovan.sinko@direct.cz
 */
@Component
@Order(1)
public class RequestIdFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(RequestIdFilter.class);

	@Override public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		UUID requestId = null;
		if (req.getHeader("RequestId").isEmpty()) {
			requestId = UUID.randomUUID();
		} else {
			requestId = UUID.fromString(req.getHeader("RequestId"));
		}
		logger.info("Logging RequestId : {}", req.getHeader("RequestId"));
		chain.doFilter(request, response);
		res.addHeader("RequestId", requestId.toString());
		logger.info("Logging Response RequestId : {}", res.getHeader("RequestId"));
	}

	@Override public void destroy() {
	}

}