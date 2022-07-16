package com.origin.takehomeassignment.calculation

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.enum.RiskCalculationEnum
import org.springframework.stereotype.Component

@Component
class IneligibleUtils {

    fun verifyIneligible(
        riskCalculationRequest: RiskCalculationRequest
    ): RiskCalculationResponse {
        val riskCalculationResponse = RiskCalculationResponse()
        val income = riskCalculationRequest.income
        val house = riskCalculationRequest.house
        val vehicle = riskCalculationRequest.vehicle
        val age = riskCalculationRequest.age

        if (income == 0)
            riskCalculationResponse.disability = RiskCalculationEnum.ineligible

        if (vehicle == null)
            riskCalculationResponse.auto = RiskCalculationEnum.ineligible

        if (house == null)
            riskCalculationResponse.home = RiskCalculationEnum.ineligible

        if (age > 60) {
            riskCalculationResponse.disability = RiskCalculationEnum.ineligible
            riskCalculationResponse.life = RiskCalculationEnum.ineligible
        }

        return riskCalculationResponse
    }
}
