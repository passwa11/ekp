package com.landray.kmss.sys.zone.webservice;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.zone.context.ImageContext;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZoneImageService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.webservice.exception.SysZoneFaultException;
import com.landray.kmss.sys.zone.webservice.exception.SysZoneFaultUtils;
import com.landray.kmss.sys.zone.webservice.exception.ValidationException;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Controller
@RestApi(docUrl = "/sys/zone/webservice/sysZone_help.jsp",
		name = "sysZoneWebService",
		resourceKey = "对外接口更新用户头像服务")
@RequestMapping(value = "/api/third-zone/sysZoneWebService",
		method = RequestMethod.POST)
public class SysZoneWebService implements ISysZoneWebService{
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysZoneWebService.class);

	private ISysZonePersonInfoService sysZonePersonInfoService;

	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}

	private ISysOrgPersonService sysOrgPersonService;
	public void setSysOrgPersonService(
			ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private ISysZoneImageService sysZoneImageService;

	public void setSysZoneImageService(
			ISysZoneImageService sysZoneImageService) {
		this.sysZoneImageService = sysZoneImageService;
	}

	private String getPersonId(String userId) throws SysZoneFaultException{
		try{
			String personId = null;
			HQLInfo hqlInfo = new HQLInfo();
			String hql = " sysOrgPerson.fdLoginName =:fdLoginName";
			hqlInfo.setParameter("fdLoginName", userId);
			hqlInfo.setWhereBlock(hql);
			hqlInfo.setSelectBlock(" sysOrgPerson.fdId ");
			Object obj = sysOrgPersonService.findFirstOne(hqlInfo);
			if(obj!=null){
				personId = (String)obj;
				return personId;
			}else{
				return null;
			}

		} catch (ValidationException e) {
			logger
					.warn("SysZoneWebService execute getPersonId occur wran:"
							+ e.getMessage());
			SysZoneFaultUtils.throwValidationException(e.getErrorCode(), e
					.getMessage());
		} catch (Throwable ex) {
			logger.error(
					"SysZoneWebService execute getPersonId occur an error:"
							+ ex.getMessage(), ex);
			SysZoneFaultUtils.throwApplicationError();
		}
		return null;
	}

	//更新用户头像
	@Override
    @ResponseBody
	@RequestMapping("/updateUserImage")
	public void updateUserImage(@ModelAttribute String userId, @ModelAttribute AttachImage imagebyte)
			throws SysZoneFaultException {
		String personId = "";
		logger.debug("### SysZoneWebService.updateUserImage start ### userId:"+userId+",imagebyte:"+imagebyte);
		try{
			if(StringUtil.isNotNull(userId) && imagebyte != null){
				ByteArrayInputStream bais = new ByteArrayInputStream(imagebyte.getImageByte());
				BufferedImage bi1 =ImageIO.read(bais);

				personId = getPersonId(userId);
				if(StringUtil.isNotNull(personId)){
					SysZonePersonInfo zonePerson =
							(SysZonePersonInfo)sysZonePersonInfoService.findByPrimaryKey(personId);

					ByteArrayOutputStream imgContent = new ByteArrayOutputStream();
					ImageIO.write(bi1, "png", imgContent);
					String downUrl = sysAttMainService.addAttachment(zonePerson, "personPic",
							imgContent.toByteArray(), "tphoto.png", "byte");
					String attId = downUrl.substring(downUrl.lastIndexOf("=") + 1);
					int startX = 0;
					int startY = 0;
					int width = 240;
					int height = 240;
					if(bi1!=null){
						width = bi1.getWidth();//原图宽
						height = bi1.getHeight();//原图高
					}

					ImageContext imageContext = new ImageContext(null, null,
							width,height, null);
					imageContext = sysZoneImageService.updateZoomImg(attId,
							imageContext);
					String zoomPath = imageContext.getZoomPath();

					ImageContext imageContext2 = new ImageContext(startX, startY, width,
							height, zoomPath);
					sysZoneImageService.updateCropImgById(personId,
							attId, imageContext2);

				}
			}
			logger.debug("### SysZoneWebService.updateUserImage end ### success");
		} catch (ValidationException e) {
			logger
					.warn("SysZoneWebService execute updateUserImage occur wran:"
							+ e.getMessage());
			SysZoneFaultUtils.throwValidationException(e.getErrorCode(), e
					.getMessage());
		} catch (Throwable ex) {
			logger.error(
					"SysZoneWebService execute updateUserImage occur an error:"
							+ ex.getMessage(), ex);
			SysZoneFaultUtils.throwApplicationError();
		}
	}
}
