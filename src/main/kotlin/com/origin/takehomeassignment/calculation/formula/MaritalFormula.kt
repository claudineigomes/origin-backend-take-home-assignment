package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.enum.MaritalStatusEnum

class MaritalFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        return when (riskCalculationRequest.marital_status) {
            MaritalStatusEnum.single -> emptyList()
            MaritalStatusEnum.married -> listOf(ScoreCalculationDetail(0, -1, 0, 1))
        }
    }
}
