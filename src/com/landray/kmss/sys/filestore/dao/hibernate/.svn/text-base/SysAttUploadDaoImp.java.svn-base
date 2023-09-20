package com.landray.kmss.sys.filestore.dao.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateObjectRetrievalFailureException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.landray.kmss.sys.filestore.constant.SysAttUploadConstant;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileSlice;
import com.landray.kmss.util.DbUtils;

public class SysAttUploadDaoImp extends HibernateDaoSupport implements ISysAttUploadDao {

	@Override
	public Session getHibernateSession() {
		return com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this);
	}

	@Override
	public void clearHibernateSession() {
		com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).clear();
	}

	@Override
	public void flushHibernateSession() {
		com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).flush();
	}

	@Override
	public void add(Object modelObj) throws Exception {
		getHibernateTemplate().save(modelObj);
		flushHibernateSession();
	}

	@Override
	public void delete(Object modelObj) throws Exception {
		getHibernateTemplate().delete(modelObj);
		flushHibernateSession();
	}

	@Override
	public Object findByPrimaryKey(String id, Object modelInfo, boolean noLazy) throws Exception {
		Object rtnVal = null;
		if (id != null) {
			try {
				if (modelInfo instanceof Class) {
					if (noLazy) {
						rtnVal = getHibernateTemplate().get((Class) modelInfo, id);
					} else {
						rtnVal = getHibernateTemplate().load((Class) modelInfo, id);
					}
				} else {
					if (noLazy) {
						rtnVal = getHibernateTemplate().get((String) modelInfo, id);
					} else {
						rtnVal = getHibernateTemplate().load((String) modelInfo, id);
					}
				}
			} catch (HibernateObjectRetrievalFailureException e) {
			}
		}
		return rtnVal;
	}

	@Override
	public void update(Object modelObj) throws Exception {
		getHibernateTemplate().saveOrUpdate(modelObj);
		flushHibernateSession();
	}

	@Override
	public Date getCurTimestamp() throws Exception {
		return DbUtils.getDbTime();
	}

	@Override
	public SysAttFile getFileByMd5(String fileMd5, long fileSize) throws Exception {
		String hql = "select sysAttFile from SysAttFile sysAttFile "
				+ "where sysAttFile.fdMd5 = :md5 and sysAttFile.fdFileSize=:fileSize";
		Query query = com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(hql);
		query.setParameter("md5", fileMd5);
		query.setParameter("fileSize", fileSize);
		query.setMaxResults(1);
		List list = query.list();
		if (!list.isEmpty() && list.size() > 0) {
			return (SysAttFile) list.get(0);
		}
		return null;
	}

	@Override
	public long getUploadedCount(String fileID) throws Exception {
		String hql = "select sum(sysAttFileSlice.fdEndPoint - sysAttFileSlice.fdStartPoint) "
				+ "from SysAttFileSlice sysAttFileSlice "
				+ "where sysAttFileSlice.fdFile.fdId=:fileID and sysAttFileSlice.fdStatus=:status ";
		Query query = com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(hql);
		query.setParameter("fileID", fileID);
		query.setParameter("status", SysAttUploadConstant.SYS_ATT_SLICE_STATUS_UPLOADED);
		query.setMaxResults(1);
		List list = query.list();
		if (!list.isEmpty() && list.size() > 0) {
			Object obj = list.get(0);
			if (obj != null) {
				return (Long) obj;
			}
		}
		return 0L;
	}

	@Override
	public SysAttCatalog getDefultCatalog() {
		String hql = "select sysAttCatalog from SysAttCatalog sysAttCatalog " + "where sysAttCatalog.fdIsCurrent=:fdIsCurrent";
		Query query = com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(hql);
		query.setBoolean("fdIsCurrent", true);
		query.setMaxResults(1);
		List list = query.list();
		if (!list.isEmpty() && list.size() > 0) {
			return (SysAttCatalog) list.get(0);
		}
		return null;
	}

	@Override
	public SysAttFileSlice getNextFileSlice(String fileID) throws Exception {
		SysAttFileSlice attFileSlice = null;
		String hql = "select sysAttFileSlice from SysAttFileSlice sysAttFileSlice "
				+ "where sysAttFileSlice.fdFile.fdId=:fileID and sysAttFileSlice.fdStatus=:status "
				+ "order by sysAttFileSlice.fdOrder";
		Query query = com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(hql);
		query.setParameter("fileID", fileID);
		query.setParameter("status", SysAttUploadConstant.SYS_ATT_SLICE_STATUS_INIT);
		query.setMaxResults(1);
		List list = query.list();
		if (!list.isEmpty() && list.size() > 0) {
			attFileSlice = (SysAttFileSlice) list.get(0);
		} else {
			query.setParameter("status", SysAttUploadConstant.SYS_ATT_SLICE_STATUS_UPLOADING);
			query.setMaxResults(1);
			list = query.list();
			if (!list.isEmpty() && list.size() > 0) {
				attFileSlice = (SysAttFileSlice) list.get(0);
			}
		}
		return attFileSlice;
	}
	
	@Override
	public List<SysAttFile> findNotExistMainBeforeByTime(int limitNum) throws Exception {
		String whereBlock = " docCreateTime < :time and not exists (select 1 from sysAttMain where sysAttMain.fdFileId=sysAttFile.fdId) ";
		String hql = "select f from SysAttFile f "
				+ "where not exists (select 1 from SysAttMain m where m.fdFileId=f.fdId) ";
		Query query = com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(hql);
		query.setMaxResults(limitNum);
		return query.list();
	}

	@Override
	public int clearFileSlice(String fileID) throws Exception {
		return com.landray.kmss.sys.hibernate.spi.HibernateWrapper.getSession(this).createQuery(
				"delete from SysAttFileSlice sysAttFileSlice " + "where sysAttFileSlice.fdFile.fdId='" + fileID + "'")
				.executeUpdate();
	}
}
