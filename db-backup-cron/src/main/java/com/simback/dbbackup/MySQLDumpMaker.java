package com.simback.dbbackup;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import com.smattme.MysqlExportService;

public class MySQLDumpMaker {

	public static File generateDump(String database, String userLogin, String userPassword, String localDir) {
		File file = null;
		Properties properties = new Properties();

		properties.setProperty(MysqlExportService.DB_NAME, database);
		properties.setProperty(MysqlExportService.DB_USERNAME, userLogin);
		properties.setProperty(MysqlExportService.DB_PASSWORD, userPassword);

		properties.setProperty(MysqlExportService.TEMP_DIR, new File(localDir).getPath());
//		properties.setProperty(MysqlExportService.TEMP_DIR, new File("E:\\dumps\\").getPath());
		properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
		
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		try {
			mysqlExportService.export();
			file = mysqlExportService.getGeneratedZipFile();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	/*
	public static void main(String[] args) {
		File file = generateDump("alp_trf", "root", "", "E:\\\\dumps\\\\");
		if(null != file)
			System.out.println("MySQL Dump file generated is located at : " + file.getAbsolutePath());
	}
	*/
}
