package com.github.prbrios.erpx.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.github.prbrios.erpx.dao.DAO;
import com.github.prbrios.erpx.dao.Row;

import groovy.lang.Binding;

public class Script {

	private static ScriptEngineManager manager = new ScriptEngineManager();
	private static ScriptEngine engine = manager.getEngineByName("groovy");
	private static String functions = "";
	private List<String> arguments;
	private Object result;
	private Script parentScript;
	private Binding parentBinding;
	private String scriptName;
	private String script;
	private DAO dao;

	static {
		try {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(Script.class.getResourceAsStream("/com/github/prbrios/erpx/model/Script.groovy")));
				String line = null;
				while((line = reader.readLine()) != null) {
					functions += line + "\n";
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Script(Script parentScript, Binding parentBinding, String scriptName) {
		if (parentScript != null && parentScript.scriptName.equals(scriptName))
			throw new RuntimeException("Recursividade");

		this.dao = new DAO();
		this.parentScript = parentScript;
		this.arguments = parentScript != null ? parentScript.arguments : null;
		this.parentBinding = parentBinding;
		this.scriptName = scriptName;
	}

	private String appendFunctions(String function) {
		return functions + function + "\n";
	}

	public boolean init() {
		Row row = this.getScript();
		this.script = this.appendFunctions((String) row.get("funcao"));
		this.arguments = new ArrayList<String>();
		if (row.get("argumentos") != null) {
			this.arguments = Arrays.asList(((String) row.get("argumentos")).split(","));
		}

		return row != null;
	}

	private Row getScript() {
		return dao.getRow("select f.* from md_funcao f where f.nome = '" + this.scriptName + "';");
	}

	public boolean execute(){
		return this.execute(null);
	}

	private void configureParameters(Bindings bindings, Map<String, Object> parameters) {

		if(parameters != null && 
			 parameters.keySet() != null &&
			 parameters.keySet().size() > 0) {
			for(String key : parameters.keySet()) {
				bindings.put(key, parameters.get(key));
			}
		}
	}

	public boolean execute(Map<String, Object> parameters) {

		this.result = null;
		if (this.script != null) {
			try {
				Bindings bindings = engine.createBindings();
				bindings.put("wgscript", this);
				
				this.configureParameters(bindings, parameters);

				CompiledScript compiledScript = null;
				compiledScript = ((Compilable) engine).compile(this.script);
				this.result = compiledScript.eval(bindings);

				return true;

			} catch (ScriptException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	public boolean executeWithArgs(Object[] args){
		Map<String, Object> parameters = new HashMap<String, Object>();

		for (int i = 0; i < this.arguments.size(); i++) {
			String arg = this.arguments.get(i);
			if (i < args.length) {
				parameters.put(arg.trim(), args[i]);
			} else {
				parameters.put(arg.trim(), null);
			}
		}

		return this.execute(parameters);
	}

	public Object getResult() {
		return this.result;
	}

}
