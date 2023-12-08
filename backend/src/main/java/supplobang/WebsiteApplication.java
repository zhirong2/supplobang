package supplobang;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.security.Keys;
import supplobang.entities.Role;
import supplobang.entities.User;
import supplobang.repository.UserRepository;

// @EnableJpaRepositories(basePackages = "supplobang.repository")
// @EntityScan(basePackages = "supplobang.entities")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class WebsiteApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {

		SpringApplication.run(WebsiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole((Role.ADMIN));
		if(adminAccount == null){
			User admin = new User();
			admin.setUsername("supplobang");
			admin.setEmail("supplobang@gmail.com");
			admin.setPhoneNumber("91234567");
			admin.setStreetName("Edgefield plains");
			admin.setBlockNumber("456");
			admin.setUnitNumber("17-653");
			admin.setPostalCode("123456");
			admin.setRole(Role.ADMIN);
			admin.setPassword(new BCryptPasswordEncoder().encode("supplobang"));
			userRepository.save(admin);
		}

		User user = new User();
		user.setUsername("user");
		user.setEmail("user@gmail.com");
		user.setPhoneNumber("91234567");
		user.setStreetName("Edgefield plains");
		user.setBlockNumber("456");
		user.setUnitNumber("17-653");
		user.setPostalCode("123456");
		user.setRole(Role.USER);
		user.setPassword(new BCryptPasswordEncoder().encode("user"));
		userRepository.save(user);

		// SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

        // byte[] encodedKey = key.getEncoded();
        // String base64EncodedKey = Base64.getEncoder().encodeToString(encodedKey);

        // System.out.println("Generated Secret Key: " + base64EncodedKey); // Print the generated secret key

	}

}
