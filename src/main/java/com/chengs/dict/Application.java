package com.chengs.dict;

import java.util.Scanner;

import com.chengs.dict.service.CreaterService;

/**
 *      主启动类
 * @author Shu Cheng
 *
 */
public class Application{
	
    public static void main(String[] args) throws Exception{
    	CreaterService cs=new CreaterService();
    	/*System.out.println("请输入数据库连接URL:");
    	String url = scan.nextLine();
    	System.out.println("请输入数据库用户名:");
    	String user = scan.nextLine();
    	System.out.println("请输入数据库密码:");
    	String password = scan.nextLine();
    	System.out.println("请输入需要输出字典的数据库名称:");
    	String dbName = scan.nextLine();*/
    	if(args.length != 4) {
    		throw new Exception("参数错误");
    	}else {
    		cs.creater(args[0], args[1], args[2], args[3]);
    	}
    }
    
}
