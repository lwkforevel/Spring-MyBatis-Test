package time.no.see.test;

import java.security.SecureRandom;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.After;
import org.junit.Before;
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
public class HashServiceMapper {
		
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String algorithmName = "MD5";
    private int hashIterations = 2;
	private long id = 2;
	private String username = "visitor";
	private String password = "royal";
	private String salt = null;
	private String Dpassword = null;
	
	private DefaultHashService defaultHashService;
	
	
	   @Autowired
	   Sys_userMapper sys_userMapper;
	   
	   @Before
	   public void initDefaultHashService() {
		   defaultHashService = new DefaultHashService();
		   defaultHashService.setHashAlgorithmName(algorithmName);
		   defaultHashService.setHashIterations(hashIterations);
		   defaultHashService.setPrivateSalt(ByteSource.Util.bytes(username));
		   
	   }
	   		
	   		@Test
		    public void testPasswordAdd() throws Exception {
		   		String salt = randomNumberGenerator.nextBytes().toHex();
				HashRequest request = new HashRequest.Builder()
		    	            .setSource(ByteSource.Util.bytes(password))
		    	            .setSalt(ByteSource.Util.bytes(salt))
		    	            .build();
			    String hex = defaultHashService.computeHash(request).toHex();
			    System.out.println("username："+username);
			    System.out.println("publicSalt："+ salt);
			    System.out.println("password："+ hex);
		   }
	   
	   		
	   @After
	   public void testPassword() {
			Sys_user user = sys_userMapper.selectByPrimaryKey(id);
			String password = user.getPassword();
			String encryPassword = encryPassword(password);
			boolean equals = Dpassword.equals(encryPassword);
			System.out.println(equals);
	   }
	   
	   
	   private String encryPassword(String password) {
  			String Lsalt  = salt == null? randomNumberGenerator.nextBytes().toHex() : salt;
			HashRequest request = new HashRequest.Builder()
	    	            .setSource(ByteSource.Util.bytes(password))
	    	            .setSalt(ByteSource.Util.bytes(Lsalt))
	    	            .build();
			String hex = defaultHashService.computeHash(request).toHex();
			Dpassword = hex;
  			return hex;
  		}
  	
	   
}
