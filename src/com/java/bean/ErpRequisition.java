package com.java.bean;

/*
 * ������
 * 
 */

public class ErpRequisition {

	private int rownum;   //��ҳ����
	public int getRownum(){
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	private String requisition_id; //���
	private String goods_id;   //��Ʒ���
	private String out_warehouse_id;   //�����ֿ�ID
	private String in_warehouse_id;   //�����ֿ�id
	private int num;   //������
	private String requision_state;   //����״̬
	private String describe;   //��ע
	private String out_time;   //����ʱ��
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getRequisition_id() {
		return requisition_id;
	}
	public void setRequisition_id(String requisitionId) {
		requisition_id = requisitionId;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goodsId) {
		goods_id = goodsId;
	}
	public String getOut_warehouse_id() {
		return out_warehouse_id;
	}
	public void setOut_warehouse_id(String outWarehouseId) {
		out_warehouse_id = outWarehouseId;
	}
	public String getIn_warehouse_id() {
		return in_warehouse_id;
	}
	public void setIn_warehouse_id(String inWarehouseId) {
		in_warehouse_id = inWarehouseId;
	}
	
	public String getRequision_state() {
		return requision_state;
	}
	public void setRequision_state(String requisionState) {
		requision_state = requisionState;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getOut_time() {
		return out_time;
	}
	public void setOut_time(String outTime) {
		out_time = outTime;
	}
	
	
	
	
	
	
	
}
