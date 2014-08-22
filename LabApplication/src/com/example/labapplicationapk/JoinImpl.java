package com.example.labapplicationapk;
/* 
 * JoinImpl.java 
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * This program is the implementation of the interface
 *
 * @author      Shobhit Dutia
 */

public class JoinImpl extends UnicastRemoteObject implements JoinInterface {
	private static final long serialVersionUID = 1L;
	static int countOfPeers=0;
	static Map<Integer, String> ipMap=new HashMap<Integer, String>();
	Connection con;
	Statement stmt;
	private String username="root";
	private String password="KL@nkng$0unD";
	String userHome = System.getProperty("user.home");
	char sep=File.separatorChar;
	//char sep=File.pathSeparatorChar;

	protected JoinImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * join request method.
	 * 
	 * @param    args   	ip address of new node
	 * @return 	 ip address of bootstrap node	
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */


	public int addUsersToDatabase(Vector<String> userListVector, String className) {
		int success=1;
		try {
			initDatabaseConnection();
			for (int i=0;i<userListVector.size();i++) {
				String uid=userListVector.get(i);
				if(stmt.executeUpdate("INSERT INTO userlist VALUES (\""+uid+"\",\""+uid+"\",\""+className+"\");")==0) {
					success=0;
				}
			}
			stmt.close();
			con.close();
			System.out.println("Inserted successfully");
		} catch (SQLException e ) {
			e.printStackTrace();
			success=0;
		}	
		return success;		
	}

