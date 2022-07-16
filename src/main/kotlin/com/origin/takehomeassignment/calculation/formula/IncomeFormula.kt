package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

class IncomeFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val income = riskCalculationRequest.income
        if (income > 200000) {
            return listOf(ScoreCalculationDetail(-1, -1, -1, -1))
        }
        return emptyList()
    }
}
