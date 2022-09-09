package com.zema.user;


import com.zema.commons.BasePath;
import com.zema.commons.exceptions.ErrorDetails;
import com.zema.commons.reponses.HttpResponse;
import com.zema.user.dto.UserCreateDto;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    User user;


    @BeforeEach
    public void setUp() {
        //testRestTemplate.getRestTemplate().getInterceptors().clear();
        //set header accept-language to en
        BasicHeader header = new BasicHeader("Accept-Language", "en");
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(List.of(header)).build();
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        //create user
        user = new User();
        user.setEmail("jhonDoe@gmail.com");
        user.setUsername("Jhon");
        user.setPassword("PoissonRouge2022");

        userRepository.deleteAll();
    }


    @Test
    public void test_create_user_with_valid_data() {
        UserCreateDto userCreateDto = makeUser();
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<HttpResponse<UserVM>>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getData().getEmail()).isEqualTo(userCreateDto.getEmail());
    }

    @Test
    public void test_create_user_with_invalid_email_address() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setEmail("jhonDoe@gmail");
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<HttpResponse<ErrorDetails>>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        userCreateDto.setEmail("jhonDoe.gmail.com");
        var response2 = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_create_user_with_null_email_address() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setEmail(null);
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).contains("email");
    }

    @Test
    public void test_create_user_with_null_username() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setUsername(null);
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).contains("username");
    }

    @Test
    public void test_create_user_with_size_less_than_2() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setUsername("J");
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).contains("username");
    }

    @Test
    public void test_create_user_with_size_greater_than_25() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setUsername("Jhon Doe geovani machado de souza jhon Doe geovani machado de souza");
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).contains("username");
    }

    @Test
    public void test_create_user_with_unmatched_password() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setConfirmPassword("poissonRouge2022!");
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_constraint_error_on_create_user_with_unmatched_password() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setConfirmPassword("poissonRouge2022!");
        //headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "en");
        //send request
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto, headers), new ParameterizedTypeReference<ErrorDetails>() {
        });
        var errorMessage = "password and confirm password must match";
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).isEqualTo(errorMessage);
    }

    @Test
    public void test_constraint_error_on_create_user_with_unmatched_password_es() {
        UserCreateDto userCreateDto = makeUser();
        userCreateDto.setConfirmPassword("poissonRouge2022!");
        var errorMessageEs = "las contraseñas no coinciden";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Language", "es");
        var response = testRestTemplate.exchange(BasePath.USER_CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(userCreateDto, headers), new ParameterizedTypeReference<ErrorDetails>() {
        });
        assertThat(Objects.requireNonNull(response.getBody()).getErrors().get(0)).isEqualTo(errorMessageEs);
    }


    private UserCreateDto makeUser() {
        UserCreateDto userCreateDto = modelMapper.map(user, UserCreateDto.class);
        userCreateDto.setConfirmPassword(user.getPassword());
        return userCreateDto;
    }
}
