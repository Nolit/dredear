package net.nolit.dredear.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class TopController {
    @GetMapping
    fun index(): String {
        println("TopController!!")
        return "index"
    }
}