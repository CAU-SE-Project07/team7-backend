package com.SEProject.SEP.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public SiteUser create(String userid, String email,String password, String role){
        SiteUser user=new SiteUser();
        user.setUserid(userid);
        user.setEmail(email);
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //비밀번호 암호화해서 저장
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        this.userRepository.save(user);
        return user;
    }
}
