package videoeditor.com.ivanov.tech.videoeditor

import android.os.Bundle
import android.util.Log

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

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
            } else {
                Log.d("Igor", "MethodChannel notImplemented")
                result.notImplemented()
            }
        }
    }
}
