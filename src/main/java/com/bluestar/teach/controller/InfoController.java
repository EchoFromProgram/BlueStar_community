package com.bluestar.teach.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Customer;
import com.bluestar.teach.entity.Staff;
import com.bluestar.teach.service.AccountService;
import com.bluestar.common.utils.ValidateUtil;

@Controller
public class InfoController {

	@Resource
	private AccountService accountService;
	
	//转跳到客户资料页面
	@RequestMapping(path = "customer_info.do", produces = {"application/json;charset=UTF8"})
	public String customerInfo() {
		return "teach/customer_info.html";
	}
	
	//转跳到员工资料页面
	@RequestMapping(path = "staff_info.do", produces = {"application/json;charset=UTF8"})
	public String staffInfo() {
		return "teach/staff_info.html";
	}
	
	/***
	 * 根据infoId获取员工信息
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "get_staff_info.do", produces = {"application/json;charset=UTF8"})
	public Object getStaffInfo(HttpSession session) {
		AccountDto accountDto = accountService.getStaffInfoByInfoId((Integer)((Map)session.getAttribute("user")).get("info_id"));
		return accountDto;
	}
	
	/***
	 * 根据staff对象和infoId更新员工信息
	 * 
	 * @param staff
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "update_staff_info.do", produces = {"application/json;charset=UTF8"})
	public Object updateStaffInfo(@Valid Staff staff, BindingResult bindingResult, HttpSession session) {
		if(bindingResult.hasErrors()) {
			return ValidateUtil.Validate(bindingResult);
		}
		staff.settId((Integer)((Map)session.getAttribute("user")).get("info_id"));
		System.out.println(staff);
		AccountDto accountDto = accountService.updateStaffInfoByInfoId(staff);
		return accountDto;
	}
	
	/***
	 * 根据infoId获取客户信息
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "get_customer_info.do", produces = {"application/json;charset=UTF8"})
	public Object getcustomerInfo(HttpSession session) {
		AccountDto accountDto = accountService.getCustomerInfoByInfoId((Integer)((Map)session.getAttribute("user")).get("info_id"));
		return accountDto;
	}
	
	/***
	 * 根据customer对象和infoId更新员工信息
	 * 
	 * @param customer
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "update_customer_info.do", produces = {"application/json;charset=UTF8"})
	public Object updateCustomerInfo(@Valid Customer customer, BindingResult bindingResult, HttpSession session) {
		if(bindingResult.hasErrors()) {
			return ValidateUtil.Validate(bindingResult);
		}
		customer.setInfoId((Integer)((Map)session.getAttribute("user")).get("info_id"));
		AccountDto accountDto = accountService.updateCustomerInfoByInfoId(customer);
		return accountDto;
	}
	
	@ResponseBody
	@RequestMapping(path = "get_provinces.do", produces = {"application/json;charset=UTF8"})
	public Object getProvinces() {
		return accountService.getAllProvinces().getData();
	}
	
	@ResponseBody
	@RequestMapping(path = "get_citys.do", produces = {"application/json;charset=UTF8"})
	public Object getCitys(Integer provinceId) {
		return accountService.getCitysByProvinceId(provinceId).getData();
	}

	@ResponseBody
	@RequestMapping(path = "get_schools.do", produces = {"application/json;charset=UTF8"})
	public Object getSchools(String city) {
		return accountService.getSchoolsByCity(city).getData();
	}
}
