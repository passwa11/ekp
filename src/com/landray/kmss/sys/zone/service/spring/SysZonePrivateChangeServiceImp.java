package com.landray.kmss.sys.zone.service.spring;

import java.sql.SQLException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.query.NativeQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.zone.forms.SysZonePrivateChangeForm;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.service.ISysZonePrivateChangeService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

public class SysZonePrivateChangeServiceImp extends BaseServiceImp implements
		ISysZonePrivateChangeService {
    
    /** 删除索引的数据库配置记录SQL语句 */
    private static final String DELETE_SEARCH_CONFIG = "delete from sys_app_config where fd_key = 'com.landray.kmss.sys.ftsearch.db.SearchConfig'";


	@Override
	public void updatePrivate(IExtendForm form, RequestContext requestContext)
			throws Exception {
		
		SysZonePrivateChangeForm changeForm = (SysZonePrivateChangeForm)form;
		String fdIds = changeForm.getFdIds();
		if(StringUtil.isNotNull(fdIds)) {
			Boolean isContactPrivate = "1".equals(changeForm.getIsContactPrivate()) ? true : false;
			Boolean isDepInfoPrivate = "1".equals(changeForm.getIsDepInfoPrivate()) ? true : false;
			Boolean isRelationshipPrivate = "1".equals(changeForm.getIsRelationshipPrivate()) ? true : false;
			Boolean isWorkmatePrivate = "1".equals(changeForm.getIsWorkmatePrivate()) ? true : false;
			String[] ids = fdIds.split(";");
			ISysZonePersonInfoService personInfoService = 
						(ISysZonePersonInfoService)SpringBeanUtil
								.getBean("sysZonePersonInfoService");
			
			for(String id : ids) {
				SysZonePersonInfo person = 
						personInfoService.updateGetPerson(id);
				if(UserOperHelper.allowLogOper("updatePrivate", "")){
					UserOperContentHelper.putUpdate(person.getFdId(), person.getDocSubject(),null)
					.putSimple("isContactPrivate", person.getIsContactPrivate(), isContactPrivate)
					.putSimple("isDepInfoPrivate", person.getIsDepInfoPrivate(), isDepInfoPrivate)
					.putSimple("isRelationshipPrivate", person.getIsRelationshipPrivate(), isRelationshipPrivate)
					.putSimple("isWorkmatePrivate", person.getIsWorkmatePrivate(), isWorkmatePrivate);
				}
				person.setIsContactPrivate(isContactPrivate);
				person.setIsDepInfoPrivate(isDepInfoPrivate);
				person.setIsRelationshipPrivate(isRelationshipPrivate);
				person.setIsWorkmatePrivate(isWorkmatePrivate);
				person.setFdLastModifiedTime(new Date());
				personInfoService.update(person);
			}
		}
	}
	
    @Override
    public void deleteSearchConfig(String param) throws Exception {
        final String temp = param;
        getBaseDao().getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String[] params = temp.substring(0, temp.length()-1).split(",");
                String whereBlock = " and fd_field in (";
                for (String modelName : params)
                {
                    whereBlock += "'"+modelName+"',";
                }
                whereBlock = whereBlock.substring(0,whereBlock.length()-1) + ")";
                NativeQuery query = session.createNativeQuery(DELETE_SEARCH_CONFIG+whereBlock);
                Object result = query.executeUpdate();
                return result;
            }
        });
    }
	
}
