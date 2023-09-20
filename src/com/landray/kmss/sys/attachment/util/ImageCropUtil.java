package com.landray.kmss.sys.attachment.util;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageCropUtil {
	
	private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ImageCropUtil.class);
	
	public final static String[] CROP_KEYS = new String[] { "_b", "_m", "_s" };
	
	public final static Integer[] B_SIZE = new Integer[] { 120, 120 };
	
	public final static Integer[] M_SIZE = new Integer[] { 60, 60 };

	public final static Integer[] S_SIZE = new Integer[] { 30, 30 };
	
	private static final List<String> ALPHA_IMAGE = new ArrayList<String>();
	static {
		ALPHA_IMAGE.add("png");
		ALPHA_IMAGE.add("gif");
	}
	 
	public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return checkType(stringBuilder.toString().toUpperCase());  
    }

	public static void cropImage(InputStream input, OutputStream out, int startX, int satrtY, int width, int height,
			String suffix) throws Exception {
		ImageInputStream iis = null;
		ByteArrayOutputStream readSuffix = null;
		InputStream readSuffixStream = null;
		InputStream src = null;
		try {
			readSuffix = cloneInputStream(input);
			/**
			 * 因为用户上传的文件后缀有修改过，这里就会报错。这里根据流来获取图片的后缀
			 * 读取后缀*/
			readSuffixStream = new ByteArrayInputStream(readSuffix.toByteArray());
			byte[] b = new byte[3];
			readSuffixStream.read(b, 0, b.length);
			suffix = bytesToHexString(b);
			/**读取后缀end*/
			Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(suffix); 
			src = new ByteArrayInputStream(readSuffix.toByteArray());
			ImageReader reader = iterator.next();
			iis = ImageIO.createImageInputStream(src);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(startX, satrtY, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, suffix, out);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (iis != null) {
					iis.close();
				}
				if (readSuffixStream != null) {
					readSuffixStream.close();
				}
				if (src != null) {
					src.close();
				}
				if (readSuffix != null) {
					readSuffix.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	public static void compress(InputStream src, OutputStream out, int w, int h, String fileName) throws IOException {
		String suffix = FilenameUtils.getExtension(fileName);
		BufferedImage bufImg = ImageIO.read(src);
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
		if (ALPHA_IMAGE.contains(suffix)) {
			// 防止png、gif白色背景变成黑色
			bfImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
			bfImage.getGraphics().drawImage(bufImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0,
					null);
			ImageIO.write(bfImage, suffix, out);
			return;
		}
		bfImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		bfImage.getGraphics().drawImage(bufImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
		
		
		ImageWriter imageWriter  =  ImageIO.getImageWritersBySuffix("jpeg").next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(out);
        imageWriter.setOutput(ios);
        
        JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
        jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
        jpegParams.setCompressionQuality(1L);
        
        //and metadata
        IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(bfImage), jpegParams);
        
        
        imageWriter.write(imageMetaData, new IIOImage(bfImage, null, null), jpegParams);
        ios.close();
        imageWriter.dispose();
		
       //import com.sun.image.codec.jpeg.JPEGCodec;
       //import com.sun.image.codec.jpeg.JPEGEncodeParam;
       //import com.sun.image.codec.jpeg.JPEGImageEncoder;
		//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		//JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bfImage);
		//jep.setQuality(1L, true);
		//encoder.encode(bfImage, jep);
		//out.flush();
		//ImageIO.write(bfImage, suffix, out);
	}
	private static ByteArrayOutputStream cloneInputStream(InputStream input) {
	    try {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len;
	        while ((len = input.read(buffer)) > -1) {
	            baos.write(buffer, 0, len);
	        }
	        baos.flush();
	        return baos;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	public static String checkType(String xxxx) {
	        /**
	         常用文件的文件头如下：(以前六位为准)
	         JPEG (jpg)，文件头：FFD8FF 
	         PNG (png)，文件头：89504E47 
	         GIF (gif)，文件头：47494638 
	         TIFF (tif)，文件头：49492A00 
	         Windows Bitmap (bmp)，文件头：424D 
	         CAD (dwg)，文件头：41433130 
	         Adobe Photoshop (psd)，文件头：38425053 
	         Rich Text Format (rtf)，文件头：7B5C727466 
	         XML (xml)，文件头：3C3F786D6C 
	         HTML (html)，文件头：68746D6C3E 
	         Email [thorough only] (eml)，文件头：44656C69766572792D646174653A 
	         Outlook Express (dbx)，文件头：CFAD12FEC5FD746F 
	         Outlook (pst)，文件头：2142444E 
	         MS Word/Excel (xls.or.doc)，文件头：D0CF11E0 
	         MS Access (mdb)，文件头：5374616E64617264204A 
	         WordPerfect (wpd)，文件头：FF575043 
	         Postscript (eps.or.ps)，文件头：252150532D41646F6265 
	         Adobe Acrobat (pdf)，文件头：255044462D312E 
	         Quicken (qdf)，文件头：AC9EBD8F 
	         Windows Password (pwl)，文件头：E3828596 
	         ZIP Archive (zip)，文件头：504B0304 
	         RAR Archive (rar)，文件头：52617221 
	         Wave (wav)，文件头：57415645 
	         AVI (avi)，文件头：41564920 
	         Real Audio (ram)，文件头：2E7261FD 
	         Real Media (rm)，文件头：2E524D46 
	         MPEG (mpg)，文件头：000001BA 
	         MPEG (mpg)，文件头：000001B3 
	         Quicktime (mov)，文件头：6D6F6F76 
	         Windows Media (asf)，文件头：3026B2758E66CF11 
	         MIDI (mid)，文件头：4D546864 
	         */
	        switch (xxxx) {
	            case "FFD8FF": return "jpg";
	            case "89504E": return "png";
	            case "474946": return "gif";
	            default: return "0000";
	        }
	    }
}
