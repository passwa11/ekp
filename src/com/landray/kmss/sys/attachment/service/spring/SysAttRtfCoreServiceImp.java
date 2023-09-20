package com.landray.kmss.sys.attachment.service.spring;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import org.apache.commons.io.IOUtils;
import org.hibernate.query.Query;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseCoreOuterServiceImp;
import com.landray.kmss.sys.attachment.interfaces.ISysAttRtfForm;
import com.landray.kmss.sys.attachment.interfaces.ISysAttRtfModel;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.service.ISysAttRtfCoreService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysAttRtfCoreServiceImp extends BaseCoreOuterServiceImp implements
		ISysAttRtfCoreService {

	private final static String imgReg = "<img.*?src=[^>]*?>";;
	private final static String srcReg = "\".*?/resource/fckeditor/editor/filemanager/download.*?\"";
	private final static String fdIdReg = "fdId=([^&]*)";
	private final static String videoReg = "<video.*?src=[^>]*?>";
	protected ISysAttUploadService sysAttUploadService;
	protected ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}
	public ISysAttUploadService getSysAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}
 
	@Override
	protected void save(IBaseModel model) throws Exception {
		if (model instanceof ISysAttRtfModel) {
			ISysAttRtfModel _model = (ISysAttRtfModel) model;
			if (StringUtil.isNull(_model.getDocContent())) {
                return;
            }
			List<String> fdIds = getRtfPics(_model.getDocContent());
			
			fdIds.addAll(getRtfVideos(_model.getDocContent()));
			
			String sql = "delete com.landray.kmss.sys.attachment.model.SysAttRtfData"
					     + " sysAttRtfData where sysAttRtfData.fdModelName=? and sysAttRtfData.fdModelId=? ";
			if(fdIds.size() > 0) {
				sql = StringUtil.linkString(sql,  " and ", "sysAttRtfData.fdId not in(:fdIds)");
			}
			Query query = getSysAttMainService()
					.getBaseDao()
					.getHibernateSession() 
					.createQuery(sql);
			query.setString(0, ModelUtil.getModelClassName(_model));
			query.setString(1, model.getFdId());
			if(fdIds.size() > 0) {
				query.setParameterList("fdIds", fdIds);
			}
			query.executeUpdate();
		}
	}
	/**
	 * 本地图片转化为rtf域图片参数进行展示，传出id by 徐先博 2019-07-18
	 */
	@Override 
	public String saveSysAttRtfDataByfilePath(String fdApplicantId,String fdModelName,String filePath) throws Exception {
		  String fileName = filePath.substring(filePath.lastIndexOf('/') + 1); 
		  File qrcPIC = new File(filePath);
		  double fdSize =qrcPIC.length();
		  FileInputStream fileInputStream = new FileInputStream(qrcPIC);
		  SysAttRtfData sysAttRtfData = new SysAttRtfData();
		  sysAttRtfData.setFdFileName(fileName);
		  sysAttRtfData.setFdContentType(FileMimeTypeUtil.getContentType(fileName));
		  sysAttRtfData.setDocCreateTime(new Date());
		  sysAttRtfData.setInputStream(fileInputStream);
		  sysAttRtfData.setFdModelId(fdApplicantId);
		  sysAttRtfData.setFdModelName(fdModelName);
		  sysAttRtfData.setFdSize(fdSize);
		  String sysAttRtfDataId = getSysAttMainService().addRtfData(sysAttRtfData, fileInputStream);
		  if(StringUtil.isNotNull(sysAttRtfDataId)){ 
			 qrcPIC.delete();
		  }
		  return sysAttRtfDataId;
	}

	private List<String> getRtfPics(String docContent) {
		Matcher imgMatcher = Pattern.compile(imgReg).matcher(docContent);
		List<String> fdIds = new ArrayList<String>();
		while (imgMatcher.find()) {
			String img = imgMatcher.group();
			Matcher srcMatcher = Pattern.compile(srcReg).matcher(img);
			while (srcMatcher.find()) {
				String url = srcMatcher.group().substring(1,
						srcMatcher.group().length() - 1);
				Matcher fdIdMatcher = Pattern.compile(fdIdReg).matcher(url);
				while (fdIdMatcher.find()) {
					fdIds.add(fdIdMatcher.group(1));
				}
			}
		}
		return fdIds;
	}
	
	private List<String> getRtfVideos(String docContent) {
		Matcher videoMatcher = Pattern.compile(videoReg).matcher(docContent);
		List<String> fdIds = new ArrayList<String>();
		while (videoMatcher.find()) {
			String video = videoMatcher.group();
			Matcher srcMatcher = Pattern.compile(srcReg).matcher(video);
			while (srcMatcher.find()) {
				String url = srcMatcher.group().substring(1,
						srcMatcher.group().length() - 1);
				Matcher fdIdMatcher = Pattern.compile(fdIdReg).matcher(url);
				while (fdIdMatcher.find()) {
					fdIds.add(fdIdMatcher.group(1));
				}
			}
		}
		return fdIds;
	}


	@Override
	public void convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		if (form instanceof ISysAttRtfForm && model instanceof ISysAttRtfModel) {
			ISysAttRtfForm _form = (ISysAttRtfForm) form;
			ISysAttRtfModel _model = (ISysAttRtfModel) model;
			String docContent = _form.getDocContent();
			if(StringUtil.isNull(docContent)) {
				return ;
			}
			int length = 100;
			String clearHTML = StringUtil.clearHTMLTag(docContent);
			if(StringUtil.isNotNull(clearHTML)) {
				if(clearHTML.length() < 100 ) {
					length = clearHTML.length();
				} 
				_model.setFdRtfSummary(clearHTML
						.substring(0, length));
			}
		}
	}

	@Override
	public void update(IBaseModel model) throws Exception {
		this.save(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void cloneModelToForm(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		if (form instanceof ISysAttRtfForm && model instanceof ISysAttRtfModel) {
			ISysAttRtfModel _model = (ISysAttRtfModel) model;
			String docContent = _model.getDocContent();
			if (StringUtil.isNull(docContent)) {
                return;
            }
			List<String> fdIds = getRtfPics(docContent);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("sysAttRtfData.fdId in(:fdIds)");
			hqlInfo.setParameter("fdIds", fdIds);
			hqlInfo.setModelName(SysAttRtfData.class.getName());
			List<SysAttRtfData> list = getSysAttMainService().findList(hqlInfo);
			for (SysAttRtfData rtf : list) {
				String fdId = rtf.getFdId();
				SysAttRtfData _rtf = (SysAttRtfData) rtf.clone();
				_rtf.setFdModelId(form.getFdId());
				String _fdId = getSysAttMainService().add(_rtf);
				docContent.replace(fdId, _fdId);
			}
			ISysAttRtfForm _form = (ISysAttRtfForm) form;
			_form.setDocContent(docContent);
		}
	}
}
