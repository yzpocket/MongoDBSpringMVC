package com.mongo.test;

import static com.mongodb.client.model.Filters.eq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
public class PostsManager {
	MongoClient mclient;
	MongoDatabase mongodb;
	MongoCollection<Document> mcol;
	Scanner sc=new Scanner(System.in);
	
	public PostsManager() {
		mclient=MongoClients.create("mongodb://localhost:27017");
		mongodb=mclient.getDatabase("mydb");
		mcol=mongodb.getCollection("posts");
		System.out.println("MongoDB mydb데이터베이스 접속 완료.");
	}
	public void close() {
		if(mclient!=null)
			mclient.close();
	}
	//입력하기
	public void insertPosts() {
		System.out.print("작성자 입력: ");
		String author=sc.nextLine();
		System.out.println("POST 제목 입력: ");
		String title=sc.nextLine();
		Date today=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String wdate=sdf.format(today);
		
		Document doc=new Document(); 
		doc.append("author", author)
			.append("title", title)
			.append("wdate", wdate);
		InsertOneResult res=mcol.insertOne(doc);
		System.out.println(res.getInsertedId()+"도큐먼트가 생성되었습니다.");
	}//-------------------
	//모든 목록 가져오기
	public void selectPostsAll() {
		FindIterable<Document> cursor=mcol.find();
		for(Document doc:cursor) {
			String title=doc.get("title").toString();
			//System.out.println(title);
			System.out.println(doc.toJson());//json문자열을 반환
		}
	}//-------------------
	//모든 목록 가져오기2
	public void selectPostsAll2() {
		FindIterable<Document> cr=mcol.find();
		MongoCursor<Document> mcr=cr.iterator();
		while(mcr.hasNext()) {//이렇게해도됨.
			Document doc=mcr.next();
			String title=doc.getString("title");
			String author=doc.getString("author");
			String wdate=doc.getString("wdate");
			System.out.println(title+"\t"+author+"\t"+wdate);
		}
	}//-------------------
	//삭제하기
	public void deletePosts() {
		System.out.println("삭제할 글의 작성자명을 입력 :");
		String author=sc.nextLine();
		
		//DeleteResult res=mcol.deleteOne(eq("author", author));//static으로 eq임포트 받아서 bson필터 쓸수있.
		DeleteResult res=mcol.deleteMany(eq("author", author));//static으로 eq임포트 받아서 bson필터 쓸수있.
		
		long n=res.getDeletedCount();
		System.out.println(n+"개의 도큐먼트 삭제됨");
	}//-------------------
	//수정하기
	public void updatePosts() {
		System.out.println("검색할 글의 작성자명을 입력 :");
		String author=sc.nextLine();
		System.out.println("수정할 제목 입력: ");
		String title=sc.nextLine();
		
		Bson query=eq("author", author);
		Bson edit1=Updates.combine(Updates.set("title", title));
		
		UpdateResult res=mcol.updateOne(query, edit1);
		long n=res.getModifiedCount();
		System.out.println(n+"개의 도큐먼트가 수정됨");
	}//-------------------
	
	
	//메인 실행단 
	public static void main(String[] args) {
		PostsManager app=new PostsManager();
		//app.insertPosts();
		System.out.println("---POSTS 목록 가져오기---");
		app.selectPostsAll();
		//app.deletePosts();
		app.updatePosts();
		
		System.out.println("---POSTS 목록 가져오기2---");
		app.selectPostsAll2();
	}



}
