package com.linketinder.dao.companydao

import com.linketinder.model.company.Benefit
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.runners.Parameterized
import org.mockito.InjectMocks
import org.mockito.Mock
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when

class BenefitDAOTest {

    @InjectMocks
    private BenefitDAO benefitDAO = new BenefitDAO()

    @Mock
    private List<Benefit> populateBenefits

    @Parameterized.Parameters
    static List<Benefit> benefits() {
        List<Benefit> benefits = new ArrayList<>()
        benefits.add(new Benefit(id: 1, title: "Vale-Transporte"))
        benefits.add(new Benefit(id: 2, title: "Vale-Refeição"))
        benefits.add(new Benefit(id: 3, title: "Gympass"))
        return benefits
    }

}
