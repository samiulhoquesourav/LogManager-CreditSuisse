package com.credit.suisse.LogManager;

import com.credit.suisse.LogManager.hsqldb.HSqlDbManager;
import com.credit.sussie.LogManager.processor.FileProcessor;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		// check argument 0 which is file name
		if (args[0] == null || args[0].length() < 1) {
			return;
		}
		
		HSqlDbManager hSqlDbManager=new HSqlDbManager();
		
		
		String createTable="CREATE TABLE if not exists log_tbl ( " + 
				" id varchar(128) NOT NULL," + 
				" type VARCHAR(32), host varchar(32), duration INT, alert boolean," + 
				" PRIMARY KEY (id));";
		
		hSqlDbManager.executeQuery(createTable);
		
		String delTable="delete from log_tbl;";
		
		hSqlDbManager.executeQuery(delTable);
		
		FileProcessor fileProcessor=new FileProcessor();
		try {
			fileProcessor.processFileAndIngest(args[0],hSqlDbManager);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
