package com.java.mapper;

/*
 * �ֿ�����
 */
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpCashStatement;

public interface ErpCashStatementMapper extends Dao<ErpCashStatement, String> {

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ������
	public int getNum(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

	// ͨ���ֿ��ID��ȡ����,��ȡ�ֿ���������Ʒ�ļ���
	public List<ErpCashStatement> getByWarehouseId(String warehouse_id);
	
	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ�ļ���
	public List<ErpCashStatement> getByWIAndGI(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);

	//���ݲֿ��id����Ʒ��id������Ʒ������
	public void updateNum(ErpCashStatement erpcash);


}
