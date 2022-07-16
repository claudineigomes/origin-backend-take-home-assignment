package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class ModelFormula {

    protected val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun execute(riskCalculationRequest: RiskCalculationRequest): List<ScoreCalculationDetail> {
        return execute(riskCalculationRequest, emptyList())
    }

    private fun execute(
        riskCalculationRequest: RiskCalculationRequest,
        beforeCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail> {
        val thisCalculationResult = calculate(riskCalculationRequest, beforeCalculations).filterNotNull()
        val nextCalculations =
            this.nextFormula?.execute(riskCalculationRequest, beforeCalculations + thisCalculationResult)
                ?: emptyList()
        return thisCalculationResult + nextCalculations
    }

    protected abstract fun calculate(
        riskCalculationRequest: RiskCalculationRequest,
        otherCalculations: List<ScoreCalculationDetail>
    ): List<ScoreCalculationDetail?>

    private var nextFormula: ModelFormula? = null

    fun then(newNextFormula: ModelFormula): ModelFormula {
        this.nextFormula?.then(newNextFormula) ?: setNextFormula(newNextFormula)
        return this
    }

    private fun setNextFormula(newNextFormula: ModelFormula) {
        this.nextFormula = newNextFormula
    }
}
