package myapp.service;

import myapp.entity.UserData;
import myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private QRCodeloginService qrCodeloginService;

    // 로그인 시 호출됨. 전달받은 id를 이용하여 사용자 정보를 조회함.
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    	System.out.println("서비스에서 받은 ID 값: " + id);  // 받은 ID 출력
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    // 회원가입: 입력된 id와 rawPassword를 받아서 저장 (비밀번호 암호화)
    public boolean registerUser(String id, String password, String username, String role) {
        if (userRepository.findById(id).isPresent()) {
            return false; // 이미 존재하는 ID
        }

        String encodedPassword = passwordEncoder.encode(password);
        String qrCodePath = qrCodeloginService.generateQRCode(id); // QR 코드 생성

        UserData newUser = new UserData(id, username, encodedPassword, role, qrCodePath);
        userRepository.save(newUser);
        return true;
    }

    // QR 코드로 사용자 조회
    public Optional<UserData> getUserByQRCode(String qrCode) {
        return userRepository.findByQrCode(qrCode);
    }
}
