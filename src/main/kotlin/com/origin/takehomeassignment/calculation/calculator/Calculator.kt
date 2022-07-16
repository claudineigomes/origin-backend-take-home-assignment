package com.origin.takehomeassignment.calculation.calculator

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

interface Calculator {

    fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
    ): List<ScoreCalculationDetail>
}
