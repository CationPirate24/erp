package com.java.service;

import java.util.List;

import com.java.bean.ErpPoGoods;

public interface ErpPoGoodsService extends Service<ErpPoGoods,String>{

	public List<ErpPoGoods> getByPoId(String poId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
