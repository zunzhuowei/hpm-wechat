package com.keega.common.dal;

import com.keega.common.dal.query.QueryBean;

import javax.annotation.Resource;

public class DalInstance {
	
	/** 实例化数组查询类 */
	@Resource(name="queryArray") protected DalQuery queryArray;
	
	/** 实例化实体查询类 */
	@Resource(name="queryBean") protected QueryBean queryBean;
	
	/** 实例化Map查询类 */
	@Resource(name="queryMap") protected DalQuery queryMap;

	/** 实例化单字段查询类 */
	@Resource(name="querySingle") protected DalQuery querySingle;

	/** 实例化新增操作类 */
	@Resource(name="editInsert") protected DalEdit editInsert;

	/** 实例化更新操作类 */
	@Resource(name="editUpdate") protected DalEdit editUpdate;

	/** 实例化删除操作类 */
	@Resource(name="editDelete") protected DalEdit editDelete;

}
