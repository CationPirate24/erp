package com.java.mapper;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpRequisition;

/*
 * ������
 * 
 */
public interface ErpRequisitionMapper extends Dao<ErpRequisition, String> {

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ��������
	public ErpRequisition getNum(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

}
