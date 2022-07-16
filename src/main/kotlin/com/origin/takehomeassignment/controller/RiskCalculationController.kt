package com.origin.takehomeassignment.controller

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.service.RiskCalculationService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/risk")
class RiskCalculationController(private val riskCalculationService: RiskCalculationService) {
    private val logger = LoggerFactory.getLogger(RiskCalculationController::class.java)

    @GetMapping(value = ["/calculate"], produces = ["application/json"])
    fun get(@RequestBody @Valid riskCalculationRequest: RiskCalculationRequest):
        ResponseEntity<RiskCalculationResponse>? {
        logger.info(riskCalculationRequest.toString())
        val riskCalculationResponse = riskCalculationService.calculate(riskCalculationRequest)
        return ResponseEntity.ok(riskCalculationResponse)
    }

    @PostMapping(value = ["/calculate"], produces = ["application/json"])
    fun post(@RequestBody @Valid riskCalculationRequest: RiskCalculationRequest):
        ResponseEntity<RiskCalculationResponse>? {
        logger.info(riskCalculationRequest.toString())
        val riskCalculationResponse = riskCalculationService.calculate(riskCalculationRequest)
        return ResponseEntity.ok(riskCalculationResponse)
    }
}
