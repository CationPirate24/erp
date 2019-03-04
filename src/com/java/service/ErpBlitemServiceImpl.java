package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.java.bean.ErpBlitem;
import com.java.mapper.ErpBlitemMapper;

/*
 * ����һ���̵㵥
 */
public class ErpBlitemServiceImpl implements ErpBlitemService {

	@Autowired
	private ErpBlitemMapper erpBlitemMapper;

	public boolean add(ErpBlitem t) {

		return erpBlitemMapper.add(t);
	}

	public void delete(String id) {
		erpBlitemMapper.delete(id);

	}

	public List<ErpBlitem> getAll(String con) {

		return erpBlitemMapper.getAll(con);
	}

	public ErpBlitem getById(String id) {

		return erpBlitemMapper.getById(id);
	}

	public void update(ErpBlitem t) {

		erpBlitemMapper.update(t);

	}

	// ͨ���ֿ��id��ȡ�ֿ������е�List<ErpBlitem>
	public List<ErpBlitem> getByWarehouseId(String warehouseId) {

		return erpBlitemMapper.getByWarehouseId(warehouseId);
	}

	// ͨ���ֿ��ID����Ʒ��ID��ȡ��Ʒ��������
	public ErpBlitem getNum(String warehouseId, String goodsId) {

		return erpBlitemMapper.getNum(warehouseId, goodsId);
	}

	public List<ErpBlitem> getByWIAndGI(String warehouseId, String goodsId) {
		
		return erpBlitemMapper.getByWIAndGI(warehouseId, goodsId);
	}

}
