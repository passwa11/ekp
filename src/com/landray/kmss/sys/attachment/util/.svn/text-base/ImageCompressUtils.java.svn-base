package com.landray.kmss.sys.attachment.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.landray.kmss.util.StringUtil;


/**
 * 图片压缩工具类
 * @author 傅游翔
 */
public class ImageCompressUtils {
	
	public static final String IMG_TYPE_JPEG = "jpeg";
	
	public static final List<String> IMG_TYPES = new ArrayList<String>();
	
	static {
		IMG_TYPES.add("jpg");
		IMG_TYPES.add("jpeg");
		IMG_TYPES.add("bmp");
		IMG_TYPES.add("gif");
		IMG_TYPES.add("png");
		IMG_TYPES.add("tif");
	}
	
	public static final boolean isImageType(String type) {
		type = type.toLowerCase();
		for (String imgType : IMG_TYPES) {
			if (imgType.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	private static ImageCompressUtils instance = new ImageCompressUtils();

	protected ImageCompressUtils() {}
	
	public static ImageCompressUtils getInstance() {
		return instance;
	}
	
	/**
	 * 根据文件名判断是否为BMP格式图片
	 * @param fileName
	 * @return
	 */
	public final boolean isBMP(String fileName) {
		if (StringUtil.isNull(fileName)) {
            return false;
        }
		return fileName.toLowerCase().lastIndexOf(".bmp") == fileName.length() - 4;
	}
	
	/**
	 * 
	 * @param src - 图片输入流
	 * @param out - 新图片
	 * @param width - 指定宽度
	 * @param height - 指定高度
	 * @param proportion - 是否等比例压缩
	 * @throws IOException
	 */
	public void compressImage(InputStream src, OutputStream out, 
			int width, int height, boolean proportion) throws IOException {
		Image img = ImageIO.read(src);
    	int imgW = img.getWidth(null);
    	int imgH = img.getHeight(null);
		if (width >= imgW && height >= imgH) {
			return ; // 图片本来就小了，就不处理
		}
		compressImage(img, out, width, height, proportion);
	}
	
	/**
	 * @param src - 图片输入流
	 * @param width - 指定宽度
	 * @param height - 指定高度
	 * @param proportion - 是否等比例压缩
	 * @return - InputStream 新图片
	 * @throws IOException
	 */
	public InputStream compressImage(InputStream src, int width, int height, 
			boolean proportion) throws IOException {
		
		Image img = ImageIO.read(src);
    	int imgW = img.getWidth(null);
    	int imgH = img.getHeight(null);
		if (width >= imgW && height >= imgH) {
			return src; // 图片本来就小了，就不处理
		}

    	ByteArrayOutputStream out = new ByteArrayOutputStream();
		compressImage(img, out, width, height, proportion);
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	/**
	 * @param src - 图片输入流
	 * @param out - 新图片
	 * @param width - 指定宽度
	 * @param height - 指定高度
	 * @param proportion - 是否等比例压缩
	 * @throws IOException
	 */
	public void compressImage(Image src, OutputStream out, 
			int width, int height, boolean proportion) throws IOException {
		Image img = src;
    	int newWidth;
        int newHeight;
        
        if (proportion) {
        	double imgW = (double) img.getWidth(null);
        	double imgH = (double) img.getHeight(null);
        	// 为等比缩放计算输出的图片宽度及高度
            double rate1 = imgW / (double) width + 0.1;
            double rate2 = imgH / (double) height + 0.1;
            // 根据缩放比率大的进行缩放控制
            double rate = rate1 > rate2 ? rate1 : rate2;
            newWidth = (int) (imgW / rate);
            newHeight = (int) (imgH / rate);
        }
        else {
            newWidth = width; // 输出的图片宽度
            newHeight = height; // 输出的图片高度
        }
        
        compressImage(img, out, newWidth, newHeight, ImageCompressUtils.IMG_TYPE_JPEG);
	}
	
	
	public void compressImage(Image img, OutputStream out, 
				int width, int height, String type) throws IOException {
		
		BufferedImage tag = new BufferedImage(
				width, height, 
				BufferedImage.TYPE_INT_RGB);
        
        /*
		 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		 */
		tag.getGraphics().drawImage(
				img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,
				null);
		

		ImageWriter imageWriter  =  ImageIO.getImageWritersBySuffix(type).next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(out);
        imageWriter.setOutput(ios);
        
        JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
        jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1L);
        
        //and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(tag), jpegParams);
        
        
        imageWriter.write(imageMetaData, new IIOImage(tag, null, null), jpegParams);
        ios.close();
        imageWriter.dispose();
		
		// 提高压缩后图片质量
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//		jep.setQuality(1f, true);
//		encoder.encode(tag, jep);
//		out.close();
        //ImageIO.write(tag, type, out);
	}
}
