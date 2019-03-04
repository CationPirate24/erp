package com.java.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpCashStatement;

/*
 * �ֿ�����
 */
public interface ErpCashStatementService extends
		Service<ErpCashStatement, String> {

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ������
	public int getNum(String warehouse_id, String goods_id);

	// ͨ���ֿ��ID��ȡÿ����Ʒ������
	public List<ErpCashStatement> getByWarehouseId(String warehouse_id);

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ�ļ���
	public List<ErpCashStatement> getByWIAndGI(@Param("warehouse_id") String warehouse_id,
			@Param("goods_id") String goods_id);
	//���ݲֿ��id����Ʒ��id������Ʒ������
	//���ݲֿ��id����Ʒ��id������Ʒ������
	public void updateNum(ErpCashStatement erpcash);
}


