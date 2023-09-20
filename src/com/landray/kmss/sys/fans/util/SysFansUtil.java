package com.landray.kmss.sys.fans.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysFansUtil {

	static ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
			.getBean("sysAttMainService");
		
	static ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");

	public static final String DEFAULT_IMG = "/sys/person/resource/images/head.png";

	/**
	 * 获取附件头像url
	 * 
	 * @param fdModelId
	 * @param fdModelName
	 * @return
	 * @throws Exception
	 */
	public static String getImgUrl(String fdModelId,String fdModelName,
			HttpServletRequest request) throws Exception {
		
		String imgUrl = new String();
		if(StringUtil.isNotNull(fdModelId)){
			SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdModelId,null,true);
			if(person!=null){ //组织架构内人员
				imgUrl = "/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId=";
				imgUrl += fdModelId;
				
			}else{ //其他人员，如社区公共账号
				
				imgUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=";
				
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock(" sysAttMain.fdModelName = :modelName and sysAttMain.fdModelId = :modelId and sysAttMain.fdAttType = :type");
				hqlInfo.setParameter("modelName",fdModelName);
				hqlInfo.setParameter("modelId",fdModelId);
				hqlInfo.setParameter("type","pic");
				SysAttMain sysAttMain = (SysAttMain)sysAttMainService.findFirstOne(hqlInfo);
				if(sysAttMain != null){  //优先取附件中的图片
					imgUrl += sysAttMain.getFdId();
					
				}else{ //没有附件图片
						imgUrl =  DEFAULT_IMG;
				
				}
			}
		}
	

	
		return imgUrl;
	}
	
}
