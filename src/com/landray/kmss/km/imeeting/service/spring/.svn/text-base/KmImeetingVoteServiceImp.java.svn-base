package com.landray.kmss.km.imeeting.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.forms.KmImeetingVoteForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMapping;
import com.landray.kmss.km.imeeting.model.KmImeetingVote;
import com.landray.kmss.km.imeeting.service.IKmImeetingMappingService;
import com.landray.kmss.km.imeeting.service.IKmImeetingVoteService;
import com.landray.kmss.km.imeeting.util.BoenUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class KmImeetingVoteServiceImp extends BaseServiceImp
		implements IKmImeetingVoteService {

	private IKmImeetingMappingService kmImeetingMappingService;

	public IKmImeetingMappingService getKmImeetingMappingService() {
		if (kmImeetingMappingService == null) {
			kmImeetingMappingService = (IKmImeetingMappingService) SpringBeanUtil
					.getBean("kmImeetingMappingService");
		}
		return kmImeetingMappingService;
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		KmImeetingVoteForm voteForm = (KmImeetingVoteForm) form;
		String optionType = voteForm.getFdVoteOptionType();
		if ("default".equals(optionType)) {
			List<String> options = new ArrayList<>();
			options.add("同意");
			options.add("反对");
			options.add("弃权");
			voteForm.setFdVoteOptions(options);
		}
		return super.add(form, requestContext);
	}

	@Override
	public List<KmImeetingVote> findByTemporaryId(String fdTemporaryId)
			throws Exception {
		List<KmImeetingVote> result = new ArrayList<>();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("kmImeetingVote.fdTemporaryId = :fdTemporaryId");
		hqlInfo.setParameter("fdTemporaryId", fdTemporaryId);
		result = findList(hqlInfo);
		return result;
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmImeetingVote kmImeetingVote = (KmImeetingVote) modelObj;
		KmImeetingMapping kim = getKmImeetingMappingService().findByModelId(
				kmImeetingVote.getFdId(),
				getModelName());
		if (kim != null) {
			deleteVoteTemplatesToBoen(kim.getFdThirdSysId());
		}
		super.delete(modelObj);
	}

	/**
	 * 删除投票模板配置
	 * 
	 * @param kmImeetingMain
	 * @throws Exception
	 */
	private void deleteVoteTemplatesToBoen(String voteId) throws Exception {
		String url = BoenUtil.getBoenUrl() + "/openapi/voteTemplate/"
				+ voteId;
		String result = BoenUtil.sendDelete(url);
		if (StringUtil.isNotNull(result)) {
			JSONObject res = JSONObject.fromObject(result);
			if (res.getInt("status") != 200) {
				String message = (String) res.get("message");
				throw new RuntimeException(message);
			}
		}
	}

}
