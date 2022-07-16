package com.origin.takehomeassignment.calculation.formula

import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.ScoreCalculationDetail
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

internal class VehicleFormulaTest {

    @ParameterizedTest
    @MethodSource("createArguments")
    fun convert(
        riskCalculationRequest: RiskCalculationRequest,
        expectedScoreCalculationDetailList: List<ScoreCalculationDetail>
    ) {
        val vehicleFormula = VehicleFormula()
        val scoreCalculationDetailList = vehicleFormula.execute(riskCalculationRequest)

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
                        MaritalStatusEnum.single,
                        listOf(false, true, false),
                        VehicleInfo(Calendar.getInstance().get(Calendar.YEAR).toLong() - 2)
                    ),
                    listOf(ScoreCalculationDetail(1, 0, 0, 0))
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        1,
                        HouseInfo(OwnershipStatusEnum.mortgaged),
                        0,
                        MaritalStatusEnum.married,
                        listOf(true, true, false),
                        VehicleInfo(Calendar.getInstance().get(Calendar.YEAR).toLong() - 5)
                    ),
                    emptyList<ScoreCalculationDetail>()
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        1,
                        HouseInfo(OwnershipStatusEnum.mortgaged),
                        0,
                        MaritalStatusEnum.married,
                        listOf(true, true, false),
                        VehicleInfo(Calendar.getInstance().get(Calendar.YEAR).toLong() - 6)
                    ),
                    emptyList<ScoreCalculationDetail>()
                )
            )
        }
    }
}
