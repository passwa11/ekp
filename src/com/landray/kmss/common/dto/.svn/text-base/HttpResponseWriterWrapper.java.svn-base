package com.landray.kmss.common.dto;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.landray.kmss.web.KmssServletOutputStream;

/**
 * 重载HttpServletResponse的writer不写入outputStream，而是存为字符串，后续手动拿出
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public class HttpResponseWriterWrapper extends HttpServletResponseWrapper {

	private ServletOutputStreamWraper stream;
	private PrintWriterWraper writer;

	public HttpResponseWriterWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriterWraper getWriter() {
		if (writer == null) {
            this.writer = new PrintWriterWraper(new EmptyWriter());
        }
		return this.writer;
	}

	@Override
	public ServletOutputStreamWraper getOutputStream() {
		if (stream == null) {
            this.stream = new ServletOutputStreamWraper(this);
        }
		return this.stream;
	}

	public String getWriteContent() {
		if (getOutputStream().getContent().length() > 0) {
			return getOutputStream().getContent().toString();
		}
		return getWriter().getContent().toString();
	}

	public static class ServletOutputStreamWraper extends KmssServletOutputStream {
		private final HttpResponseWriterWrapper wrapper;

		public ServletOutputStreamWraper(HttpResponseWriterWrapper wrapper){
			this.wrapper = wrapper;
		}
		private final StringBuilder content = new StringBuilder();

		public StringBuilder getContent() {
			return content;
		}

		@Override
		public void write(int i) throws IOException {
		}

		@Override
		public void write(byte[] bytes) throws IOException {
			String characterEncoding = wrapper.getCharacterEncoding();
			if(characterEncoding == null) {
				characterEncoding = "UTF-8";
			}
			content.append(new String(bytes, characterEncoding));
		}

		@Override
		public void write(byte[] bytes, int i, int i1) throws IOException {
			String characterEncoding = wrapper.getCharacterEncoding();
			if(characterEncoding == null) {
				characterEncoding = "UTF-8";
			}
			content.append(new String(bytes, characterEncoding), i, i1);
		}


		@Override
		public void print(String s) {
			content.append(s);
		}

		@Override
		public void println(String s) {
			content.append(s).append("\r\n");
		}

		@Override
		public void print(int i) {
			content.append(i);
		}

		@Override
		public void print(char c) {
			content.append(c);
		}

		@Override
		public void print(long l) {
			content.append(l);
		}

		@Override
		public void print(float v) {
			content.append(v);
		}

		@Override
		public void print(double v) {
			content.append(v);
		}

		@Override
		public void print(boolean b) {
			content.append(b);
		}

	}

	public static class PrintWriterWraper extends PrintWriter {

		private final StringBuilder content = new StringBuilder();

		public PrintWriterWraper(Writer writer) {
			super(writer);
		}

		public StringBuilder getContent() {
			return content;
		}

		@Override
		public void write(String s) {
			content.append(s);
		}

		@Override
		public void write(int i) {
			content.append(i);
		}

		@Override
		public void write(char[] chars) {
			content.append(chars);
		}

		@Override
		public void write(String s, int i, int i1) {
			content.append(s, i, i1);
		}

		@Override
		public void write(char[] chars, int i, int i1) {
			content.append(chars, i, i1);
		}

		@Override
		public void print(String s) {
			content.append(s);
		}

		@Override
		public void println(String s) {
			content.append(s).append("\r\n");
		}

		@Override
		public void print(int i) {
			content.append(i);
		}

		@Override
		public void print(char c) {
			content.append(c);
		}

		@Override
		public void print(long l) {
			content.append(l);
		}

		@Override
		public void print(float v) {
			content.append(v);
		}

		@Override
		public void print(double v) {
			content.append(v);
		}

		@Override
		public void print(Object o) {
			content.append(o);
		}

		@Override
		public void print(boolean b) {
			content.append(b);
		}

		@Override
		public void print(char[] chars) {
			content.append(chars);
		}
	}

	public static class EmptyWriter extends Writer {

		@Override
		@SuppressWarnings("all")
		public void write(char[] chars, int i, int i1) throws IOException {
		}

		@Override
		public void flush() {
		}

		@Override
		public void close() {
		}
	}
}
