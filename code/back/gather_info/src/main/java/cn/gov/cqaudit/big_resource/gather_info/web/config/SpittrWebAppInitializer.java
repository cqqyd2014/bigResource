package cn.gov.cqaudit.big_resource.gather_info.web.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer 
{ //配置DispatcherServlet映射到 '/' 什么情况进入DispatherServlet 
	@Override 
	protected String[] getServletMappings() 
	{ 
		return new String[]{"/"}; 
		} 
	//spring 注入bean 
	@Override 
	protected Class[] getRootConfigClasses() 
	{ return new Class[]{RootConfig.class}; } 
	//spring mvc 配置 
	@Override 
	protected Class[] getServletConfigClasses() 
	{ return new Class[]{WebConfig.class}; } 
	@Override 
	protected Filter[] getServletFilters() {
		//注册过滤器 
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter(); characterEncodingFilter.setEncoding("UTF-8"); 
		characterEncodingFilter.setForceEncoding(true); 
		return new Filter[]{new HiddenHttpMethodFilter(), characterEncodingFilter};
		}
}
