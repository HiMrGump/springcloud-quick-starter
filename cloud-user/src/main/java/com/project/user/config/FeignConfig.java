package com.project.user.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * feign配置
 *
 * @ClassName: FeignConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 18:21
 * @Version: 1.0
 */
@Configuration
public class FeignConfig {

	//打印feign日志
	@Bean
	public Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}

	@Bean
	public RequestInterceptor requestInterceptor(){
		return new FeignBasicAuthRequestInterceptor();
	}

	/** 解决增加14个小时问题**/
	@Bean
	public FeignFormatterRegistrar getFeignFormatterRegistrar() {
		return new FeignFormatterRegistrar() {

			@Override
			public void registerFormatters(FormatterRegistry formatterRegistry) {
				formatterRegistry.addConverter(Date.class, String.class, new DateToStringConverter());
			}

			class DateToStringConverter implements Converter<Date, String> {
				@Override
				public String convert(Date date) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					return sdf.format(date);
				}
			}

		};
	}
}


