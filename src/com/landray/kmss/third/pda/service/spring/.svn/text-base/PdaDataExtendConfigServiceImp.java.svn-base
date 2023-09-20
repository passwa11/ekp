package com.landray.kmss.third.pda.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.third.pda.forms.PdaDataExtendConfigForm;
import com.landray.kmss.third.pda.model.PdaDataExtendConfig;
import com.landray.kmss.third.pda.service.IPdaDataExtendConfigService;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class PdaDataExtendConfigServiceImp extends BaseServiceImp implements
		IPdaDataExtendConfigService {

	@Override
    public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		if (form instanceof PdaDataExtendConfigForm) {
			PdaDataExtendConfigForm configForm = (PdaDataExtendConfigForm) form;
			List<PdaDataExtendConfig> list = configForm.getPdaDataExtendConfigList();
			NativeQuery query = this.getBaseDao().getHibernateSession()
					.createNativeQuery("delete from pda_data_extend_config").addSynchronizedQuerySpace("pda_data_extend_config");
			query.executeUpdate();
			for (int i = 0; i < list.size(); i++) {
				update(list.get(i));
			}
		}
	}
}
