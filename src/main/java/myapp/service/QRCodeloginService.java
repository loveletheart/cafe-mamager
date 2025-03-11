package myapp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeloginService {

    private static final String QR_CODE_PATH = "src/main/resources/static/qr_codes/";
    
    //qr코드 생성 기
    public String generateQRCode(String userId) {
        try {
            String qrFileName = userId + "_qr.png"; // 파일명 설정
            String filePath = QR_CODE_PATH + qrFileName;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix bitMatrix = qrCodeWriter.encode(userId, BarcodeFormat.QR_CODE, 200, 200, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return "/qr_codes/" + qrFileName; // DB에는 상대 경로 저장
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
