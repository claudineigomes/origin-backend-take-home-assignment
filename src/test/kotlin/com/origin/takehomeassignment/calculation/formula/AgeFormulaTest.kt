package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class AgeFormulaTest {

    @Test
    fun shouldReturnRiskThirty() {
        val riskCalculationRequest = RiskCalculationRequest(
            29,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, true, false),
            VehicleInfo(2018)
        )
        val ageFormula = AgeFormula()
        val scoreCalculationDetails = ageFormula.execute(riskCalculationRequest)
        assertFalse(scoreCalculationDetails.isEmpty())
        assertEquals(scoreCalculationDetails, listOf(AgeFormula.SCORE_DETAIL_LESS_THIRTY))
    }

    @Test
    fun shouldReturnRiskBetweenThirtyAndForty() {
        val riskCalculationRequest = RiskCalculationRequest(
            35,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, true, false),
            VehicleInfo(2018)
        )
        val ageFormula = AgeFormula()
        val scoreCalculationDetails = ageFormula.execute(riskCalculationRequest)
        assertFalse(scoreCalculationDetails.isEmpty())
        assertEquals(scoreCalculationDetails, listOf(AgeFormula.SCORE_DETAILS_BETWEEN_THIRTY_AND_FORTY))
    }

    @Test
    fun shouldReturnEmptyRisk() {
        val riskCalculationRequest = RiskCalculationRequest(
            50,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, true, false),
            VehicleInfo(2018)
        )
        val ageFormula = AgeFormula()
        val scoreCalculationDetails = ageFormula.execute(riskCalculationRequest)
        assertTrue(scoreCalculationDetails.isEmpty())
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    fun convert(
        riskCalculationRequest: RiskCalculationRequest,
        expectedScoreCalculationDetailList: List<ScoreCalculationDetail>
    ) {
        val ageFormula = AgeFormula()
        val scoreCalculationDetailList = ageFormula.execute(riskCalculationRequest)

        assertEquals(expectedScoreCalculationDetailList, scoreCalculationDetailList)
    }

    companion object {
        @JvmStatic
        fun createArguments(): Stream<Arguments> {

            return Stream.of(
                Arguments.of(
                    RiskCalculationRequest(
                        29,
                        2,
                        HouseInfo(OwnershipStatusEnum.owned),
                        0,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    listOf(ScoreCalculationDetail(-2, -2, -2, -2))
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        2,
                        HouseInfo(OwnershipStatusEnum.owned),
                        0,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    listOf(ScoreCalculationDetail(-1, -1, -1, -1))
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        41,
                        2,
                        HouseInfo(OwnershipStatusEnum.owned),
                        0,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    emptyList<ScoreCalculationDetail>()
                )
            )
        }
    }
}
