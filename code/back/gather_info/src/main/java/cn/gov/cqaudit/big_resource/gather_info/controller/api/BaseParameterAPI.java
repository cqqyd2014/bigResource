package cn.gov.cqaudit.big_resource.gather_info.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.gov.cqaudit.big_resource.gather_info.entity.BaseParameter;
import cn.gov.cqaudit.big_resource.gather_info.repository.BaseParameterRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/baseparameter")
public class BaseParameterAPI {
	@Autowired
    private BaseParameterRepository baseParameterRepository;

	@RequestMapping(value= "/{ptype}",method=RequestMethod.GET)
	public List<BaseParameter> getValuesByPtype(@PathVariable String ptype){
		return baseParameterRepository.findByPkPtype(ptype);
		
		
		
		
	}
}