	private void initDatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/radb",
					username,
					password);
			stmt = con.createStatement();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int deleteUsersFromDatabase(Vector<String> userListVector, String selectedClass) {
		int success=1;
		try {
			initDatabaseConnection();
			String uid;
			for (int i=0;i<userListVector.size();i++) {
					uid=userListVector.get(i);
					if(stmt.executeUpdate("DELETE FROM userlist where uid=\""+uid+"\""
							+ " and class_name=\""+selectedClass+"\";")==0) {
						success=0;
					}
			}
			stmt.close();
			con.close();
			System.out.println("Deleted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			success=0;
		}
		return success;
	}
	public Vector<String> getUsersFromDatabase(String className) {
		Vector<String> userListVector=new Vector<String>();
		String uid;
		try {
			initDatabaseConnection();
			ResultSet rs=stmt.executeQuery("Select uid from userlist where class_name=\""+className+"\"");
			while(rs.next()){
				uid=rs.getString("uid");
				userListVector.add(uid);
			}
			stmt.close();
			con.close();
			System.out.println("GOt users successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userListVector;
	}

	@Override
	public int addMalware(String fileName, byte[] fileData,String className) throws RemoteException {
		try {
			
			File dir = new File(userHome+sep+"ISSP"+sep+className+sep+"Malwares");
			System.out.println("Add malware class name is "+className);
			//File dir = new File("Malwares\\"+className);
//			new File("C:\\Directory2\\Sub2\\Sub-Sub2").mkdirs()
			if(!dir.exists())
				dir.mkdirs();
			System.out.println("Filename is "+fileName);
			File file=new File(dir,fileName);
			BufferedOutputStream outputFile=new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
			outputFile.write(fileData,0,fileData.length);
			outputFile.flush();
			outputFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int sendEmulatorConfiguration(Vector<byte[]>bufferVector,String className)
			throws IOException {
		try {
			
			File dir = new File(userHome+sep+"ISSP"+sep+className+sep+"Emulator_Configurations");
			System.out.println("Add malware class name is "+className);
			//File dir = new File("Malwares\\"+className);
//			new File("C:\\Directory2\\Sub2\\Sub-Sub2").mkdirs()
			if(!dir.exists())
				dir.mkdirs();
			int index=0;
			BufferedOutputStream outputFile;
			for(byte fileData[]:bufferVector) {
				File file=new File(dir,"config_ini_"+(index++)+".ini");
				file.createNewFile();
				outputFile=new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()));
				outputFile.write(fileData,0,fileData.length);
				outputFile.flush();
				outputFile.close();
			}
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
		/*FileReader fr=null;
		FileWriter fw=null;
		int index=0;
		try{
			File dir = new File(userHome+sep+"ISSP"+sep+className+sep+"Emulator_Configurations");
			//File dir = new File("Emulator Configuration\\"+className);			
			if(!dir.exists()) {
				System.out.println("Making dirs");
				dir.mkdirs();				
			}
			for (File f:v) {
				fr=new FileReader(f);
				File f1=new File(dir,"config_ini_"+(index++)+".ini");
				fw=new FileWriter(f1);
				System.out.println("Creating file.. ");
				int c;
				while ((c = fr.read()) != -1) {
					fw.write(c);
				}
			}			
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (fw!= null) {
				fw.close();
			}
		  }
		return 1;*/
	}

	@Override
	public Vector getEmulatorConfiguration(String className) throws RemoteException,
	IOException {
		Vector configVector=sendFilesToClient("configuration",className);
		//File dir=new File("Emulator_Configurations");
		return configVector;
	}

	@Override
	public Vector getMalware(String classname) throws IOException {
		Vector malwareVector=sendFilesToClient("malware",classname);
		//File dir=new File("Emulator_Configurations");
		return malwareVector;
	}
	private Vector sendFilesToClient(String queryType,String classname) throws IOException {
		Vector configVector=new Vector();
		File[] files;
		//String userHome = System.getProperty("user.home");
		//char sep=File.pathSeparatorChar;
		System.out.println("Classname is "+classname);
		if(queryType.equals("configuration")) {
			
			files = new File(userHome+sep+"ISSP"+sep+classname+sep+"Emulator_Configurations").listFiles();
			//files= new File("Emulator Configuration/"+classname).listFiles();
		}
		else{
			files = new File(userHome+sep+"ISSP"+sep+classname+sep+"Malwares").listFiles();
			//files = new File("Malwares/"+classname).listFiles();
		}
		//adding every odd value as file name and even value as bytes for that file
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getAbsolutePath());
			byte buffer[]=new byte[(int)files[i].length()];
			BufferedInputStream inputFileStream=new BufferedInputStream( new FileInputStream(files[i].getAbsolutePath()));
			//Reads the file into buffer
			System.out.println("Buffer length:"+buffer.length);
			inputFileStream.read(buffer,0,buffer.length);
			inputFileStream.close();			
			configVector.add(files[i].getName());
			configVector.add(buffer);
		}
		return configVector;
	}
	@Override
	public List<String> getLogs(String className) throws RemoteException {
		String files;
		//String userHome = System.getProperty("user.home");
		//char sep=File.pathSeparatorChar;
		File folder=new File(userHome+sep+"ISSP"+sep+className+sep+"logs");
		//String path = "Logs/"+className; 
		//File folder=new File(path);
		if(!folder.exists()) {
			folder.mkdirs();
		}
		File[] listOfFiles = folder.listFiles(); 
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			files = listOfFiles[i].getName();
			list.add(files);
		}
		return list;
	}

	@Override
	public byte[] downloadFile(String fileName,String className) throws RemoteException {
		try {
			String path = userHome+sep+"ISSP"+sep+className+sep+"Logs";
			File file = new File(path+fileName);
			
			//File dir = new File("Logs\\"+className);
//			new File("C:\\Directory2\\Sub2\\Sub-Sub2").mkdirs()
			/*if(!dir.exists())
				dir.mkdirs();
			File file = new File(dir, fileName);*/
			byte buffer[] = new byte[(int)file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
			input.read(buffer,0,buffer.length);
			input.close();
			return(buffer);
		} catch(Exception e){
			System.out.println("FileImpl: "+e.getMessage());
			e.printStackTrace();
			return(null);
		}
	}

	@Override
	public List<String> getClasses() throws RemoteException {
		String name;
		List<String> list = new ArrayList<String>();
		try {
			initDatabaseConnection();
			ResultSet rs=stmt.executeQuery("SELECT distinct class_name from userlist;");
			while(rs.next()){
				name=rs.getString("class_name");
				list.add(name);			}
			stmt.close();
			con.close();
			System.out.println("GOt users successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public String getMyClassName(String uid) throws RemoteException {
		String className=null;
		try {
			initDatabaseConnection();
			ResultSet rs = stmt.executeQuery("Select class_name from userlist where uid=\""+uid+"\"");
			while(rs.next()){
				className=rs.getString("class_name");
			}
			stmt.close();
			con.close();
			System.out.println("Class Name returned");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return className;
	}

	@Override
	public void addClass(String classname) throws RemoteException {
		String path = userHome+sep+"ISSP"+sep+classname; 
		File folder=new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		//new File(path+File.separatorChar+classname).mkdir();
	}

	@Override
	public String changePassword(String uid, String oldPassword,
			String newPassword, String caller) {
		String result = null;
		try {
			System.out.println(uid+" "+oldPassword+" "+newPassword+" ");
			initDatabaseConnection();
			ResultSet rs = null;
			if(caller.equals("client calling")) 
				rs = stmt.executeQuery("Select \""+uid+"\" from userlist where pass = \""+oldPassword+"\";");
			else if(caller.equals("server calling")) 
				rs = stmt.executeQuery("Select \""+uid+"\" from inst_list where pass = \""+oldPassword+"\";");
				
			if(!rs.isBeforeFirst()){
				result="No such user name/password combination";
			}
			else {
				if(caller.equals("server calling")) {
					if(stmt.executeUpdate("UPDATE inst_list SET pass=\""+newPassword+"\""
							+ " where uid=\""+uid+"\" and pass=\""+oldPassword+"\";")==0) {
						result="error";
					}
					else {
					result="success";
					}
				}
				if(caller.equals("client calling")) {
					if(stmt.executeUpdate("UPDATE userlist SET pass=\""+newPassword+"\""
							+ " where uid=\""+uid+"\" and pass=\""+oldPassword+"\";")==0) {
						result="error";
					}
					else {
					result="success";
					}
				}
			}
			System.out.println("result is "+result);
			 	
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int addInstructor(String instId) throws RemoteException {
		int success=1;
		try {
			initDatabaseConnection();
			if(stmt.executeUpdate("INSERT INTO inst_list VALUES (\""+instId+"\",\""+instId+"\");")==0) {
					success=0;
			}
			System.out.println("success is "+success);		 	
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	@Override
    public char[] getPassword(String uid, String caller) throws RemoteException {
        String password=null;
        System.out.println("in getPassword");
        try {
            initDatabaseConnection();
            ResultSet rs = null;
            if(caller.equals("server calling")) {
                 rs = stmt.executeQuery("Select pass from inst_list where uid=\""+uid+"\"");
            }
            else if(caller.equals("client calling")) {
                rs = stmt.executeQuery("Select pass from userlist where uid=\""+uid+"\"");
           }
            while(rs.next()){
                password=rs.getString("pass");
            }
            stmt.close();
            con.close();
            System.out.println("Password of "+uid+" returned is"+password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(password==null)
        	return null;
        else 
        	return password.toCharArray();
    }
}