package com.origin.takehomeassignment.calculation

import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import com.origin.takehomeassignment.enum.RiskCalculationEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class IneligibleUtilsTest {

    @Test
    fun shouldReturnAllRiskIneligibleWhenAllRiskQuestionsAreFalseAndIncomeLower25k() {
        val riskCalculationRequest = RiskCalculationRequest(
            29,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            0,
            MaritalStatusEnum.married,
            listOf(false, false, false),
            VehicleInfo(2018)
        )
        val ineligibleUtils = IneligibleUtils()
        val riskCalculationResponse = ineligibleUtils.verifyIneligible(riskCalculationRequest)
        assertEquals(RiskCalculationEnum.ineligible, riskCalculationResponse.auto)
        assertEquals(RiskCalculationEnum.ineligible, riskCalculationResponse.disability)
        assertEquals(RiskCalculationEnum.ineligible, riskCalculationResponse.home)
        assertEquals(RiskCalculationEnum.ineligible, riskCalculationResponse.life)
    }

    @Test
    fun shouldNotReturnAllRiskIneligibleWhenAllRiskQuestionsAreFalseAndIncomeEquals25k() {
        val riskCalculationRequest = RiskCalculationRequest(
            29,
            2,
            HouseInfo(OwnershipStatusEnum.owned),
            25000,
            MaritalStatusEnum.married,
            listOf(false, false, false),
            VehicleInfo(2018)
        )
        val ineligibleUtils = IneligibleUtils()
        val riskCalculationResponse = ineligibleUtils.verifyIneligible(riskCalculationRequest)
        assertEquals(RiskCalculationEnum.eligible, riskCalculationResponse.auto)
        assertEquals(RiskCalculationEnum.eligible, riskCalculationResponse.disability)
        assertEquals(RiskCalculationEnum.eligible, riskCalculationResponse.home)
        assertEquals(RiskCalculationEnum.eligible, riskCalculationResponse.life)
    }

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
                ),
                Arguments.of(
                    RiskCalculationRequest(
                        35,
                        2,
                        HouseInfo(OwnershipStatusEnum.owned),
                        20000,
                        MaritalStatusEnum.married,
                        listOf(false, false, false),
                        VehicleInfo(2018)
                    ),
                    RiskCalculationResponse(
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.ineligible
                    )
                )
            )
        }
    }
}
