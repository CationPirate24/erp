package com.java.vo;

public class ErpPoGoodsVo {

	private int rownum;
	private String id;				//�ɹ�������ϸ��id
	private String goods_id;		//��Ʒ���
	private int goods_num;			//��Ʒ����
	private int goods_prices;		//��Ʒ�۸�
	private String remark;			//��ע
	private String po_id;			//�ɹ���������id
	
	private String goods_name;
	private String goods_type;		//��Ʒ����
	private String goods_unit;		//��Ʒ��λ
	private String warehouse_name;	//��Ʒ���ڲֿ�
	
	private int sumAmount;

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}

	public int getGoods_num() {
		return goods_num;
	}

	public void setGoods_num(int goodsNum) {
		goods_num = goodsNum;
	}

	public int getGoods_prices() {
		return goods_prices;
	}

	public void setGoods_prices(int goodsPrices) {
		goods_prices = goodsPrices;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPo_id() {
		return po_id;
	}

	public void setPo_id(String poId) {
		po_id = poId;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goodsName) {
		goods_name = goodsName;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goodsType) {
		goods_type = goodsType;
	}

	public String getGoods_unit() {
		return goods_unit;
	}

	public void setGoods_unit(String goodsUnit) {
		goods_unit = goodsUnit;
	}


	public int getSumAmount() {
		int sumAmount = this.goods_num*this.goods_prices;
		
		return sumAmount;
	}

	public void setWarehouse_name(String warehouse_name) {
		this.warehouse_name = warehouse_name;
	}

	public String getWarehouse_name() {
		return warehouse_name;
	}

	

	/*private void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}*/

	
	
	
	
}
