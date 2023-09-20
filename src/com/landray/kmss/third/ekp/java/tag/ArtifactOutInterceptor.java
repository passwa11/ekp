package com.landray.kmss.third.ekp.java.tag;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.slf4j.Logger;

public class ArtifactOutInterceptor extends AbstractPhaseInterceptor<Message> {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ArtifactOutInterceptor.class);

	public ArtifactOutInterceptor() {
		// 这儿使用pre_stream，意思为在流关闭之前
		super(Phase.PRE_STREAM);
	}

	@Override
    public void handleMessage(Message message) {

		try {

			OutputStream os = message.getContent(OutputStream.class);

			CachedStream cs = new CachedStream();

			message.setContent(OutputStream.class, cs);

			message.getInterceptorChain().doIntercept(message);

			CachedOutputStream csnew = (CachedOutputStream) message
					.getContent(OutputStream.class);
			InputStream in = csnew.getInputStream();

			String xml = IOUtils.toString(in);

			// 这里对xml做处理，处理完后同理，写回流中
			IOUtils.copy(new ByteArrayInputStream(xml.getBytes()), os);

			cs.close();
			os.flush();

			message.setContent(OutputStream.class, os);

		} catch (Exception e) {
			log.error("Error when split original inputStream. CausedBy : "
					+ "\n" + e);
		}
	}

	private class CachedStream extends CachedOutputStream {

		public CachedStream() {

			super();

		}

		@Override
        protected void doFlush() throws IOException {

			currentStream.flush();

		}

		@Override
        protected void doClose() throws IOException {

		}

		@Override
        protected void onWrite() throws IOException {

		}

	}

}
