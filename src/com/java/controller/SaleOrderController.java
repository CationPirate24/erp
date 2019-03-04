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
import com.java.bean.ErpCashStatement;
import com.java.bean.ErpCode;
import com.java.bean.ErpCustomer;
import com.java.bean.ErpDept;
import com.java.bean.ErpGoods;
import com.java.bean.ErpOrganization;
import com.java.bean.ErpSaleOrder;
import com.java.bean.ErpSoGoods;
import com.java.bean.ErpUser;
import com.java.service.ErpCashStatementService;
import com.java.service.ErpCodeService;
import com.java.service.ErpCustomerService;
import com.java.service.ErpDeptService;
import com.java.service.ErpGoodsService;
import com.java.service.ErpOrganizationService;
import com.java.service.ErpSaleOrderService;
import com.java.service.ErpSoGoodsService;
import com.java.service.ErpUserService;
import com.java.util.IdUtil;
import com.java.util.VoUtil;
import com.java.vo.ErpCashStatementVo;
import com.java.vo.ErpSaleOrderVo;
import com.java.vo.ErpSoGoodsVo;

@Controller
public class SaleOrderController {

	
	@Autowired
	private ErpSaleOrderService erpSaleOrderService;
	@Autowired
	private ErpCustomerService erpCustomerService;
	@Autowired
	private ErpDeptService erpDeptService;
	@Autowired
	private ErpOrganizationService erpOrganizationService;
	@Autowired
	private ErpUserService erpUserService;
	@Autowired
	private ErpGoodsService erpGoodsService;
	@Autowired
	private ErpSoGoodsService erpSoGoodsService;
	@Autowired
	private ErpCodeService erpCodeService;
	@Autowired
	private ErpCashStatementService erpCashStatementService;
	
	
	@RequestMapping("/saleOrder/toAddSo.do")
	public ModelAndView toAddSo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("saleOrder/addSo");
		
		String so_id = request.getParameter("so_id");
		System.out.println("_____"+so_id);
		if(so_id==null||so_id==""||so_id.equals("null")){
			String soId = IdUtil.getInvoicesId();
			so_id = soId;
			request.setAttribute("so_id", so_id);
		}
		System.out.println("֮���so_id"+so_id);
		
