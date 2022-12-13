package com.mongo.test;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.*;
import org.bson.codecs.pojo.*;
import org.springframework.data.mongodb.core.MongoClientSettingsFactoryBean;
import org.bson.codecs.configuration.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
public class TestMongoPOJO {
	String db="mydb";
	String table="posts";
	MongoClient mclient;
	MongoDatabase mdb;
	MongoCollection<PostVO> mcol;
	
	public TestMongoPOJO() {
		this.mappingPojo();
	}

	//Bson문서를 Java Pojo객체에 매핑하는 메서드 ==> 코덱 레지스트리가 필요함
	private void mappingPojo() {
		ConnectionString conStr=new ConnectionString("mongodb://localhost:27017");
		CodecRegistry pojoCodec= fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry=fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodec);
		//mongodb에서 가져온 BSon데이터를 자바 POJO로 인코딩 디코딩 하도록 설정하는 작업
		MongoClientSettings clientSettings=MongoClientSettings.builder()
				.applyConnectionString(conStr)
				.codecRegistry(codecRegistry)
				.build();
		mclient=MongoClients.create(clientSettings);
		mdb=mclient.getDatabase(db);
	}
	
	public void insertOne() {
		mcol=mdb.getCollection(table, PostVO.class);
		PostVO vo=new PostVO(null, "Kim", "오늘도 수고 많으셨어요", "2022-12-13");
		mcol.insertOne(vo);
		System.out.println(vo.getTitle()+"글을 등록 했습니다.");
	}
	
	public static void main(String[] args) {
		TestMongoPOJO app=new TestMongoPOJO();
		app.insertOne();
	}
}
