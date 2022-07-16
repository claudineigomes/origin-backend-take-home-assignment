package com.origin.takehomeassignment.enum

enum class RiskCalculationEnum {
    eligible, ineligible, economic, regular, responsible;

    companion object {
        fun fromScore(score: Int): RiskCalculationEnum {
            return when {
                score <= 0 -> {
                    economic
                }
                score in 1..2 -> {
                    regular
                }
                else -> {
                    responsible
                }
            }
        }

        fun isIneligible(riskCalculationEnum: RiskCalculationEnum): Boolean {
            return riskCalculationEnum == ineligible
        }
    }
}
