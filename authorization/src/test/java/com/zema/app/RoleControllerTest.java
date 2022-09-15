package com.zema.app;

import com.zema.app.role.Role;
import com.zema.app.role.RoleRepository;
import com.zema.commons.reponses.roles.RoleVM;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;



import com.zema.commons.BasePath;
import com.zema.commons.reponses.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoleControllerTest {



    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    public void testCreateRoleWithValidData() {
        Role role= makeRole();


        var response=testRestTemplate.exchange(BasePath.ROLE_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(role), new ParameterizedTypeReference<HttpResponse<RoleVM>>() {
        });

        Role newRole=roleRepository.findByName(role.getName());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(newRole.getId()).isEqualTo(1);
    }


    private Role makeRole(){
        Role role = new Role();
        role.setName("role name 1");
        role.setDescription("lorem ipsum");
        return role;
    }

    private Role makeDynamicallyCreateRole(String name, String description){
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }

    private Role CreateRole(){
        Role role = makeRole();
        return roleRepository.save(role);
    }

    private Role CreateDynamicallyRole(String name, String description){
        Role role = makeDynamicallyCreateRole(name, description);
        return roleRepository.save(role);
    }

}


