package cz.sinko.smarthome.web.rest.filters.utils;

import javax.servlet.http.HttpServletRequest;

public class SkipLoggingAndRequestId {

	public static boolean skip(HttpServletRequest request) {
		String requestUrl = request.getRequestURI();
		return requestUrl != null && (requestUrl.contains("/actuator")
				|| requestUrl.contains("/v2/api-docs")
				|| requestUrl.contains("/swagger-ui.html")
				|| requestUrl.contains("/swagger-resources")
				|| requestUrl.contains("/csrf")
				|| requestUrl.contains("/webjars/springfox-swagger-ui")
				|| (requestUrl.endsWith("/") && requestUrl.length() == 1));
	}

}
