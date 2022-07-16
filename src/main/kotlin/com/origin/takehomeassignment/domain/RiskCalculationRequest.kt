package com.origin.takehomeassignment.domain

import com.origin.takehomeassignment.enum.MaritalStatusEnum

data class RiskCalculationRequest(
    val age: Int,
    val dependents: Int,
    val house: HouseInfo?,
    val income: Int,
    val marital_status: MaritalStatusEnum,
    val risk_questions: List<Boolean>,
    val vehicle: VehicleInfo?,
)
