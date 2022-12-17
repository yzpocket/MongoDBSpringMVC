package com.mongo.melon.domain;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
//@Document(collection="melon_20221216")
public class MelonVO {
	
	@Id
	private String id;
	private String songTitle;//노래제목
	private String singer;//가수
	private int Ranking;
	private String ctime;// 수집된 시간 정보
	private String albumImage;//앨범 이미지 url
	
	private int cntBySinger;//차트에 등록된 가수별 노래 수 
}
