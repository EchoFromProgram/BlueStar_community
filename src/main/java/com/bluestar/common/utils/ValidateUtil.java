package com.bluestar.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.enums.Statusable;

/**
 * 验证通用方法
 * @author 王鸿175
 *
 */
public class ValidateUtil {
	
	public static  AccountDto Validate(BindingResult bindingResult) {
		
			Map<String, Object> map = new HashMap<>();
			List<FieldError> errors = bindingResult.getFieldErrors();
			for(FieldError fieldError: errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return new AccountDto(new Statusable() {
				
				@Override
				public String getInfo() {
					return (map.values().toArray(new String[0]))[0];
				}
				
				@Override
				public int getCode() {
					return -1;
				}
			});
			
		}
	}

