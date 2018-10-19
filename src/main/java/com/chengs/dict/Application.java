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
    	Scanner scan = new Scanner(System.in);
    	String url,user,password,dbName;
    	System.out.println("请输入数据库连接URL:");
    	url = scan.nextLine();
    	System.out.println("请输入数据库用户名:");
    	user = scan.nextLine();
    	System.out.println("请输入数据库密码:");
    	password = scan.nextLine();
    	System.out.println("请输入需要输出字典的数据库名称:");
    	dbName = scan.nextLine();
//    	cs.creater("jdbc:mysql://127.0.0.1:3306/iisp?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false",
//    			"root", "root", "iisp");
    	scan.close();
    	cs.creater(url, user, password, dbName);
    }
    
}
