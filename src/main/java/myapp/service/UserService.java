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
        // 이제 id를 기준으로 사용자 조회
        Optional<UserData> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + id);
        }
        UserData user = userOptional.get();
        System.out.println("인증된 사용자 id: " + user.getId());
        System.out.println("암호화된 비밀번호: " + user.getPassword());
        
        // Spring Security의 UserDetails 객체 반환
        return User.builder()
                   .username(user.getId())
                   .password(user.getPassword())  // 암호화된 비밀번호 사용
                   .roles("USER")
                   .build();
    }

    /**
     * 회원가입 시, id와 비밀번호를 받아서 사용자 등록
     */
    public boolean registerUser(String id, String rawPassword) {
        // id 중복 체크
        if (userRepository.findById(id).isPresent()) {
            System.out.println("❌ 이미 존재하는 id: " + id);
            return false;
        }
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);
        UserData newUser = new UserData(id, encodedPassword);
        userRepository.save(newUser);
        System.out.println("회원가입 성공: " + id);
        return true;
    }
    
    /**
     * 로그인 검증 (필요 시 추가)
     */
    public boolean validateUser(String id, String rawPassword) {
        Optional<UserData> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            System.out.println("로그인 실패: id 없음 (" + id + ")");
            return false;
        }
        UserData user = userOptional.get();
        boolean isMatch = passwordEncoder.matches(rawPassword, user.getPassword());
        if (isMatch) {
            System.out.println("로그인 성공: " + id);
        } else {
            System.out.println("로그인 실패: 비밀번호 불일치");
        }
        return isMatch;
    }
}
