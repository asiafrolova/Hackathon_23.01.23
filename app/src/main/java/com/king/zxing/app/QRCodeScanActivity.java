package com.king.zxing.app;

import android.app.Activity;
import android.content.Intent;

import com.google.zxing.Result;
import com.king.camera.scan.AnalyzeResult;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.analyze.Analyzer;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import com.king.zxing.BarcodeCameraScanActivity;
import com.king.zxing.analyze.QRCodeAnalyzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class QRCodeScanActivity extends BarcodeCameraScanActivity {

    @Override
    public void initCameraScan(@NonNull CameraScan<Result> cameraScan) {
        super.initCameraScan(cameraScan);

        cameraScan.setPlayBeep(true);
    }

    @Nullable
    @Override
    public Analyzer<Result> createAnalyzer() {

        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.QR_CODE_HINTS)
                .setFullAreaScan(false)
                .setAreaRectRatio(0.8f)
                .setAreaRectVerticalOffset(0)
                .setAreaRectHorizontalOffset(0);

        return new QRCodeAnalyzer(decodeConfig);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    public void onScanResultCallback(@NonNull AnalyzeResult<Result> result) {

        getCameraScan().setAnalyzeImage(false);

        Intent intent = new Intent();
        intent.putExtra(CameraScan.SCAN_RESULT, result.getResult().getText());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
