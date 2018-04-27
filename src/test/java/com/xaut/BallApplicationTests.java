package com.xaut;

import com.xaut.dao.GameInfoDao;
import com.xaut.dao.RoleInfoDao;
import com.xaut.dao.UserGameDao;
import com.xaut.dao.UserInfoDao;
import com.xaut.dao.UserRoleDao;
import com.xaut.entity.RoleInfo;
import com.xaut.entity.UserInfo;
import com.xaut.entity.UserRole;
import com.xaut.service.UserService;
import com.xaut.service.impl.UserServiceImpl;
import com.xaut.util.RedisCountHotBookUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {BallApplication.class})
@Slf4j
public class BallApplicationTests {

	@Mock
    UserInfoDao userInfoDao;

	@Mock
    RedisCountHotBookUtil redisCountHotBookUtil;

	@Mock
    UserRoleDao userRoleDao;

	@Mock
    RoleInfoDao roleInfoDao;

	@Mock
    GameInfoDao gameInfoDao;

	@Mock
    UserGameDao userGameDao;


	@InjectMocks
	UserServiceImpl userServiceImpl;


	@Before
	public void setUp(){
		// 初始化测试用例类中由Mockito的注解标注的所有模拟对象
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void testRegister(){
        UserInfo userInfo = new UserInfo();
        userInfo.setName("wz");
        userInfo.setUid("123");
        UserRole userRole = new UserRole();
        userRole.setRoleUid("1212");
        RoleInfo role = new RoleInfo();
        role.setDescription("普通用户");
        when(redisCountHotBookUtil.getInRedis("wz",UserInfo.class)).thenReturn(null);
//        when(redisCountHotBookUtil.putRedis(userInfo,UserInfo.class)).thenReturn(null);
        when(userInfoDao.selectByName("wz")).thenReturn(userInfo);
        when(userRoleDao.selectByUserUid("123")).thenReturn(userRole);
        when(roleInfoDao.selectByPrimaryKey("1212")).thenReturn(role);

        userServiceImpl.checkLogin("wz","123456");
    }

	@Test
	public void testLogin(){
	    UserInfo userInfo = new UserInfo();
	    userInfo.setName("wz");
	    userInfo.setUid("123");
        UserRole userRole = new UserRole();
        userRole.setRoleUid("1212");
        RoleInfo role = new RoleInfo();
        role.setDescription("普通用户");
        when(redisCountHotBookUtil.getInRedis("wz",UserInfo.class)).thenReturn(null);
//        when(redisCountHotBookUtil.putRedis(userInfo,UserInfo.class)).thenReturn(null);
        when(userInfoDao.selectByName("wz")).thenReturn(userInfo);
        when(userRoleDao.selectByUserUid("123")).thenReturn(userRole);
        when(roleInfoDao.selectByPrimaryKey("1212")).thenReturn(role);

        userServiceImpl.checkLogin("wz","123456");
	}


	@Test
	public void getUser(){

		when(userInfoDao.selectByUid("123")).thenReturn(new UserInfo("wangzhe"));
		UserInfo userInfo = userServiceImpl.selectByUid("123");
		verify(userInfoDao).selectByUid("123");


		System.out.println(userInfo);
//		when(userInfoDao.selectByUid("c9a8525f76fc408b926a803c84882448"));
	}

	@Test
	public void simpleTest(){

		//创建mock对象，参数可以是类，也可以是接口
		List<String> list = mock(List.class);

		//设置方法的预期返回值
		when(list.get(0)).thenReturn("helloworld");

		String result = list.get(0);

		//验证方法调用(是否调用了get(0))
		verify(list).get(0);

		//junit测试
		Assert.assertEquals("helloworld", result);
	}

}
