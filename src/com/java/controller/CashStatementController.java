package com.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.ErpAccount;
import com.java.bean.ErpBlitem;
import com.java.bean.ErpCashStatement;
import com.java.bean.ErpCode;
import com.java.bean.ErpGoods;
import com.java.bean.ErpRequisition;
import com.java.bean.ErpWarehouse;
import com.java.service.ErpBlitemService;
import com.java.service.ErpCashStatementService;
import com.java.service.ErpCodeService;
import com.java.service.ErpGoodsService;
import com.java.service.ErpRequisitionService;
import com.java.service.ErpWarehouseService;
import com.java.util.IdUtil;
import com.java.util.VoUtil;
import com.java.vo.ErpBlitemVo;
import com.java.vo.ErpCashStatementVo;
import com.java.vo.ErpRequisitionVo;

@Controller
public class CashStatementController {
	
	@Autowired
	private ErpWarehouseService erpWarehouseService;//�����ֿ����
	@Autowired
	private ErpCashStatementService erpCashStatementService;//�ֿ�����
	@Autowired
	private ErpBlitemService erpBlitemService;//�����̵㵥����
	@Autowired
	private ErpGoodsService erpGoodsService;//������Ʒ�Ķ���
	@Autowired
	private ErpCodeService erpCodeService;//��ȡ������
	@Autowired
	private ErpRequisitionService erpRequisitionService;
	//��ѯ�ֿ�������
	@RequestMapping("/cashStatement/toBalance.do")
	public ModelAndView toBlance(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cashStatement/blance");
		
		String con = "%%";
		//��ȡ�ֿ�������Ϣ
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);
		//��ȡ��Ʒ�������Ϣ
		List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		
		request.setAttribute("EGlist", goodsList);
		request.setAttribute("EWlist", warehouseList);
		
		//��ȡ�ֿ��id
		String warehouse_id = request.getParameter("warehouse_id");
		System.out.println("jjjjjjjjjjjjjjjj============"+warehouse_id);
		request.setAttribute("id",warehouse_id );
		//��ȡ��Ʒ��id
		String goods_id=request.getParameter("goods_id");
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhh================"+goods_id);
		request.setAttribute("goods_id", goods_id);
		
		List<ErpCashStatementVo> list = new ArrayList<ErpCashStatementVo>();
		

		if(warehouse_id==null || "null".equals(warehouse_id)||"".equals(warehouse_id)){
			warehouse_id = "";
			if(goods_id==null || "null".equals(goods_id)){
				goods_id = "%%";
				
			}else{
				goods_id = "%"+goods_id+"%";
				
			}
			//������Ʒ��idʹ��ģ����ѯ�������е���Ʒ��ѯ����
			List<ErpCashStatement>	erpCashStatementList = erpCashStatementService.getAll(goods_id);
			list = VoUtil.getCashStatementVo(erpCashStatementList);
		}else{
			
			if(goods_id==null || "null".equals(goods_id)||"".equals(goods_id)){
				goods_id = "";
				List<ErpCashStatement>	erpCashStatementList = erpCashStatementService.getByWarehouseId(warehouse_id);
				list = VoUtil.getCashStatementVo(erpCashStatementList);
			}else{
				List<ErpCashStatement>	erpCashStatementList = erpCashStatementService.getByWIAndGI(warehouse_id,goods_id);
				list = VoUtil.getCashStatementVo(erpCashStatementList);
			}
		}
		
