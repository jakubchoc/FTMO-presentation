package com.myapp.presentation.service

import org.springframework.stereotype.Service

@Service
class DummyService {

    fun getValue() : String {
        return "hi"
    }
}