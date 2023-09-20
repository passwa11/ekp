package com.landray.kmss.fssc.asset.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.asset.dao.IFsscAssetGoodsDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.asset.model.FsscAssetGoods;
import com.landray.kmss.common.dao.BaseDaoImp;

/**
  * 资产物资 Dao层实现
  */
public class FsscAssetGoodsDaoImp extends BaseDaoImp implements IFsscAssetGoodsDao {

    public String add(IBaseModel modelObj) throws Exception {
        FsscAssetGoods fsscAssetGoods = (FsscAssetGoods) modelObj;
        if (fsscAssetGoods.getDocCreator() == null) {
            fsscAssetGoods.setDocCreator(UserUtil.getUser());
        }
        if (fsscAssetGoods.getDocCreateTime() == null) {
            fsscAssetGoods.setDocCreateTime(new Date());
        }
        return super.add(fsscAssetGoods);
    }
}
