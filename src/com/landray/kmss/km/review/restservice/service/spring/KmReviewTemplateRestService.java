package com.landray.kmss.km.review.restservice.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.model.KmReviewTemplate;
import com.landray.kmss.km.review.restservice.dto.KmReviewBaseRestListDTO;
import com.landray.kmss.km.review.restservice.dto.KmReviewTemplateRestBaseDTO;
import com.landray.kmss.km.review.restservice.dto.KmReviewTemplateRestModelDTO;
import com.landray.kmss.km.review.restservice.dto.PaddingRequestContext;
import com.landray.kmss.km.review.restservice.service.IKmReviewTemplateRestService;
import com.landray.kmss.km.review.restservice.util.DTOConvertorUtil;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;

import java.util.ArrayList;
import java.util.List;

public class KmReviewTemplateRestService implements IKmReviewTemplateRestService {

	private IKmReviewTemplateService kmReviewTemplateService;

	public void setKmReviewTemplateService(IKmReviewTemplateService kmReviewTemplateService) {
		this.kmReviewTemplateService = kmReviewTemplateService;
	}

	/**
	 * 审批模板数据列表
	 */
	@Override
	@SuppressWarnings("unchecked")
	public KmReviewBaseRestListDTO list(PaddingRequestContext ctx) throws Exception {
		KmReviewBaseRestListDTO result = new KmReviewBaseRestListDTO();
		HQLInfo hqlInfo = new HQLInfo();
		Integer pageNo = ctx.getPageNo();
		Integer rowSize = ctx.getRowSize();
		if (pageNo != null && pageNo > 0) {
			hqlInfo.setPageNo(pageNo);
		}
		if (rowSize != null && rowSize > 0) {
			if(rowSize > 500) {
                rowSize = 500;
            }
			hqlInfo.setRowSize(rowSize);
		}
		// 关闭权限校验
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		Page page = kmReviewTemplateService.findPage(hqlInfo);
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
	private List<KmReviewTemplateRestBaseDTO> convert2BaseDTOList(List<KmReviewTemplate> pageList) {
		List<KmReviewTemplateRestBaseDTO> list = new ArrayList<>();
		for (KmReviewTemplate template : pageList) {
			list.add(convert2BaseDTO(new KmReviewTemplateRestBaseDTO(), template));
		}
		return list;
	}

	/**
	 * 列表单个DTO转换
	 */
	private KmReviewTemplateRestBaseDTO convert2BaseDTO(KmReviewTemplateRestBaseDTO modelDTO, KmReviewTemplate model) {
		modelDTO.setFdId(model.getFdId());
		modelDTO.setFdName(model.getFdName());
		modelDTO.setFdIsAvailable(model.getFdIsAvailable());
		modelDTO.setFdOrder(model.getFdOrder());
		modelDTO.setDocCreateTime(model.getDocCreateTime());
		modelDTO.setDocCreator(DTOConvertorUtil.convert2IdNameProperty(model.getDocCreator(), "getFdName"));
		return modelDTO;
	}

	/**
	 * 审批模板数据详情
	 */
	@Override
	@SuppressWarnings("unchecked")
	public KmReviewTemplateRestModelDTO get(String fdId) throws Exception {
		if (StringUtil.isNull(fdId)) {
			throw new IllegalArgumentException("fdId is null");
		}
		IBaseModel model = kmReviewTemplateService.findByPrimaryKey(fdId, KmReviewTemplate.class, true);
		if (model == null) {
			throw new NoRecordException();
		}
		KmReviewTemplate template = (KmReviewTemplate) model;
		KmReviewTemplateRestModelDTO modelDTO = new KmReviewTemplateRestModelDTO();
		convert2BaseDTO(modelDTO, template);
		modelDTO.setDocAlterTime(template.getDocAlterTime());
		modelDTO.setDocAlteror(DTOConvertorUtil.convert2IdNameProperty(template.getDocAlteror(), "getFdName"));
		//权限
		modelDTO.getAuthReaders().addAll(DTOConvertorUtil.convert2IdNamePropertyList(template.getAuthReaders(), "getFdName"));
		modelDTO.getAuthEditors().addAll(DTOConvertorUtil.convert2IdNamePropertyList(template.getAuthEditors(), "getFdName"));
		//表单
		modelDTO.getSysFormTemplateModels().addAll(DTOConvertorUtil.convert2PropertyDTOList(template, "reviewMainDoc", KmReviewMain.class.getName()));
		return modelDTO;
	}

}
