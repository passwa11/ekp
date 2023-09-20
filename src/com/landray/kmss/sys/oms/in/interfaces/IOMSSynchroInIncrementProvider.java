package com.landray.kmss.sys.oms.in.interfaces;

import java.util.Date;

public interface IOMSSynchroInIncrementProvider extends OMSSynchroInProvider {

	public void setLastUpdateTime(Date date);

	public IOMSResultSet getAddedRecordBaseAttributes() throws Exception;

	public IOMSResultSet getDeletedRecordBaseAttributes() throws Exception;

	public IOMSResultSet getSynchroRecords() throws Exception;

	public ValueMapTo getDeptParentValueMapTo();

	public ValueMapType[] getDeptParentValueMapType();

	public ValueMapTo getDeptLeaderValueMapTo();

	public ValueMapType[] getDeptLeaderValueMapType();

	public ValueMapTo getDeptSuperLeaderValueMapTo();

	public ValueMapType[] getDeptSuperLeaderValueMapType();

	public ValueMapTo getPersonDeptValueMapTo();

	public ValueMapType[] getPersonDeptValueMapType();

	public ValueMapTo getPersonPostValueMapTo();

	public ValueMapType[] getPersonPostValueMapType();

	public ValueMapTo getPostDeptValueMapTo();

	public ValueMapType[] getPostDeptValueMapType();

	public ValueMapTo getPostLeaderValueMapTo();

	public ValueMapType[] getPostLeaderValueMapType();

	public ValueMapTo getPostPersonValueMapTo();

	public ValueMapType[] getPostPersonValueMapType();

	public ValueMapTo getGroupMemberValueMapTo();

	public ValueMapType[] getGroupMemberValueMapType();
}
