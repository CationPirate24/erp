package com.java.service;

import java.util.List;

import com.java.bean.ErpSoGoods;

public interface ErpSoGoodsService extends Service<ErpSoGoods,String>{
	
	public List<ErpSoGoods> getBySoId(String soId);
	
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
