package cz.sinko.smarthome.web.rest.filters.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.input.TeeInputStream;

public class RequestWrapper extends HttpServletRequestWrapper {

	private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private final long requestNumber;

	public RequestWrapper(Long requestNumber, HttpServletRequest request) {
		super(request);
		this.requestNumber = requestNumber;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream() {
			private TeeInputStream tee = new TeeInputStream(RequestWrapper.super.getInputStream(), bos);

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int read() throws IOException {
				return tee.read();
			}
		};
	}

	public byte[] toByteArray() {
		return bos.toByteArray();
	}

	public long getRequestNumber() {
		return requestNumber;
	}

}