package com.java.bean;

/**
 * �ֿ���  �ֶ����Զ�ӦERP_WAREHOUSE��
 * @author Admin
 *
 */
public class ErpWarehouse {

	private int rownum;					//
	private String warehouse_id;		//�ֿ���
	private String name;				//�ֿ���
	private String subsidiary_organ;	//��������SUBSIDIARY_ORGAN
	private String principal;			//������PRINCIPAL
	private String phone;				//��ϵ�绰PHONE
	private String create_time;			//����ʱ��CREATE_TIME
	private String state;				//״̬
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubsidiary_organ() {
		return subsidiary_organ;
	}
	public void setSubsidiary_organ(String subsidiary_organ) {
		this.subsidiary_organ = subsidiary_organ;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
}
