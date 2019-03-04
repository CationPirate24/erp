package com.java.service;

import java.util.List;

import com.java.bean.ErpPurchaseGoods;

public interface ErpPurchaseGoodsService extends Service<ErpPurchaseGoods,String>{

	//ͨ�����id��ѯ��Ӧ�������Ʒ��Ϣ
	public List<ErpPurchaseGoods> getByPurchaseId(String purchaseId);
	
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}

