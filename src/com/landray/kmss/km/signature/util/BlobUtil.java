package com.landray.kmss.km.signature.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.util.IOUtils;

import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil.FileConverter;
import com.landray.kmss.util.StringUtil;

public class BlobUtil {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(BlobUtil.class);
	public static byte[] blobToBytes(Blob blob) throws Exception {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(blob.getBinaryStream());
			byte[] bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;

			while (offset < len
					&& (read = bis.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
			return bytes;
		} catch (Exception e) {
			return null;
		} finally {
			IOUtils.closeQuietly(bis);
			
		}
	}

	/**
	 * 格式化文件大小的显示，以MB，B，G等单位结尾
	 * 
	 * @param fileSize
	 * @return
	 */
	public static String format(String fileSize) {
		String result = "";
		int index;
		double size;
		// fileSize = new String(fileSize);
		if (StringUtil.isNotNull(fileSize)) {
			if ((index = fileSize.indexOf("E")) > 0) {
				size = (Float.parseFloat(fileSize.substring(0, index)) * Math
						.pow(10, Integer
								.parseInt(fileSize.substring(index + 1))));
			} else {
				size = Double.parseDouble(fileSize);
			}
			if (size < 1024) {
                result = size + "B";
            } else {
				double ksize = Math.round(size * 100 / 1024);
				size = ksize / 100;
				// size = Math.round(size * 100 / 1024) / 100;
				if (size < 1024) {
                    result = size + "KB";
                } else {
					double msize = Math.round(size * 100 / 1024);
					size = msize / 100;
					// size = Math.round(size * 100 / 1024) / 100;
					if (size < 1024) {
                        result = size + "M";
                    } else {
						size = Math.round(size * 100 / 1024) / (double) 100;
						result = size + "G";
					}
				}
			}
		}
		return result;
	}

	/**
	 * 文档封面图片
	 * 
	 * @param expert
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getImgUrl(KmSignatureMain kmSignatureMain,
			HttpServletRequest request) throws Exception {
		return "/km/signature/km_signature_main/kmSignatureMain.do?method=docThumb&modelName=com.landray.kmss.km.signature.model.KmSignatureMain"
				+ "&" + "fdId=" + kmSignatureMain.getFdId();
	}

	public static String getThumbUrlByAttMain(SysAttMain attmain) {
		String imgAttUrl = "";
		String fileName = FilenameUtils.getExtension(attmain.getFdFileName());
		String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
		List<FileConverter> converters = SysFileStoreUtil.getFileConverters(
				extName, attmain.getFdModelName());
		if (converters.size() > 0) {
			FileConverter converter = converters.get(0);
			if ("image2thumbnail".equals(converter.getConverterKey())) {
                imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&filekey=sigPic&picthumb=big&fdId="
                        + attmain.getFdId();
            } else {
                imgAttUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=view&filethumb=yes&picthumb=big&fdId="
                        + attmain.getFdId();
            }
		}
		return imgAttUrl;
	}

}
