package com.java.vo;

public class DeptVo {

	private String dept_id;			//���ű��
	private String name;			//��������
	private String subsidiary_organ;//�������� SUBSIDIARY_ORGAN
	private String state;			//״̬
	
	private String stateVo;

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
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
