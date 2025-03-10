package myapp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class QRCodeloginService {
	
	private static final String QR_CODE_PATH = "src/main/resources/static/qr_codes/";

    public String generateQRCode(String userId) throws WriterException, IOException {
        String qrCodeText = "myapp://login?qr=" + userId;  // QR 코드에 저장될 내용 (유저 ID 포함)
        int width = 200;
        int height = 200;

        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, width, height);
        
        // QR 코드 이미지를 저장할 경로 설정
        String filePath = QR_CODE_PATH + "qr_" + userId + ".png";
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        
        return filePath; // 저장된 QR 코드 이미지 경로 반환
    }
}