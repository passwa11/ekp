package com.landray.kmss.fssc.voucher.service.spring;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.voucher.model.FsscVoucherModelConfig;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherModelConfigService;
import com.landray.kmss.fssc.voucher.util.FsscVoucherUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscVoucherModelConfigServiceImp extends ExtendDataServiceImp implements IFsscVoucherModelConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscVoucherModelConfig) {
            FsscVoucherModelConfig fsscVoucherModelConfig = (FsscVoucherModelConfig) model;
            fsscVoucherModelConfig.setDocAlterTime(new Date());
            fsscVoucherModelConfig.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscVoucherModelConfig fsscVoucherModelConfig = new FsscVoucherModelConfig();
        fsscVoucherModelConfig.setDocCreateTime(new Date());
        fsscVoucherModelConfig.setDocAlterTime(new Date());
        fsscVoucherModelConfig.setDocCreator(UserUtil.getUser());
        fsscVoucherModelConfig.setDocAlteror(UserUtil.getUser());
        FsscVoucherUtil.initModelFromRequest(fsscVoucherModelConfig, requestContext);
        return fsscVoucherModelConfig;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscVoucherModelConfig fsscVoucherModelConfig = (FsscVoucherModelConfig) model;
    }

    @Override
    public FsscVoucherModelConfig getFsscVoucherModelConfig(String fdModelName) throws Exception {
        if(StringUtil.isNull(fdModelName)){
            return null;
        }
        HQLInfo hqlInfo = new HQLInfo();
        StringBuilder where = new StringBuilder();
        where.append(" fsscVoucherModelConfig.fdModelName = :fdModelName ");
        hqlInfo.setParameter("fdModelName", fdModelName);
        hqlInfo.setWhereBlock(where.toString());
        List<FsscVoucherModelConfig> list = this.findList(hqlInfo);
        return ArrayUtil.isEmpty(list)?null:list.get(0);
    }

    /**
     * 初始化
     * @return
     * @throws Exception
     */
    @Override
    public String updateInit() throws Exception{
        String path = ConfigLocationsUtil.getWebContentPath();
        String dir = path+"/fssc/voucher/resource/json/FsscVoucherModelConfig_init.json";
        File filePath = new File(dir);
        JSONArray btnArray = null;

        //读取文件
        String input = FileUtils.readFileToString(filePath, "UTF-8");
        //将读取的数据转换为JSONObject
        JSONObject jsonObject = JSONObject.fromObject(input);
        if (jsonObject != null) {
            btnArray = jsonObject.getJSONArray("datas");
        }
        if(ArrayUtil.isEmpty(btnArray)){
            return null;
        }
        Iterator iter =  btnArray.iterator();
        JSONObject object = null;
        while (iter.hasNext()){
            object = JSONObject.fromObject(iter.next());
            if(!(object.containsKey("fdName") && object.containsKey("fdModelName") && object.containsKey("fdPath"))){
                continue;
            }
            if(!FsscCommonUtil.checkHasModule(object.getString("fdPath"))){
    			continue;
    		}
            String fdName = object.getString("fdName");
            String fdModelName = object.getString("fdModelName");
            String fdPath = object.getString("fdPath");
            String fdCategoryName = null;
            String fdCategoryPropertyName = null;
            if(object.containsKey("fdCategoryName") && object.containsKey("fdCategoryPropertyName")) {
                fdCategoryName = object.getString("fdCategoryName");
                fdCategoryPropertyName = object.getString("fdCategoryPropertyName");
            }
            FsscVoucherModelConfig main = getFsscVoucherModelConfig(fdModelName);
            if(main == null){
                main = new FsscVoucherModelConfig();
                main.setDocCreateTime(new Date());
                main.setDocCreator(UserUtil.getUser());
            }
            main.setFdName(fdName);
            main.setFdModelName(fdModelName);
            main.setFdPath(fdPath);
            main.setFdCategoryName(fdCategoryName);
            main.setFdCategoryPropertyName(fdCategoryPropertyName);
            main.setDocAlterTime(new Date());
            main.setDocAlteror(UserUtil.getUser());
            this.update(main);
        }
        return null;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
}
