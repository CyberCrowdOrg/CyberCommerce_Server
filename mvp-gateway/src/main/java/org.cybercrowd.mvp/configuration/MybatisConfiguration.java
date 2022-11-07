package org.cybercrowd.mvp.configuration;


import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Properties;


/**
 * Mybatis配置类
 * 
 * @author
 *
 */
@Configuration
@MapperScan(basePackages = "org.cybercrowd.mvp.mapper")
public class MybatisConfiguration {

	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		
		//是否将大于最后一页的页码当做最后一页
		//properties.setProperty("reasonable", "true");
		properties.setProperty("reasonable", "false");
		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}

}
