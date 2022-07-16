package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail

class InitializeFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val initializeScores = riskCalculationRequest.risk_questions.sumOf { it.compareTo(false) }
        return listOf(ScoreCalculationDetail(initializeScores, initializeScores, initializeScores, initializeScores))
    }
}
