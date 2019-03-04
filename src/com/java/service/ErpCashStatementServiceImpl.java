package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.bean.ErpCashStatement;
import com.java.mapper.ErpCashStatementMapper;

/*
 * �ֿ�����
 */
public class ErpCashStatementServiceImpl implements ErpCashStatementService {

	@Autowired
	private ErpCashStatementMapper erpCashStatementMapper;

	// ͨ���ֿ��ID��ȡ����
	public List<ErpCashStatement> getByWarehouseId(String warehouseId) {

		return erpCashStatementMapper.getByWarehouseId(warehouseId);
	}

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ������
	public int getNum(String warehouseId, String goodsId) {

		return erpCashStatementMapper.getNum(warehouseId, goodsId);
	}

	public boolean add(ErpCashStatement t) {

		return erpCashStatementMapper.add(t);
	}

	public void delete(String id) {

		erpCashStatementMapper.delete(id);

	}

	public List<ErpCashStatement> getAll(String con) {

		return erpCashStatementMapper.getAll(con);
	}

	public ErpCashStatement getById(String id) {

		return erpCashStatementMapper.getById(id);
	}

	public void update(ErpCashStatement t) {

		erpCashStatementMapper.update(t);
	}
	//ͨ����Ʒid�Ͳֿ��id����ȡ����ļ���
	public List<ErpCashStatement> getByWIAndGI(String warehouseId,
			String goodsId) {
		
		return erpCashStatementMapper.getByWIAndGI(warehouseId, goodsId);
	}

	public void updateNum(ErpCashStatement erpcash) {
		erpCashStatementMapper.updateNum(erpcash);
		
	}

	

}
