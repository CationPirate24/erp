package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.ErpAccount;
import com.java.bean.ErpCustomer;
import com.java.bean.ErpDept;
import com.java.bean.ErpGoods;
import com.java.bean.ErpOrganization;
import com.java.bean.ErpPoGoods;
import com.java.bean.ErpPurchaseOrder;
import com.java.bean.ErpUser;
import com.java.bean.ErpWarehouse;
import com.java.service.ErpCustomerService;
import com.java.service.ErpDeptService;
import com.java.service.ErpGoodsService;
import com.java.service.ErpOrganizationService;
import com.java.service.ErpPoGoodsService;
import com.java.service.ErpPurchaseOrderService;
import com.java.service.ErpUserService;
import com.java.service.ErpWarehouseService;
import com.java.util.IdUtil;
import com.java.util.VoUtil;
import com.java.vo.ErpPoGoodsVo;
import com.java.vo.ErpPurchaseOrderVo;

@Controller
public class PurchaseOrderController {

	@Autowired
	private ErpPurchaseOrderService erpPurchaseOrderService;
	@Autowired
	private ErpCustomerService erpCustomerService;
	@Autowired
	private ErpDeptService erpDeptService;
	@Autowired
	private ErpOrganizationService erpOrganizationService;
	@Autowired
	private ErpWarehouseService erpWarehouseService;
	@Autowired
	private ErpUserService erpUserService;
	@Autowired
	private ErpGoodsService erpGoodsService;
	@Autowired
	private ErpPoGoodsService erpPoGoodsService;
	
	
	@RequestMapping("/purchaseOrder/toAddPo.do")
	public ModelAndView toAddPo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("purchaseOrder/addPo");
		String po_id = request.getParameter("po_id");
		System.out.println("��������������������toAddPo��ġ���������������"+po_id);
		if(po_id==null||po_id==""||po_id.equals("null")){
			String poId = IdUtil.getInvoicesId();
			po_id = poId;
			request.setAttribute("po_id", po_id);
			
		}
		request.setAttribute("po_id", po_id);
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);
		List<ErpDept> deptList = erpDeptService.getAll(con);
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);
		List<ErpUser> userList = erpUserService.getAll(con);
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);
		
		if(poGoodsList!=null){
			List<ErpPoGoodsVo> poGoodsVoList = VoUtil.getErpPoGoodsVoList(poGoodsList);
			int totalRecode = poGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("poGoodsList", poGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("warehouseList", warehouseList);
		request.setAttribute("userList", userList);
		return mav;
	}
	
	@RequestMapping("/purchaseOrder/addPo.do")
	public String addPo(HttpServletRequest request,HttpServletResponse response){
		
		System.out.println("-����1-");
		String po_id = request.getParameter("po_id");
		System.out.println("��������������������addPo��ġ���������������"+po_id);
		System.out.println(po_id);
		String create_time = request.getParameter("create_time");
		String supplier_id = request.getParameter("supplier_id");
		String purchaser_id = request.getParameter("purchaser_id");
		String dept_id = request.getParameter("dept_id");
		String warehouse_id = request.getParameter("warehouse_id");
		
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
		String originator_id = ea.getUser_id();	//�Ƶ��˵�id���ǵ�ǰ��¼��Ա��Ա��id
		String invalid_id="";
		int po_amount =	erpPoGoodsService.getAmount(po_id);
		
		ErpPurchaseOrder erpPurchaseOrder = new ErpPurchaseOrder();
		erpPurchaseOrder.setPo_id(po_id);
		erpPurchaseOrder.setCreate_time(create_time);
		erpPurchaseOrder.setSupplier_id(supplier_id);
		erpPurchaseOrder.setPurchaser_id(purchaser_id);
		erpPurchaseOrder.setDept_id(dept_id);
		erpPurchaseOrder.setWarehouse_id(warehouse_id);
		erpPurchaseOrder.setPo_amount(po_amount);
		erpPurchaseOrder.setOriginator_id(originator_id);
		erpPurchaseOrder.setOrganization_id(organization_id);
		erpPurchaseOrder.setInvoices_state(invoices_state);
		erpPurchaseOrder.setInvalid_id(invalid_id);
		
		if(!erpPurchaseOrderService.add(erpPurchaseOrder)){
			List<ErpPoGoods> pogList = erpPoGoodsService.getByPoId(po_id);
			if(pogList!=null){
				for(int i=0;i<pogList.size();i++){
					erpPoGoodsService.delete(pogList.get(i).getId());
				}
			}
		}
		
		return "forward:/purchaseOrder/toListPo.do";
	}
	
	
	@RequestMapping("/purchaseOrder/toAddPoGoods.do")
	public ModelAndView toAddPoGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String po_id = request.getParameter("po_id"); 
		System.out.println("����������#����������toAddPoGoods��ġ�����&����������"+po_id);
		System.out.println("++++++++++++"+po_id);
		String con = "%%";
		List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);
		
		if(poGoodsList!=null){
			List<ErpPoGoodsVo> poGoodsVoList = VoUtil.getErpPoGoodsVoList(poGoodsList);
			int totalRecode = poGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("poGoodsList", poGoodsVoList);
		}
		
		request.setAttribute("po_id", po_id);
		request.setAttribute("goodsList", goodsList);
		mav.setViewName("purchaseOrder/addPoGoods");
		return mav;
	}
	
	
	@RequestMapping("/purchaseOrder/addPoGoods.do")
	public  String addPoGoods(HttpServletRequest request){
		//�ɹ�������Ʒ����û�е���Ʒ�ǣ�Ҫ�����������Ʒ���еļ�¼��Ȼ����Ӵ�����еļ�¼��
		String id = IdUtil.getUuid();
		String goods_id = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������addPoGoods��ġ���������������"+po_id);
		
		int goods_prices = 0;
		ErpGoods erpGoods = erpGoodsService.getById(goods_id);
		if(erpGoods!=null){
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);
		
		System.out.println("++++++++++++"+po_id);
		
		ErpPoGoods erpPoGoods = new ErpPoGoods();
		erpPoGoods.setGoods_id(goods_id);
		erpPoGoods.setId(id);
		erpPoGoods.setGoods_num(goods_num);
		erpPoGoods.setGoods_prices(goods_prices);
		erpPoGoods.setRemark(remark);
		erpPoGoods.setPo_id(po_id);
		erpPoGoodsService.add(erpPoGoods);
		
		request.setAttribute("po_id", po_id);
		return "forward:/purchaseOrder/toAddPoGoods.do";
		
	}
	
	@RequestMapping("/purchaseOrder/toListPo.do")
	public ModelAndView toListPo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		
		//ʹ�ò�ѯ������ȡ����Ҫ��ѯ���ֶ�
		String po_id = request.getParameter("po_id");
		String create_time = request.getParameter("create_time");
		String supplier_id = request.getParameter("supplier_id");
		String purchaser_id = request.getParameter("purchaser_id");
		String dept_id = request.getParameter("dept_id");
		String warehouse_id = request.getParameter("warehouse_id");
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		String originatior_id = request.getParameter("originatior_id");
		
		ErpPurchaseOrder erpPurchaseOrder = new ErpPurchaseOrder();
		erpPurchaseOrder.setPo_id(po_id);
		erpPurchaseOrder.setCreate_time(create_time);
		erpPurchaseOrder.setSupplier_id(supplier_id);
		erpPurchaseOrder.setPurchaser_id(purchaser_id);
		erpPurchaseOrder.setDept_id(dept_id);
		erpPurchaseOrder.setWarehouse_id(warehouse_id);
		erpPurchaseOrder.setOriginator_id(originatior_id);
		erpPurchaseOrder.setOrganization_id(organization_id);
		erpPurchaseOrder.setInvoices_state(invoices_state);
		
		String con = "%%";
		
		List<ErpPurchaseOrderVo> erpPurchaseOrderVoList = new ArrayList<ErpPurchaseOrderVo>();
		if(erpPurchaseOrder==null){
			//�õ���δ�����ѯʱ�����вɹ��������б�
			List<ErpPurchaseOrder> erpPurchaseOrderList = erpPurchaseOrderService.getAll(con);
			if(erpPurchaseOrderList!=null){//ת����vo����ҳ�����
				erpPurchaseOrderVoList = VoUtil.getErpPoVoList(erpPurchaseOrderList);
				int totalRecode = erpPurchaseOrderVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
			}
			
		}else{
			//���ݵ����ѯʱ��ȡ�Ĳ�ѯ�����õ�����
			List<ErpPurchaseOrder> erpPurchaseOrderList = erpPurchaseOrderService.selectPo(erpPurchaseOrder);
			if(erpPurchaseOrderList!=null){//ת����vo����ҳ�����
				erpPurchaseOrderVoList = VoUtil.getErpPoVoList(erpPurchaseOrderList);
				int totalRecode = erpPurchaseOrderVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
				request.setAttribute("erpPurchaseOrderList", erpPurchaseOrderVoList);
			}
		}
		request.setAttribute("erpPurchaseOrderList", erpPurchaseOrderVoList);
		
		//��ȡ���пɲ�ѯ���ֶε��б�
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);//��Ӧ���б�
		List<ErpDept> deptList = erpDeptService.getAll(con);//�����б�
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);//�����б�
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);//�ֿ��б�
		List<ErpUser> userList = erpUserService.getAll(con);//Ա���б�
		//����Щ�б���ҳ�洫ֵ
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("warehouseList", warehouseList);
		request.setAttribute("userList", userList);
		
		request.setAttribute("po_id", po_id);
		mav.setViewName("purchaseOrder/listPo");
		return mav;
		
	}
	
	@RequestMapping("/purchaseOrder/toListPoGoods.do")
	public ModelAndView toListPoGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������toListPoGoods��ġ���������������"+po_id);
		
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);
		if(poGoodsList!=null){
			List<ErpPoGoodsVo> poGoodsVoList = VoUtil.getErpPoGoodsVoList(poGoodsList);
			int totalRecode = poGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("poGoodsList", poGoodsVoList);
		}
		
		
		request.setAttribute("po_id", po_id);
		mav.setViewName("purchaseOrder/listPoGoods");
		return mav;
	}
	
	@RequestMapping("/purchaseOrder/toLookPo.do")
	public ModelAndView toLookPo(HttpServletRequest request){
		
		
		ModelAndView mav = new ModelAndView();
		
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������toLookPo��ġ���������������"+po_id);
		
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderService.getById(po_id);
		if(erpPurchaseOrder!=null){
			ErpPurchaseOrderVo erpPurchaseOrderVo = VoUtil.getPurchaseOrderVo(erpPurchaseOrder);
			request.setAttribute("erpPurchaseOrder", erpPurchaseOrderVo);
		}
		
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);
		if(poGoodsList!=null){
			List<ErpPoGoodsVo> poGoodsVoList = VoUtil.getErpPoGoodsVoList(poGoodsList);
			int totalRecode = poGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("poGoodsList", poGoodsVoList);
		}
		
		mav.setViewName("purchaseOrder/lookPo");
		return mav;
		
	}
	
	
	@RequestMapping("/purchaseOrder/toLookPoGoods.do")
	public ModelAndView toLookPoGoods(HttpServletRequest request){
		
		
		ModelAndView mav = new ModelAndView();
		
		String po_id = request.getParameter("po_id");
		String id = request.getParameter("id");
		
		System.out.println("��������������������toLookPoGoods��ġ���������������"+po_id);
		
		ErpPoGoods erpPoGoods  = erpPoGoodsService.getById(id);
		
		if(erpPoGoods!=null){
			ErpPoGoodsVo erpPoGoodsVo = VoUtil.getErpPoGoodsVo(erpPoGoods);
			request.setAttribute("erpPoGoodsVo", erpPoGoodsVo);
		}
		request.setAttribute("po_id", po_id);
		
		
		mav.setViewName("purchaseOrder/lookPoGoods");
		return mav;
		
	} 
	
	
	@RequestMapping("/purchaseOrder/toUpdatePo.do")
	public ModelAndView toUpdatePo(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������toUpdatePo��ġ���������������"+po_id);
		
		request.setAttribute("po_id", po_id);
		ErpPurchaseOrder erpPurchaseOrder = erpPurchaseOrderService.getById(po_id);
		request.setAttribute("erpPurchaseOrder", erpPurchaseOrder);
		
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);	//��Ӧ���б�
		List<ErpDept> deptList = erpDeptService.getAll(con);				//�����б�
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);//�����б�
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);//�ֿ��б�
		List<ErpUser> userList = erpUserService.getAll(con);//Ա���б�
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);//�ɹ�������Ʒ�б�
		
		if(poGoodsList!=null){
			List<ErpPoGoodsVo> poGoodsVoList = VoUtil.getErpPoGoodsVoList(poGoodsList);
			int totalRecode = poGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("poGoodsList", poGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("warehouseList", warehouseList);
		request.setAttribute("userList", userList);
		
		mav.setViewName("purchaseOrder/updatePo");
		return mav;
		
	}
	
	@RequestMapping("/purchaseOrder/updatePo.do")
	public String updatePo(HttpServletRequest request,HttpServletResponse response){
		System.out.println("-�����޸�-");
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������updatePo��ġ���������������"+po_id);
		
		String create_time = request.getParameter("create_time");
		String supplier_id = request.getParameter("supplier_id");
		String purchaser_id = request.getParameter("purchaser_id");
		String dept_id = request.getParameter("dept_id");
		String warehouse_id = request.getParameter("warehouse_id");
		
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		String invalid_id="";
		if(invoices_state.equals("B")){
			ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
			invalid_id = ea.getUser_id();
		}
		int po_amount =	erpPoGoodsService.getAmount(po_id);
		
		ErpPurchaseOrder erpPurchaseOrder = new ErpPurchaseOrder();
		erpPurchaseOrder.setPo_id(po_id);
		erpPurchaseOrder.setCreate_time(create_time);
		erpPurchaseOrder.setSupplier_id(supplier_id);
		erpPurchaseOrder.setPurchaser_id(purchaser_id);
		erpPurchaseOrder.setDept_id(dept_id);
		erpPurchaseOrder.setWarehouse_id(warehouse_id);
		erpPurchaseOrder.setPo_amount(po_amount);
		//erpPurchaseOrder.setOriginator_id(originatior_id);
		erpPurchaseOrder.setOrganization_id(organization_id);
		erpPurchaseOrder.setInvoices_state(invoices_state);
		erpPurchaseOrder.setInvalid_id(invalid_id);
		
		erpPurchaseOrderService.update(erpPurchaseOrder);
		
		return "forward:/purchaseOrder/toListPo.do";
	}
	
	
	@RequestMapping("/purchaseOrder/toUpdatePoGoods.do")
	public ModelAndView toUpdatePoGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String po_id = request.getParameter("po_id"); 
		String id = request.getParameter("id");
		
		System.out.println("��������������������toUpdatePoGoods��ġ���������������"+po_id);
		String con = "%%";
		List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		
		ErpPoGoods erpPoGoods  = erpPoGoodsService.getById(id);
		
		if(erpPoGoods!=null){
			ErpPoGoodsVo erpPoGoodsVo = VoUtil.getErpPoGoodsVo(erpPoGoods);
			request.setAttribute("erpPoGoodsVo", erpPoGoodsVo);
		}
		
		request.setAttribute("po_id", po_id);
		request.setAttribute("goodsList", goodsList);
		mav.setViewName("purchaseOrder/updatePoGoods");
		return mav;
	}
	
	/**
	 * �޸Ĳɹ���������Ʒ
	 * @param request
	 * @return
	 */
	@RequestMapping("/purchaseOrder/updatePoGoods.do")
	public String updatePoGoods(HttpServletRequest request){
		
		String id = request.getParameter("id");
		String goods_id = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String po_id = request.getParameter("po_id");
		
		System.out.println("��������������������updatePoGoods��ġ���������������"+po_id);
	
		int goods_prices = 0;
		ErpGoods erpGoods = erpGoodsService.getById(goods_id);
		if(erpGoods!=null){
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);
		
		System.out.println("++++++++++++"+po_id);
		
		ErpPoGoods erpPoGoods = new ErpPoGoods();
		erpPoGoods.setGoods_id(goods_id);
		erpPoGoods.setId(id);
		erpPoGoods.setGoods_num(goods_num);
		erpPoGoods.setGoods_prices(goods_prices);
		erpPoGoods.setRemark(remark);
		erpPoGoods.setPo_id(po_id);
		
		erpPoGoodsService.update(erpPoGoods);
		
		request.setAttribute("po_id", po_id);
		return "forward:/purchaseOrder/toListPoGoods.do";
	}
	
	/**
	 * ɾ���ɹ�����
	 * @param request
	 * @return
	 */
	@RequestMapping("/purchaseOrder/deletePo.do")
	public String deletePo(HttpServletRequest request){
		
		String po_id = request.getParameter("po_id");
		List<ErpPoGoods> poGoodsList = erpPoGoodsService.getByPoId(po_id);

		if(poGoodsList!=null){
			for(int i=0;i<poGoodsList.size();i++){
				erpPoGoodsService.delete(poGoodsList.get(i).getGoods_id());
			}
		}
		erpPurchaseOrderService.delete(po_id);
		
		
		return "forward:/purchaseOrder/toListPo.do";
	}
	
	/**
	 * ɾ���ɹ���������Ʒ
	 * @param request
	 * @return
	 */
	@RequestMapping("/purchaseOrder/deletePoGoods.do")
	public String deletePoGoods(HttpServletRequest request){
		String id = request.getParameter("id");

		if(id.indexOf(",")>-1){
			String[] idArr = id.split(",");
			for(int i=0;i<idArr.length;i++){
				erpPoGoodsService.delete(idArr[i]);
			}
		}else{
			erpPoGoodsService.delete(id);
		}
		
		return "forward:/purchaseOrder/toListPoGoods.do";
	}
	
}
