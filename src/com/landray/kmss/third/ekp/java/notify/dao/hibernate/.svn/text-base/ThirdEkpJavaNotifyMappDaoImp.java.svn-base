package com.landray.kmss.third.ekp.java.notify.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyMappDao;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyMapp;
import org.hibernate.query.Query;

public class ThirdEkpJavaNotifyMappDaoImp extends BaseDaoImp implements IThirdEkpJavaNotifyMappDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdEkpJavaNotifyMapp thirdEkpJavaNotifyMapp = (ThirdEkpJavaNotifyMapp) modelObj;
        if (thirdEkpJavaNotifyMapp.getDocCreateTime() == null) {
            thirdEkpJavaNotifyMapp.setDocCreateTime(new Date());
        }
        return super.add(thirdEkpJavaNotifyMapp);
    }

    @Override
    public void cleanFinishedNotifyMapp(int days) throws Exception {

        Query query = super.getSession()
                .createQuery(
                        "delete from com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyMapp where docCreateTime<:date and fdNotifyId not in (select fdId from com.landray.kmss.sys.notify.model.SysNotifyTodo)");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        query.setParameter("date", c.getTime());
        query.executeUpdate();

    }
}
