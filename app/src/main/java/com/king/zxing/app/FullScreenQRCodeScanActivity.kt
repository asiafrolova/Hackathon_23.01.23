package com.king.zxing.app

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.google.zxing.Result
import com.king.camera.scan.AnalyzeResult
import com.king.camera.scan.CameraScan
import com.king.camera.scan.analyze.Analyzer
import com.king.camera.scan.util.PointUtils
import com.king.view.viewfinderview.ViewfinderView.ViewfinderStyle
import com.king.zxing.DecodeConfig
import com.king.zxing.DecodeFormatManager
import com.king.zxing.BarcodeCameraScanActivity
import com.king.zxing.analyze.QRCodeAnalyzer


class FullScreenQRCodeScanActivity : BarcodeCameraScanActivity() {

    override fun initUI() {
        super.initUI()


        viewfinderView.setViewfinderStyle(ViewfinderStyle.POPULAR)

    }


    override fun initCameraScan(cameraScan: CameraScan<Result>) {
        super.initCameraScan(cameraScan)

        cameraScan.setPlayBeep(true)
    }

    override fun createAnalyzer(): Analyzer<Result>? {

        val decodeConfig = DecodeConfig().apply {

            hints = DecodeFormatManager.QR_CODE_HINTS

            isFullAreaScan = true
        }

        return QRCodeAnalyzer(decodeConfig)
    }


    override fun getLayoutId(): Int {
        return super.getLayoutId()
    }

    override fun onScanResultCallback(result: AnalyzeResult<Result>) {

        cameraScan.setAnalyzeImage(false)

        displayResultPoint(result)


        val intent = Intent()
        intent.putExtra(CameraScan.SCAN_RESULT, result.result.text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


    private fun displayResultPoint(result: AnalyzeResult<Result>) {
        var width = result.bitmapWidth
        var height = result.bitmapHeight

        val resultPoints = result.result.resultPoints
        val size = resultPoints.size
        if (size > 0) {
            var x = 0f
            var y = 0f
            resultPoints.forEach {
                x += it.x
                y += it.y
            }
            var centerX = x / size
            var centerY = y / size

            val point = PointUtils.transform(
                centerX.toInt(),
                centerY.toInt(),
                width,
                height,
                viewfinderView.width,
                viewfinderView.height
            )

            viewfinderView.showResultPoints(listOf(point))
        }
    }

}