package com.java.mapper;

import java.util.List;

import com.java.bean.ErpPurchaseGoods;

public interface ErpPurchaseGoodsMapper extends Dao<ErpPurchaseGoods,String>{

	//ͨ�����id��ѯ��Ӧ�������Ʒ��Ϣ
	public List<ErpPurchaseGoods> getByPurchaseId(String purchaseId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
	
}
