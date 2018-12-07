package cz.sinko.smarthome.web.rest.filters;

import static net.logstash.logback.marker.Markers.append;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.Data;

@Component
@Order(2)
public class LoggingFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
	private final AtomicLong requestNumber = new AtomicLong(0);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain)
			throws ServletException, IOException {
		if (!SkipLoggingAndRequestId.skip(request)) {
			if (logger.isDebugEnabled()) {
				long requestNumber = this.requestNumber.incrementAndGet();
				request = new RequestWrapper(requestNumber, request);
				response = new ResponseWrapper(requestNumber, response);
			}
			try {
				filterChain.doFilter(request, response);
			} finally {
				if (logger.isDebugEnabled()) {
					logRequest(request);
					logResponse((ResponseWrapper) response);
				}
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private void logRequest(final HttpServletRequest request) {
		LogRequest logRequest = new LogRequest();
		if (request instanceof RequestWrapper) {
			logRequest.setRequestNumber(((RequestWrapper) request).getRequestNumber());
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			logRequest.setSessionId(session.getId());
		}
		if (request.getMethod() != null) {
			logRequest.setMethod(request.getMethod());
		}
		if (request.getContentType() != null) {
			logRequest.setContentType(request.getContentType());
		}
		if (request.getHeaderNames() != null) {
			Collections.list(request.getHeaderNames()).forEach(headerName ->
					Collections.list(request.getHeaders(headerName)).forEach(headerValue ->
							logRequest.addHeader(headerName, headerValue)));
		}
		logRequest.setUri(request.getRequestURI());
		if (request.getQueryString() != null) {
			logRequest.setUri(logRequest.getUri() + request.getQueryString());
		}

		if (request instanceof RequestWrapper && !isMultipart(request) && !isBinaryContent(request)) {
			RequestWrapper requestWrapper = (RequestWrapper) request;
			try {
				String charEncoding =
						requestWrapper.getCharacterEncoding() != null ? requestWrapper.getCharacterEncoding() :
								"UTF-8";
				logRequest.setPayload(new String(requestWrapper.toByteArray(), charEncoding));
			} catch (UnsupportedEncodingException e) {
				logger.warn("Failed to parse request payload", e);
			}

		}
		logger.info(append("request", logRequest), logRequest.toString());
	}

	private boolean isBinaryContent(final HttpServletRequest request) {
		if (request.getContentType() == null) {
			return false;
		}
		return request.getContentType().startsWith("image") || request.getContentType().startsWith("video")
				|| request.getContentType().startsWith("audio");
	}

	private boolean isMultipart(final HttpServletRequest request) {
		return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
	}

	private void logResponse(final ResponseWrapper response) {
		LogResponse logResponse = new LogResponse();
		logResponse.setRequestNumber(response.getRequestNumber());
		if (response.getHeaderNames() != null) {
			response.getHeaderNames()
					.forEach(headerName ->
							logResponse.addHeader(headerName, response.getHeader(headerName)));
		}
		if (response.getContentType() != null) {
			logResponse.setContentType(response.getContentType());
		}
		logResponse.setResponseCode(response.getStatus());
		try {
			logResponse.setPayload(new String(response.toByteArray(), response.getCharacterEncoding()));
		} catch (UnsupportedEncodingException e) {
			logger.warn("Failed to parse response payload", e);
		}
		logger.info(append("response", logResponse), logResponse.toString());
	}

	@Data
	private class LogRequest {
		long requestNumber;
		String sessionId;
		String method;
		String uri;
		String contentType;
		Map<String, String> headers;
		String payload;

		void addHeader(String key, String value) {
			if (headers == null) {
				headers = new HashMap<>();
			}
			headers.put(key, value);
		}
	}

	@Data
	private class LogResponse {
		long requestNumber;
		int responseCode;
		String contentType;
		Map<String, String> headers;
		String payload;

		void addHeader(String key, String value) {
			if (headers == null) {
				headers = new HashMap<>();
			}
			headers.put(key, value);
		}
	}

}