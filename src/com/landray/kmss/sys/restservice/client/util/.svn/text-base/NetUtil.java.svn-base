package com.landray.kmss.sys.restservice.client.util;

import java.net.SocketException;
import java.net.UnknownHostException;

import com.landray.kmss.sys.cluster.remoting.util.RemotingHelper;
import com.landray.kmss.util.StringUtil;

public class NetUtil {

	/**
	 * 注册到Cloud的本机Client IP，获取IP优先级为：
	 * <pre>
	 * 1.获取JVM中jgroups绑定ip地址的参数
	 * 2.获取本地非回环IP（如果为IPv6，则格式化为URL语法中的IPv6地址文本格式）
	 * </pre>
	 * @return
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static String getCloundAccessableIp() throws UnknownHostException, SocketException {
		String address = System.getProperty("jgroups.bind_addr");
		if (StringUtil.isNull(address)) {
			address = RemotingHelper.getLocalAddress();
			// Filter network card No
			int index = address.indexOf('%');
			if (index > 0) {
				address = address.substring(0, index) + "]";
			}
		}
		return address;
	}
}
