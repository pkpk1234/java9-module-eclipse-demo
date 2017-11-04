package com.example.hello;

public class HelloModule {
	public static void main(String[] args) {
		Class<HelloModule> clazz = HelloModule.class;
		Module module = clazz.getModule();
		String moduleName = module.getName();
		System.out.println("Hello from module: "+moduleName);
	}
}
