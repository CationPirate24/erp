package com.java.service;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpRequisition;

public interface ErpRequisitionService extends Service<ErpRequisition, String> {
	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ��������
	public ErpRequisition getNum(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

}
