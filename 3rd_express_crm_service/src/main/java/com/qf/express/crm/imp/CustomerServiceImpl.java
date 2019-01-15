package com.qf.express.crm.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qf.express.common.AppExcption;
import com.qf.express.common.AppResult;
import com.qf.express.crm.api.CustomerService;
import com.qf.express.crm.entity.TCustomer;
import com.qf.express.crm.entity.TCustomerExample;
import com.qf.express.crm.mapper.TCustomerMapper;
@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private TCustomerMapper customerMapper;
	
	@Override
	public List<TCustomer> findCustomerNoAss() {
		TCustomerExample example = new TCustomerExample();
		example.createCriteria().andDecidedzoneIdIsNull();
		example.or().andDecidedzoneIdEqualTo("");
		return customerMapper.selectByExample(example);
	}

	@Override
	public List<TCustomer> findCustomerAss(String decidedzone_id) {
		TCustomerExample example = new TCustomerExample();
		example.createCriteria().andDecidedzoneIdEqualTo(decidedzone_id);
		return customerMapper.selectByExample(example);
	}

	@Override
	public AppResult CustomerAss(Integer[] customerlds, String id) {
		
		TCustomerExample example = new TCustomerExample();
		example.createCriteria().andDecidedzoneIdEqualTo(id);
		List<TCustomer> clist = customerMapper.selectByExample(example);
		customerMapper.deleteByExample(example);
		
		for (TCustomer tCustomer : clist) {
			tCustomer.setDecidedzoneId("");
			int count = customerMapper.insertSelective(tCustomer);
			if(count<1) {
				throw new AppExcption(303,"绑定操作失败");
			}
		}
		TCustomer customer = new TCustomer();
		customer.setDecidedzoneId(id);
		if(customerlds.length!=0&&customerlds!=null&&customerlds.equals("")==false) {
			for (int i = 0; i < customerlds.length; i++) {
				customer.setId(customerlds[i]);
				int count = customerMapper.updateByPrimaryKeySelective(customer);
				if(count<1) {
					throw new AppExcption(303,"绑定操作失败");
				}
			}
		}
		return new AppResult(200, "绑定操作成功", null);
	}

	@Override
	public TCustomer findCusByPhone(String phone) {
		TCustomer cus = null;
		TCustomerExample example = new TCustomerExample();
		example.createCriteria().andTelephoneEqualTo(phone);
		List<TCustomer> list = customerMapper.selectByExample(example);
		if(list!=null&&list.size()>0) {
			cus = list.get(0);
		}
		return cus;
	}

	@Override
	public AppResult addCustomer(TCustomer customer) {
		int count = customerMapper.insertSelective(customer);
		if(count<1) {
			throw new AppExcption(203, "注册失败");
		}
		return new AppResult(200, "注册成功 ", null);
	}

}
