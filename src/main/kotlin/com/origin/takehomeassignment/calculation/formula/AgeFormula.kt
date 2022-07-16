package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

class AgeFormula : ModelFormula() {
    companion object {
        val SCORE_DETAIL_LESS_THIRTY = ScoreCalculationDetail(-2, -2, -2, -2)
        val SCORE_DETAILS_BETWEEN_THIRTY_AND_FORTY = ScoreCalculationDetail(-1, -1, -1, -1)
    }

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val age = riskCalculationRequest.age
        if (age < 30) {
            return listOf(SCORE_DETAIL_LESS_THIRTY)
        } else if (age in 30..40) {
            return listOf(SCORE_DETAILS_BETWEEN_THIRTY_AND_FORTY)
        }
        return emptyList()
    }
}
