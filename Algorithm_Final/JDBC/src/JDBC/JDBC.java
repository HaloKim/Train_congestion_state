package JDBC;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
public class JDBC {
	
	public static void main(String[] args)  throws IOException{
		int s;
		// file outputStream 
		String[] temp = new String[20];
		String[] t = new String[2];
		String time,day,sql = "";
		String station_num,congestion = "";
		int count =0;
		int ll=0;
		//FileWriter congestion_outputStream = new FileWriter("congestion.txt", true); // append 할 경우 이렇게 쓰기!
		
		// make connection to mysql server
		Connection con = null; // create connection object
	    try {
	        Class.forName("com.mysql.jdbc.Driver"); 
	        String url = "jdbc:mysql://localhost/algorithm";
	        String user = "root", passwd = "";
	        con = (Connection) DriverManager.getConnection(url, user, passwd); 
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    System.out.println("Connection OK!"); //Ok message
	    

	    Statement stmt = null; //SQL 문장에 대한 object
    		ResultSet rs = null;  //결과를 받을 오브젝트! 결과 테이블을 이 오브젝트로 받는다.
    		try {
    	    String psql1 = "select station_num, ";
    	    String psql2 = " from line2_total where day='";
    	    String psql3 = "';";
    	    
    	    //input으로 받기 -> 나중에 합칠때!
    	    for(int x=0;x<20;x++)
    	    		temp[x] = (x+5) + "_" + (x+6);
    	    temp[19] = "24";
    	    for(int x=1;x<=7;x++)
    	    {
    	    		FileWriter station_outputStream = new FileWriter(x+"station_num.txt");
    	    		FileWriter time_outputStream = new FileWriter(x+"time.txt");
    	    		FileWriter congestion_outputStream = new FileWriter(x+"congestion.txt");
    	    		for(int y=0;y<19;y++)
    	    		{
    	    			time = temp[y];
    	    			day = Integer.toString(x); //day : 월요일 1 ~ 일요일 7
        	    		sql = ""+psql1+time+psql2+day+psql3;
            	    System.out.println(sql);
            	    stmt = (Statement) con.createStatement();
            	    rs = stmt.executeQuery(sql); //executeQuery!!!! -> table을 받아온다.
            	    t = temp[y].split("_");
            	
            	    while (rs.next() && count < 43 * 4) 
            	    {// 아직도 내가 볼 tuple이 남아있으면 true!
            	        station_num = rs.getString(1); 
            	        congestion = rs.getString(2); 
            	        if (rs.wasNull()) 
            	        		station_num = "null"; 
            	        if (rs.wasNull()) 
            	        		congestion = "null";
            	        time = t[0];
            	        station_outputStream.write(station_num+"\r\n");
            	        if(y==19)
            	        		time_outputStream.write(24+"\r\n");
            	        else
            	        		time_outputStream.write(time+"\r\n");
            	        int i = Math.abs(Integer.parseInt(congestion))/8;
            	        
            	        if(i < 80)
        	        			i = 1;
            	        else if(i < 160)
    	        				i = 2;
            	        else if(i < 200)
	        				i = 3;
            	        else if(i < 280)
	        				i = 4;
            	        else
	        				i = 5;
            	        congestion_outputStream.write(i+"\r\n");
            	        System.out.println(station_num + "\t" + i + "\t" + time);
            	        ++count;
            	        ll++;
            	    }
            	    count = 0;
    	    		}
    	    		System.out.println(ll);
    	    		station_outputStream.close();
    	    		time_outputStream.close();
    	    		congestion_outputStream.close();
    	    }
    	    
    	} catch (SQLException e1) {
    	    e1.printStackTrace();
    	}

    	try {
    	    if (stmt != null && !stmt.isClosed()) stmt.close(); //close 
    	} catch (SQLException e1) {
    	    e1.printStackTrace();
    	}
    	
	}
}
