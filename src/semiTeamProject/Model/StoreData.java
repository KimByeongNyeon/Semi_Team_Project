package semiTeamProject.Model;

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
	
	public StoreData(String path) {
		this.filePath = path;
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
		return lines;
	} // loadData
	public List<String> loadDataNIO() throws IOException {
		// 파일 경로를 나타내는 Path 객체 생성
		Path path = Paths.get(filePath);
        // 파일의 모든 행을 읽어옴
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		return lines;
	} // loadData_NIO
	
} // class
