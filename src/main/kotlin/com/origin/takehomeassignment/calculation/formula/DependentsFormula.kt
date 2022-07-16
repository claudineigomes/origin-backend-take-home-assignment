package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

class DependentsFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val dependents = riskCalculationRequest.dependents
        if (dependents > 0) {
            return listOf(ScoreCalculationDetail(0, 1, 0, 1))
        }
        return emptyList()
    }
}
