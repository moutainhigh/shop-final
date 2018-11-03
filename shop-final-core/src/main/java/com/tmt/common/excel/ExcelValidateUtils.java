package com.tmt.common.excel;

import java.io.InputStreamReader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import com.tmt.common.config.Globals;
import com.tmt.common.utils.StringUtil3;


/**
 * Excel 校验工具
 * 
 * @author root
 */
public class ExcelValidateUtils {

	private static ScriptEngineManager SEM = new ScriptEngineManager();
	private static ScriptEngine SE = SEM.getEngineByName("JavaScript");
	static {
		InputStreamReader reader = null;
		try{
			reader = new InputStreamReader(ExcelValidateUtils.class.getResourceAsStream("excel-validate.js"), Globals.DEFAULT_ENCODING);
			SE.eval(reader);
		}catch(Exception e){}finally{
			IOUtils.closeQuietly(reader);
		}
	}
	
	/**
	 * 校验值的有效性
	 * @param value
	 * @param verifyFormat
	 * @return
	 */
	public static String validate(String value, String rules) {
		if(StringUtil3.isBlank(rules)) {
			return null;
		}
		Bindings params = SE.getBindings(ScriptContext.ENGINE_SCOPE);
		params.put("value", value);
		params.put("rules", rules);
		try {
			return (String) SE.eval(new StringBuilder("validator.doValidator(value, rules)").toString(), params);
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
}