//package com.zjp.demo.impl;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//import javax.ws.rs.core.Application;
//
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.xml.XmlConfiguration;
//
//import junit.framework.TestCase;
//
//public class AppTest extends TestCase {
//	public static void main(String[] args) throws Exception {
//		Server server = new Server();
//
//		File file = new File(Application.class.getResource("/jetty-demo.xml").getFile());
//
//		XmlConfiguration config = new XmlConfiguration(new FileInputStream(file.getPath()));
//		config.configure(server);
//		server.start();
//	}
//}
