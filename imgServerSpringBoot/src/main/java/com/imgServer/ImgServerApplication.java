package com.imgServer;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.imgServer.controller.PoxyFilter;


@SpringBootApplication
@Configuration
public class ImgServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImgServerApplication.class, args);
	}
	
	
	 /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //单个文件最大  
        factory.setMaxFileSize("20480KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  


    @Bean
    public FilterRegistrationBean testFilterRegistration() {
    
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(new PoxyFilter());
      registration.addUrlPatterns("/*");
      registration.addInitParameter("paramName", "paramValue");
      registration.setName("mypoxyFilter");
      registration.setOrder(1);
      return registration;
    }
	 
	 
}
