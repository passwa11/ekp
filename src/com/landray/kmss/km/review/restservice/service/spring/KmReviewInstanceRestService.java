package com.landray.kmss.km.review.restservice.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.restservice.dto.*;
import com.landray.kmss.km.review.restservice.service.IKmReviewInstanceRestService;
import com.landray.kmss.km.review.restservice.util.DTOConvertorUtil;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KmReviewInstanceRestService implements IKmReviewInstanceRestService {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(KmReviewInstanceRestService.class);

	private IKmReviewMainService kmReviewMainService;

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	private ILbpmProcessCurrentInfoService lbpmProcessCurrentInfoService;

	public void setLbpmProcessCurrentInfoService(ILbpmProcessCurrentInfoService lbpmProcessCurrentInfoService) {
		this.lbpmProcessCurrentInfoService = lbpmProcessCurrentInfoService;
	}

	private ILbpmProcessService lbpmProcessService;

	public void setLbpmProcessService(ILbpmProcessService lbpmProcessService) {
		this.lbpmProcessService = lbpmProcessService;
	}

	/**
	 * 审mReviewInstanceRestService kmReviewInstanceService) {
	 * this.kmReviewInstanceService = kmReviewInstance批模板数据列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public KmReviewBaseRestListDTO list(KmReviewInstanceListRequestContext ctx) throws Exception {
		KmReviewBaseRestListDTO result = new KmReviewBaseRestListDTO();
		HQLInfo hqlInfo = new HQLInfo();
		Integer pageNo = ctx.getPageNo();
		Integer rowSize = ctx.getRowSize();
		if (pageNo != null && pageNo > 0) {
			hqlInfo.setPageNo(pageNo);
		}
		if (rowSize != null && rowSize > 0) {
			hqlInfo.setRowSize(rowSize);
		}

		//模板ID
		String fdTemplateId = ctx.getFdTemplateId();
		if (StringUtil.isNotNull(fdTemplateId)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmReviewMain.fdTemplate.fdId=:fdTemplateId"));
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);
		}

		//创建人
		String docCreator = ctx.getDocCreator();
		if (StringUtil.isNotNull(docCreator)) {
			hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmReviewMain.docCreator.fdId=:docCreator"));
			hqlInfo.setParameter("docCreator", docCreator);
		}

		//创建时间
		if (ctx.getDocCreateTime() != null) {
			//开始时间
			long start = ctx.getDocCreateTime().getStart();
			if (start > 0) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmReviewMain.docCreateTime >= :start"));
				hqlInfo.setParameter("start", new Date(start));
			}
			//结束时间
			long end = ctx.getDocCreateTime().getEnd();
			if (end > 0) {
				hqlInfo.setWhereBlock(StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "kmReviewMain.docCreateTime <= :end"));
				hqlInfo.setParameter("end", new Date(end));
			}
		}

		// 当前处理人
		if (StringUtil.isNotNull(ctx.getFdCurrentHandler())) {
			String[] fdCurrentHandler = ctx.getFdCurrentHandler().split(";");
			LbpmUtil.buildLimitBlockForMyApproval("kmReviewMain", hqlInfo, kmReviewMainService.getOrgAndPost(null, fdCurrentHandler));    //getOrgAndPost并没有用到request参数
			hqlInfo.setAuthCheckType(null);
		}

		// 已处理人
		String fdAlreadyHandler = ctx.getFdAlreadyHandler();
		if (StringUtil.isNotNull(fdAlreadyHandler)) {
			LbpmUtil.buildLimitBlockForMyApproved("kmReviewMain", hqlInfo, fdAlreadyHandler);
			hqlInfo.setAuthCheckType(null);
		}

		// 关闭权限校验
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		Page page = kmReviewMainService.findPage(hqlInfo);
		result.setPageNo(page.getPageno());
		result.setRowSize(page.getRowsize());
		result.setTotalPage(page.getTotal());
		result.setTotalSize(page.getTotalrows());
		result.setDatas(convert2BaseDTOList(page.getList()));
		return result;
	}

	/**
	 * 列表DTO转换
	 */
	private List<KmReviewInstanceRestBaseDTO> convert2BaseDTOList(List<KmReviewMain> pageList) {
		List<KmReviewInstanceRestBaseDTO> list = new ArrayList<>();
		for (KmReviewMain model : pageList) {
			list.add(convert2BaseDTO(new KmReviewInstanceRestBaseDTO(), model));
		}
		return list;
	}

	/**
	 * 列表单个DTO转换
	 */
	private KmReviewInstanceRestBaseDTO convert2BaseDTO(KmReviewInstanceRestBaseDTO modelDTO, KmReviewMain model) {
		modelDTO.setFdId(model.getFdId());
		modelDTO.setDocSubject(model.getDocSubject());
		modelDTO.setFdNumber(model.getFdNumber());
		modelDTO.setDocCreateTime(model.getDocCreateTime());
		modelDTO.setDocCreator(DTOConvertorUtil.convert2IdNameProperty(model.getDocCreator(), "getFdName"));
		modelDTO.setDocPublishTime(model.getDocPublishTime());
		modelDTO.setDocStatus(model.getDocStatus());

		LbpmProcess process = getProcess(model.getFdId());
		if (process != null) {
			//流程当前节点
			modelDTO.setWfNode(lbpmProcessCurrentInfoService.getCurNodesName(process));
			//当前审批人
			List<IdNameProperty> wfHandler = new ArrayList<>();
			String currentHandlersId = lbpmProcessCurrentInfoService.getCurrentHandlersId(process);
			String curHanderNames = lbpmProcessCurrentInfoService.getCurHanderNames(process);
			if (StringUtil.isNotNull(currentHandlersId) && StringUtil.isNotNull(curHanderNames)) {
				String[] handlerIdArr = currentHandlersId.split(";");
				String[] handlerNameArr = curHanderNames.split(";");
				for (int i = 0; i < handlerIdArr.length; i++) {
					String name = handlerNameArr.length > i ? handlerNameArr[i] : "";
					wfHandler.add(IdNameProperty.of(handlerIdArr[i], name));
				}
			}
			modelDTO.setWfHandler(wfHandler);
		}
		return modelDTO;
	}

	private LbpmProcess getProcess(String id) {
		try {
			return (LbpmProcess) lbpmProcessService.findByPrimaryKey(id, null, true);
		} catch (Exception e) {
			log.error("[获取审批实例接口]未找到流程实例(ID=" + id + ")", e);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public KmReviewInstanceRestModelDTO get(String fdId) throws Exception {
		if (StringUtil.isNull(fdId)) {
			throw new IllegalArgumentException("fdId is null");
		}
		IBaseModel model = kmReviewMainService.findByPrimaryKey(fdId, KmReviewMain.class, true);
		if (model == null) {
			throw new NoRecordException();
		}
		KmReviewMain instance = (KmReviewMain) model;
		KmReviewInstanceRestModelDTO modelDTO = new KmReviewInstanceRestModelDTO();
		convert2BaseDTO(modelDTO, instance);
		modelDTO.setFdTemplate(DTOConvertorUtil.convert2IdNameProperty(instance.getFdTemplate(), "getFdName"));
		modelDTO.setFdDepartment(DTOConvertorUtil.convert2IdNameProperty(instance.getFdDepartment(), "getFdName"));
		//权限
		modelDTO.getAuthReaders().addAll(DTOConvertorUtil.convert2IdNamePropertyList(instance.getAuthReaders(), "getFdName"));
		//表单数据
		ExtendDataModelInfo extendDataModelInfo = instance.getExtendDataModelInfo();
		if (extendDataModelInfo != null && extendDataModelInfo.getModelData() != null) {
			modelDTO.getModelData().putAll(extendDataModelInfo.getModelData());
		}
		return modelDTO;
	}

}
