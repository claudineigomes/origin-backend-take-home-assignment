package com.origin.takehomeassignment.domain

import com.origin.takehomeassignment.enum.RiskCalculationEnum

data class RiskCalculationResponse(
    var auto: RiskCalculationEnum? = RiskCalculationEnum.eligible,
    var disability: RiskCalculationEnum? = RiskCalculationEnum.eligible,
    var home: RiskCalculationEnum? = RiskCalculationEnum.eligible,
    var life: RiskCalculationEnum? = RiskCalculationEnum.eligible
)
