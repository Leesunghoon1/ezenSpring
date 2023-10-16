package com.ezen.myproject.handler;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.myproject.domain.FileVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@AllArgsConstructor
@Component
public class FileHandler {
	
	// 내 파일 기본 경로 설정
	private final String UP_DIR = "D:\\_myweb\\_java\\fileUpload";
	
	public List<FileVO> uploadFiles(MultipartFile[] files) {
		//멀티파트 파일 객체를 받아서 fileVO 형태로 저장 한 후
		//오늘 날짜 경로(가변형태로 생성) / 실제 파일을 해당 경로에 저장
		//fileVO를 List에 추가 => List를 리턴
		
		//오늘날짜 경로 생성
		
		LocalDate date = LocalDate.now();
		log.info(">>> date : " + date);
		String today = date.toString(); //2023-10-23 String으로 변환
		// 2023\\10\\13 String 생성
		today = today.replace("-", File.separator);
		
		//오늘날짜(today)의 폴더 구성
		File folders = new File(UP_DIR, today);
		if(!folders.exists()) {
			folders.mkdirs(); //폴더 생성 명령
			
		}
		
		//리스트 설정
		List<FileVO> flist = new ArrayList<FileVO>();
		//여러개의 파일들 중 순서대로 하나의 파일 가져오기
		for(MultipartFile file : files) {
			FileVO fvo = new FileVO();
			fvo.setSave_dir(today); //공통 뒷쪽 오늘날짜 경로만 set
			fvo.setFile_size(file.getSize()); //파일 사이즈 return Long
			
			//파일 이름 설정(OriginalFilename() 설정)
			//originalFileName : 파일 경로를 포함하고 있을 수 있음.			
			log.info(">>getName>>" + file.getName()); //파일 객체의 종류
			log.info(">>oriName>>" + file.getOriginalFilename()); // 이름
			String originalFileName = file.getOriginalFilename();
			String onlyFileName = 
					originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1); // 실 파일명 추출
			
			log.info(">>>>> onlyFileName>>>" + onlyFileName);
			fvo.setFile_name(onlyFileName); //파일 이름 설정
			
			//UUID 설정
			UUID uuid = UUID.randomUUID();
			//중복될 확율이 없는 랜덤이름
			fvo.setUuid(uuid.toString()); 
			//uuid = uuid의 객체 이므로 .tostring 변환 후 저장
			log.info("uuid >>> " + uuid.toString());
			//여기까지 fvo 설정 완료 fvo setting
			
			//디스크에 저장할 파일 객체 생성 -> 저장
			//uuid_파일네임
			String fullFileName = uuid.toString()+"_"+onlyFileName;
			File storeFile = new File(folders, fullFileName);
			
			//저장 => 폴더가 없으면 저장이 안되기 때문에 io Exception 발생
			try {
				file.transferTo(storeFile); //원본 객체를 저장을 위한 형태로 변경 후 복사
				//파일 타입을 결정 => 이미지 파일이라면 썸네일 생성
				
				if(isImgeFile(storeFile)) {
					fvo.setFile_type(1);
					//uuid_th_파일네임
					File thumbNail = new File(folders, 
							uuid.toString()+"_th_"+onlyFileName);	
					
					log.info("썸네일 >>>" + thumbNail);
					Thumbnails.of(storeFile).size(75, 75).toFile(thumbNail);
				}
			} catch (Exception e) {
				log.info(">>> file 생성 오류");
				e.printStackTrace();
			}		
			flist.add(fvo);
		}
			
		return flist;
		
	}
	
	//tika를 사용하여 파일 형식 체크 -> 이미지 파일이 맞는지 확인
	public boolean isImgeFile(File storFile) throws IOException {
		String mimeType = new Tika().detect(storFile); // image/jpg, imge/png
		return mimeType.startsWith("image")? true : false;
	}


}
