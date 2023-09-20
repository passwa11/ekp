package com.landray.kmss.sys.filestore.scheduler.third.suwell;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.sys.attachment.io.DecryptionInputStream;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.suwell.ofd.custom.agent.HTTPAgent;
import com.suwell.ofd.custom.wrapper.Const;
import com.suwell.ofd.custom.wrapper.Const.Meta;
import com.suwell.ofd.custom.wrapper.Const.PackType;
import com.suwell.ofd.custom.wrapper.Const.Target;
import com.suwell.ofd.custom.wrapper.Packet;
import com.suwell.ofd.custom.wrapper.model.Common;

/**
 * 数科转OFD
 * @author linjw
 *
 */
public class Suwell {

	protected static final Log logger = LogFactory.getLog(Suwell.class);
	
	public static String convertUrl = SysFileStoreUtil.getSuWellConvertUrl() + "convert-issuer/";

	public static HTTPAgent ha = new HTTPAgent(convertUrl);
	/**
	 * 测试http地址是否可以访问
	 * @return
	 */
	public static Boolean isOfdConvertEnable() {
		Boolean isEnable = false;
		URL url;
		try {
			url = new URL(convertUrl);
			// InputStream in = url.openStream();
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(1500);
			int state = con.getResponseCode();
			if (state == 200) {
				isEnable = true;
			}
		} catch (Exception e1) {
			isEnable = false;
			url = null;
		}
		return isEnable;
	}

	/**
	 * 数科office转OFD：提供路径由数科回调来存放转换后的文件
	 * @param is
	 * @param filePath
	 * @param officeExtName
	 */
	public static void officeToOFD(InputStream is, String filePath, String officeExtName) {
		try {
			if (isOfdConvertEnable()) {
				String basePath = SysFileStoreUtil.getSuWellResultPath();
				if (basePath.endsWith("/")) {
					basePath = basePath.substring(0, basePath.lastIndexOf("/"));
				}
				Packet pkg = new Packet(PackType.COMMON, Target.OFD);
				pkg.metadata(Meta.DOC_ID, basePath + filePath);
				pkg.metadata(Const.Meta.CUSTOM_DATA, "FH=00001");
				pkg.file(new Common("1", "doc", is));
				pkg.fileHandler("suwell-custom-handler-suwell-company", "cpcns.convert.mc.impl.FileHandlerBridge",
						null);
				ha.submit(pkg);
			}
		} catch (Exception e) {
			logger.error("数科转OFD异常:", e);
		}
	}
	
	/**
	 * 数科office转OFD：指定路径来保存转换后的文件
	 * @param sysAsttFile
	 * @param input
	 */
	public static void officeToOFDExtend(SysAttFile sysAsttFile,InputStream input) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("====进入Suwell officeToOFDExtend====");
			}
			
			if (isOfdConvertEnable()) {
					if (input instanceof DecryptionInputStream) {
						if (sysAsttFile != null) {
							FileOutputStream fos =null;
							String convertUrl = SysFileStoreUtil.getSuWellConvertUrl() + "convert-issuer/";
							if (logger.isDebugEnabled()) {
								logger.debug("转换服务地址路径:"+convertUrl);
							}
							
							HTTPAgent ha = new HTTPAgent(convertUrl);
							try {
								String pathPrefix = sysAsttFile.getFdCata() == null ? null : sysAsttFile.getFdCata().getFdPath();
								String fdPath = sysAsttFile.getFdFilePath();
								if (logger.isDebugEnabled()) { 
									logger.debug("附件前缀:"+pathPrefix);
									logger.debug("系统文件位置：含有日期和附件id组成的目录:"+fdPath);
								}
								
								//String srcPath = sysFileLocationProxyService.formatReadFilePath(pathPrefix, fdPath);
								String srcPath = SysFileStoreUtil.getSuWellResultPath();
								if (srcPath.endsWith("/")) {
									srcPath = srcPath.substring(0, srcPath.lastIndexOf("/"));
								}
								
								srcPath = srcPath + fdPath ;
								
								if (logger.isDebugEnabled()) { 
									logger.debug("转换结果文件存放地址:"+srcPath);
								}
								
								String targetDir = srcPath + "_convert";
								File targetFileTemp = new File(targetDir);
								if (!targetFileTemp.exists()) {
									targetFileTemp.mkdir();
								}
								String tagetPath = targetDir + File.separator+ "toOFD-Suwell_ofd";
								if (logger.isDebugEnabled()) { 
									logger.debug("转换结果文件所在路径:"+tagetPath);
								}
								
								File srcFile = new File(srcPath);
								if (!srcFile.exists()) {
									return;
								}
								File targetFile = new File(tagetPath);
								if (!targetFile.exists()) {
									targetFile.createNewFile();
								}
								fos = new FileOutputStream(targetFile);
								//ha.officeToOFD(srcFile, fos);
								
								Packet pkg = new Packet(PackType.COMMON, Target.OFD);
								pkg.file(new Common("1", "doc", input)); // 转换文件
								ha.convert(pkg, fos); // 转换文件后写出到指定路径
								
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (fos != null) {
									fos.close();
								}
								ha.close();
							}
						}
					}
			} else {
				logger.warn("数科转OFD服务配置信息有误,无法请求!");
			}
		} catch (Exception e) {
			logger.error("数科转OFD异常:", e);
		}
	}
}
