package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import java.util.*

class VehicleFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val vehicle = riskCalculationRequest.vehicle
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        vehicle?.year?.let { year ->
            val diffYears = currentYear - year
            if (diffYears < 5) {
                return listOf(ScoreCalculationDetail(1, 0, 0, 0))
            }
        }

        return emptyList()
    }
}
