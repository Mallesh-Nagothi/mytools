package com.timetravel.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

public class TimeTravelAgent {
	private static String daysToTravel = "0";
	
    public static void premain(String agentArgs, Instrumentation inst) {
    	System.out.println(agentArgs);
    	if(null==agentArgs || !isStringInt(agentArgs)){ 
    		daysToTravel = "0";
    	}else {
    		daysToTravel = agentArgs;
    	}
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
            	byte[] byteCode = null;
                if("java/util/Date".equals(s)){
                	try {
                        ClassPool cp = ClassPool.getDefault();
                        CtClass cc = cp.get("java.util.Date");
                        CtConstructor cnstructr = cc.getConstructor("()V");
                        cnstructr.setBody("{ long timeTravelTime = (long) "+ daysToTravel +" * 24 * 60 * 60 * 1000; this(System.currentTimeMillis() - timeTravelTime); }");
                        byteCode = cc.toBytecode();
                        cc.detach();
                   } catch (Exception ex) {
                       ex.printStackTrace();
                    }
                }
                return byteCode;
            }
        });
    }
    
    public static boolean  isStringInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
}