package com.petitsraids.yunbiandialer.support

import android.content.Context

object MyUtils {
    const val TAG = "YunBianDialer"

    const val REQUEST_CODE_CALL = 0

    const val SHARED_NAME = "font_size"
    const val SHARED_KEY = "font_size_key"
    const val FONT_SIZE_SMALL = 1
    const val FONT_SIZE_MEDIUM = 2
    const val FONT_SIZE_LARGE = 3
    const val FONT_SIZE_XLARGE = 4
    const val FONT_SIZE_XXLARGE = 5

    fun dp2px(context: Context, value: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (scale * value + 0.5f).toInt()
    }

    fun theme2dp(theme: Int): Float {
        var dpSize = 0
        when (theme) {
            FONT_SIZE_SMALL -> dpSize = 36
            FONT_SIZE_MEDIUM -> dpSize = 41
            FONT_SIZE_LARGE -> dpSize = 46
            FONT_SIZE_XLARGE -> dpSize = 51
            FONT_SIZE_XXLARGE -> dpSize = 56
        }
        return dpSize.toFloat()
    }

    fun theme2sp(theme: Int): Float {
        var dpSize = 0
        when (theme) {
            FONT_SIZE_SMALL -> dpSize = 18
            FONT_SIZE_MEDIUM -> dpSize = 22
            FONT_SIZE_LARGE -> dpSize = 26
            FONT_SIZE_XLARGE -> dpSize = 30
            FONT_SIZE_XXLARGE -> dpSize = 34
        }
        return dpSize.toFloat()
    }
}
