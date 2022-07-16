package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

class DependentsFormula : ModelFormula() {

    companion object {
        val SCORE_DEPENDENTS_BIGGER_THAN_ZERO = ScoreCalculationDetail(0, 1, 0, 1)
    }

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val dependents = riskCalculationRequest.dependents
        if (dependents > 0) {
            return listOf(SCORE_DEPENDENTS_BIGGER_THAN_ZERO)
        }
        return emptyList()
    }
}
