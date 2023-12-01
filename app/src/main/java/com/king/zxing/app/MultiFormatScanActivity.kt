package com.king.zxing.app

import android.widget.Toast
import com.google.zxing.Result
import com.king.camera.scan.AnalyzeResult
import com.king.camera.scan.CameraScan
import com.king.camera.scan.analyze.Analyzer
import com.king.zxing.DecodeConfig
import com.king.zxing.BarcodeCameraScanActivity
import com.king.zxing.analyze.MultiFormatAnalyzer


class MultiFormatScanActivity : BarcodeCameraScanActivity() {

    var toast: Toast? = null

    override fun initCameraScan(cameraScan: CameraScan<Result>) {
        super.initCameraScan(cameraScan)

        cameraScan.setPlayBeep(true)
    }

    override fun createAnalyzer(): Analyzer<Result>? {

        val decodeConfig = DecodeConfig().apply {

            isSupportVerticalCode = true

            isSupportLuminanceInvert = true
        }

        return MultiFormatAnalyzer(decodeConfig)
    }


    override fun getLayoutId(): Int {
        return super.getLayoutId()
    }

    override fun onScanResultCallback(result: AnalyzeResult<Result>) {

        cameraScan.setAnalyzeImage(false)

        showToast(result.result.text)

        cameraScan.setAnalyzeImage(true)
    }

    private fun showToast(text: String) {
        if(toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(text)
        }
        toast?.show()
    }
}