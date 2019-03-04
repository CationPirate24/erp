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
import com.java.bean.ErpCustomer;
import com.java.bean.ErpDept;
import com.java.bean.ErpGoods;
import com.java.bean.ErpOrganization;
import com.java.bean.ErpPoGoods;
import com.java.bean.ErpPurchase;
import com.java.bean.ErpPurchaseGoods;
import com.java.bean.ErpPurchaseOrder;
import com.java.bean.ErpUser;
import com.java.bean.ErpWarehouse;
import com.java.service.ErpCashStatementService;
import com.java.service.ErpCustomerService;
import com.java.service.ErpDeptService;
import com.java.service.ErpGoodsService;
import com.java.service.ErpOrganizationService;
import com.java.service.ErpPoGoodsService;
import com.java.service.ErpPurchaseGoodsService;
import com.java.service.ErpPurchaseOrderService;
import com.java.service.ErpPurchaseService;
import com.java.service.ErpUserService;
import com.java.service.ErpWarehouseService;
import com.java.util.IdUtil;
import com.java.util.VoUtil;
import com.java.vo.ErpPurchaseGoodsVo;
import com.java.vo.ErpPurchaseVo;

@Controller
public class PurchaseController {
	
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
	private ErpPurchaseService erpPurchaseService;
	@Autowired
	private ErpPurchaseGoodsService erpPurchaseGoodsService;
	@Autowired
	private ErpPurchaseOrderService erpPurchaseOrderService;
	@Autowired
	private ErpPoGoodsService erpPoGoodsService;
	@Autowired
	private ErpCashStatementService erpCashStatementService;
	
	
	
	
	@RequestMapping("/purchase/toAddPurchase.do")
	public ModelAndView toAddPurchase(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("purchase/addPurchase");
		
		String purchase_id = request.getParameter("purchase_id");
		if(purchase_id==null||purchase_id==""||purchase_id.equals("null")){
			String poId = IdUtil.getInvoicesId();
			purchase_id = poId;
			request.setAttribute("purchase_id", purchase_id);
			
		}
		
		request.setAttribute("purchase_id", purchase_id);
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);
		List<ErpDept> deptList = erpDeptService.getAll(con);
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);
		List<ErpUser> userList = erpUserService.getAll(con);
		
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
		
		if(purchaseGoodsList!=null){
			List<ErpPurchaseGoodsVo> purchaseGoodsVoList = VoUtil.getPurchaseGoodsVo(purchaseGoodsList);
			request.setAttribute("purchaseGoodsList", purchaseGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("warehouseList", warehouseList);
		request.setAttribute("userList", userList);
		
		return mav;
	}
	
	@RequestMapping("/purchase/addPurchase.do")
	public String addPurchase(HttpServletRequest request,HttpServletResponse response){
		//���ɲɹ����൱�ڻ�����⣬����������ӣ������û�еĵ���ƷҪ��Ӽ�¼���ı��棬���Ը�����Ʒid�Ͳֿ�idȷ����ӵ�����һ����Ʒ
			String pathUri = "";
		
			String purchase_id = request.getParameter("purchase_id");
			String create_time = request.getParameter("create_time");
			String supplier_id = request.getParameter("supplier_id");
			String purchaser_id = request.getParameter("purchaser_id");
			String dept_id = request.getParameter("dept_id");
			String warehouse_id = request.getParameter("warehouse_id");
			
			String organization_id = request.getParameter("organization_id");
			String invoices_state = request.getParameter("invoices_state");
			
			ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
			String originator_id = ea.getUser_id();	//�Ƶ��˵�id���ǵ�ǰ��¼��Ա��Ա��id
			String invalid_id="";//���ʱ�ĵ���״̬Ĭ��Ϊ������ֻ����������֮�󣬲ſ����޸�״̬���޸�״̬֮��Ż���������
			int po_amount =	erpPurchaseGoodsService.getAmount(purchase_id);
			
			ErpPurchase erpPurchase = new ErpPurchase();
			erpPurchase.setPurchase_id(purchase_id);
			erpPurchase.setCreate_time(create_time);
			erpPurchase.setSupplier_id(supplier_id);
			erpPurchase.setPurchaser_id(purchaser_id);
			erpPurchase.setDept_id(dept_id);
			erpPurchase.setWarehouse_id(warehouse_id);
			erpPurchase.setPurchase_amount(po_amount);
			erpPurchase.setOriginator_id(originator_id);
			erpPurchase.setOrganization_id(organization_id);
			erpPurchase.setInvoices_state(invoices_state);
			erpPurchase.setInvalid_id(invalid_id);
			
			if(erpPurchaseService.add(erpPurchase)){
				//������ӳɹ��󣬻�ȡһ���������Ӧ����Ʒ�ļ���
				List<ErpPurchaseGoods> pgList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
				if(pgList!=null){
					//�����Ʒ���ϲ�Ϊ�գ���ѭ��֮�輯�ϣ����Ŀ�������е���Ʒ����
					for(ErpPurchaseGoods epg:pgList){
						//������Ʒid�Ͳֿ�idȷ����û�ж�Ӧ�ļ�¼
						List<ErpCashStatement> csl =  erpCashStatementService.getByWIAndGI(warehouse_id, epg.getGoods_id());
						//�����Ϊ�գ����Ƕ�Ӧ�Ĳֿ�ԭ���������Ʒ��ֻҪ���������Ϳ�����
						if(csl.size()>0){
							int num = csl.get(0).getGoods_num();//ԭ������еĶ�Ӧ��Ʒ������
							int goodsStock = num-epg.getGoods_num();//�����ԭ������Ʒ�������ϲɹ������Ʒ����
							String csId = csl.get(0).getStatement_id();
							ErpCashStatement ecs = new ErpCashStatement();
							ecs.setGoods_num(goodsStock);
							ecs.setStatement_id(csId);
							erpCashStatementService.update(ecs);
						//���Ϊ�գ���˵��ԭ����Ӧ�Ĳֿ���û�������Ʒ������Ҫ�����һ����¼
						}else{
							ErpCashStatement erpCashStatement = new ErpCashStatement();
							erpCashStatement.setStatement_id(IdUtil.getUuid());
							erpCashStatement.setGoods_id(epg.getGoods_id());
							erpCashStatement.setGoods_num(epg.getGoods_num());
							erpCashStatement.setWarehouse_id(warehouse_id);
							erpCashStatementService.add(erpCashStatement);
						}
					}
				}
			}
			pathUri = "forward:/purchase/toListPurchase.do";
		
		return pathUri;
	}
	
	@RequestMapping("/purchase/addPurchase2.do")
	public String addPurchase2(HttpServletRequest request){
		
		String po_id = request.getParameter("po_id");	//��ȡҪ���ɲɹ����Ĳɹ�������id
		
		ErpPurchaseOrder epo = erpPurchaseOrderService.getById(po_id);
		ErpPurchase erpPurchase = new ErpPurchase();
		String p_id = IdUtil.getInvoicesId();//�ɹ����ı��id
		
		erpPurchase.setPurchase_id(p_id);
		erpPurchase.setCreate_time(epo.getCreate_time());
		erpPurchase.setDept_id(epo.getDept_id());
		//��������һ��ʼ��û�У�ֻ�������Ϻ���У����Ҳɹ�����������״̬�ǲ������ɲɹ�����
		erpPurchase.setInvalid_id("");	
		erpPurchase.setOrganization_id(epo.getOrganization_id());
		
		erpPurchase.setPurchase_amount(epo.getPo_amount());
		erpPurchase.setPurchaser_id(epo.getPurchaser_id());
		erpPurchase.setSupplier_id(epo.getSupplier_id());
		erpPurchase.setWarehouse_id(epo.getWarehouse_id());
		erpPurchase.setInvoices_state(epo.getInvoices_state());
		
		ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
		erpPurchase.setOriginator_id(ea.getUser_id());	//�Ƶ���Ӧ���ǵ�ǰ��¼����������Ա��id
		
		if(erpPurchaseService.add(erpPurchase)){
			ErpPurchaseOrder erppo = new ErpPurchaseOrder();
			erppo.setPo_id(po_id);
			erppo.setInvoices_state("C");//�޸Ĳɹ�������״̬
			erpPurchaseOrderService.update(erppo);
			List<ErpPoGoods> epgl = erpPoGoodsService.getByPoId(po_id);//��ȡ�ɹ����������Ʒ����ӵ��ɹ�������Ʒ��
			for(int i=0;i<epgl.size();i++){
				ErpPurchaseGoods erpPurchaseGoods = new ErpPurchaseGoods();
				erpPurchaseGoods.setId(IdUtil.getUuid());
				erpPurchaseGoods.setGoods_id(epgl.get(i).getGoods_id());
				erpPurchaseGoods.setGoods_num(epgl.get(i).getGoods_num());
				erpPurchaseGoods.setGoods_prices(epgl.get(i).getGoods_prices());
				erpPurchaseGoods.setRemark(epgl.get(i).getRemark());
				erpPurchaseGoods.setPurchase_id(p_id);
				if(erpPurchaseGoodsService.add(erpPurchaseGoods)){//��ӳɹ����޸Ŀ�������е���Ʒ����
					//������Ʒid�Ͳֿ�idȷ����û�ж�Ӧ�ļ�¼
					List<ErpCashStatement> csl =  erpCashStatementService.getByWIAndGI(epo.getWarehouse_id(), erpPurchaseGoods.getGoods_id());
					
					//���û�о��´���һ����¼��������Ϊ���βɹ�������
					if(csl.size()==0){
						ErpCashStatement erpCashStatement = new ErpCashStatement();
						erpCashStatement.setStatement_id(IdUtil.getUuid());
						erpCashStatement.setGoods_id(erpPurchaseGoods.getGoods_id());
						erpCashStatement.setGoods_num(erpPurchaseGoods.getGoods_num());
						erpCashStatement.setWarehouse_id(epo.getWarehouse_id());
						erpCashStatementService.add(erpCashStatement);
						
					//����У��͸ı�鵽�ļ�¼����Ʒ����
					}else{
						int num = csl.get(0).getGoods_num();//ԭ������еĶ�Ӧ��Ʒ������
						int goodsStock = num+erpPurchaseGoods.getGoods_num();//�����ԭ������Ʒ�������ϲɹ������Ʒ����
						String csId = csl.get(0).getStatement_id();
						ErpCashStatement ecs = new ErpCashStatement();
						ecs.setGoods_num(goodsStock);
						ecs.setStatement_id(csId);
						erpCashStatementService.update(ecs);
					}
					
				}
			}
		}
		return "forward:/purchaseOrder/toListPo.do";
	}
	
	
	
	
	@RequestMapping("/purchase/toAddPurchaseGoods.do")
	public ModelAndView toAddPurchaseGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String purchase_id = request.getParameter("purchase_id"); 
		String con = "%%";
		List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
		
		if(purchaseGoodsList!=null){
			List<ErpPurchaseGoodsVo> purchaseGoodsVoList = VoUtil.getPurchaseGoodsVo(purchaseGoodsList);
			request.setAttribute("purchaseGoodsList", purchaseGoodsVoList);
		}
		
		request.setAttribute("purchase_id", purchase_id);
		request.setAttribute("goodsList", goodsList);
		mav.setViewName("purchase/addPurchaseGoods");
		return mav;
	}
	

	@RequestMapping("/purchase/addPurchaseGoods.do")
	public  String addPurchaseGoods(HttpServletRequest request){
      
		String id = IdUtil.getUuid();
		String goods_id = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String purchase_id = request.getParameter("purchase_id");
		
		int goods_prices = 0;
		ErpGoods erpGoods = erpGoodsService.getById(goods_id);
		if(erpGoods!=null){
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);
		
		System.out.println("++++++++++++"+purchase_id);
		
		
		ErpPurchaseGoods erpPurchaseGoods = new ErpPurchaseGoods();
		erpPurchaseGoods.setGoods_id(goods_id);
		erpPurchaseGoods.setId(id);
		erpPurchaseGoods.setGoods_num(goods_num);
		erpPurchaseGoods.setGoods_prices(goods_prices);
		erpPurchaseGoods.setRemark(remark);
		erpPurchaseGoods.setPurchase_id(purchase_id);
		erpPurchaseGoodsService.add(erpPurchaseGoods);
		
		request.setAttribute("purchase_id", purchase_id);
		return "forward:/purchase/toAddPurchaseGoods.do";
		
	}
	
	@RequestMapping("/purchase/toListPurchase.do")
	public ModelAndView toListPurchase(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		
		//ʹ�ò�ѯ������ȡ����Ҫ��ѯ���ֶ�
		String purchase_id = request.getParameter("purchase_id");
		String create_time = request.getParameter("create_time");
		String supplier_id = request.getParameter("supplier_id");
		String purchaser_id = request.getParameter("purchaser_id");
		String dept_id = request.getParameter("dept_id");
		String warehouse_id = request.getParameter("warehouse_id");
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		String originatior_id = request.getParameter("originatior_id");
		
		ErpPurchase erpPurchase = new ErpPurchase();
		erpPurchase.setPurchase_id(purchase_id);
		erpPurchase.setCreate_time(create_time);
		erpPurchase.setSupplier_id(supplier_id);
		erpPurchase.setPurchaser_id(purchaser_id);
		erpPurchase.setDept_id(dept_id);
		erpPurchase.setWarehouse_id(warehouse_id);
		erpPurchase.setOriginator_id(originatior_id);
		erpPurchase.setOrganization_id(organization_id);
		erpPurchase.setInvoices_state(invoices_state);
		
		String con = "%%";
		List<ErpPurchaseVo> erpPurchaseVoList = new ArrayList<ErpPurchaseVo>();
		if(erpPurchase==null){
			//�õ���δ�����ѯʱ�����вɹ��������б�
			List<ErpPurchase> erpPurchaseList = erpPurchaseService.getAll(con);
			if(erpPurchaseList!=null){//ת����vo����ҳ�����
				erpPurchaseVoList = VoUtil.getPurchaseVo(erpPurchaseList);
				int totalRecode = erpPurchaseVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
			}
			
		}else{
			//���ݵ����ѯʱ��ȡ�Ĳ�ѯ�����õ�����
			List<ErpPurchase> erpPurchaseList = erpPurchaseService.selectPurchase(erpPurchase);
			if(erpPurchaseList!=null){//ת����vo����ҳ�����
				erpPurchaseVoList = VoUtil.getPurchaseVo(erpPurchaseList);
				int totalRecode = erpPurchaseVoList.size();
				
				request.setAttribute("totalRecode", totalRecode);
				//request.setAttribute("erpPurchaseList", erpPurchaseVoList);
			}
		}
		request.setAttribute("erpPurchaseList", erpPurchaseVoList);
		
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
		
		request.setAttribute("purchase_id", purchase_id);
		mav.setViewName("purchase/listPurchase");
		return mav;
		
	} 
	
	@RequestMapping("/purchase/toListPurchaseGoods.do")
	public ModelAndView toListPurchaseGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String purchase_id = request.getParameter("purchase_id");
		String updateNum = (String)request.getAttribute("updateNum");
		System.out.println("��������������������toListPoGoods��ġ���������������"+purchase_id);
		System.out.println("��������������������toListPurchaseGoods���updateNum����������������"+updateNum);
		String con = request.getParameter("con");
		if(con==null||con.equals("null")||con==""){
			con = "%%";
		}else{
			con ="%"+con+"%";
		}
		
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
		if(purchaseGoodsList!=null){
			List<ErpPurchaseGoodsVo> purchaseGoodsVoList = VoUtil.getPurchaseGoodsVo(purchaseGoodsList);
			request.setAttribute("purchaseGoodsList", purchaseGoodsVoList);
			int totalRecode = purchaseGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
		}
		
		request.setAttribute("updateNum", updateNum);
		request.setAttribute("purchase_id", purchase_id);
		mav.setViewName("purchase/listPurchaseGoods");
		return mav;
	}
	
	@RequestMapping("/purchase/toLookPurchase.do")
	public ModelAndView toLookPurchase(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		String purchase_id = request.getParameter("purchase_id");
		
		ErpPurchase erpPurchase = erpPurchaseService.getById(purchase_id);
		if(erpPurchase!=null){
			ErpPurchaseVo erpPurchaseVo = VoUtil.getPurchaseVo(erpPurchase);
			request.setAttribute("erpPurchase", erpPurchaseVo);
		}
		
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
		if(purchaseGoodsList!=null){
			List<ErpPurchaseGoodsVo> purchaseGoodsVoList = VoUtil.getPurchaseGoodsVo(purchaseGoodsList);
			int totalRecode = purchaseGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("purchaseGoodsList", purchaseGoodsVoList);
		}
		
		mav.setViewName("purchase/lookPurchase");
		return mav;
		
	}
	
	
	@RequestMapping("/purchase/toLookPurchaseGoods.do")
	public ModelAndView toLookPurchaseGoods(HttpServletRequest request){
		
		
		ModelAndView mav = new ModelAndView();
		
		String purchase_id = request.getParameter("purchase_id");
		String id = request.getParameter("id");
		
		ErpPurchaseGoods erpPurchaseGoods  = erpPurchaseGoodsService.getById(id);
		
		if(erpPurchaseGoods!=null){
			ErpPurchaseGoodsVo erpPurchaseGoodsVo = VoUtil.getPurchaseGoodsVo(erpPurchaseGoods);
			request.setAttribute("erpPurchaseGoodsVo", erpPurchaseGoodsVo);
		}
		request.setAttribute("purchase_id", purchase_id);
		
		
		mav.setViewName("purchase/lookPurchaseGoods");
		return mav;
		
	} 
	
	
	@RequestMapping("/purchase/toUpdatePurchase.do")
	public ModelAndView toUpdatePurchase(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		
		
		String purchase_id = request.getParameter("purchase_id");
		
		request.setAttribute("purchase_id", purchase_id);
		ErpPurchase erpPurchase = erpPurchaseService.getById(purchase_id);
		request.setAttribute("erpPurchase", erpPurchase);
		
		String con = "%%";
		List<ErpCustomer> customerList = erpCustomerService.getAll(con);	//��Ӧ���б�
		List<ErpDept> deptList = erpDeptService.getAll(con);				//�����б�
		List<ErpOrganization> organizationList = erpOrganizationService.getAll(con);//�����б�
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);//�ֿ��б�
		List<ErpUser> userList = erpUserService.getAll(con);//Ա���б�
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);//�ɹ�������Ʒ�б�
		
		if(purchaseGoodsList!=null){
			List<ErpPurchaseGoodsVo> purchaseGoodsVoList = VoUtil.getPurchaseGoodsVo(purchaseGoodsList);
			int totalRecode = purchaseGoodsVoList.size();
			
			request.setAttribute("totalRecode", totalRecode);
			request.setAttribute("purchaseGoodsList", purchaseGoodsVoList);
		}
		request.setAttribute("customerList", customerList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("organizationList", organizationList);
		request.setAttribute("warehouseList", warehouseList);
		request.setAttribute("userList", userList);
		
		mav.setViewName("purchase/updatePurchase");
		return mav;
		
	}
	
	@RequestMapping("/purchase/updatePurchase.do")
	public String updatePurchase(HttpServletRequest request,HttpServletResponse response){
		System.out.println("-�����޸�-");
		String purchase_id = request.getParameter("purchase_id");
		
		String create_time = request.getParameter("create_time");
		String supplier_id = request.getParameter("supplier_id");
		String purchaser_id = request.getParameter("purchaser_id");
		String dept_id = request.getParameter("dept_id");
		String warehouse_id = request.getParameter("warehouse_id");
		
		String organization_id = request.getParameter("organization_id");
		String invoices_state = request.getParameter("invoices_state");
		//�����˵�id���ǵ�ǰ��¼��Ա��id
		String invalid_id="";
		if(invoices_state.equals("B")){
			ErpAccount ea = (ErpAccount)request.getSession().getAttribute("erpAccount");
			invalid_id = ea.getUser_id();
		}
		
		int purchase_amount =	erpPurchaseGoodsService.getAmount(purchase_id);
		
		ErpPurchase erpPurchase = new ErpPurchase();
		erpPurchase.setPurchase_id(purchase_id);
		erpPurchase.setCreate_time(create_time);
		erpPurchase.setSupplier_id(supplier_id);
		erpPurchase.setPurchaser_id(purchaser_id);
		erpPurchase.setDept_id(dept_id);
		erpPurchase.setWarehouse_id(warehouse_id);
		erpPurchase.setPurchase_amount(purchase_amount);
		//erpPurchase.setOriginator_id(originatior_id);
		erpPurchase.setOrganization_id(organization_id);
		erpPurchase.setInvoices_state(invoices_state);
		erpPurchase.setInvalid_id(invalid_id);
		
		erpPurchaseService.update(erpPurchase);
		List<ErpPurchaseGoods> erpPurchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
		for(ErpPurchaseGoods erpPurchaseGoods:erpPurchaseGoodsList){
			
			List<ErpCashStatement> csl2 = erpCashStatementService.getByWIAndGI(warehouse_id, erpPurchaseGoods.getGoods_id());
			if(csl2.size()>0){
				String statementId2 = csl2.get(0).getStatement_id();
				int sNum2 = csl2.get(0).getGoods_num();
				int stockNum2 = sNum2+erpPurchaseGoods.getGoods_num();
				
				ErpCashStatement ecs2 = new ErpCashStatement();
				ecs2.setGoods_num(stockNum2);
				ecs2.setStatement_id(statementId2);
				erpCashStatementService.update(ecs2);
			}else{
				ErpCashStatement erpCashStatement = new ErpCashStatement();
				erpCashStatement.setStatement_id(IdUtil.getUuid());
				erpCashStatement.setGoods_id(erpPurchaseGoods.getGoods_id());
				erpCashStatement.setGoods_num(erpPurchaseGoods.getGoods_num());
				erpCashStatement.setWarehouse_id(warehouse_id);
				erpCashStatementService.add(erpCashStatement);
			}
			
			
		}
		
		return "forward:/purchase/toListPurchase.do";
	}
	
	@RequestMapping("/purchase/toUpdatePurchaseGoods.do")
	public ModelAndView toUpdatePurchaseGoods(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		String purchase_id = request.getParameter("purchase_id"); 
		String id = request.getParameter("id");
		String updateNum = request.getParameter("updateNum");
		
		System.out.println("��������������������toUpdatePurchaseGoods���updateNum����������������"+updateNum);
		String con = "%%";
		List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		
		ErpPurchaseGoods erpPurchaseGoods  = erpPurchaseGoodsService.getById(id);
		
		if(erpPurchaseGoods!=null){
			ErpPurchaseGoodsVo erpPurchaseGoodsVo = VoUtil.getPurchaseGoodsVo(erpPurchaseGoods);
			request.setAttribute("erpPurchaseGoodsVo", erpPurchaseGoodsVo);
		}
		request.setAttribute("updateNum", updateNum);
		request.setAttribute("purchase_id", purchase_id);
		request.setAttribute("goodsList", goodsList);
		mav.setViewName("purchase/updatePurchaseGoods");
		return mav;
	}
	
	@RequestMapping("/purchase/updatePurchaseGoods.do")
	public String updatePurchaseGoods(HttpServletRequest request){
		
		int updateNum = 0;//�޸���Ʒ�Ĵ���
		
		String updateNumStr = request.getParameter("updateNum");
		if(updateNumStr==null||"".equals(updateNumStr)||"null".equals(updateNumStr)){
			updateNum = 1;
			
		}else{
			updateNum = Integer.valueOf(updateNumStr);
		}
		
		System.out.println("++++updatePurchaseGoods���updateNum+++++++++"+updateNum);
		
		String id = request.getParameter("id");
		String goods_id = request.getParameter("goods_id");
		String goods_numStr = request.getParameter("goods_num");
		String remark = request.getParameter("remark");
		String purchase_id = request.getParameter("purchase_id");
		
		int goods_prices = 0;
		ErpGoods erpGoods = erpGoodsService.getById(goods_id);
		if(erpGoods!=null){
			goods_prices = erpGoods.getGoods_prices();		//ͨ���õ�����Ʒid��ȡ��Ʒ�ĵ���
		}
		int goods_num = Integer.valueOf(goods_numStr);//ҳ�����޸ĵĲɹ�����
		//��ȡ�޸�ǰ����Ʒ
		
		if(updateNum==1){
			
			//ֻҪѡ�����޸���Ʒ��ѡ����ܸ��˼�����Ʒ����Ĭ���޸������е���Ʒ���������е�������Ʒ�Ŀ���Ϊ��û����������ɹ���֮ǰ��״̬
			List<ErpPurchaseGoods> purchaseGl = erpPurchaseGoodsService.getByPurchaseId(purchase_id);
			for(ErpPurchaseGoods epg:purchaseGl){
				System.out.println("++++++�����ϵ���Ʒ����+++++++++"+epg.getGoods_id()+epg.getGoods_num());
				int goods_num2 = epg.getGoods_num();//�޸�ǰ����Ʒ����
				String goods_id2 = epg.getGoods_id();//�޸�ǰ����Ʒid
				String warehouse_id = erpPurchaseService.getById(purchase_id).getWarehouse_id();//���ݲɹ������ȷ���˴βɹ�����Ʒ����Ĳֿ�
				//�ȼ�ȥ��ǰ��ӵ�������
				List<ErpCashStatement> csl = erpCashStatementService.getByWIAndGI(warehouse_id, goods_id2);//��ȡ�޸�ǰ�ٿ�������еļ�¼
				if(csl.size()>0){
					
					String statementId = csl.get(0).getStatement_id();
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++"+statementId);
					int sNum = csl.get(0).getGoods_num();//����е���Ʒ����
					System.out.println("+++++����е���Ʒ����++++"+sNum);
					int stockNum = sNum-goods_num2;//����е�������ȥ�޸�ǰ��Ʒ���������ص���û�ɹ�ǰ��״̬
					System.out.println("--------�޸�֮�����Ʒ����------------------"+stockNum);
					if(stockNum==0){
						erpCashStatementService.delete(statementId);
					}else{
						ErpCashStatement ecs = new ErpCashStatement();
						ecs.setGoods_num(stockNum);
						ecs.setStatement_id(statementId);
						erpCashStatementService.update(ecs);
					}
				}
			}
		}
		
		System.out.println("++++++++++++"+purchase_id);
		
		ErpPurchaseGoods erpPurchaseGoods = new ErpPurchaseGoods();
		erpPurchaseGoods.setGoods_id(goods_id);
		erpPurchaseGoods.setId(id);
		erpPurchaseGoods.setGoods_num(goods_num);
		erpPurchaseGoods.setGoods_prices(goods_prices);
		erpPurchaseGoods.setRemark(remark);
		erpPurchaseGoods.setPurchase_id(purchase_id);
		
		
		erpPurchaseGoodsService.update(erpPurchaseGoods);
		//����֮���ٸ��ݸ��µ���Ʒ��Ϣ���¿��
		updateNum++;
		String updateNumstr2 = updateNum+"";
		System.out.println("updatePurchaseGoods���������updateNum-----------=="+updateNum);
		request.setAttribute("updateNum", updateNumstr2);
		request.setAttribute("purchase_id", purchase_id);
		return "forward:/purchase/toListPurchaseGoods.do";
	}
	
	@RequestMapping("/purchase/deletePurchase.do")
	public String deletePurchase(HttpServletRequest request){
		
		String purchase_id = request.getParameter("purchase_id");
		List<ErpPurchaseGoods> purchaseGoodsList = erpPurchaseGoodsService.getByPurchaseId(purchase_id);

		if(purchaseGoodsList!=null){
			for(int i=0;i<purchaseGoodsList.size();i++){
				erpPurchaseGoodsService.delete(purchaseGoodsList.get(i).getGoods_id());
			}
		}
		erpPurchaseService.delete(purchase_id);
		
		
		return "forward:/purchase/toListPurchase.do";
	}
	
	@RequestMapping("/purchase/deletePurchaseGoods.do")
	public String deletePurchaseGoods(HttpServletRequest request){
		String id = request.getParameter("id");

		if(id.indexOf(",")>-1){
			String[] idArr = id.split(",");
			for(int i=0;i<idArr.length;i++){
				erpPurchaseGoodsService.delete(idArr[i]);
			}
		}else{
			erpPurchaseGoodsService.delete(id);
		}
		
		return "forward:/purchase/toListPurchaseGoods.do";
	}
	
	
}
