package com.java.mapper;

import java.util.List;

import com.java.bean.ErpPoGoods;

public interface ErpPoGoodsMapper extends Dao<ErpPoGoods,String>{
	
	//ͨ�����id��ѯ��Ӧ�������Ʒ��Ϣ
	public List<ErpPoGoods> getByPoId(String poId);
	
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
