package com.tpe.student_management;

import com.tpe.student_management.entity.classes.user.UserRole;
import com.tpe.student_management.entity.enums.Gender;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.payload.request.user.UserRequestDTO;
import com.tpe.student_management.service.user.UserRoleService;
import com.tpe.student_management.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class StudentManagementApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final UserService userService;

    public StudentManagementApplication(UserRoleService userRoleService, UserService userService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
    }

    public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//SpringApplication.run calistiginad, once bu metod calisir, sonra uygulama baslar.
		if (userRoleService.getAllUserRoles().isEmpty()){
			UserRole admin = new UserRole();
			admin.setRole(Role.ADMIN);
			admin.setRoleName("Admin");

			userRoleService.saveRole(admin);

			UserRole manager = new UserRole();
			manager.setRole(Role.MANAGER);
			manager.setRoleName("Dean");

			userRoleService.saveRole(manager);

			UserRole assistantManager = new UserRole();
			assistantManager.setRole(Role.ASSISTANT_MANAGER);
			assistantManager.setRoleName("ViceDean");

			userRoleService.saveRole(assistantManager);

			UserRole teacher = new UserRole();
			teacher.setRole(Role.TEACHER);
			teacher.setRoleName("Teacher");

			userRoleService.saveRole(teacher);

			UserRole student = new UserRole();
			student.setRole(Role.STUDENT);
			student.setRoleName("Student");

			userRoleService.saveRole(student);

			if (userService.countAllAdmins() == 0){
				UserRequestDTO adminCreateDTO = new UserRequestDTO();
				adminCreateDTO.setUsername("Admin");
				adminCreateDTO.setEmail("admin@admin.com");
				adminCreateDTO.setSsn("111-11-1111");
				adminCreateDTO.setPassword("12345678");
				adminCreateDTO.setFirstName("admin");
				adminCreateDTO.setLastName("admin");
				adminCreateDTO.setPhoneNumber("111-111-1111");
				adminCreateDTO.setGender(Gender.FEMALE);
				adminCreateDTO.setDateOfBirth(LocalDate.of(1980,2,2));
				adminCreateDTO.setBirthPlace("Istanbul");

				userService.saveUser(adminCreateDTO, "Admin");
			}
		}
	}
}
