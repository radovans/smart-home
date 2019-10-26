package cz.sinko.smarthome.web.rest.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import cz.sinko.smarthome.web.rest.filters.utils.RequestWrapper;
import cz.sinko.smarthome.web.rest.filters.utils.ResponseWrapper;
import cz.sinko.smarthome.web.rest.filters.utils.SkipLoggingAndRequestId;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(3)
public class LoggingFilter extends OncePerRequestFilter {

	private final AtomicLong requestNumber = new AtomicLong(0);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			final FilterChain filterChain)
			throws ServletException, IOException {
		if (!SkipLoggingAndRequestId.skip(request)) {
			long requestNumber = this.requestNumber.incrementAndGet();
			request = new RequestWrapper(requestNumber, request);
			response = new ResponseWrapper(requestNumber, response);
			logRequest(request);
			try {
				filterChain.doFilter(request, response);
			} finally {
				logResponse((ResponseWrapper) response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private void logRequest(final HttpServletRequest request) throws IOException {
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
			String payload = requestWrapper.getReader().lines().collect(Collectors.joining());
			logRequest.setPayload(new Gson().fromJson(payload, Map.class));
		}
		log.info(logRequest);
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
			String payload = new String(response.toByteArray(), response.getCharacterEncoding());
			logResponse.setPayload(new Gson().fromJson(payload, Map.class));
		} catch (UnsupportedEncodingException e) {
			log.warn("Failed to parse response payload", e);
		}
		log.info(logResponse);
	}

	@Data
	private class LogRequest {
		long requestNumber;
		String sessionId;
		String method;
		String uri;
		String contentType;
		Map<String, String> headers;
		Map<String, Object> payload;

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
		Map<String, Object> payload;

		void addHeader(String key, String value) {
			if (headers == null) {
				headers = new HashMap<>();
			}
			headers.put(key, value);
		}
	}
}
