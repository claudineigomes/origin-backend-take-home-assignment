package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class DependentsFormulaTest {

    @Test
    fun shouldReturnRiskBiggerThanZero() {
        val riskCalculationRequest = RiskCalculationRequest(
            29,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, true, false),
            VehicleInfo(2018)
        )
        val dependentsFormula = DependentsFormula()
        val scoreCalculationDetails = dependentsFormula.execute(riskCalculationRequest)
        Assertions.assertFalse(scoreCalculationDetails.isEmpty())
        assertEquals(scoreCalculationDetails, listOf(DependentsFormula.SCORE_DEPENDENTS_BIGGER_THAN_ZERO))
    }

    @Test
    fun shouldReturnRiskEmpty() {
        val riskCalculationRequest = RiskCalculationRequest(
            29,
            0,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, true, false),
            VehicleInfo(2018)
        )
        val dependentsFormula = DependentsFormula()
        val scoreCalculationDetails = dependentsFormula.execute(riskCalculationRequest)
        Assertions.assertTrue(scoreCalculationDetails.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    fun convert(
        riskCalculationRequest: RiskCalculationRequest,
        expectedScoreCalculationDetailList: List<ScoreCalculationDetail>
    ) {
        val dependentsFormula = DependentsFormula()
        val scoreCalculationDetailList = dependentsFormula.execute(riskCalculationRequest)

        assertEquals(expectedScoreCalculationDetailList, scoreCalculationDetailList)
    }

    companion object {
        @JvmStatic
        fun createArguments(): Stream<Arguments> {

            return Stream.of(
                Arguments.of(
                    RiskCalculationRequest(
                        29,
                        0,
                        HouseInfo(OwnershipStatusEnum.owned),
                        0,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    emptyList<ScoreCalculationDetail>()
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        1,
                        HouseInfo(OwnershipStatusEnum.owned),
                        0,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    listOf(ScoreCalculationDetail(0, 1, 0, 1))
                )
            )
        }
    }
}
