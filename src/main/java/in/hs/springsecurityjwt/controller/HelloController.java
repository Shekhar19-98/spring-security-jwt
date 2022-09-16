package in.hs.springsecurityjwt.controller;

import in.hs.springsecurityjwt.domain.Request;
import in.hs.springsecurityjwt.domain.Response;
import in.hs.springsecurityjwt.service.MyUserDetailsService;
import in.hs.springsecurityjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.management.remote.JMXAuthenticator;

@RestController
public class HelloController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello World";
    }

    @PostMapping(value = "/authenticate", consumes = "application/json")
    public ResponseEntity<?> authenticateUser(@RequestBody Request request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        }
        catch (BadCredentialsException exception){
            throw new Exception("Incorrect Username or Password", exception);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new Response(jwtToken));

    }
}
