package com.java.mapper;

import java.util.List;

import com.java.bean.ErpSrGoods;

public interface ErpSrGoodsMapper extends Dao<ErpSrGoods,String>{

	public List<ErpSrGoods> getBySrId(String srId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
