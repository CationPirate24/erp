package com.java.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpAccount;

public interface ErpAccountService extends Service<ErpAccount,String> {

	public ErpAccount checkLogin(@Param("username")String username,@Param("password")String password);
	
	//zdb----
	//�����û����ж��Ƿ��Ѿ�����
	public int checkTheSameUsername(String username);


	//��������û���ÿ���û�����������
	public  List<ErpAccount>getErpAccountAndErpCommentAll();
	
	//��������û���ÿ���û����������ۼ�ÿ�����۵����лظ���Ϣ
	public List<ErpAccount>getErpAccountAndErpCommentAndErpReplyAll();
}




