package com.origin.takehomeassignment.calculation

import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import com.origin.takehomeassignment.enum.RiskCalculationEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class IneligibleUtilsTest {

    @ParameterizedTest
    @MethodSource("createArguments")
    fun convert(
        riskCalculationRequest: RiskCalculationRequest,
        expectedRiskCalculationResponse: RiskCalculationResponse
    ) {
        val ineligibleUtils = IneligibleUtils()
        val riskCalculationResponse = ineligibleUtils.verifyIneligible(riskCalculationRequest)

        assertEquals(expectedRiskCalculationResponse, riskCalculationResponse)
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
                    RiskCalculationResponse(
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.eligible
                    )
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        1,
                        HouseInfo(OwnershipStatusEnum.mortgaged),
                        10,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        null
                    ),
                    RiskCalculationResponse(
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.eligible
                    )
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        30,
                        1,
                        null,
                        10,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2018)
                    ),
                    RiskCalculationResponse(
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.eligible
                    )
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        61,
                        1,
                        HouseInfo(OwnershipStatusEnum.mortgaged),
                        10,
                        MaritalStatusEnum.married,
                        listOf(false, true, false),
                        VehicleInfo(2019)
                    ),
                    RiskCalculationResponse(
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.eligible,
                        RiskCalculationEnum.ineligible
                    )
                )
            )
        }
    }
}
