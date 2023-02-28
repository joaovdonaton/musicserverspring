package br.pucpr.musicserverspring.rest.users;

import br.pucpr.musicserverspring.lib.security.JWT;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
    private JWT jwt;

    public UsersController(JWT jwt) {
        this.jwt = jwt;
    }

    @PostMapping("/test")
    public ResponseEntity<TestUserDTO> getTestUser(@Valid @RequestBody TestUserDTO credentials){
        var token = jwt.createToken(credentials.getToken());
        return token == null ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).build() :
                ResponseEntity.ok(new TestUserDTO(token));
    }
}
