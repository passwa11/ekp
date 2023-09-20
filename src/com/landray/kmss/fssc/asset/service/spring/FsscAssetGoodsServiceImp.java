package com.landray.kmss.fssc.asset.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.fssc.asset.model.FsscAssetGoods;
import com.landray.kmss.fssc.asset.service.IFsscAssetGoodsService;
import com.landray.kmss.fssc.asset.util.FsscAssetUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
  * 资产物资 服务实现
  */
public class FsscAssetGoodsServiceImp extends ExtendDataServiceImp implements IFsscAssetGoodsService,IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscAssetGoods) {
            FsscAssetGoods fsscAssetGoods = (FsscAssetGoods) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscAssetGoods fsscAssetGoods = new FsscAssetGoods();
        fsscAssetGoods.setDocCreateTime(new Date());
        fsscAssetGoods.setDocCreator(UserUtil.getUser());
        FsscAssetUtil.initModelFromRequest(fsscAssetGoods, requestContext);
        return fsscAssetGoods;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscAssetGoods fsscAssetGoods = (FsscAssetGoods) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public List getDataList(RequestContext request) throws Exception {
		 List<Map<String, String>> rtnList=new ArrayList<>();
		 return rtnList;
   }

	@Override
	public JSONObject checkGoodsNum(HttpServletRequest request) throws Exception {
		String code="200";//成功
		String msg="";//提示
		JSONObject rtn=new JSONObject();
		String data = request.getParameter("data");
		JSONArray jsonArray = JSONArray.fromObject(data);//将结果转换成JSONArray对象的形式	
		 Map<String, Integer> useNumMap = new HashMap<String, Integer>();
		 if(jsonArray!=null && jsonArray.size()>0){
			 for(int i=0;i<jsonArray.size();i++){
				 JSONObject jsonObj=(JSONObject) jsonArray.get(i);
			        for (Iterator<?> iter = jsonObj.keys(); iter.hasNext(); ) {
			            String key = (String) iter.next();
			            Integer value = Integer.valueOf(jsonObj.get(key).toString());
			            if(useNumMap.get(key)!=null){
			            	value+=useNumMap.get(key);
			            }
			            useNumMap.put(key, value);
			        }
			 }
		 }
		 Set<String> idSet=useNumMap.keySet();
		 if(idSet.size()<=0){
			 rtn.put("code", "404");
				rtn.put("msg", "没找到数据!");
				return rtn;
		 }
		String[] idArr=new String[idSet.size()];
		idSet.toArray(idArr);
		List<FsscAssetGoods> list=this.findByPrimaryKeys(idArr);
		for (FsscAssetGoods fsscAssetGoods : list) {
			Integer num=fsscAssetGoods.getFdNum();
			String id=fsscAssetGoods.getFdId();
			Integer userNum=useNumMap.get(id);
			if(userNum>num){//如果使用值大于库存，那么不能提交并且提示
				code="500";
				msg+="物资:"+fsscAssetGoods.getFdName()+";需要数量:"+userNum+";超出可用数量:"+num+";<br/>";
			}
		}
		rtn.put("code", code);
		rtn.put("msg", msg);
		return rtn;
	}
	
	
	public String[] distankArr(String[] idArr){
		Set<String> set = new HashSet<>();
		for (int i = 0; i < idArr.length; i++) {
			set.add(idArr[i]);
		}
		return (String[]) set.toArray();
		}
	
}
