package com.origin.takehomeassignment.calculation.calculator

import com.origin.takehomeassignment.calculation.formula.AgeFormula
import com.origin.takehomeassignment.calculation.formula.DependentsFormula
import com.origin.takehomeassignment.calculation.formula.HouseFormula
import com.origin.takehomeassignment.calculation.formula.IncomeFormula
import com.origin.takehomeassignment.calculation.formula.InitializeFormula
import com.origin.takehomeassignment.calculation.formula.MaritalFormula
import com.origin.takehomeassignment.calculation.formula.VehicleFormula
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import org.springframework.stereotype.Component

@Component
class ScoreCalculator() : Calculator {

    override fun calculate(riskCalculationRequest: RiskCalculationRequest): List<ScoreCalculationDetail> {
        return InitializeFormula()
            .then(AgeFormula())
            .then(DependentsFormula())
            .then(HouseFormula())
            .then(IncomeFormula())
            .then(MaritalFormula())
            .then(VehicleFormula())
            .execute(riskCalculationRequest)
    }
}
