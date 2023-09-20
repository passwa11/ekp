package com.landray.kmss.sys.portal.cloud;

import org.springframework.beans.BeanUtils;

import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiOperation;

/**
 * 解析&lt;operation&gt;标签中的多语言信息
 * 
 * @author chao
 *
 */
public class OperationParser {

	public SysUiOperation parse(SysUiOperation operation) {
		SysUiOperation opt = new SysUiOperation();
		// 不改变原来的对象，这里采用复制的方式
		BeanUtils.copyProperties(operation, opt);
		opt.setName(
				CloudPortalUtil.replaceMessageKey(opt.getName(), true));
		return opt;
	}
}
