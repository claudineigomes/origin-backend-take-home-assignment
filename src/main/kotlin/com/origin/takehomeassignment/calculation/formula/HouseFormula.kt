package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.enum.OwnershipStatusEnum

class HouseFormula : ModelFormula() {

    override fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val house = riskCalculationRequest.house
        return when (house?.ownership_status) {
            OwnershipStatusEnum.owned -> emptyList()
            OwnershipStatusEnum.mortgaged -> listOf(ScoreCalculationDetail(0, 1, 1, 0))
            else -> emptyList()
        }
    }
}
