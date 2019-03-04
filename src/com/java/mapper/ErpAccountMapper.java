package com.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.java.bean.ErpAccount;


public interface ErpAccountMapper extends Dao<ErpAccount, String> {

	public ErpAccount checkLogin(@Param("username")String username,@Param("password")String password);
	
	//��������û���ÿ���û�����������
	public  List<ErpAccount>getErpAccountAndErpCommentAll();
	
	//��������û���ÿ���û����������ۼ�ÿ�����۵����лظ���Ϣ
	public List<ErpAccount>getErpAccountAndErpCommentAndErpReplyAll();
	
	//zdb----
	//�����û����ж��Ƿ��Ѿ�����
	public int checkTheSameUsername(String username);

}



















