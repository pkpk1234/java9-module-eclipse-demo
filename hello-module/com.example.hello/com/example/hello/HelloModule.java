package com.example.hello;

/**
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2017-11-04
 */
public class HelloModule {
	public static void main(String[] args) {
		Class<HelloModule> clazz = HelloModule.class;
		Module module = clazz.getModule();
		String moduleName = module.getName();
		System.out.println("Hello from module: "+moduleName);
	}
}
