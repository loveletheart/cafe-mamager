package myapp.repository;

import myapp.entity.QRToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRTokenRepository extends JpaRepository<QRToken, String> {}
