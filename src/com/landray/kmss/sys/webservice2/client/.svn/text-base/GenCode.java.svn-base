package com.landray.kmss.sys.webservice2.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


import com.landray.kmss.sys.authentication.ssoclient.Logger;
import org.apache.commons.io.IOUtils;

import com.landray.kmss.sys.webservice2.util.SysWsUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.sso.client.util.StringUtil;
import org.apache.cxf.tools.common.CommandInterfaceUtils;
import org.apache.cxf.tools.common.ToolContext;
import org.apache.cxf.tools.common.ToolException;
import org.apache.cxf.tools.wsdlto.WSDLToJava;

/**
 * 生成客户端代码及服务接口
 * 
 * @author jeff
 * 
 */
public class GenCode {
	public static String TMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * 主方法，生成代码并并打包成压缩文件
	 * 
	 * @param address
	 *            WebService的URL
	 * @param serviceName
	 *            服务名
	 * @param serviceClass
	 *            服务接口
	 * @param serviceBean
	 *            服务实现类
	 * @return 压缩文件全名
	 * @throws Exception
	 */
	public String main(String address, String serviceName, String serviceClass,
			String serviceBean) throws Exception {
		return main(address, serviceName, serviceClass, serviceBean, null, null);
	}

	/**
	 * 主方法，生成代码并并打包成压缩文件
	 * 
	 * @param address
	 *            WebService的URL
	 * @param serviceName
	 *            服务名
	 * @param serviceClass
	 *            服务接口
	 * @param serviceBean
	 *            服务实现类
	 * @param user
	 *            用户名
	 * @param password
	 *            密码的加密文本
	 * @return 压缩文件全名
	 * @throws Exception
	 */
	public String main(String address, String serviceName, String serviceClass,
			String serviceBean, String user, String password) throws Exception {
		String srcDir = TMP_DIR + File.separator + serviceBean + "Source";
		copyClientCode(srcDir);
		addCfgFile(srcDir, address, serviceClass, serviceBean, user, password);
		wsdl2Java(srcDir, address);

		// 压缩文件目录
		String zipFileName = TMP_DIR + File.separator + serviceName
				+ "客户端代码.zip";
		SysWsUtil.zip(zipFileName, new File(srcDir));

		return zipFileName;
	}

	/**
	 * 生成客户端代码
	 * 
	 * @param srcDir
	 *            源代码目录
	 * @throws Exception
	 */
	public void copyClientCode(String srcDir) throws Exception {
		File tmpDir = new File(srcDir);
		if (tmpDir.exists()) {
			FileUtil.deleteDir(tmpDir);
		}

		if (!tmpDir.mkdir()) {
			throw new Exception("创建代码临时目录时发生异常！");
		}
		copyFile("sources/AddSoapHeader.txt", srcDir + File.separator
				+ "AddSoapHeader.java");
		copyFile("sources/WebServiceClient.txt", srcDir + File.separator
				+ "WebServiceClient.java");
		copyFile("sources/WebServiceConfig.txt", srcDir + File.separator
				+ "WebServiceConfig.java");
	}

	private void copyFile(String input, String output) throws Exception {
		InputStream is = this.getClass().getResourceAsStream(input);
		FileOutputStream fos = new FileOutputStream(output);
		try {
			IOUtils.copy(is, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * 根据WSDL生成服务接口类
	 * 
	 * @param srcDir
	 * @param address
	 */
	public void wsdl2Java(String srcDir, String address) {
		String[] args = { "-d", srcDir, "-client", address + "?wsdl" };

		//WSDL2Java.main(args);

		//WSDLToJava.main(args);
		System.setProperty("org.apache.cxf.JDKBugHacks.defaultUsesCaches", "true");
		CommandInterfaceUtils.commandCommonMain();
		WSDLToJava w2j = new WSDLToJava(args);

		try {
			w2j.run(new ToolContext());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建客户端连接配置文件
	 * 
	 * @param address
	 * @param serviceClass
	 * @param serviceBean
	 * @param user
	 * @param password
	 * @throws IOException
	 */
	public void addCfgFile(String srcDir, String address, String serviceClass,
			String serviceBean, String user, String password) throws Exception {
		File cfgFile = new File(srcDir + File.separator + "client.properties");
		if (!cfgFile.exists() && !cfgFile.createNewFile()) {
			throw new Exception("创建客户端连接配置文件时发生异常！");
		}

		PrintWriter pw = new PrintWriter(cfgFile);
		try {
			pw.println("#Web服务的URL");
			pw.println("address=" + address);
			pw.println("#Web服务接口");
			pw.println("service_class=" + serviceClass);
			pw.println("#Web服务标识");
			pw.println("service_bean=" + serviceBean);

			if (StringUtil.isNotNull(user) && StringUtil.isNotNull(password)) {
				pw.println("#用户");
				pw.println("user=" + user);
				pw.println("#密码");
				pw.println("password=" + password);
			}
		} finally {
			pw.close();
		}
	}
}
