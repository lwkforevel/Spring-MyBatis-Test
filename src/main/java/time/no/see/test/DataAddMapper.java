package time.no.see.test;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.StartApplication;

import mapper.Sys_userMapper;
import model.Sys_user;
import util.PasswordHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class DataAddMapper {
		
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "md5";
    private final int hashIterations = 2;
    
	   @Autowired
	   Sys_userMapper sys_userMapper;
	
	   @Test
	//   @Transactional
	    public void testgetMessage() throws Exception {
		/*
		 * Sys_user user = new Sys_user(); user.setOrganizationId((long)1);
		 * user.setLocked(false); user.setRoleIds("1,2,3,4");
		 * user.setUsername("admin1"); user.setPassword("admin1");
		 * user.setSalt("100000000");
		 */
		  Sys_user user = sys_userMapper.selectByPrimaryKey((long)1);
			String salt = randomNumberGenerator.nextBytes().toHex();
			
		   user.setUsername("life");
		   user.setId((long)3);
		   user.setSalt(salt);
			DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
	    	hashService.setHashAlgorithmName("SHA-512");
	    	hashService.setPrivateSalt(new SimpleByteSource("salt")); //私盐，默认无
			/*
			 * hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
			 * hashService.setRandomNumberGenerator(new
			 * SecureRandomNumberGenerator());//用于生成公盐。默认就这个
			 */

	    	 hashService.setHashIterations(hashIterations); //生成Hash值的迭代次数
			 HashRequest request = new HashRequest.Builder()
	    	            .setAlgorithmName(algorithmName).setSource(ByteSource.Util.bytes("royal"))
	    	            .setSalt(ByteSource.Util.bytes(salt)).setIterations(hashIterations).build();
			 
			/*
			 * String hex = hashService.computeHash(request).toHex(); String encryValue =
			 * new SimpleHash( algorithmName, origin, ByteSource.Util.bytes(key),
			 * hashIterations).toHex();
			 */
		    String hex = hashService.computeHash(request).toHex();
		    user.setPassword(hex);
			  Sys_user users = sys_userMapper.selectByPrimaryKey((long)3);
			  System.out.println(hex);
			  System.out.println(users.getPassword());
			  boolean equals = users.getPassword().equals(hex);
			  System.out.println(equals);
	   }
	
}
