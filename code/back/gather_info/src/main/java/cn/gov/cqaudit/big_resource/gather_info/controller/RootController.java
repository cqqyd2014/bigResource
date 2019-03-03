package cn.gov.cqaudit.big_resource.gather_info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.gov.cqaudit.big_resource.gather_info.entity.BaseParameter;
import cn.gov.cqaudit.big_resource.gather_info.entity.BaseParameterPK;
import cn.gov.cqaudit.big_resource.gather_info.repository.BaseParameterRepository;


@Controller
public class RootController {
	
	@Autowired
    private BaseParameterRepository baseParameterRepository;
	
	@RequestMapping("/")
	@ResponseBody
	public String root() {
		
        
		List<BaseParameter> list=baseParameterRepository.findByPkPtype("money_type");
		System.out.println(list.size());
		return "Hello";
	}

}
