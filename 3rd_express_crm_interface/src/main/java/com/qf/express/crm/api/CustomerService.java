package com.qf.express.crm.api;

import java.util.List;

import javax.jws.WebService;

import com.qf.express.common.AppResult;
import com.qf.express.crm.entity.TCustomer;



@WebService
public interface CustomerService {
	
	//查询没有被承包区关联的客户
	List<TCustomer> findCustomerNoAss();
	
	//查询被承包区关联的客户
	List<TCustomer> findCustomerAss(String decidedzone_id);
	
	//客户绑定承包区
	AppResult CustomerAss(Integer[] customerlds,String id);

	//根据电话号码查询客户
	TCustomer findCusByPhone(String phone);
	
	//注册客户
	AppResult addCustomer(TCustomer customer);
}
