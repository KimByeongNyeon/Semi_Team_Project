package Semi_Team_Project.Model;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StoreData {
	String filePath = "";
	String readLine;
	//
	public StoreData(String path) {
		this.filePath = path;
//		System.out.println("path="+path);
	}

	public List<String> loadData() throws IOException {
		
		List<String> lines = new ArrayList<String>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			String bytesRead = "";
			while ((bytesRead = br.readLine()) != null) {
				lines.add(bytesRead);
			} // end while
			
		} finally {
			if(br != null) {br.close();}	// end if
		} // finally
		  
		/*
		try (FileInputStream fis = new FileInputStream(filePath);
	            DataInputStream dis = new DataInputStream(fis)) {

			byte[] buffer = new byte[1024];
	          int bytesRead;
	          while ((bytesRead = fis.read(buffer)) != -1) {
	             
	              System.out.write(buffer, 0, bytesRead);
	              
	              // 16비트 데이터 읽기 (short 형)
	              short shortValue = dis.readShort();
		          System.out.println("Read 16-bit data: " + shortValue);

		          // 8비트 데이터 읽기 (byte 형)
		          byte byteValue = dis.readByte();
		          System.out.println("Read 8-bit data: " + byteValue);
		          
	          }
	        

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
//		try (FileInputStream fis = new FileInputStream(new File(filePath))) {      
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//               
//                System.out.write(buffer, 0, bytesRead);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		LogInfoVO logInfo = new LogInfoVO();
		*/
		
//		System.out.println("StoreData!!");
		
		return lines;
	} // loadData
	
	public List<String> loadDataNIO() throws IOException {
//		System.out.println("StoreData!! NIO!!");
		// 파일 경로를 나타내는 Path 객체 생성
		Path path = Paths.get(filePath);
		

        // 파일의 모든 행을 읽어옴
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        // 읽어온 데이터 처리 (예: 화면에 출력)
        /*
        for (String line : lines) {
            System.out.println(line);
        }
        LogInfoVO logInfo = new LogInfoVO();
        */

			
			
//		System.out.println("StoreData!!NIO!!");
		
		return lines;
	} // loadData_NIO
	
} // class