		request.setAttribute("so_id", so_id);
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);
		List<ErpDept> deptList = erpDeptService.getAll(con);
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);
		List<ErpUser> userList = erpUserService.getAll(con);
		List<ErpCode> paymentList = erpCodeService.getByType("PAYMENT_METHOD");
		List<ErpCode> saleTypeList = erpCodeService.getByType("SALE_TYPE");
		List<ErpCode> deliveryWayList = erpCodeService.getByType("DELIVERY_WAY");
		
		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);
		if(soGoodsList!=null){
			List<ErpSoGoodsVo> soGoodsVoList = VoUtil.getSoGoodsVo(soGoodsList);
			int totalRecode = soGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("soGoodsList", soGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("userList", userList);
		request.setAttribute("paymentList", paymentList);
		request.setAttribute("saleTypeList", saleTypeList);
		request.setAttribute("deliveryWayList", deliveryWayList);
		return mav;
	}
	
	@RequestMapping("/saleOrder/addSo.do")
	public String addSo(HttpServletRequest request){
		
		String so_id = request.getParameter("so_id");				//���۶���id		
		
		String create_time = request.getParameter("create_time");				//����
		String sale_type = request.getParameter("sale_type");			//��������
		String dept_id = request.getParameter("dept_id");				//����id
		String salesman_id = request.getParameter("salesman_id");			//����Աid
		String customer_id = request.getParameter("customer_id");			//�ͻ�id
		String delivery_way = request.getParameter("delivery_way");		//���˷�ʽ
		String payment_method = request.getParameter("payment_method");		//֧����ʽ
		String organization_id = request.getParameter("organization_id");		//��������id
		String invoices_state = request.getParameter("invoices_state");		//����״̬
		int money = erpSoGoodsService.getAmount(so_id);		//��� ͨ�����۶�����Ʒ��ϸ���е���Ʒˬ�ͽ����л�ȡ
		ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
		String originator_id = ea.getUser_id();	//�Ƶ��˵�id���ǵ�ǰ��¼��Ա��Ա��id
		String invalid_id="";
		
		ErpSaleOrder erpSaleOrder = new ErpSaleOrder();
		
		erpSaleOrder.setSo_id(so_id);
		erpSaleOrder.setCreate_time(create_time);
		erpSaleOrder.setCustomer_id(customer_id);
		erpSaleOrder.setDelivery_way(delivery_way);
		erpSaleOrder.setDept_id(dept_id);
		erpSaleOrder.setInvalid_id(invalid_id);
		erpSaleOrder.setInvoices_state(invoices_state);
		erpSaleOrder.setMoney(money);
		erpSaleOrder.setOrganization_id(organization_id);
		erpSaleOrder.setOriginator_id(originator_id);
		erpSaleOrder.setPayment_method(payment_method);
		erpSaleOrder.setSale_type(sale_type);
		erpSaleOrder.setSalesman_id(salesman_id);
	
		if(!erpSaleOrderService.add(erpSaleOrder)){
			List<ErpSoGoods> pgList = erpSoGoodsService.getBySoId(so_id);
			if(pgList!=null){
				for(int i=0;i<pgList.size();i++){
					erpSoGoodsService.delete(pgList.get(i).getId());
				}
			}
		}
		
		return "forward:/saleOrder/toListSo.do";
	}
	
	@RequestMapping("/saleOrder/toAddSoGoods.do")
	public ModelAndView toAddSoGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String so_id = request.getParameter("so_id"); 
		System.out.println("����������#����������toAddPoGoods��ġ�����&����������"+so_id);
		System.out.println("++++++++++++"+so_id);
		String con = "%%";
		List<ErpCashStatement> goodsList = erpCashStatementService.getAll(con);//������е���Ʒ���б�
		if(goodsList!=null){
			List<ErpCashStatementVo> cashGoodsVo = VoUtil.getCashStatementVo(goodsList);
			request.setAttribute("goodsList", cashGoodsVo);
		}
		
		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);
		
		if(soGoodsList!=null){
			List<ErpSoGoodsVo> soGoodsVoList = VoUtil.getSoGoodsVo(soGoodsList);
			request.setAttribute("soGoodsList", soGoodsVoList);
		}
		
		request.setAttribute("so_id", so_id);
		mav.setViewName("saleOrder/addSoGoods");
		return mav;
	}
	
	@RequestMapping("/saleOrder/addSoGoods.do")
	public  String addPoGoods(HttpServletRequest request){
        
		String id = IdUtil.getUuid();
		String cashStatement = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������addSoGoods��ġ���������������"+so_id);
		
		String goods_id = "";
		String warehouse_id = "";
		int goods_prices = 0;
		ErpCashStatement erpCashGoods = erpCashStatementService.getById(cashStatement);
		if(erpCashGoods!=null){
			goods_id = erpCashGoods.getGoods_id();
			warehouse_id = erpCashGoods.getWarehouse_id();
			ErpGoods erpGoods = erpGoodsService.getById(goods_id);
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);
		
		System.out.println("++++++++++++"+so_id);
		
		ErpSoGoods erpSoGoods = new ErpSoGoods();
		erpSoGoods.setGoods_id(goods_id);
		erpSoGoods.setId(id);
		erpSoGoods.setGoods_num(goods_num);
		erpSoGoods.setGoods_prices(goods_prices);
		erpSoGoods.setRemark(remark);
		erpSoGoods.setSo_id(so_id);
		erpSoGoods.setWarehouse_id(warehouse_id);
		erpSoGoodsService.add(erpSoGoods);
		
		request.setAttribute("so_id", so_id);
		return "forward:/saleOrder/toAddSoGoods.do";
		
	}
	
	@RequestMapping("/saleOrder/toListSo.do")
	public ModelAndView toListSo(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		
		//ʹ�ò�ѯ������ȡ����Ҫ��ѯ���ֶ�
		String so_id = request.getParameter("so_id");
		String create_time = request.getParameter("create_time");
		String customer_id = request.getParameter("customer_id");
		String purchaser_id = request.getParameter("salesman_id");
		String dept_id = request.getParameter("dept_id");
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		String originatior_id = request.getParameter("originatior_id");
		String sale_type = request.getParameter("sale_type");	
		String delivery_way = request.getParameter("delivery_way");		//���˷�ʽ
		String payment_method = request.getParameter("payment_method");		//֧����ʽ
		
		ErpSaleOrder erpSaleOrder = new ErpSaleOrder();
		erpSaleOrder.setSo_id(so_id);
		erpSaleOrder.setCreate_time(create_time);
		erpSaleOrder.setCustomer_id(customer_id);
		erpSaleOrder.setSalesman_id(purchaser_id);
		erpSaleOrder.setDept_id(dept_id);
		erpSaleOrder.setOriginator_id(originatior_id);
		erpSaleOrder.setOrganization_id(organization_id);
		erpSaleOrder.setInvoices_state(invoices_state);
		erpSaleOrder.setPayment_method(payment_method);
		erpSaleOrder.setSale_type(sale_type);
		erpSaleOrder.setDelivery_way(delivery_way);
		
		String con = "%%";
		
		List<ErpSaleOrderVo> erpSaleOrderVoList = new ArrayList<ErpSaleOrderVo>();
		if(erpSaleOrder==null){
			//�õ���δ�����ѯʱ�����вɹ��������б�
			List<ErpSaleOrder> erpSaleOrderList = erpSaleOrderService.getAll(con);
			if(erpSaleOrderList!=null){//ת����vo����ҳ�����
				erpSaleOrderVoList = VoUtil.getSaleOrderVo(erpSaleOrderList);
				int totalRecode = erpSaleOrderVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
			}
			
		}else{
			//���ݵ����ѯʱ��ȡ�Ĳ�ѯ�����õ�����
			List<ErpSaleOrder> erpSaleOrderList = erpSaleOrderService.selectSaleOrder(erpSaleOrder);
			if(erpSaleOrderList!=null){//ת����vo����ҳ�����
				erpSaleOrderVoList = VoUtil.getSaleOrderVo(erpSaleOrderList);
				int totalRecode = erpSaleOrderVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
				request.setAttribute("erpSaleOrderList", erpSaleOrderVoList);
			}
		}
		request.setAttribute("erpSaleOrderList", erpSaleOrderVoList);
		
		//��ȡ���пɲ�ѯ���ֶε��б�
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);//��Ӧ���б�
		List<ErpDept> deptList = erpDeptService.getAll(con);//�����б�
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);//�����б�
		List<ErpUser> userList = erpUserService.getAll(con);//Ա���б�
		List<ErpCode> paymentList = erpCodeService.getByType("PAYMENT_METHOD");
		List<ErpCode> saleTypeList = erpCodeService.getByType("SALE_TYPE");
		List<ErpCode> deliveryWayList = erpCodeService.getByType("DELIVERY_WAY");
		
		//����Щ�б���ҳ�洫ֵ
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("userList", userList);
		request.setAttribute("paymentList", paymentList);
		request.setAttribute("saleTypeList", saleTypeList);
		request.setAttribute("deliveryWayList", deliveryWayList);
		
		
		request.setAttribute("so_id", so_id);
		mav.setViewName("saleOrder/listSo");
		return mav;
	}
	
	@RequestMapping("/saleOrder/toListSoGoods.do")
	public ModelAndView toListSoGoods(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������toListPoGoods��ġ���������������"+so_id);

		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);
		if(soGoodsList!=null){
			List<ErpSoGoodsVo> soGoodsVoList = VoUtil.getSoGoodsVo(soGoodsList);
			int totalRecode = soGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("soGoodsList", soGoodsVoList);
		}
		
		request.setAttribute("so_id", so_id);
		mav.setViewName("saleOrder/listSoGoods");
		return mav;
	}
	
	@RequestMapping("/saleOrder/toLookSo.do")
	public ModelAndView toLookSo(HttpServletRequest request){
		
		
		ModelAndView mav = new ModelAndView();
		
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������toLookPo��ġ���������������"+so_id);
		
		ErpSaleOrder erpSaleOrder = erpSaleOrderService.getById(so_id);
		if(erpSaleOrder!=null){
			ErpSaleOrderVo erpSaleOrderVo = VoUtil.getSaleOrderVo(erpSaleOrder);
			request.setAttribute("erpSaleOrder", erpSaleOrderVo);
		}
		
		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);
		if(soGoodsList!=null){
			List<ErpSoGoodsVo> soGoodsVoList = VoUtil.getSoGoodsVo(soGoodsList);
			int totalRecode = soGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("soGoodsList", soGoodsVoList);
		}
		
		mav.setViewName("saleOrder/lookSo");
		return mav;
		
	}
	
	@RequestMapping("/saleOrder/toLookSoGoods.do")
	public ModelAndView toLookSoGoods(HttpServletRequest request){
		
		
		ModelAndView mav = new ModelAndView();
		
		String so_id = request.getParameter("so_id");
		String id = request.getParameter("id");
		
		System.out.println("��������������������toLookPoGoods��ġ���������������"+so_id);
		
		ErpSoGoods erpSoGoods  = erpSoGoodsService.getById(id);
		
		if(erpSoGoods!=null){
			ErpSoGoodsVo erpSoGoodsVo = VoUtil.getSoGoodsVo(erpSoGoods);
			request.setAttribute("erpSoGoodsVo", erpSoGoodsVo);
		}
		request.setAttribute("so_id", so_id);
		
		
		mav.setViewName("saleOrder/lookSoGoods");
		return mav;
		
	} 
	
	@RequestMapping("/saleOrder/toUpdateSo.do")
	public ModelAndView toUpdateSo(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������toUpdatePo��ġ���������������"+so_id);
		
		request.setAttribute("so_id", so_id);
		ErpSaleOrder erpSaleOrder = erpSaleOrderService.getById(so_id);
		request.setAttribute("erpSaleOrder", erpSaleOrder);
		
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);	//��Ӧ���б�
		List<ErpDept> deptList = erpDeptService.getAll(con);				//�����б�
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);//�����б�
		List<ErpUser> userList = erpUserService.getAll(con);//Ա���б�
		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);//�ɹ�������Ʒ�б�
		List<ErpCode> paymentList = erpCodeService.getByType("PAYMENT_METHOD");
		List<ErpCode> saleTypeList = erpCodeService.getByType("SALE_TYPE");
		List<ErpCode> deliveryWayList = erpCodeService.getByType("DELIVERY_WAY");
		
		
		if(soGoodsList!=null){
			List<ErpSoGoodsVo> soGoodsVoList = VoUtil.getSoGoodsVo(soGoodsList);
			int totalRecode = soGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("soGoodsList", soGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("userList", userList);
		request.setAttribute("paymentList", paymentList);
		request.setAttribute("saleTypeList", saleTypeList);
		request.setAttribute("deliveryWayList", deliveryWayList);
		
		
		mav.setViewName("saleOrder/updateSo");
		return mav;
		
	}
	
	
	@RequestMapping("/saleOrder/updateSo.do")
	public String updateSo(HttpServletRequest request,HttpServletResponse response){
		System.out.println("-�����޸�-");
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������updatePo��ġ���������������"+so_id);
		
		String create_time = request.getParameter("create_time");
		String customer_id = request.getParameter("customer_id");
		String salesman_id = request.getParameter("salesman_id");
		String dept_id = request.getParameter("dept_id");
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		String originatior_id = request.getParameter("originatior_id");
		String sale_type = request.getParameter("sale_type");	
		String delivery_way = request.getParameter("delivery_way");		//���˷�ʽ
		String payment_method = request.getParameter("payment_method");	
		
		String invalid_id="";
		if(invoices_state.equals("B")){
			ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
			invalid_id = ea.getUser_id();
		}
		int money =	erpSoGoodsService.getAmount(so_id);
		
		ErpSaleOrder erpSaleOrder = new ErpSaleOrder();
		erpSaleOrder.setSo_id(so_id);
		erpSaleOrder.setCreate_time(create_time);
		erpSaleOrder.setCustomer_id(customer_id);
		erpSaleOrder.setSalesman_id(salesman_id);
		erpSaleOrder.setDept_id(dept_id);
		erpSaleOrder.setOriginator_id(originatior_id);
		erpSaleOrder.setOrganization_id(organization_id);
		erpSaleOrder.setInvoices_state(invoices_state);
		erpSaleOrder.setPayment_method(payment_method);
		erpSaleOrder.setSale_type(sale_type);
		erpSaleOrder.setDelivery_way(delivery_way);
		erpSaleOrder.setInvalid_id(invalid_id);
		erpSaleOrder.setMoney(money);
		
		erpSaleOrderService.update(erpSaleOrder);
		
		return "forward:/saleOrder/toListSo.do";
	}
	
	@RequestMapping("/saleOrder/toUpdateSoGoods.do")
	public ModelAndView toUpdateSoGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String so_id = request.getParameter("so_id"); 
		String id = request.getParameter("id");
		
		System.out.println("��������������������toUpdateSoGoods��ġ���������������"+so_id);
		String con = "%%";
		List<ErpCashStatement> goodsList = erpCashStatementService.getAll(con);//������е���Ʒ���б�
		if(goodsList!=null){
			List<ErpCashStatementVo> cashGoodsVo = VoUtil.getCashStatementVo(goodsList);
			request.setAttribute("goodsList", cashGoodsVo);
		}
		
		ErpSoGoods erpSoGoods  = erpSoGoodsService.getById(id);
		
		if(erpSoGoods!=null){
			ErpSoGoodsVo erpSoGoodsVo = VoUtil.getSoGoodsVo(erpSoGoods);
			request.setAttribute("erpSoGoodsVo", erpSoGoodsVo);
		}
		
		request.setAttribute("so_id",so_id);
		mav.setViewName("saleOrder/updateSoGoods");
		return mav;
	}
	
	@RequestMapping("/saleOrder/updateSoGoods.do")
	public String updateSoGoods(HttpServletRequest request){
		
		String id = request.getParameter("id");
		String cashStatement = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String so_id = request.getParameter("so_id");
		
		System.out.println("��������������������updatePoGoods��ġ���������������"+so_id);
	
		String goods_id = "";
		String warehouse_id = "";
		int goods_prices = 0;
		ErpCashStatement erpCashGoods = erpCashStatementService.getById(cashStatement);
		if(erpCashGoods!=null){
			goods_id = erpCashGoods.getGoods_id();
			warehouse_id = erpCashGoods.getWarehouse_id();
			ErpGoods erpGoods = erpGoodsService.getById(goods_id);
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);
		
		System.out.println("++++++++++++"+so_id);
		
		ErpSoGoods erpSoGoods = new ErpSoGoods();
		erpSoGoods.setGoods_id(goods_id);
		erpSoGoods.setId(id);
		erpSoGoods.setGoods_num(goods_num);
		erpSoGoods.setGoods_prices(goods_prices);
		erpSoGoods.setRemark(remark);
		erpSoGoods.setSo_id(so_id);
		erpSoGoods.setWarehouse_id(warehouse_id);
		erpSoGoodsService.update(erpSoGoods);
		
		request.setAttribute("so_id", so_id);
		return "forward:/saleOrder/toListSoGoods.do";
	}
	
	@RequestMapping("/saleOrder/deleteSo.do")
	public String deleteSo(HttpServletRequest request){
		
		String so_id = request.getParameter("so_id");
		List<ErpSoGoods> soGoodsList = erpSoGoodsService.getBySoId(so_id);

		if(soGoodsList!=null){
			for(int i=0;i<soGoodsList.size();i++){
				erpSoGoodsService.delete(soGoodsList.get(i).getGoods_id());
			}
		}
		erpSaleOrderService.delete(so_id);
		
		
		return "forward:/purchaseOrder/toListPo.do";
	}
	
	
	@RequestMapping("/saleOrder/deleteSoGoods.do")
	public String deleteSoGoods(HttpServletRequest request){
		String id = request.getParameter("id");

		if(id.indexOf(",")>-1){
			String[] idArr = id.split(",");
			for(int i=0;i<idArr.length;i++){
				erpSoGoodsService.delete(idArr[i]);
			}
		}else{
			erpSoGoodsService.delete(id);
		}
		
		return "forward:/saleOrder/toListSoGoods.do";
	}
	
}
