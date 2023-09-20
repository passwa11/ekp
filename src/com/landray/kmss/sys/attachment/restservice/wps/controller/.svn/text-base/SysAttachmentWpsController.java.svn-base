package com.landray.kmss.sys.attachment.restservice.wps.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.annotation.RestApi;

import de.sty.io.mimetype.helper.RuntimeException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/api/sys-attachment/sysAttachmentWpsRestService")
@RestApi(docUrl = "/sys/attachment/restservice/sysAttachmentRestServiceHelp.jsp", name = "sysAttachmentWpsRestService", resourceKey = "sys-attachment:sysAttachmentWpsRestService.title")
public class SysAttachmentWpsController {
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentWpsController.class);

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private ISysAttUploadService sysAttUploadService;

	public void setSysAttUploadService(ISysAttUploadService sysAttUploadService) {
		this.sysAttUploadService = sysAttUploadService;
	}

	private String getPersonImage(String personId) throws Exception {
		String personImageUrl = "";
		String modelName = SysZonePersonInfo.class.getName();
		String fdKey = SysZoneConstant.PHOTO_SRC_KEY + ImageCropUtil.CROP_KEYS[0];
		List list = sysAttMainService.findByModelKey(modelName, personId, fdKey);
		// 旧的key值
		if (list.isEmpty()) {
			list = sysAttMainService.findByModelKey(modelName, personId, "zonePersonInfo");
		}
		if (list !=null && !list.isEmpty()) {
			SysAttMain sysAtt = (SysAttMain) list.get(0);
			personImageUrl = sysAttMainService.getRestDownloadUrl(sysAtt.getFdId());
		} else {
			String defaultHeadImageUrl = PersonInfoServiceGetter.getPersonDefaultHeadImageUrl(personId);
			File file = new File(ConfigLocationsUtil.getWebContentPath() + defaultHeadImageUrl);
			if (file.exists()) {
				InputStream in = new FileInputStream(file);
				byte[] bytes = IOUtils.toByteArray(in);
				personImageUrl = "data:image/png;base64," + new String(Base64.encodeBase64(bytes));
			}
		}
		return personImageUrl;
	}

	private boolean checkToken(HttpServletRequest request) throws Exception {
		boolean result = true;
		String token = request.getHeader("x-wps-weboffice-token");
		String fileId = request.getHeader("x-weboffice-file-id");
		String expires = request.getParameter("_w_Expires");
		System.out.println();
		System.out.println("token:" + token);
		System.out.println("fileId:" + fileId);
		System.out.println("expires:" + expires);
		if (StringUtil.isNotNull(expires) && Long.valueOf(expires) < System.currentTimeMillis()) {
			throw new RuntimeException("token已过期");
		}
		result = sysAttMainService.validateDownloadSignatureRest(expires, fileId, token);
		if(!result){
			throw new RuntimeException("请求签名无效");
		}
		return result;
	}



	// 2. 获取文件元数据
	@RequestMapping(value = "/v1/3rd/file/info", method = RequestMethod.GET)
	@ResponseBody
	public Object fileInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("_w_fileId") String fileId)
			throws Exception {
		System.out.println("/v1/3rd/file/info被调用");
		if (fileId == null || fileId.isEmpty()) {
			return null;
		}
		SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fileId);
		if (sysAttMain == null) {
			return null;
		}
		if (checkToken(request)) {
			String fdMode = request.getParameter("_w_fdMode");

			String userid = request.getParameter("_w_userid");
			String username = request.getParameter("_w_username");
			String markword = request.getParameter("_w_markWord");

			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			SysOrgPerson creator = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(sysAttMain.getFdCreatorId());
			String filename = sysAttMain.getFdFileName();
			JSONObject jsonObject = new JSONObject();
			JSONObject file = new JSONObject();
			JSONObject user = new JSONObject();
			try {
				file.put("id", fileId);
				file.put("name", filename);
				file.put("version", 1);
				file.put("size", sysAttMain.getFdSize());
				file.put("creator", creator.getFdName());
				file.put("create_time", sysAttMain.getDocCreateTime() != null ? sysAttMain.getDocCreateTime().getTime()
						: new Date().getTime());
				file.put("modifier", username);
				file.put("modify_time", new Date().getTime());
				file.put("download_url", sysAttMainService.getRestDownloadUrl(fileId));
				JSONObject watermarkCfg = SysAttViewerUtil.getWaterMarkConfigInDB(true);
				if (StringUtil.isNull(markword)) {
					markword = username;
				}
				JSONObject watermark = new JSONObject();
				watermark.put("type", 1);
				watermark.put("value", markword);
				String markOpacity = "rgba( 192, 192, 192, 0.6 )";
				if (watermarkCfg.get("markOpacity") != null) {
					markOpacity = "rgba( 192, 192, 192, " + watermarkCfg.get("markOpacity") + ")";
				}
				watermark.put("fillstyle", markOpacity);
				String markWordFontFamily = "bold 20px Serif";
				if (watermarkCfg.get("markWordFontFamily") != null) {
					markWordFontFamily = "bold " + watermarkCfg.get("markWordFontSize") + "px "
							+ watermarkCfg.get("markWordFontFamily");
				}
				watermark.put("font", markWordFontFamily);
				watermark.put("rotate", -0.7853982);

				int markRowSpace = 50;
				if (watermarkCfg.get("markRowSpace") != null) {
					markRowSpace = watermarkCfg.getInt("markRowSpace");
				}
				watermark.put("horizontal", markRowSpace);
				int markColSpace = 100;
				if (watermarkCfg.get("markColSpace") != null) {
					markColSpace = watermarkCfg.getInt("markColSpace");
				}
				watermark.put("vertical", markColSpace);

				file.put("watermark", watermark);

				jsonObject.put("file", file);
				user.put("id", userid);
				user.put("name", username);
				user.put("permission", fdMode);
				user.put("avatar_url", getPersonImage(userid));
				jsonObject.put("user", user);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
		return null;
	}

	// 3. 获取用户信息
	@RequestMapping(value = "/v1/3rd/user/info", method = RequestMethod.POST)
	@ResponseBody
	public Object userInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String ids)
			throws Exception {
		System.out.println("/v1/3rd/user/info被调用");
		if (checkToken(request)) {
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			if (StringUtil.isNotNull(ids)) {
				JSONObject paramJson = JSONObject.fromObject(ids);
				if (paramJson.get("ids") != null) {
					JSONArray idArray = paramJson.getJSONArray("ids");
					if (!idArray.isEmpty()) {
						JSONObject jsonObject = new JSONObject();
						JSONArray jsonArray = new JSONArray();
						for (int i = 0; i < idArray.size(); i++) {
							String id = idArray.getString(i);
							SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(id,
									SysOrgPerson.class, true);
							if (person != null) {
								JSONObject user = new JSONObject();
								user.put("id", person.getFdId());
								user.put("name", person.getFdName());
								user.put("avatar_url", getPersonImage(person.getFdId()));
								jsonArray.add(user);
							}
						}
						jsonObject.put("users", jsonArray);
						return jsonObject.toString();
					}
				}
			}
		}
		return null;
	}

	// 4. 通知此文件目前有那些人正在协作
	@RequestMapping(value = "/v1/3rd/file/online", method = RequestMethod.POST)
	@ResponseBody
	public void online(HttpServletRequest request, HttpServletResponse response, @RequestBody String ids)
			throws Exception {
		System.out.println("/v1/3rd/file/online被调用");
		if (checkToken(request)) {
			if (StringUtil.isNotNull(ids)) {
				JSONObject paramJson = JSONObject.fromObject(ids);
				if (paramJson.get("ids") != null) {
					JSONArray idArray = paramJson.getJSONArray("ids");
					if (!idArray.isEmpty()) {
						for (int i = 0; i < idArray.size(); i++) {
							System.out.println("id:" + idArray.getString(i));
						}
					}
				}
			}
		}
	}

	// 5. 上传文件新版本
	@RequestMapping(value = "/v1/3rd/file/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file, @RequestParam("_w_fileId") String fileId)
			throws Exception {
		System.out.println("/v1/3rd/file/save被调用");
		if (checkToken(request)) {
			JSONObject jsonObject = new JSONObject();
			JSONObject fileInfo = new JSONObject();
			if (!file.isEmpty() && StringUtil.isNotNull(fileId)) {
				try {
					SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fileId);
					if (sysAttMain != null) {
						InputStream in = file.getInputStream();
						sysAttMain.setInputStream(in);
						sysAttMain.setFdSize(new Double(in.available()));
						sysAttMainService.update(sysAttMain);
						fileInfo.put("id", fileId);
						fileInfo.put("name", sysAttMain.getFdFileName());
						fileInfo.put("version", 1);
						fileInfo.put("size", sysAttMain.getFdSize());
						fileInfo.put("download_url", sysAttMainService.getRestDownloadUrl(fileId));
						jsonObject.put("file", fileInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return jsonObject.toString();
		}
		return null;
	}

	// 6. 获取特定版本的文件信息
	@RequestMapping(value = "/v1/3rd/file/version", method = RequestMethod.GET)
	@ResponseBody
	public Object fileVersionInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("_w_fileId") String fileId)
			throws Exception {
		System.out.println("/v1/3rd/file/version被调用");
		if (fileId == null || fileId.isEmpty()) {
			return null;
		}
		SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fileId);
		if (sysAttMain == null) {
			return null;
		}
		if (checkToken(request)) {
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			SysOrgPerson creator = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(sysAttMain.getFdCreatorId());
			String filename = sysAttMain.getFdFileName();
			JSONObject jsonObject = new JSONObject();
			JSONObject file = new JSONObject();
			String username = request.getParameter("_w_username");
			try {
				file.put("id", fileId);
				file.put("name", filename);
				file.put("version", 1);
				file.put("size", sysAttMain.getFdSize());
				file.put("creator", creator.getFdName());
				file.put("create_time", sysAttMain.getDocCreateTime().getTime());
				file.put("modifier", username);
				file.put("modify_time", new Date().getTime());
				file.put("download_url", sysAttMainService.getRestDownloadUrl(fileId));
				jsonObject.put("file", file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
		return null;
	}

	// 7. 文件重命名
	@RequestMapping(value = "/v1/3rd/file/rename", method = RequestMethod.PUT)
	@ResponseBody
	public Object fileRename(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("_w_fileId") String fileId) throws Exception {
		System.out.println("/v1/3rd/file/rename被调用");
		if (checkToken(request)) {
			String fdNewName = request.getParameter("name");
			int status = 200;
			String msg = "重命名成功";
			try {
				if (fileId == null || fileId.isEmpty()) {
					msg = "文件不存在";
					return null;
				}
				SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fileId);
				if (sysAttMain == null) {
					msg = "文件不存在";
					return null;
				}
				sysAttMain.setFdFileName(fdNewName);
				sysAttMainService.update(sysAttMain);
			} catch (Exception e) {
				status = response.getStatus();
				msg = e.getMessage();
			}
	
			JSONObject result = new JSONObject();
			result.put("status", status);
			result.put("msg", msg);
			return result.toString();
		}
		return null;
	}

	// 8.获取所有历史版本文件信息
	@RequestMapping(value = "/v1/3rd/file/history", method = RequestMethod.POST)
	@ResponseBody
	public Object fileHstoryInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("_w_fileId") String fileId) throws Exception {
		System.out.println("/v1/3rd/file/history被调用");
		if (fileId == null || fileId.isEmpty()) {
			return null;
		}
		SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fileId);
		if (sysAttMain == null) {
			return null;
		}
		if (checkToken(request)) {
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");
			SysOrgPerson creator = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(sysAttMain.getFdCreatorId());
			String filename = sysAttMain.getFdFileName();
			JSONObject jsonObject = new JSONObject();
			JSONArray fileArr = new JSONArray();
			JSONObject file = new JSONObject();
			JSONObject creatorObj = new JSONObject();
			JSONObject modifierObj = new JSONObject();
			String userid = request.getParameter("_w_userid");
			String username = request.getParameter("_w_username");
			try {
				file.put("id", fileId);
				file.put("name", filename);
				file.put("version", 1);
				file.put("size", sysAttMain.getFdSize());
				file.put("create_time", sysAttMain.getDocCreateTime().getTime());
				file.put("modify_time", new Date().getTime());
				file.put("download_url", sysAttMainService.getRestDownloadUrl(fileId));
				creatorObj.put("id", creator.getFdId());
				creatorObj.put("name", creator.getFdName());
				creatorObj.put("avatar_url", getPersonImage(creator.getFdId()));
				file.put("creator", creatorObj);
				modifierObj.put("id", userid);
				modifierObj.put("name", username);
				modifierObj.put("avatar_url", getPersonImage(userid));
				file.put("modifier", modifierObj);
				fileArr.add(file);
				jsonObject.put("histories", fileArr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObject.toString();
		}
		return null;
	}

	// 9. 新建文件
	@RequestMapping(value = "/v1/3rd/file/new", method = RequestMethod.POST)
	@ResponseBody
	public Object newFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile file, @RequestParam("name") String filename) throws Exception {
		JSONObject jsonObject = new JSONObject();
		System.out.println("/v1/3rd/file/new被调用");
		if (checkToken(request)) {
			if (!file.isEmpty() && StringUtil.isNotNull(filename)) {
				try {
					String fdKey = request.getParameter("_w_fdKey");
					String fdModelId = request.getParameter("_w_fdModelId");
					String fdModelName = request.getParameter("_w_fdModelName");
					SysAttMain sysAttMain = new SysAttMain();
					sysAttMain.setFdKey(fdKey);
					sysAttMain.setFdModelId(fdModelId);
					sysAttMain.setFdModelName(fdModelName);
					sysAttMain.setFdFileName(filename);
					InputStream in = file.getInputStream();
					sysAttMain.setInputStream(in);
					sysAttMain.setFdSize(new Double(in.available()));
					sysAttMainService.add(sysAttMain);
					jsonObject.put("redirect_url", sysAttMainService.getRestDownloadUrl(sysAttMain.getFdId()));
					jsonObject.put("user_id", sysAttMain.getFdCreatorId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return jsonObject.toString();
		}
		return null;
	}

	// 10. 回调通知
	@RequestMapping(value = "/v1/3rd/onnotify", method = RequestMethod.POST)
	@ResponseBody
	public Object notifyMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("/v1/3rd/onnotify被调用");
		if (checkToken(request)) {
			JSONObject jsonObject = new JSONObject();
			String cmd = request.getParameter("cmd");
			String body = request.getParameter("body");
			System.out.println(cmd);
			System.out.println(body);
			jsonObject.put("msg", "success");
			return jsonObject;
		}
		return null;
	}


	/**
	 * 业务接收数据接口
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("fdId") String fdId, @RequestParam("Expires") String Expires,
			@RequestParam("token") String token) throws Exception {
		if (sysAttMainService.validateDownloadSignatureRest(Expires, fdId, token)) {
			byte[] byt = null;
			HttpHeaders headers = new HttpHeaders();
			try {
				ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
						.getBean("sysAttMainService");
				SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(fdId);
				InputStream in = sysAttMainService.getInputStream(sysAttMain);
				String filename = sysAttMain.getFdFileName();
				headers.setContentDispositionFormData("attachment", new String(filename.getBytes("UTF-8"), "ISO8859-1"));
				headers.setPragma("public");
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				ByteArrayOutputStream bao = new ByteArrayOutputStream();
				byte[] buff = new byte[100];
				int rc = 0;
				while ((rc = in.read(buff, 0, 100)) > 0) {
					bao.write(buff, 0, rc);
				}
				byt = bao.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<byte[]>(byt, headers, HttpStatus.OK);
		}
		return null;
	}
}
