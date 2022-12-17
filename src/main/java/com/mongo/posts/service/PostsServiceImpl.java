package com.mongo.posts.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mongo.posts.domain.PostVO;
import com.mongo.posts.mapper.PostsMapper;

@Service(value = "postsServiceImpl")
public class PostsServiceImpl implements PostsService {
	@Resource(name="postsMapperImpl")
	private PostsMapper pMapper;
	
	@Override
	public int insertPosts(PostVO vo) {
		return pMapper.insertPosts(vo);
	}

	@Override
	public List<PostVO> listPosts() {
		return this.pMapper.listPosts();
	}

	@Override
	public int deletePosts(String id) {
		return this.pMapper.deletePosts(id);
	}

	@Override
	public PostVO selectPosts(String id) {
		return this.pMapper.selectPosts(id);
	}
	
	@Override
	public int updatePosts(PostVO vo) {
		return this.pMapper.updatePosts(vo);
	}

}
