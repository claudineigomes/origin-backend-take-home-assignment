package com.origin.takehomeassignment.service

import com.origin.takehomeassignment.calculation.IneligibleUtils
import com.origin.takehomeassignment.calculation.calculator.ScoreCalculator
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.enum.RiskCalculationEnum
import org.springframework.stereotype.Component

@Component
class RiskCalculationService(
    private val ineligibleUtils: IneligibleUtils,
    private val scoreCalculator: ScoreCalculator
) {

    fun calculate(riskCalculationRequest: RiskCalculationRequest): RiskCalculationResponse {
        val riskCalculationResponse = ineligibleUtils.verifyIneligible(riskCalculationRequest)
        val scoreCalculationDetail = scoreCalculator.calculate(riskCalculationRequest)
        generateRiskCalculationResponseForEligible(riskCalculationResponse, scoreCalculationDetail)
        return riskCalculationResponse
    }

    private fun generateRiskCalculationResponseForEligible(
        riskCalculationResponse: RiskCalculationResponse,
        scoreCalculationDetailList: List<ScoreCalculationDetail>
    ) {
        val summarizeRisks = summarizeRisks(scoreCalculationDetailList)
        riskCalculationResponse.auto?.let { autoRisk ->
            takeIf { !RiskCalculationEnum.isIneligible(autoRisk) }?.let {
                riskCalculationResponse.auto = summarizeRisks.auto
            }
        }
        riskCalculationResponse.home?.let { homeRisk ->
            takeIf { !RiskCalculationEnum.isIneligible(homeRisk) }?.let {
                riskCalculationResponse.home = summarizeRisks.home
            }
        }
        riskCalculationResponse.disability?.let { disabilityRisk ->
            takeIf { !RiskCalculationEnum.isIneligible(disabilityRisk) }?.let {
                riskCalculationResponse.disability = summarizeRisks.disability
            }
        }
        riskCalculationResponse.life?.let { lifeRisk ->
            takeIf { !RiskCalculationEnum.isIneligible(lifeRisk) }?.let {
                riskCalculationResponse.life = summarizeRisks.life
            }
        }
    }

    private fun summarizeRisks(scoreCalculationDetailList: List<ScoreCalculationDetail>): RiskCalculationResponse {
        val autoRisk = RiskCalculationEnum.fromScore(scoreCalculationDetailList.sumOf { it.autoScoreValue })
        val disabilityRisk =
            RiskCalculationEnum.fromScore(scoreCalculationDetailList.sumOf { it.disabilityScoreValue })
        val homeRisk = RiskCalculationEnum.fromScore(scoreCalculationDetailList.sumOf { it.homeScoreValue })
        val lifeRisk = RiskCalculationEnum.fromScore(scoreCalculationDetailList.sumOf { it.lifeScoreValue })
        return RiskCalculationResponse(autoRisk, disabilityRisk, homeRisk, lifeRisk)
    }
}
