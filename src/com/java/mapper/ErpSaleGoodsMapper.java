package com.java.mapper;

import java.util.List;


import com.java.bean.ErpSaleGoods;

public interface ErpSaleGoodsMapper extends Dao<ErpSaleGoods,String>{

	public List<ErpSaleGoods> getBySaleId(String saleId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String saleId);
}