		request.setAttribute("cashStatementList", list);
		return mav;
	}
	//�̵�֮ǰ�Ƚ��ֿ������е�����д���̵����
	@RequestMapping("/cashStatement/toBlitem.do")
	public ModelAndView toBlitem(HttpServletRequest request){
		//�Ȼ�ȡ�ֿ����������
		String  con="%%";
		List<ErpCashStatement> ECSlist =  erpCashStatementService.getAll(con);
		//����������
		
			for(ErpCashStatement ecs:ECSlist){
				System.out.println("11111111111111111111111111111111111111111");
				ErpBlitem eb = new ErpBlitem();//����һ���̵㵥����
				
				//���ݲֿ�id����Ʒid�ֻ�ȡ�̵㵥�е���Ʒ����
				List<ErpBlitem> list = erpBlitemService.getByWIAndGI(ecs.getWarehouse_id(),ecs.getGoods_id());
				if(list.size()==0){//�ж��̵㵥���Ƿ��и���Ʒ
					eb.setWarehouse_id(ecs.getWarehouse_id());
					eb.setGoods_id(ecs.getGoods_id());
					eb.setNum(ecs.getGoods_num());
					eb.setBlitem_id(IdUtil.getUuid());
					ErpAccount erpAccount = (ErpAccount)request.getSession().getAttribute("erpAccount");
					eb.setHandler_id(erpAccount.getUser_id());
					
					//��������ӵ��̵㵥��
					erpBlitemService.add(eb);
					
				}else{//����̵㵥���и�����¼��ֻ�����
					eb.setWarehouse_id(ecs.getWarehouse_id());
					eb.setGoods_id(ecs.getGoods_id());
					eb.setNum(ecs.getGoods_num());
					eb.setCheck_num(1);
					//��ȡblitem_id
					String blitem_id = list.get(0).getBlitem_id();
					eb.setBlitem_id(blitem_id);
					//���������
					erpBlitemService.update(eb);
					
					
					
				}
				
			}
			
		
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cashStatement/blitem");

		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);
		
		request.setAttribute("EWlist", warehouseList);
		String warehouse_id = request.getParameter("warehouse_id");//����Ϊ�ϴ�����value��ֵ�����Կ��Ը���ͨ��name��ȡҳ���ϵ�ֵ
		
		request.setAttribute("warehouse_id", warehouse_id);
		List<ErpBlitemVo> erpBlitemVo = null;
		if(warehouse_id==null || "null".equals(warehouse_id) || "".equals(warehouse_id)){
			warehouse_id = "%%";
			List<ErpBlitem> list = erpBlitemService.getAll(warehouse_id);
			erpBlitemVo = VoUtil.getBlitemVo(list);
		}else{
			List<ErpBlitem> list = erpBlitemService.getByWarehouseId(warehouse_id);
			erpBlitemVo = VoUtil.getBlitemVo(list);
		}
		int totalRecode = erpBlitemVo.size();//��һ���ܵļ�¼�������ڷ�ҳ
		
		request.setAttribute("totalRecode", totalRecode);
		request.setAttribute("list", erpBlitemVo);
		
		return mav;
		
		
		
		
		
	}
	@RequestMapping("/cashStatement/toUpdate.do")
	public ModelAndView toUpdate(HttpServletRequest request){
//		System.out.println("jjjjjjjjjjjj");
		ModelAndView mav =  new ModelAndView();
		String id = request.getParameter("id");
		System.out.println("------------------"+id);
		 ErpBlitem	eb =  erpBlitemService.getById(id);
		 ErpBlitemVo ebv = VoUtil.getBlitemVo(eb);
			
		request.setAttribute("eb", ebv);
		mav.setViewName("cashStatement/update");
		return mav;
		
	}
	//���²���
	@RequestMapping("/cashStatement/update.do")
	public String update(HttpServletRequest request){
		
		
		//��string ת����int��
		int check_num = Integer.parseInt(request.getParameter("check_num"));//�̵������
		System.out.println("-----------------------------------");
		System.out.println("�̵�����zzzzzzzzzzzzz"+check_num);
		System.out.println("-----------------------------------");
		String blitem_id = request.getParameter("blitem_id");
		String warehouse_id = request.getParameter("warehouse_id");
		String goods_id = request.getParameter("goods_id");
		int num = Integer.parseInt(request.getParameter("num"));//�̵�ǰ������
		System.out.println("-----------------------------------");
		System.out.println("�̵�ǰ������hhhhhhhhhhhhhhhhhhhhhh"+num);
		System.out.println("-----------------------------------");
		int profit_and_loss =Integer.parseInt(request.getParameter("profit_and_loss"));
		String handler_id = request.getParameter("handler_id");
		
		ErpBlitem eb = new ErpBlitem();
		eb.setBlitem_id(blitem_id);
		eb.setCheck_num(check_num);
		eb.setGoods_id(goods_id);
		eb.setHandler_id(handler_id);
		eb.setNum(num);
		eb.setProfit_and_loss(profit_and_loss);
		eb.setWarehouse_id(warehouse_id);
		
		erpBlitemService.update(eb);//�����̵���е�����
		//���²ֿ������е�����
		ErpCashStatement ecs = new ErpCashStatement();
		ecs.setGoods_id(goods_id);
		ecs.setWarehouse_id(warehouse_id);
		ecs.setGoods_num(check_num);
		erpCashStatementService.updateNum(ecs);
		
		return "forward:/cashStatement/toBlitem.do";
		
	}
	
	//��ת��������
	@RequestMapping("/cashStatement/toRequisition.do")
	public ModelAndView toRequisition(HttpServletRequest request){
		
		//��Ҫ�Ƚ�
		String con = "%%";
		//��ȡ�ֿ�������Ϣ
		List<ErpWarehouse> warehouseList = erpWarehouseService.getAll(con);
		//��ȡ��Ʒ�������Ϣ��
		//List<ErpGoods> goodsList = erpGoodsService.getAll(con);
		List<ErpCashStatement> goodsList = erpCashStatementService.getAll(con);
		List<ErpCashStatementVo> goodListVo = VoUtil.getCashStatementVo(goodsList);
		//��ȡ����״̬
		List<ErpCode> codeList = erpCodeService.getByType("RE_STATE");
		
		request.setAttribute("EGlist", goodListVo);
		request.setAttribute("EWlist", warehouseList);
		request.setAttribute("codeList", codeList);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cashStatement/requisition");
		return mav;
		
	}
	
	//�������ύ����ת���б�ҳ
	@RequestMapping("/cashStatement/list.do")
	public ModelAndView list(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView();
		//�ȴ�ҳ���ϻ�ȡ����
		String out_warehouse_id = request.getParameter("Out_warehouse_id");//�����ֿ��id
		String in_warehouse_id = request.getParameter("in_warehouse_id");//����ֿ��id
		String goods_id = request.getParameter("goods_id");//��Ʒ��id
		String requision_state = request.getParameter("re_state");//����״̬
		String num1 = request.getParameter("num");//������
		
		int num = Integer.valueOf(num1);//������
		String out_time = request.getParameter("time");
		
		String describe = request.getParameter("describe");
		
		//����ErpRequisition����
		ErpRequisition er = new ErpRequisition();
		er.setDescribe(describe);
		er.setGoods_id(goods_id);
		er.setIn_warehouse_id(in_warehouse_id);
		er.setNum(num);
		er.setOut_time(out_time);
		er.setOut_warehouse_id(out_warehouse_id);
		er.setRequision_state(requision_state);
		er.setRequisition_id(IdUtil.getUuid());
		//��������ӵ�ErpRequisition��ӵ����ݿ���
		erpRequisitionService.add(er);
		//�����ֿ����Ķ���
		ErpCashStatement erpCashStatement = new ErpCashStatement();
		if(requision_state.equals("A")){
			//�ȼ���������ֿ�ĸ���Ʒ������
			//�����ֿ����Ϣ
			List<ErpCashStatement> ecsList_out = erpCashStatementService.getByWIAndGI(out_warehouse_id, goods_id);
			if(ecsList_out.size()>0){//���Ҫ���и���Ʒ��
				int out_goods_num = ecsList_out.get(0).getGoods_num();
				if(out_goods_num>num){
					/*//��������ӵ�ErpRequisition��ӵ����ݿ���
					erpRequisitionService.add(er);*/
					ErpRequisitionVo erv = VoUtil.getRequisitionVo(er);
					request.setAttribute("er", erv);//�����󴫹�ȥ
					
					//�ı�ֿ�������������ֿ�������٣�������ֿ�������ӣ����жϵ���ֿ��Ƿ��и���Ʒ�����ֻ��������������û�����������ӣ�
					 out_goods_num = out_goods_num-num;//�����ֿ�ʣ����
					//���µ����ֿ����Ʒ������
					
					erpCashStatement.setGoods_id(goods_id);
					erpCashStatement.setWarehouse_id(out_warehouse_id);
					erpCashStatement.setGoods_num(out_goods_num);
					erpCashStatementService.updateNum(erpCashStatement);
					//���µ���ֿ������
					//����ֿ����Ϣ
					List<ErpCashStatement> ecsList_in = erpCashStatementService.getByWIAndGI(in_warehouse_id, goods_id);
					
					if(ecsList_in.size()>0){//����ֿ��к���,ֱ�Ӹ�������
						
						int in_goods_num = ecsList_in.get(0).getGoods_num();
						in_goods_num = in_goods_num+num;
						erpCashStatement.setGoods_id(goods_id);
						erpCashStatement.setWarehouse_id(in_warehouse_id);
						erpCashStatement.setGoods_num(in_goods_num);
						erpCashStatementService.updateNum(erpCashStatement);
						
					}else{//����ֿ���û���Զ����
						erpCashStatement.setGoods_id(goods_id);
						erpCashStatement.setWarehouse_id(in_warehouse_id);
						erpCashStatement.setStatement_id(IdUtil.getUuid());
						erpCashStatement.setGoods_num(num);
						erpCashStatementService.add(erpCashStatement);
						
					}
					
					mav.setViewName("cashStatement/list");//�ɹ���ת���������б�ҳ
					
				}else{
					mav.setViewName("cashStatement/errorList");
					
				}
			}else{//���Ҫ�ǵ����ֿ���û�и���Ʒ
				mav.setViewName("cashStatement/errorList");
				
			}
		}else{//���״̬ΪB��ֱ����ת������ҳ
			mav.setViewName("cashStatement/errorList");
			
		}
		return mav;
	}
	
	
	
}
