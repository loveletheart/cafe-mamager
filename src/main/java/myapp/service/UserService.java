package myapp.service;

import myapp.entity.UserData;
import myapp.repository.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private QRCodeloginService qrCodeloginService;

    // QR 코드 이미지 크기 설정
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    // 로그인 시 호출됨. 전달받은 id를 이용하여 사용자 정보를 조회함.
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    // 회원가입: 입력된 id와 rawPassword를 받아서 저장 (비밀번호 암호화)
    public boolean registerUser(String id, String rawPassword, String role, String username) {
        if (userRepository.findById(id).isPresent()) {
            System.out.println("이미 존재하는 id 또는 username: " + id + ", " + username);
            return false;
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        // QR 코드 생성
        String qrCodePath = "";
        try {
            qrCodePath = qrCodeloginService.generateQRCode(id);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return false; // QR 코드 생성 실패 시 회원가입 중단
        }
        
        // 새 사용자 생성 및 저장
        UserData newUser = new UserData(id, username, encodedPassword, role, qrCodePath);
        userRepository.save(newUser);
        
        System.out.println("회원가입 성공: " + id);
        return true;
    }

    // QR 코드 생성 (Base64 문자열 반환)
    private String generateQRCode(String userId) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(userId, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            byte[] qrImage = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(qrImage);
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 생성 중 오류 발생", e);
        }
    }

    // QR 코드로 사용자 조회
    public Optional<UserData> getUserByQRCode(String qrCode) {
        return userRepository.findByQrCode(qrCode);
    }
}
