package oran.myapp.reservation.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.patient;

public class QrCodeActivity extends AppCompatActivity {

    private ImageView QrCodeImage;
    public static final int QRCodeWidth = 500;
    private int color_black, color_white;

    // User Data
    private splashScreen inst = splashScreen.getInst ( );
    private patient userData = inst.GetUserData ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_qr_code );
        QrCodeImage = findViewById(R.id.QrCodeClient);
        color_black = getResources().getColor(R.color.black);
        color_white = getResources().getColor(R.color.white);
        ObjectToImageEnCode(userData.getUid ());
    }




    private Bitmap ObjectToImageEnCode(Object value) {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter ().encode(value.toString(),
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);

        } catch (IllegalArgumentException | WriterException e) {
            return null;

        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? color_black : color_white;

            }


        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        QrCodeImage.setImageBitmap(bitmap);
        QrCodeImage.setVisibility( View.VISIBLE);

        return bitmap;

    }
}