package com.java.vo;

public class WarehouseVo {

	private String warehouse_id;		//仓库编号
	private String name;				//仓库名
	private String subsidiary_organ;	//所属机构SUBSIDIARY_ORGAN
	private String principal;			//负责人PRINCIPAL
	private String phone;				//联系电话PHONE
	private String create_time;			//创建时间CREATE_TIME
	private String state;				//状态
	
	private String stateVo;

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

	public String getStateVo() {
		return stateVo;
	}

	public void setStateVo(String stateVo) {
		this.stateVo = stateVo;
	}
	
	
}
