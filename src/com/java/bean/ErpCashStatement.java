package com.java.bean;

/*
 * ����һ�Ųֿ�����pojo
 */

public class ErpCashStatement {
	
	private int rownum;   //��ҳ����
	private String statement_id;   //���
	private String goods_id;   //��Ʒ���
	private String warehouse_id;   //�ֿ�id
	private int goods_num;     //�ֿ��Ʒ����
	
	public String getStatement_id() {
		return statement_id;
	}
	public void setStatement_id(String statementId) {
		statement_id = statementId;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouseId) {
		warehouse_id = warehouseId;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goodsNum) {
		goods_num = goodsNum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getRownum() {
		return rownum;
	}
	
	
}
