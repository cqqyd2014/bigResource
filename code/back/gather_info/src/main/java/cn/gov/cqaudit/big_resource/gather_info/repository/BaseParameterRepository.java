package cn.gov.cqaudit.big_resource.gather_info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.gov.cqaudit.big_resource.gather_info.entity.BaseParameter;
import cn.gov.cqaudit.big_resource.gather_info.entity.BaseParameterPK;

@Repository
public interface BaseParameterRepository extends JpaRepository<BaseParameter, BaseParameterPK>{
	
	public BaseParameter save(BaseParameter base_parameter);
	public  List<BaseParameter> findByPkPtype(String ptype);
}

