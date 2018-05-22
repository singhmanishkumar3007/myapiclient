package com.tech.boot.myclient.log.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.boot.myclient.constants.LogConstants;

@WebFilter(urlPatterns = "/*")
@Component
@Order(1)
public class LogFilter extends OncePerRequestFilter {

	private static Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

	private Clock clock;

	public LogFilter() {
		super();
		clock = Clock.systemDefaultZone();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Long startTime = clock.millis();
		clearMDC();
		request.setAttribute(LogConstants.API_START_TIME, startTime);

		// Populate tracking id into response header
		String trackingId = request.getHeader(LogConstants.HTTP_HEADER_TRACKING_ID);
		if (trackingId == null) {
			trackingId = UUID.randomUUID().toString();
		}
		MDC.put(LogConstants.API_TRACKING_ID, trackingId);

		try {
			response.addHeader(LogConstants.HTTP_HEADER_TRACKING_ID,
					URLEncoder.encode(trackingId, StandardCharsets.UTF_8.name()));
		} catch (UnsupportedEncodingException uee) {
			LOGGER.error("Unable to add tracking id to response header", uee);
		}

		try {
			addMetadataToMDC(request, startTime);
			filterChain.doFilter(request, response);
			response.flushBuffer();
		} finally {
			MDC.put(LogConstants.API_RESPONSE_STATUS, Integer.toString(response.getStatus()));
			MDC.put(LogConstants.API_RESPONSE_TIME, Long.toString(clock.millis() - startTime));
		}
	}

	protected void addMetadataToMDC(HttpServletRequest request, Long startTime) {
		MDC.put(LogConstants.API_REQUEST_TIMESTAMP, Long.toString(startTime));
		MDC.put(LogConstants.API_CLIENT_IP, request.getRemoteAddr());
		MDC.put(LogConstants.API_REQUEST_METHOD, request.getMethod());
		MDC.put(LogConstants.API_KEY, ServletRequestUtils.getStringParameter(request, LogConstants.API_KEY, ""));
		MDC.put(LogConstants.API_METHOD, (String) request.getAttribute(LogConstants.API_METHOD_NAME));
		if (null != request.getQueryString() && !"".equals(request.getQueryString().trim())) {
			MDC.put(LogConstants.API_REQUEST_URI,
					String.format("%s?%s", request.getRequestURI(), request.getQueryString()));
		} else {
			MDC.put(LogConstants.API_REQUEST_URI, String.format("%s", request.getRequestURI()));
		}
	}

	protected void clearMDC() {
		MDC.remove(LogConstants.API_TRACKING_ID);
		MDC.remove(LogConstants.API_REQUEST_TIMESTAMP);
		MDC.remove(LogConstants.API_REQUEST_DATE);
		MDC.remove(LogConstants.API_CLIENT_IP);
		MDC.remove(LogConstants.API_REQUEST_METHOD);
		MDC.remove(LogConstants.API_KEY);
		MDC.remove(LogConstants.API_METHOD);
		MDC.remove(LogConstants.API_REQUEST_URI);
		MDC.remove(LogConstants.API_REQUEST_HEADERS);
		MDC.remove(LogConstants.API_RESPONSE_STATUS);
		MDC.remove(LogConstants.API_RESPONSE_TIME);
		MDC.remove(LogConstants.API_EXCEPTION);
		MDC.clear();
	}

}
