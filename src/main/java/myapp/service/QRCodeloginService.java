package myapp.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

@Service
public class QRCodeloginService {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    public byte[] generateQRCode(String userId) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(userId, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("QR 코드 생성 중 오류 발생", e);
        }
    }
}

