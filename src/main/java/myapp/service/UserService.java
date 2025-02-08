package myapp.service;

import myapp.entity.UserData;
import myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인 시 호출됨. 전달받은 id를 이용하여 사용자 정보를 조회함.
     */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserData user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));

        return user;  // ✅ UserData 자체를 반환!
    }

    // 회원가입: 입력된 id와 rawPassword를 받아서 저장 (비밀번호 암호화)
    public boolean registerUser(String id, String rawPassword, String role, String username) {
        if (userRepository.findById(id).isPresent()) {
            System.out.println("이미 존재하는 id 또는 username: " + id + ", " + username);
            return false;
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserData newUser = new UserData(id, username, encodedPassword, role);
        userRepository.save(newUser);
        System.out.println("회원가입 성공: " + id);
        return true;
    }
    
    
}
