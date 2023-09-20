package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.km.archives.service.IKmArchivesPeriodService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class KmArchivesPeriodServiceImp extends ExtendDataServiceImp
		implements IKmArchivesPeriodService, IXMLDataBean {

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesPeriod) {
            KmArchivesPeriod kmArchivesPeriod = (KmArchivesPeriod) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesPeriod kmArchivesPeriod = new KmArchivesPeriod();
        kmArchivesPeriod.setDocCreateTime(new Date());
        kmArchivesPeriod.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesPeriod, requestContext);
        return kmArchivesPeriod;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesPeriod kmArchivesPeriod = (KmArchivesPeriod) model;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		checkValue((KmArchivesPeriod) modelObj);
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		checkValue((KmArchivesPeriod) modelObj);
		super.update(modelObj);
	}

	/**
	 * 由于档案文档直接使用“保存年限”具体的值，所以在新增或更新保管期限时，需要防止具体的年限值有重复的情况
	 * 
	 * @param period
	 * @throws Exception
	 */
	private void checkValue(KmArchivesPeriod period) throws Exception {
		if (StringUtil.isNotNull(checkSaveLife(period.getFdSaveLife(), period.getFdId()))) {
			throw new KmssRuntimeException(new KmssMessage("km-archives:error.kmArchivesPeriod.fdSaveLife.repeat"));
		}
	}

	@Override
	public KmArchivesPeriod findByValue(Integer saveLife) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdSaveLife = :fdSaveLife");
		hqlInfo.setParameter("fdSaveLife", saveLife);
//		List<KmArchivesPeriod> list = findList(hqlInfo);
		Object one = findFirstOne(hqlInfo);
		if(one != null){
			return (KmArchivesPeriod) one;
		}
//		if (CollectionUtils.isNotEmpty(list)) {
//			return list.get(0);
//		}
		return null;
	}

	private String checkSaveLife(Integer fdSaveLife, String fdId) throws Exception {
		String result = "";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdSaveLife");
		hqlInfo.setWhereBlock("fdSaveLife = :fdSaveLife and fdId != :fdId");
		hqlInfo.setParameter("fdSaveLife", fdSaveLife);
		hqlInfo.setParameter("fdId", fdId);
		List list = findValue(hqlInfo);
		if (CollectionUtils.isNotEmpty(list)) {
			result = list.get(0).toString();
		}
		return result;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		String fdSaveLife = requestInfo.getParameter("value");
		String fdId = requestInfo.getParameter("fdId");
		if (StringUtil.isNull(fdSaveLife) || StringUtil.isNull(fdId)) {
			map.put("result", "");
			return list;
		}

		String result = checkSaveLife(Integer.parseInt(fdSaveLife), fdId);
		map.put("result", result);
		list.add(map);
		return list;
	}

}
