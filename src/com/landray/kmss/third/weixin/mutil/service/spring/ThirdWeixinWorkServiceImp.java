package com.landray.kmss.third.weixin.mutil.service.spring;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.weixin.mutil.service.IThirdWeixinWorkService;
import com.landray.kmss.third.weixin.mutil.util.WxmutilUtils;
/**
 * 应用配置业务接口实现
 * 
 * @author
 * @version 1.0 2017-05-02
 */
public class ThirdWeixinWorkServiceImp extends ExtendDataServiceImp
		implements
			IThirdWeixinWorkService {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String fdId = super.add(modelObj);
		WxmutilUtils.resetWxworkConfig();
		return fdId;
	}
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		WxmutilUtils.resetWxworkConfig();
		super.update(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		WxmutilUtils.resetWxworkConfig();
		super.delete(modelObj);
	}
}
