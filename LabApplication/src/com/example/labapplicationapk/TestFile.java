package com.example.labapplicationapk;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class TestFile {
	public static void main(String[] args) throws IOException {
		File[] files = new File("Emulator_Configurations").listFiles();
		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
		/*File f=new File("abc.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReader fr = null;
		FileWriter fw = null;
		try{
			File dir = new File("tempdir");
			if(!dir.exists())
				dir.mkdir();

				fr=new FileReader(f);
				File f1=new File(dir,"temppp.txt");
				fw=new FileWriter(f1);
				System.out.println("Creating file.. ");
				int c;
		        while ((c = fr.read()) != -1) {
		        	fw.write(c);
		        }
						
		} finally {
            if (fr != null) {
                fr.close();
            }
            if (fw!= null) {
                fw.close();
            }
        }
	*/	
	}
}
