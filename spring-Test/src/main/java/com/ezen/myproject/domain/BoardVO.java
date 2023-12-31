package com.ezen.myproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 전체 생성자
public class BoardVO {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private String isDel;
	private String registerDate;
	private int read_count;
	private int commentCount;
	private int fileCount;
	
}
