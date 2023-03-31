package com.alejandromo.springboot.fundamentos;

import com.alejandromo.springboot.fundamentos.bean.MyBean;
import com.alejandromo.springboot.fundamentos.bean.MyBeanWithDependency;
import com.alejandromo.springboot.fundamentos.bean.MyBeanWithProperties;
import com.alejandromo.springboot.fundamentos.component.ComponentDependency;
import com.alejandromo.springboot.fundamentos.entity.User;
import com.alejandromo.springboot.fundamentos.pojo.UserPojo;
import com.alejandromo.springboot.fundamentos.repository.UserRepository;
import com.alejandromo.springboot.fundamentos.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo,
								  UserRepository userRepository,
								  UserService userService) {
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// ejemplosAnteriores();
		saveUsersInDatabase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

	private void saveWithErrorTransactional() {
		User test1 = new User("TestTransactional1", "TestTransactional1@domain.com", LocalDate.now());
		User test2 = new User("TestTransactional2", "TestTransactional2@domain.com", LocalDate.now());
		User test3 = new User("TestTransactional3", "TestTransactional1@domain.com", LocalDate.now());
		User test4 = new User("TestTransactional4", "TestTransactional4@domain.com", LocalDate.now());

		List<User> users = Arrays.asList(test1, test2, test3, test4);

		try {
			userService.saveTransactional(users);
		} catch (Exception e) {
			LOGGER.error("Esta es una excepción dentro del metodo transaccional " + e);
		}

		userService.getAllUsers()
				.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional: " + user));
	}

		private void getInformationJpqlFromUser() {
			/**LOGGER.info("Usuario con el metodo findByUserEmail: " +
			 userRepository.findByUserEmail("julie@gmail.com")
			 .orElseThrow(() -> new RuntimeException("No se encontro el usuario")));

			 userRepository.findAndSort("Luis", Sort.by("id").descending())
			 .forEach(user -> LOGGER.info("Usuario con metodo sort: " + user));

			 userRepository.findByName("Marisol")
			 .forEach(user -> LOGGER.info("Usuario con query method: " + user.toString()));

			 LOGGER.info("Usuario con query method findByEmailAndName: " + userRepository.findByEmailAndName("daniela@domain.com", "Daniela")
			 .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

			 userRepository.findByNameLike("%u%")
			 .forEach(user -> LOGGER.info("Usuario findByNameLike: " + user));

			 userRepository.findByNameOrEmail("user10", null)
			 .forEach(user -> LOGGER.info("Usuario findByNameOrEmail: " + user));**/

			userRepository.findBybirthDateBetween(LocalDate.of(2021, 3, 1), LocalDate.of(2022, 4, 2))
					.forEach(user -> LOGGER.info("Usuario con intervalo de fechas: " + user));

        /*userRepository.findByNameLikeOrderByIdDesc("%user%")
				.forEach(user -> LOGGER.info("Usuario encontrado con like y ordenado: " + user));*/

			userRepository.findByNameContainingOrderByIdDesc("user")
					.forEach(user -> LOGGER.info("Usuario encontrado con containing y ordenado: " + user));

			LOGGER.info("El usuario a partir del named parameter es: " + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2022, 9, 8), "daniela@domain.com")
					.orElseThrow(() -> new RuntimeException("No se encontro el usuario a partir del named parameter")));
		}

		private void saveUsersInDatabase() {
			User user1 = new User("Alejandro", "alejandro@gmail.com", LocalDate.of(2023, 3, 28));
			User user2 = new User("Julie", "julie@gmail.com", LocalDate.of(2023, 1, 18));
			User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2022, 9, 8));
			User user4 = new User("Marisol", "marisol@domain.com", LocalDate.of(2022, 6, 18));
			User user5 = new User("Karen", "karen@domain.com", LocalDate.of(2022, 1, 1));
			User user6 = new User("Carlos", "carlos@domain.com", LocalDate.of(2021, 7, 7));
			User user7 = new User("Enrique", "enrique@domain.com", LocalDate.of(2021, 11, 12));
			User user8 = new User("Luis", "luis@domain.com", LocalDate.of(2021, 2, 27));
			User user9 = new User("Paola", "paola@domain.com", LocalDate.of(2022, 4, 10));
			User user10 = new User("user10", "user10@domain.com", LocalDate.of(2012, 4, 10));
			User user11 = new User("user11", "user11@domain.com", LocalDate.of(2019, 4, 10));
			User user12 = new User("user12", "user12@domain.com", LocalDate.of(2020, 4, 10));

			List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12);
			list.forEach(userRepository::save);
		}

		private void ejemplosAnteriores() {
			componentDependency.saludar();
			myBean.print();
			myBeanWithDependency.printWithDependency();
			System.out.println(myBeanWithProperties.function());
			System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword());
			try {
				int value = 10 / 0;
				LOGGER.debug("Mi valor: " + value);
			} catch (Exception e) {
				LOGGER.error("Esto es un error al dividir por cero " + e.getMessage());
			}
		}
}
