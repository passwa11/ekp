package com.landray.kmss.sys.organization.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.sql.DataSource;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.dao.ISysOrgPersonDao;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class SysOrgPersonDaoImp extends SysOrgElementDaoImp implements
        ISysOrgPersonDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        SysOrgPerson person = (SysOrgPerson) modelObj;
        loginNameToLowerCase(person);
        return super.add(person);
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        SysOrgPerson person = (SysOrgPerson) modelObj;
        loginNameToLowerCase(person);
        super.update(person);
    }

    @Override
    public void updatePerson(IBaseModel modelObj) throws Exception {
        SysOrgPerson person = (SysOrgPerson) modelObj;
        person.setFdAlterTime(new Date());
        loginNameToLowerCase(person);
        getHibernateTemplate().update(person);
    }

    @Override
    public void abandonPerson(IBaseModel modelObj) throws Exception {
        SysOrgPerson person = (SysOrgPerson) modelObj;
        loginNameToLowerCase(person);
        getHibernateTemplate().update(person);
    }

    // 因为hibernate会缓存数据，所以用原始的SQL去数据库获取原始的数据
    @Override
    @SuppressWarnings("unchecked")
    public String getOriginalPassword(SysOrgPerson person) throws Exception {
        String password = null;
        DataSource dataSource = (DataSource) SpringBeanUtil
                .getBean("dataSource");
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(
                    "select fd_password from sys_org_person where fd_id = ? ");
            ps.setString(1, person.getFdId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                password = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return password;
    }

    private void loginNameToLowerCase(SysOrgPerson person) {
        String loginName = person.getFdLoginName();
        if (StringUtil.isNotNull(loginName)) {
            // 登录名去前后空格
            person.setFdLoginName(loginName.trim());
            // 登录名转小写
            person.setFdLoginNameLower(person.getFdLoginName().toLowerCase());
        }
    }
}
