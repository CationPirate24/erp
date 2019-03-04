package com.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpBlitem;

/*
 * ����һ���̵㵥
 */
public interface ErpBlitemMapper extends Dao<ErpBlitem, String> {

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ��������
	public ErpBlitem getNum(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

	// ͨ���ֿ��id��ȡ�ֿ������е�List<ErpBlitem>
	public List<ErpBlitem> getByWarehouseId(String warehouse_id);
	//���ݲֿ��id����Ʒ��id��ȡ�̵㵥�еĶ��󼯺�
	public List<ErpBlitem> getByWIAndGI(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

}
