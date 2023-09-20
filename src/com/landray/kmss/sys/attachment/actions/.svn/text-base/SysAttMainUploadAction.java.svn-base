package com.landray.kmss.sys.attachment.actions;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;


/**
 * 上传附件时使用PUT/POST方法，且以二进制上传文件
 * 用例：政务微信/钉钉调用WPS，WPS上传文件到政务微信的EKP
 * @author
 * @date 2021-01-04
 * 
 */
@Controller
@RequestMapping(value = "/sys/attachment")
public class SysAttMainUploadAction  {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttMainUploadAction.class);
	private ISysAttMainCoreInnerService sysAttMainService;
	
	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}
	
	@RequestMapping(value="/uploadByWpsCallback", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT)
	public void uploadByWpsCallback( @RequestParam String fdId, @RequestParam String useId,
			HttpServletRequest request ,HttpServletResponse response) throws Exception {

		JSONObject json = new JSONObject();
		logger.info("fdId:" + fdId + "\n  useId:" + useId + "");

		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(useId)) {
           //不为二进行流，可以使用以下代码
			/*
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).getServletContext());
			commonsMultipartResolver.setDefaultEncoding("utf-8");
			MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
			MultipartFile multipartFile = multipartRequest.getFile("file");
			ServletInputStream inputStream=    request.getInputStream();//获取输入流

			 BufferedInputStream bis=new BufferedInputStream(inputStream);//转化为带缓存区的输入流
			*/
			InputStream in = null;
				try {
					
					in =  request.getInputStream();
					SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdId);
					sysAttMain.setInputStream(in);
					sysAttMainService.update(sysAttMain);
					json.put("data", "");
					json.put("result", 0);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					json.put("result", -1);
					json.put("message", "保存失败");
				} finally {
					// TODO: handle finally clause
					IOUtils.closeQuietly(in);
				}
				
				 
		} else {
			
			json.put("result", -1);
			json.put("message", "参数错误");
		}
		
		response.setContentType("application/json");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}


	@RequestMapping(value="/uploadFile", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void uploadFile( @RequestParam String fdId, @RequestParam String userId,
									 HttpServletRequest request ,HttpServletResponse response) throws Exception {

		JSONObject json = new JSONObject();
		if(logger.isDebugEnabled()) {
			logger.debug("上传文件信息：fdId:{},userId:{}",fdId, userId);
		}


		if (StringUtil.isNotNull(fdId) && StringUtil.isNotNull(userId)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpServletRequest
					.getFile("file");
			InputStream in = null;
			try {

				in = multipartFile.getInputStream();
				SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdId);
				sysAttMain.setInputStream(in);
				sysAttMainService.update(sysAttMain);
				json.put("message", "");
				json.put("resultCode", 0);
				if(logger.isDebugEnabled()) {
					logger.debug("上传文件处理成功");
				}
			} catch (Exception e) {
				json.put("resultCode", -1);
				json.put("message", "保存失败");
				logger.error("上传文件处理失败：{}", e);
			} finally {
				// TODO: handle finally clause
				IOUtils.closeQuietly(in);
			}


		} else {

			json.put("resultCode", -1);
			json.put("message", "参数错误");
			if(logger.isDebugEnabled()) {
				logger.debug("上传文件处理失败：参数错误");
			}
		}

		response.setContentType("application/json");
		response.getWriter().append(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
}
