package com.java.service;

import java.util.List;

import com.java.bean.ErpSrGoods;

public interface ErpSrGoodsService extends Service<ErpSrGoods,String>{

	public List<ErpSrGoods> getBySrId(String srId);
	//ͨ�������id��ѯ�ܽ��
	public int getAmount(String poId);
}
