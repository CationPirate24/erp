package com.java.service;

import java.util.List;

import com.java.bean.ErpSaleGoods;

public interface ErpSaleGoodsService extends Service<ErpSaleGoods,String>{

	public List<ErpSaleGoods> getBySaleId(String saleId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
