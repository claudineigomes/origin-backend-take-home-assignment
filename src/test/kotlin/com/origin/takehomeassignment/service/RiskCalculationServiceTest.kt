package com.origin.takehomeassignment.service

import com.origin.takehomeassignment.calculation.IneligibleUtils
import com.origin.takehomeassignment.calculation.calculator.ScoreCalculator
import com.origin.takehomeassignment.domain.HouseInfo
import com.origin.takehomeassignment.domain.RiskCalculationRequest
import com.origin.takehomeassignment.domain.RiskCalculationResponse
import com.origin.takehomeassignment.domain.VehicleInfo
import com.origin.takehomeassignment.enum.MaritalStatusEnum
import com.origin.takehomeassignment.enum.OwnershipStatusEnum
import com.origin.takehomeassignment.enum.RiskCalculationEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class RiskCalculationServiceTest {

    private val ineligibleUtils = IneligibleUtils()

    private val scoreCalculator = ScoreCalculator()

    private lateinit var riskCalculationService: RiskCalculationService

    @BeforeEach
    internal fun init() {
        riskCalculationService = RiskCalculationService(ineligibleUtils, scoreCalculator)
    }

    @ParameterizedTest
    @MethodSource("createArguments")
    fun convert(
        riskCalculationRequest: RiskCalculationRequest,
        expectedRiskCalculationResponse: RiskCalculationResponse
    ) {
        val riskCalculationResponse = riskCalculationService.calculate(riskCalculationRequest)

        assertEquals(expectedRiskCalculationResponse, riskCalculationResponse)
    }

    companion object {
        private fun getRiskCalculationRequest(
            age: Int,
            dependents: Int,
            ownership: OwnershipStatusEnum,
            income: Int,
            marital: MaritalStatusEnum,
            riskQuestions: List<Boolean>,
            vehicleYear: Long
        ): RiskCalculationRequest {
            return RiskCalculationRequest(
                age, dependents,
                HouseInfo(ownership),
                income,
                marital,
                riskQuestions,
                VehicleInfo(vehicleYear)
            )
        }

        private fun getRiskCalculationResponse(
            auto: RiskCalculationEnum,
            disability: RiskCalculationEnum,
            home: RiskCalculationEnum,
            life: RiskCalculationEnum
        ): RiskCalculationResponse {
            return RiskCalculationResponse(auto, disability, home, life)
        }

        @JvmStatic
        fun createArguments(): Stream<Arguments> {

            return Stream.of(
                Arguments.of(
                    getRiskCalculationRequest(
                        29,
                        0,
                        OwnershipStatusEnum.owned,
                        0,
                        MaritalStatusEnum.single,
                        listOf(false, true, false),
                        2019
                    ),
                    getRiskCalculationResponse(
                        RiskCalculationEnum.economic,
                        RiskCalculationEnum.ineligible,
                        RiskCalculationEnum.economic,
                        RiskCalculationEnum.economic
                    )
                )
            )
        }
    }
}
