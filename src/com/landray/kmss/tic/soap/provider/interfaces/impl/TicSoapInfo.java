package com.landray.kmss.tic.soap.provider.interfaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.tic.core.provider.process.provider.interfaces.ITicCoreInfo;
import com.landray.kmss.tic.core.provider.vo.TicSysCateVo;
import com.landray.kmss.tic.core.provider.vo.TicSysFuncVo;
import com.landray.kmss.tic.soap.connector.model.TicSoapCategory;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.service.ITicSoapCategoryService;
import com.landray.kmss.tic.soap.connector.service.ITicSoapMainService;
import com.landray.kmss.util.StringUtil;

public class TicSoapInfo implements ITicCoreInfo {

	private ITicSoapCategoryService ticSoapCategoryService;
	
	private ITicSoapMainService ticSoapMainService;
	
	public ITicSoapCategoryService getTicSoapCategoryService() {
		return ticSoapCategoryService;
	}
	
	public void setTicSoapCategoryService(
			ITicSoapCategoryService ticSoapCategoryService) {
		this.ticSoapCategoryService = ticSoapCategoryService;
	}
	
	public ITicSoapMainService getTicSoapMainService() {
		return ticSoapMainService;
	}


	public void setTicSoapMainService(
			ITicSoapMainService ticSoapMainService) {
		this.ticSoapMainService = ticSoapMainService;
	}


	@Override
    public List<TicSysCateVo> getCateInfo(String selectId, String pluginKey)
			throws Exception {
		// TODO 自动生成的方法存根
		List<TicSysCateVo> cateVos =new ArrayList<TicSysCateVo>(1);
		HQLInfo hqlInfo = new HQLInfo();
		if (StringUtil.isNull(selectId)) {
			hqlInfo
					.setWhereBlock("ticSoapCategory.hbmParent is null");
			hqlInfo.setOrderBy("ticSoapCategory.fdOrder");
		} else {
			hqlInfo
					.setWhereBlock(" ticSoapCategory.hbmParent.fdId =:fdId ");
			hqlInfo.setParameter("fdId", selectId);
			hqlInfo.setOrderBy("ticSoapCategory.fdOrder");
		}
		List<TicSoapCategory> dbList = ticSoapCategoryService
				.findList(hqlInfo);
		for (TicSoapCategory ticSoapCategory : dbList) {
			Map<String, String> h_map = new HashMap<String, String>();
			TicSysCateVo cate=new TicSysCateVo(ticSoapCategory.getFdId(),ticSoapCategory.getFdName(),pluginKey );
			cateVos.add(cate);
		}
		return cateVos;
	}
	
	@Override
    public List<TicSysFuncVo> getFuncDataList(String cateId, String pluginKey) throws Exception{
		HQLInfo hqlInfo = new HQLInfo();
		List<TicSysFuncVo> ticSysFuncVos=new ArrayList<TicSysFuncVo>();
		if (StringUtil.isNull(cateId)) {
			hqlInfo.setWhereBlock("ticSoapMain.wsEnable = 1 and ticSoapMain.docIsNewVersion = '1' ");
		} else {
			//hqlInfo.setSelectBlock(" ticSoapMain.docSubject,ticSoapMain.fdId ");
			hqlInfo
					.setWhereBlock("ticSoapMain.wsEnable = 1 and ticSoapMain.docIsNewVersion = '1' and "
							+ " ticSoapMain.docCategory.fdId in "
							+ " (select ticSoapCategory.fdId from com.landray.kmss.tic.soap.connector.model.TicSoapCategory ticSoapCategory where ticSoapCategory.fdHierarchyId like :selectId ) ");
			hqlInfo.setParameter("selectId", "%"+cateId+"%");
		}
		List<TicSoapMain> dbList = ticSoapMainService.findList(hqlInfo);
		
		for(TicSoapMain ticSoapMain:dbList){
			
			TicSysFuncVo tsv = new TicSysFuncVo(ticSoapMain.getFdId(),
					ticSoapMain.getFdName(), pluginKey);
			ticSysFuncVos.add(tsv);
		}
		return ticSysFuncVos;
	}



	
	

}
