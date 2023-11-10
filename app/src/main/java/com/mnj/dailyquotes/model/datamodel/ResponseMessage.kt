package com.mnj.dailyquotes.model.datamodel

import android.annotation.SuppressLint

class ResponseMessage(code: Int, s: String) {
    private var status = 0
    private var detail: String? = null
}