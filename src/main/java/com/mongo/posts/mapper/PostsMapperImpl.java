package com.mongo.posts.mapper;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongo.posts.domain.PostVO;
import com.mongodb.client.result.DeleteResult;


@Repository(value="postsMapperImpl")
public class PostsMapperImpl implements PostsMapper {
	
	@Resource(name="mongoTemplate")
	private MongoTemplate mTemplate;
	
	@Override
	public int insertPosts(PostVO vo) {
		PostVO rvo=mTemplate.insert(vo, "posts");
										//도메인객체, 컬렉션이름(posts)
		System.out.println("rvo==="+rvo);
		if(rvo==null) return 0;
		return 1;
	}

	@Override
	public List<PostVO> listPosts() {
		List<PostVO> arr=mTemplate.findAll(PostVO.class,"posts");
		return arr;
	}

	@Override
	public int deletePosts(String id) {
		DeleteResult res=mTemplate.remove(Query.query(Criteria.where("_id").is(id)), PostVO.class, "posts");
		return (int)res.getDeletedCount();
	}

	@Override
	public PostVO selectPosts(String id) {
		PostVO vo=mTemplate.findOne(Query.query(Criteria.where("_id").is(id)), PostVO.class, "posts");
		return vo;
	}
	
	@Override
	public int updatePosts(PostVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
