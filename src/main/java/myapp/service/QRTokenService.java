package myapp.service;

import myapp.entity.QRToken;
import myapp.entity.UserData;
import myapp.repository.QRTokenRepository;
import myapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QRTokenService {

    @Autowired
    private QRTokenRepository qrTokenRepository;
    @Autowired
    private UserRepository userRepository;

    // QR 토큰 생성 (UserData와 연결)
    public String createToken(String userId) {
        String token = UUID.randomUUID().toString();
        QRToken qrToken = new QRToken(token, userId);
        qrTokenRepository.save(qrToken);
        return token;
    }

    // 토큰으로 UserData 조회
    public UserData getUserByToken(String token) {
        Optional<QRToken> qrToken = qrTokenRepository.findById(token);
        return qrToken.map(QRToken::getUserId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
