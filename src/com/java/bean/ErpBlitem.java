package com.java.bean;

/*
 * ����һ���̵㵥
 */
public class ErpBlitem {
	
	private int rownum;   //��ҳ����
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	private String blitem_id;    //���
	private String warehouse_id;   //�ֿ�ID
	private String goods_id;   //��Ʒ���
	private int num;       //�̵�ǰ������
	private int check_num;    //�̵�����
	private int profit_and_loss;    //ӯ������
	private String handler_id;     //������
	
	public String getBlitem_id() {
		return blitem_id;
	}
	public void setBlitem_id(String blitemId) {
		blitem_id = blitemId;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouseId) {
		warehouse_id = warehouseId;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}


	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCheck_num() {
		return check_num;
	}
	public void setCheck_num(int checkNum) {
		check_num = checkNum;
	}
	public int getProfit_and_loss() {
		return profit_and_loss;
	}
	public void setProfit_and_loss(int profitAndLoss) {
		profit_and_loss = profitAndLoss;
	}
	public String getHandler_id() {
		return handler_id;
	}
	public void setHandler_id(String handlerId) {
		handler_id = handlerId;
	}
	
	
	
	
}