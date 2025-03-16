package myapp.service;

import myapp.entity.QRToken;
import myapp.entity.UserData;
import myapp.repository.QRTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QRTokenService {

    @Autowired
    private QRTokenRepository qrTokenRepository;

    // QR 토큰 생성 (UserData와 연결)
    public String createToken(UserData user) {
        String token = UUID.randomUUID().toString(); // UUID 기반 토큰 생성
        QRToken qrToken = new QRToken(token, user); // UserData와 연결
        qrTokenRepository.save(qrToken);
        return token;
    }

    // 토큰으로 UserData 조회
    public UserData getUserByToken(String token) {
        Optional<QRToken> qrToken = qrTokenRepository.findById(token);
        return qrToken.map(QRToken::getUser).orElse(null);
    }
}
