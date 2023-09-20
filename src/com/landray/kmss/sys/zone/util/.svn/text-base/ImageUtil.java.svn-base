package com.landray.kmss.sys.zone.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUtil {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 根据尺寸图片居中裁剪
	 * 
	 * @param src
	 *            源图片路径
	 * @param out
	 *            裁剪后的图片路径
	 * @param width
	 *            裁剪后的宽
	 * @param height
	 *            裁剪后的高
	 */
	public static void cutCenterImage(InputStream in,OutputStream out, String src, int width,
			int height) {
		ImageInputStream iis = null;
		try {
			// 图片后缀
			String suffix = FilenameUtils.getExtension(src);
			Iterator<ImageReader> iterator = ImageIO
					.getImageReadersByFormatName(suffix);
			ImageReader reader = iterator.next();
			iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			int imageIndex = 0;
			Rectangle rect = new Rectangle(
					(reader.getWidth(imageIndex) - width) / 2,
					(reader.getHeight(imageIndex) - height) / 2, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, suffix, out);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		} finally {
			try {
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				LOGGER.error("关闭流错误", e);
			}
		}

	}

	/**
	 * 图片裁剪二分之一
	 * 
	 * @param src
	 *            源图片路径
	 * @param dest
	 *            裁剪后的图片路径
	 */
	public static void cutHalfImage(InputStream in, OutputStream out,String src) {
		ImageInputStream iis = null;
		try {
			// 图片后缀
			String suffix = FilenameUtils.getExtension(src);
			Iterator<ImageReader> iterator = ImageIO
					.getImageReadersByFormatName(suffix);
			ImageReader reader = iterator.next();
			iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			int imageIndex = 0;
			int width = reader.getWidth(imageIndex) / 2;
			int height = reader.getHeight(imageIndex) / 2;
			Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, suffix, out);
		} catch (FileNotFoundException e) {
			LOGGER.error("无法找到文件", e);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		} finally {
			try {
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				LOGGER.error("关闭流错误", e);
			}
		}
	}

	/**
	 * 图片裁剪通用接口
	 * 
	 * @param src
	 *            源图片路径
	 * @param dest
	 *            裁剪后的图片路径
	 * @param startX
	 *            起点x坐标
	 * @param satrtY
	 *            起点y坐标
	 * @param width
	 *            裁剪后的宽
	 * @param height
	 *            裁剪后的高
	 */
	public static void cutImage(InputStream src, OutputStream dest,String srcName, int startX,
			int satrtY, int width, int height) throws Exception {
			// 图片后缀
			String suffix = FilenameUtils.getExtension(srcName);
			if (StringUtils.isEmpty(suffix)) {
				suffix = "jpg";
			}
			cutImage(src, dest, startX, satrtY, width,  height, suffix);
	}

	/**
	 * 图片裁剪通用接口
	 * 
	 * @param src
	 *            源图片路径
	 * @param dest
	 *            裁剪后的图片路径
	 * @param startX
	 *            起点x坐标
	 * @param satrtY
	 *            起点y坐标
	 * @param width
	 *            裁剪后的宽
	 * @param height
	 *            裁剪后的高
	 * @param suffix
	 *            后缀
	 * @throws Exception 
	 */
	public static void cutImage(InputStream in, OutputStream os, int startX,
			int satrtY, int width, int height, String suffix) throws Exception {
		ImageInputStream iis = null;
		try {
			// 图片后缀
			Iterator<ImageReader> iterator = ImageIO
					.getImageReadersByFormatName(suffix);
			ImageReader reader = iterator.next();
			iis = ImageIO.createImageInputStream(in);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(startX, satrtY, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, suffix, os);
			
		} catch (Exception e) {
			LOGGER.error("无法找到文件", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(os);
			try {
				if (iis != null) {
					iis.close();
				}
			} catch (IOException e) {
				LOGGER.error("关闭流错误", e);
			}
		}
	}
	
	
	/**
	 * 图片缩放（非约束比例）
	 * 
	 * @param src
	 *            源图片路径
	 * @param dest
	 *            缩放后的图片路径
	 * @param w
	 * @param h
	 * @throws Exception
	 */
	public static void zoomImageNoRule(InputStream in, OutputStream out,String dest, int w, int h) {
		try {
			BufferedImage bufImg = ImageIO.read(in);
			Image tmpImg = bufImg.getScaledInstance(w, h,
					BufferedImage.SCALE_SMOOTH);
			// 宽缩放比例
			double wr = w * 1.0 / bufImg.getWidth();
			// 高缩放比例
			double hr = h * 1.0 / bufImg.getHeight();
			AffineTransformOp ato = new AffineTransformOp(
					AffineTransform.getScaleInstance(wr, hr), null);
			tmpImg = ato.filter(bufImg, null);
			ImageIO.write((BufferedImage) tmpImg,
					dest.substring(dest.lastIndexOf(".") + 1), out);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		}

	}

	/**
	 * 图片缩放（约束比例）
	 * 
	 * @param src
	 * @param dest
	 * @param w
	 * @param h
	 */
//	public static void zoomImageRule(File src, String dest, int w, int h,
//			String fileName, String type) {
//		try {
//			InputStream is = new FileInputStream(src);
//			zoomImageRule(is, dest, w, h, fileName, type);
//		} catch (FileNotFoundException e) {
//			LOGGER.error("文件无法找到", e);
//		}
//	}

	/**
	 * 图片缩放（约束比例）
	 * 
	 * @param src
	 *            源图片路径
	 * @param dest
	 *            缩放后的图片路径
	 * @param w
	 * @param h
	 * @throws Exception
	 */
	public static void zoomImageRule(InputStream src, OutputStream os, int w,
			int h, String fileName, String type) {
		try {
			String suffix = FilenameUtils.getExtension(fileName);
			BufferedImage bufImg = ImageIO.read(src);
			if ("zoom".equals(type)) {
				if (bufImg.getWidth() <= 240 || bufImg.getHeight() <= 240) {
					ImageIO.write(bufImg, suffix, os);
					return;
				}
			}
			// 宽缩放比例
			double wr = w * 1.0 / bufImg.getWidth();
			// 高缩放比例
			double hr = h * 1.0 / bufImg.getHeight();
			double ratio = 0.0;
			if (wr < hr) {
				ratio = h * 1.0 / bufImg.getHeight();
			} else {
				ratio = w * 1.0 / bufImg.getWidth();
			}
			int newWidth = (int) (bufImg.getWidth() * ratio);
			int newHeight = (int) (bufImg.getHeight() * ratio);
			BufferedImage bfImage = null;
			if ("png".equalsIgnoreCase(suffix)
					|| "gif".equalsIgnoreCase(suffix)) {
				bfImage = new BufferedImage(newWidth, newHeight,
						BufferedImage.TYPE_INT_ARGB);
				bfImage.getGraphics().drawImage(
						bufImg.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				ImageIO.write(bfImage, suffix, os);
				return;
			}
			bfImage = new BufferedImage(newWidth, newHeight,
					BufferedImage.TYPE_INT_RGB);
			bfImage.getGraphics().drawImage(
					bufImg.getScaledInstance(newWidth, newHeight,
							Image.SCALE_SMOOTH), 0, 0, null);
			
			ImageIO.write(bfImage, suffix, os);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (src != null) {
					src.close();

				}
			} catch (IOException e) {
				LOGGER.error("IO异常", e);
			}

		}

	}

	/**
	 * 去除图片黑色/白色背景
	 * 
	 * @param src
	 * @param dest
	 */
	public static void transferAlpha(InputStream src, OutputStream dest,
			String fileName) {
		try {
			BufferedImage bufImg = ImageIO.read(src);
			ImageIcon imageIcon = new ImageIcon(bufImg);
			BufferedImage bufferedImage = new BufferedImage(
					imageIcon.getIconWidth(), imageIcon.getIconHeight(),
					BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
			g2D.drawImage(imageIcon.getImage(), 0, 0,
					imageIcon.getImageObserver());
			int alpha = 0;
			for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
					.getHeight(); j1++) {
				for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
						.getWidth(); j2++) {
					int rgb = bufferedImage.getRGB(j2, j1);
					int R = (rgb & 0xff0000) >> 16;
					int G = (rgb & 0xff00) >> 8;
					int B = (rgb & 0xff);
					// if(((255-R)<30) && ((255-G)<30) && ((255-B)<30)){
					// //去除白色背景
					if (((255 - R) > 160) && ((255 - G) > 160)
							&& ((255 - B) > 160)) {// 去除黑色背景
						rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
					}
					bufferedImage.setRGB(j2, j1, rgb);
				}
			}
			g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
			ImageIO.write(bufferedImage, "png", dest);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		}

	}

	/**
	 * 转换图片格式
	 * 
	 * @param src
	 * @param dest
	 */
	public static void formatImg(InputStream src, OutputStream dest) {
		try {
			BufferedImage img = ImageIO.read(src);
			BufferedImage newBufferedImage = new BufferedImage(img.getWidth(),
					img.getHeight(), BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(img, 0, 0, Color.WHITE,
					null);
			ImageIO.write(newBufferedImage, "jpg", dest);
		} catch (IOException e) {
			LOGGER.error("IO异常", e);
		} finally {
			if (src != null) {
				try {
					src.close();
				} catch (IOException e) {
					LOGGER.error("IO异常", e);
				}
			}
		}
	}
}
