package com.linketinder.service

import com.linketinder.dao.companydao.CompanyDAO
import com.linketinder.model.company.*
import com.linketinder.model.shared.*

import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.Parameterized
import org.mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService = new CompanyService()

    @Mock
    private CompanyDAO companyDAO

    @Parameterized.Parameters
    static List<Company> companiesList() {
        List<Company> companies = new ArrayList<>()
        Person company1 = new Company(id: 1, name: "Empresa 1", email: "empresa1@empresa1.com.br", city: "Araraquara", state: State.SP, country: "Brasil", cep: "14800000", description: "Sobre a empresa 1", cnpj: "65498732165478", jobVacancies: [2,3], benefits: [new Benefit(id: 1, title: "Vale Transporte"), new Benefit(id: 2, title: "Refeitório no local"), new Benefit(id: 3, title: "Plano de Saúde")])
        Person company2 = new Company(id: 2, name: "Empresa 2", email: "empresa2@empresa2.com.br", city: "Uberlândia", state: State.MG, country: "Brasil", cep: "04680000", description: "Sobre a empresa 2", cnpj: "12345678912345", jobVacancies: [1], benefits: [new Benefit(id: 1, title: "Plano de Saúde")])
        Person company3 = new Company(id: 3, name: "Empresa 3", email: "empresa3@empresa3.com.br", city: "São Paulo", state: State.SP, country: "Brasil", cep: "04500000", description: "Sobre a empresa 3", cnpj: "45671237236234", jobVacancies: [], benefits: [new Benefit(id: 1, title: "Vale Transporte"), new Benefit(id: 2, title: "Vale Refeição"), new Benefit(id: 3, title: "Plano de Saúde"), new Benefit(id: 4, title: "Plano Odontológico")])
        Person company4 = new Company(id: 4, name: "Empresa 4", email: "empresa4@empresa4.com.br", city: "Manaus", state: State.AM, country: "Brasil", cep: "17900000", description: "Sobre a empresa 4", cnpj: "61789456435412", jobVacancies: [], benefits: [])
        companies.add(company1)
        companies.add(company2)
        companies.add(company3)
        companies.add(company4)

        return companies
    }

    @Test
    @DisplayName("Test getAll")
    void testShouldGetAListOfCompanies() {
        when(companyDAO.getAllCompanies()).thenReturn(companiesList())
        List<Company> result = companyService.getAll()

        List<Company> expectedResult = new ArrayList<>()
        expectedResult.add(new Company(id: 1, name: "Empresa 1", email: "empresa1@empresa1.com.br", city: "Araraquara", state: State.SP, country: "Brasil", cep: "14800000", description: "Sobre a empresa 1", cnpj: "65498732165478", jobVacancies: [2,3], benefits: [new Benefit(id: 1, title: "Vale Transporte"), new Benefit(id: 2, title: "Refeitório no local"), new Benefit(id: 3, title: "Plano de Saúde")]))
        expectedResult.add(new Company(id: 2, name: "Empresa 2", email: "empresa2@empresa2.com.br", city: "Uberlândia", state: State.MG, country: "Brasil", cep: "04680000", description: "Sobre a empresa 2", cnpj: "12345678912345", jobVacancies: [1], benefits: [new Benefit(id: 1, title: "Plano de Saúde")]))
        expectedResult.add(new Company(id: 3, name: "Empresa 3", email: "empresa3@empresa3.com.br", city: "São Paulo", state: State.SP, country: "Brasil", cep: "04500000", description: "Sobre a empresa 3", cnpj: "45671237236234", jobVacancies: [], benefits: [new Benefit(id: 1, title: "Vale Transporte"), new Benefit(id: 2, title: "Vale Refeição"), new Benefit(id: 3, title: "Plano de Saúde"), new Benefit(id: 4, title: "Plano Odontológico")]))
        expectedResult.add(new Company(id: 4, name: "Empresa 4", email: "empresa4@empresa4.com.br", city: "Manaus", state: State.AM, country: "Brasil", cep: "17900000", description: "Sobre a empresa 4", cnpj: "61789456435412", jobVacancies: [], benefits: []))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.name, result.name)
        assertEquals(expectedResult.cnpj, result.cnpj)
        assertEquals(expectedResult.benefits.title, result.benefits.title)
    }

    @Test
    @DisplayName("Test getById using valid id")
    void testShouldGetCompanyByValidId() {
        int id = 2
        when(companyDAO.getCompanyById(id)).thenReturn(companiesList().find {it.id == id})
        Person result = companyService.getById(id)

        Person expectedResult = new Company(id: 2, name: "Empresa 2", email: "empresa2@empresa2.com.br", city: "Uberlândia", state: State.MG, country: "Brasil", cep: "04680000", description: "Sobre a empresa 2", cnpj: "12345678912345", jobVacancies: [1], benefits: [new Benefit(id: 1, title: "Plano de Saúde")])

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.name, result.name)
        assertEquals(expectedResult.cnpj, result.cnpj)
    }

    @Test
    @DisplayName("Test getById using invalid id")
    void testShouldBeNullUsingInvalidId() {
        int id = 10
        when(companyDAO.getCompanyById(id)).thenReturn(companiesList().find {it.id == id})

        Person result = companyService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test getById using null id")
    void testShouldBeNullUsingNullId() {
        int id
        when(companyDAO.getCompanyById(id)).thenReturn(companiesList().find {it.id == id})

        Person result = companyService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test add using valid Company")
    void testShouldBeAddCompany() {
        List<Company> companies = companiesList()
        Answer<Person> answer = new Answer<Person>() {
            @Override
            Person answer(InvocationOnMock invocation) throws Throwable {
                Person company = (Company) invocation.getArguments()[0]
                if (company != null) {
                    companies.add(company)
                }
                return null
            }
        }

        Person company = new Company(id: 5, name: "Empresa 5", email: "empresa5@empresa5.com.br", city: "Curitiba", state: State.PR, country: "Brasil", cep: "19400000", description: "Sobre a empresa 5", cnpj: "12373415345763", jobVacancies: [], benefits: [new Benefit(id: 1, title: "Plano de Saúde"), new Benefit(id: 2, title: "Plano Odontológico"), new Benefit(id: 3, title: "Vale Refeição"), new Benefit(id: 4, title: "Vale Transporte")])
        doAnswer(answer).when(companyDAO).insertCompany(company)
        companyService.add(company)

        boolean result = companies.contains(company)

        assertTrue(result)
    }

    @Test
    @DisplayName("Test add using null Company")
    void testShouldBeThrowExceptionAddNullCompany() {
        List<Company> companies = companiesList()
        Answer<Person> answer = new Answer<Person>() {
            @Override
            Person answer(InvocationOnMock invocation) throws Throwable {
                Person company = (Company) invocation.getArguments()[0]
                if (company != null) {
                    companies.add(company)
                }
                return null
            }
        }

        Company company = null
        doAnswer(answer).when(companyDAO).insertCompany(company)
        companyService.add(company)

        boolean result = companies.contains(company)
        assertFalse(result)
    }

    @Test
    @DisplayName("Test update using a valid Company")
    void testShouldBeUpdateCompany() {
        List<Company> companies = companiesList()
        Integer id = 1

        Person updatedCompany = new Company(id: 1, name: "Empresa 5", email: "empresa5@empresa5.com.br", city: "Curitiba", state: State.PR, country: "Brasil", cep: "19400000", description: "Sobre a empresa 5", cnpj: "12373415345763", jobVacancies: [], benefits: [new Benefit(id: 1, title: "Plano de Saúde"), new Benefit(id: 2, title: "Plano Odontológico"), new Benefit(id: 3, title: "Vale Refeição"), new Benefit(id: 4, title: "Vale Transporte")])

        companyService.update(id, updatedCompany)
        Company expectedResult = companies.find {it.id == id}

        assertEquals(expectedResult.id, updatedCompany.id)
        assertNotEquals(expectedResult.name, updatedCompany.name)
        assertNotEquals(expectedResult.cnpj, updatedCompany.cnpj)
    }

    @Test
    @DisplayName("Test update using a null Company")
    void testShouldntBeUpdateCompanyUsingEmptyCompany() {
        List<Company> companies = companiesList()
        Integer id = 1
        Person updatedCompany = null

        companyService.update(id, updatedCompany)
        Company expectedResult = companies.find {it.id == id}

        assertNull(updatedCompany)
        assertEquals(expectedResult.name, "Empresa 1")
        assertEquals(expectedResult.cnpj, "65498732165478")
    }

    @Test
    @DisplayName("Test delete using a valid id")
    void testShouldBeDeleteCompany() {
        List<Company> companies = companiesList()
        int id = 4
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            Integer answer(InvocationOnMock invocation) throws Throwable {
                Integer index = (Integer) invocation.getArguments()[0]
                companies.removeIf {it.id == index}
                return null
            }
        }

        doAnswer(answer).when(companyDAO).deleteCompanyById(id)
        companyService.delete(id)
        boolean result = companies.find {it.id == 4}

        assertFalse(result)
    }

}
