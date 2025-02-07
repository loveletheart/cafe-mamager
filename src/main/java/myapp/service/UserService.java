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

        Optional<UserData> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id);
        }
        UserData user = userOptional.get();

        System.out.println("DB에서 조회된 사용자: " + user.getId());
        System.out.println("DB에 저장된 암호화된 비밀번호: " + user.getPassword());

        return User.builder()
                   .username(user.getId())
                   .password(user.getPassword())
                   .roles("USER")
                   .build();
    }

    // 회원가입: 입력된 id와 rawPassword를 받아서 저장 (비밀번호 암호화)
    public boolean registerUser(String id, String rawPassword, String username, String role) {
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
