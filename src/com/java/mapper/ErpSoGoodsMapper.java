package com.java.mapper;

import java.util.List;

import com.java.bean.ErpSoGoods;

public interface ErpSoGoodsMapper extends Dao<ErpSoGoods,String>{

	public List<ErpSoGoods> getBySoId(String soId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
