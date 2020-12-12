package com.credit.sussie.LogManager.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.HashMap;

import com.credit.suisse.LogManager.hsqldb.HSqlDbManager;
import com.credit.suisse.LogManager.model.LogEntry;
import com.fasterxml.jackson.databind.ObjectMapper;



public class FileProcessor {

	
	public void processFileAndIngest(String fileLocation, HSqlDbManager hSqlDbManager) throws Exception
	{
		
		
         
		 try {
	            final BufferedReader br = new BufferedReader(new FileReader(fileLocation));
	            String line;
	            
	            HashMap<String, LogEntry> logMap=new HashMap<String, LogEntry>();
	            while ((line = br.readLine()) != null) {

	                ObjectMapper objectMapper=new ObjectMapper();
	                
	                
	                LogEntry obj = objectMapper.readValue(line, LogEntry.class);
	                
	                if(logMap.containsKey(obj.getId()))
	                {
	                	LogEntry le=logMap.get(obj.getId());
	                	
	                	long diff=0;
	                	boolean alert=false;
	                	if(le.getState().equalsIgnoreCase("started") && obj.getState().equalsIgnoreCase("finished"))
	                	{
	                		diff=obj.getTimestamp()-le.getTimestamp();
	                		
	                		if(diff>4)
	                		{
	                			alert=true;
	                		}
	                	}
	                	
	                	if(obj.getState().equalsIgnoreCase("started") && le.getState().equalsIgnoreCase("finished"))
	                	{
	                		diff=le.getTimestamp()-obj.getTimestamp();
	                		
	                		if(diff>4)
	                		{
	                			alert=true;
	                		}
	                	}
	                	
	                	String insertQuery="INSERT INTO log_tbl VALUES ('"+obj.getId()+"', '"+obj.getType()+"', '"
	        	                +obj.getHost()+"',"+diff+","+alert+")";
	        	                
	                			System.out.println("insert query::"+insertQuery);
	        	                int result=hSqlDbManager.executeQuery(insertQuery);
	        	                System.out.println("insert result::"+result);
	                	
	                	logMap.remove(obj.getId());
	                	
	                }
	                else
	                {
	                	logMap.put(obj.getId(), obj);
	                }
	            }
	            
	            
			/*
			 * if(obj.getState().equalsIgnoreCase("started")) { String
			 * insertQuery="INSERT INTO public.log_tbl VALUES ("+obj.getId()+",'"+obj.
			 * getState()+"', '"+obj.getType()+"', '"
			 * +obj.getHost()+"',"+obj.getTimestamp()+",NULL,NULL,false);";
			 * 
			 * int result=hSqlDbManager.executeQuery(insertQuery);
			 * System.out.println("insert result::"+result);
			 * hSqlDbManager.getConnection().commit(); }
			 * 
			 * if(obj.getState().equalsIgnoreCase("finished")) {
			 * 
			 * String update="UPDATE public.log_tbl SET endTime = "+obj.getTimestamp()
			 * +", duration="+obj.getTimestamp()+"-startTime , alert= CASE WHEN "+obj.
			 * getTimestamp()+"-startTime >4 THEN true " +
			 * "ELSE false END "+" WHERE id = "+obj.getId()+";"; int
			 * result=hSqlDbManager.executeQuery(update);
			 * 
			 * System.out.println("insert result::"+result);
			 * hSqlDbManager.getConnection().commit(); }
			 */
	                
	                String select= "Select id,type,host,duration,alert from log_tbl;";
	                
	                ResultSet rs=hSqlDbManager.executeSelectQuery(select);
	                
	                while(rs.next()){
	                    System.out.println(rs.getString("id")+" | "+
	                       rs.getString("type") +" | "+rs.getString("host")+" | "+rs.getInt("duration")+" | "+rs.getBoolean("alert"));
	                 }
	                
	                
	            

	            hSqlDbManager.getConnection().close();
	        } catch (final Exception ex) {
	            // if there is any exception like no file found then throw exception
	            throw ex;
	        }
	}
}
