package com.lhx.spring.services.impl;

import com.lhx.spring.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhx.spring.cache.dao.UserDao;
import com.lhx.spring.cache.entity.User;
import com.lhx.spring.cache.services.IUserService;

@Service
@Transactional
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserDao userDao;

	/**
	 * condition满足缓存条件的数据才会放入缓存，condition在调用方法之前和之后都会判断
	 * unless用于否决缓存更新的，不像condition，该表达只在方法执行之后判断，此时可以拿到返回值result进行判断了
	 */
	@Override
	@Cacheable(key = "#id", unless = "#result == null")
	public User queryUser(String id) {
		return this.userDao.queryUser();
	}

	@Override
	@CachePut(key = "#id")
	public User updateUser(String id) {
		return this.userDao.queryUser();
	}

	@Override
	@CacheEvict(key = "#id")
	public int deleteUser(String id) {
		return 1;
	}

}
