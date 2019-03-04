package com.java.bean;

/**
 * 销售单商品明细类 对应销售商品明细表
 * 
 * @author Administrator
 *
 */
public class ErpSaleGoods {

	private int rownum;
	private String id;				//采购订单明细表id
	private String goods_id;		//商品编号
	private int goods_num;			//商品数量
	private int goods_prices;		//商品价格
	private String remark;			//备注
	private String sale_id;			//采购订单主表id
	private String warehouse_id;		//仓库id
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
	public String getSale_id() {
		return sale_id;
	}
	public void setSale_id(String saleId) {
		sale_id = saleId;
	}
	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}
	public String getWarehouse_id() {
		return warehouse_id;
	}
	
}
