package videoeditor.com.ivanov.tech.videoeditor

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import com.video_trim.K4LVideoTrimmer
import com.video_trim.interfaces.OnK4LVideoListener
import com.video_trim.interfaces.OnTrimVideoListener
import com.video_trim.utils.TrimVideoUtils
import java.io.File


class MainActivity : FlutterActivity() {

    companion object {
        const val CHANNEL = "flutter.rortega.com.channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GeneratedPluginRegistrant.registerWith(this)

        MethodChannel(flutterView, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "showNativeView") {

                val json: String? = call.argument("json")
                result.success("recieved in Android")
                Log.d("Igor", "MethodChannel showNativeView json=$json")
            } else if (call.method == "video_trim") {
                val path: String? = call.argument("path")
                Log.d("Igor", "MethodChannel video_trim source path=$path")

                val result_path: String? = trimVideo(path)

                Log.d("Igor", "MethodChannel video_trim result path=$result_path")
                result.success(result_path)
            } else {
                Log.d("Igor", "MethodChannel notImplemented")
                result.notImplemented()
            }
        }
    }

    fun trimVideo(path: String?): String? {

        val file = File(path)
        var result_path: String? = null
        TrimVideoUtils.startTrim(
                file,
                getDestinationPath(),
                1000,
                5000,
                object : OnTrimVideoListener {
                    override fun onTrimStarted() {
                        Log.d("Igor", "onTrimStarted")
                    }

                    override fun getResult(uri: Uri?) {
                        Log.d("Igor", "getResult uri=$uri")
                        result_path = uri?.path
                    }

                    override fun onError(message: String?) {
                        Log.d("Igor", "onError message=$message")
                    }

                    override fun cancelAction() {
                        Log.d("Igor", "cancelAction")
                    }

                }

        )


        return result_path
    }

    private fun getDestinationPath(): String {

        val folder = Environment.getExternalStorageDirectory()
        val mFinalPath = folder.path + File.separator
        Log.d("Igor", "getDestinationPath path=$mFinalPath")

        return mFinalPath
    }
}
