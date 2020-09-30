package com.example.demo.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义传入日期转换器
 * 用以解决前端提交时间数据，后台date字段接收转换不准确的问题
 */
@Configuration
public class DateConverConfig {
	
	@Bean
	public Converter<String, Date> stringDateConvert() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				//yyyy-MM-dd HH:mm:ss日期字符串转换为Date类型
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = null;
				try {
					date = sdf.parse((String) source);
				} catch (Exception e) {
					//yyyy-MM-dd日期字符串转换为Date类型
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						date = sdf.parse((String) source);
					} catch (ParseException e1) {
						//EEE MMM dd HH:mm:ss Z yyyy字符串转换为Date类型
						sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
						try {
							date = sdf.parse((String) source);
						} catch (ParseException e2) {
							e2.printStackTrace();
						}
					}
				}
				return date;
			}
		};
	}
}
