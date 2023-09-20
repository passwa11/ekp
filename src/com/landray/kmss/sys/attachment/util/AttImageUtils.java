package com.landray.kmss.sys.attachment.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.util.StringUtil;

/**
 * 附件图片压缩
 * 
 * @author 傅游翔
 */
public class AttImageUtils {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(AttImageUtils.class);

	private static final ThreadLocal<Long> startTimes = new ThreadLocal<Long>();

	/**
	 * 假如附件类型是图片类型，同时指定了图片大小情况下，进行压缩处理
	 * 
	 * @param sysAttMain
	 * @throws IOException
	 */
	public static void resetInputStream(SysAttBase sysAttMain)
			throws IOException {
		if ("pic".equals(sysAttMain.getFdAttType())) { // 只能处理图片
            if (null != sysAttMain.getWidth() && null != sysAttMain.getHeight()
                    && sysAttMain.getWidth() > 0 && sysAttMain.getHeight() > 0) { // 默认在图片大小都指定时才压缩图片
				if (log.isDebugEnabled()) {
					log.debug("开始执行图片压缩 model:" + sysAttMain.getFdModelName()
							+ " key:" + sysAttMain.getFdKey());
					startTimes.set(System.currentTimeMillis());
				}
				comprass(sysAttMain);
				if (log.isDebugEnabled()) {
					log.debug("图片压缩结束 共用时间：'"
							+ (System.currentTimeMillis() - startTimes.get())
							+ "'毫秒...");
				}
			} else {
				log.debug("图片没设定大小，不进行压缩！");
			}
		} else {
			log.debug("不是图片，不进行处理！");
		}
	}

	private static void comprass(SysAttBase sysAttMain) throws IOException {
		InputStream in = sysAttMain.getInputStream();
		int width = sysAttMain.getWidth();
		int height = sysAttMain.getHeight();
		boolean proportion = true;
		if ("false".equals(sysAttMain.getProportion())) {
			proportion = false;
		}
		BufferedImage img = ImageIO.read(in);
		int imgW = img.getWidth();
		int imgH = img.getHeight();
		if (log.isDebugEnabled()) {
			log.debug("要求大小：（" + width + "x" + height + "）实际大小：（" + imgW + "x"
					+ imgH + "）");
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (width >= imgW && height >= imgH) {
			if (log.isDebugEnabled()) {
                log.debug("图片小于规定大小，不进行原始图片压缩！");
            }
			if (ImageCompressUtils.getInstance()
					.isBMP(sysAttMain.getFdFileName())) {
				if (log.isDebugEnabled()) {
					log.debug("图片是‘BMP’格式，转换成‘JPEG’格式。否则Flash无法显示。");
				}
				// 某些BMP格式图片转换后会失真
				ImageCompressUtils.getInstance().compressImage(img, out, imgW,
						imgH, ImageCompressUtils.IMG_TYPE_JPEG);
			} else {
				img.flush();
				if (in.markSupported()) {
                    in.reset(); // important! 把输入流位置调整到开始位置
                }
				return;
			}
		} else { // 压缩
			if (log.isDebugEnabled()) {
                log.debug("开始压缩图片：" + sysAttMain.getFdFileName());
            }
			ImageCompressUtils.getInstance().compressImage(img, out, width,
					height, proportion);
		}
		ByteArrayInputStream newIn =
				new ByteArrayInputStream(out.toByteArray());
		sysAttMain.setInputStream(newIn); // 设置新的输入流
		sysAttMain.setFdSize((double) newIn.available()); // 压缩后重设大小
		img.flush();
		IOUtils.closeQuietly(in); // 放弃原来的输入流（原始图片）
	}

	/**
	 * 检查网络资源是否存在
	 * 
	 * @param String
	 *            netUrl 网络资源路径 
	 * @return boolean true:表示网络资源存在
	 *            false表示网络资源不存在 
	 * @throws
	 */
	public static boolean checkUrlResourceExist(String netUrl)
			throws IOException {
		InputStream in = null;
		try {
			// 构建网络url
			URL url = new URL(netUrl);
			// 返回一个 URLConnection 对象，它表示到 URL 所引用的远程对象的连接。
			URLConnection uc = url.openConnection();
			// 打开的连接读取的输入流。
			in = uc.getInputStream();
		} catch (Exception e) {
			return false;
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return true;
	}

	/**
	 * 获取请求全路径前缀(类似如：http://localhost:7070/ekp_dev)
	 * 
	 * @param HttpServletRequest
	 *            request http请求的request对象 
	 * @return String
	 *            类似如：http://localhost:7070/ekp_dev 
	 * @throws
	 */
	public static String getCurDnsHostContextPath(HttpServletRequest request) {
		String host = "";
		if (request != null) {
			String scheme = request.getScheme();
			String serverName = request.getServerName();
			host = scheme + "://" + serverName;
			int serverPort = request.getServerPort();
			if (serverPort != 80) {
				host = host + ":" + serverPort;
			}
			String contextPath = request.getContextPath();
			host = host + contextPath;
		}
		return host;
	}

	public static boolean cacheConsulation(HttpServletRequest request,
			HttpServletResponse response, String contentType, String fileName) {
		boolean rtnVal = false;

		String filethumb = request.getParameter("filethumb");
		Boolean isThumb = "yes".equals(filethumb);

		if (!isThumb) {
			String filekey = request.getParameter("filekey");
			if (StringUtil.isNotNull(filekey)) {
                isThumb = filekey.indexOf("image2thumbnail") >= 0;
            }
		}

		if (contentType.indexOf("image") > -1
				|| (fileName.endsWith(".png")
						&& "application/octet-stream".equals(contentType))
				|| isThumb) {

			if (StringUtil.isNotNull(request.getHeader("If-Modified-Since"))) {
				response.reset();
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				rtnVal = true;
			} else {
				long expires = 7 * 24 * 60 * 60;
				long nowTime = System.currentTimeMillis();
				response.addDateHeader("Last-Modified", nowTime + expires);
				response.addDateHeader("Expires", nowTime + expires * 1000);
				response.setContentType(contentType);
				response.setHeader("Cache-Control", "max-age=" + expires);
			}
		}

		return rtnVal;
	}

}
