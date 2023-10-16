package com.linketinder.service

import com.linketinder.dao.companydao.JobVacancyDAO
import com.linketinder.model.candidate.Candidate
import com.linketinder.model.jobvacancy.ContractType
import com.linketinder.model.jobvacancy.JobVacancy
import com.linketinder.model.jobvacancy.LocationType
import com.linketinder.model.shared.Person
import com.linketinder.model.shared.Skill
import com.linketinder.service.interfaces.IJobVacancyService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runners.Parameterized
import org.mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.Answer
import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.*

@ExtendWith(MockitoExtension)
class JobVacancyServiceTest {

    @InjectMocks
    private IJobVacancyService jobVacancyService = new JobVacancyService(jobVacancyDAO)

    @Mock
    private JobVacancyDAO jobVacancyDAO

    @Parameterized.Parameters
    static List<JobVacancy> jobVacancies() {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        jobVacancies.add(new JobVacancy(id: 1, title: "Vaga 1", description: "Descrição Vaga 1", requiredSkills: [new Skill(id: 1, title: "Java", proficiency: null), new Skill(id: 3, title: "PostgreSQL", proficiency: null), new Skill(id: 2, title: "Angular", proficiency: null)], salary: 1000.0, contractType: ContractType.Estágio, locationType: LocationType.Híbrido))
        jobVacancies.add(new JobVacancy(id: 2, title: "Vaga 2", description: "Descrição Vaga 2", requiredSkills: [new Skill(id: 5, title: "SQL Server", proficiency: null), new Skill(id: 14, title: "Javascript", proficiency: null), new Skill(id: 4, title: "C#", proficiency: null)], salary: 2000.0, contractType: ContractType.Temporário, locationType: LocationType.Presencial))
        jobVacancies.add(new JobVacancy(id: 3, title: "Vaga 3", description: "Descrição Vaga 3", requiredSkills: [], salary: 3000.0, contractType: ContractType.CLT, locationType: LocationType.Remoto))
        jobVacancies.add(new JobVacancy(id: 4, title: "Vaga 4", description: "Descrição Vaga 4", requiredSkills: [new Skill(id: 6, title: "Java", proficiency: null), new Skill(id: 7, title: "C++", proficiency: null), new Skill(id: 8, title: "Angular", proficiency: null)], salary: 2500.0, contractType: ContractType.PJ, locationType: LocationType.Presencial))
        jobVacancies.add(new JobVacancy(id: 5, title: "Vaga 5", description: "Descrição Vaga 5", requiredSkills: [new Skill(id: 9, title: "Typescript", proficiency: null), new Skill(id: 10, title: "Python", proficiency: null), new Skill(id: 11, title: "C#", proficiency: null)], salary: 3500.0, contractType: ContractType.CLT, locationType: LocationType.Presencial))
        jobVacancies.add(new JobVacancy(id: 6, title: "Vaga 6", description: "Descrição Vaga 6", requiredSkills: [new Skill(id: 13, title: "SQL Server", proficiency: null), new Skill(id: 15, title: "C++", proficiency: null), new Skill(id: 16, title: "Kotlin", proficiency: null), new Skill(id: 12, title: "C#", proficiency: null)], salary: 2250.0, contractType: ContractType.PJ, locationType: LocationType.Remoto))
        return jobVacancies
    }

    @Parameterized.Parameters
    static List<JobVacancy> jobVacanciesByCompanyId2() {
        List<JobVacancy> jobVacancies = new ArrayList<>()
        jobVacancies.add(new JobVacancy(id: 4, title: "Vaga 4", description: "Descrição Vaga 4", requiredSkills: [new Skill(id: 6, title: "Java", proficiency: null), new Skill(id: 7, title: "C++", proficiency: null), new Skill(id: 8, title: "Angular", proficiency: null)], salary: 2500.0, contractType: ContractType.PJ, locationType: LocationType.Presencial))
        jobVacancies.add(new JobVacancy(id: 5, title: "Vaga 5", description: "Descrição Vaga 5", requiredSkills: [new Skill(id: 9, title: "Typescript", proficiency: null), new Skill(id: 10, title: "Python", proficiency: null), new Skill(id: 11, title: "C#", proficiency: null)], salary: 3500.0, contractType: ContractType.CLT, locationType: LocationType.Presencial))
        jobVacancies.add(new JobVacancy(id: 6, title: "Vaga 6", description: "Descrição Vaga 6", requiredSkills: [new Skill(id: 13, title: "SQL Server", proficiency: null), new Skill(id: 15, title: "C++", proficiency: null), new Skill(id: 16, title: "Kotlin", proficiency: null), new Skill(id: 12, title: "C#", proficiency: null)], salary: 2250.0, contractType: ContractType.PJ, locationType: LocationType.Remoto))
        return jobVacancies
    }

    @Test
    @DisplayName("Test getAll")
    void testShouldGetAListOfCandidates() {
        when(jobVacancyDAO.getAllJobVacancies()).thenReturn(jobVacancies())
        List<JobVacancy> result = jobVacancyService.getAll()

        List<JobVacancy> expectedResult = new ArrayList<>()
        expectedResult.add(new JobVacancy(id: 1, title: "Vaga 1", description: "Descrição Vaga 1", requiredSkills: [new Skill(id: 1, title: "Java", proficiency: null), new Skill(id: 3, title: "PostgreSQL", proficiency: null), new Skill(id: 2, title: "Angular", proficiency: null)], salary: 1000.0, contractType: ContractType.Estágio, locationType: LocationType.Híbrido))
        expectedResult.add(new JobVacancy(id: 2, title: "Vaga 2", description: "Descrição Vaga 2", requiredSkills: [new Skill(id: 5, title: "SQL Server", proficiency: null), new Skill(id: 14, title: "Javascript", proficiency: null), new Skill(id: 4, title: "C#", proficiency: null)], salary: 2000.0, contractType: ContractType.Temporário, locationType: LocationType.Presencial))
        expectedResult.add(new JobVacancy(id: 3, title: "Vaga 3", description: "Descrição Vaga 3", requiredSkills: [], salary: 3000.0, contractType: ContractType.CLT, locationType: LocationType.Remoto))
        expectedResult.add(new JobVacancy(id: 4, title: "Vaga 4", description: "Descrição Vaga 4", requiredSkills: [new Skill(id: 6, title: "Java", proficiency: null), new Skill(id: 7, title: "C++", proficiency: null), new Skill(id: 8, title: "Angular", proficiency: null)], salary: 2500.0, contractType: ContractType.PJ, locationType: LocationType.Presencial))
        expectedResult.add(new JobVacancy(id: 5, title: "Vaga 5", description: "Descrição Vaga 5", requiredSkills: [new Skill(id: 9, title: "Typescript", proficiency: null), new Skill(id: 10, title: "Python", proficiency: null), new Skill(id: 11, title: "C#", proficiency: null)], salary: 3500.0, contractType: ContractType.CLT, locationType: LocationType.Presencial))
        expectedResult.add(new JobVacancy(id: 6, title: "Vaga 6", description: "Descrição Vaga 6", requiredSkills: [new Skill(id: 13, title: "SQL Server", proficiency: null), new Skill(id: 15, title: "C++", proficiency: null), new Skill(id: 16, title: "Kotlin", proficiency: null), new Skill(id: 12, title: "C#", proficiency: null)], salary: 2250.0, contractType: ContractType.PJ, locationType: LocationType.Remoto))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.title, result.title)
        assertEquals(expectedResult.description, result.description)
        assertEquals(expectedResult.salary, result.salary)
    }

    @Test
    @DisplayName("Test getAllByCompanyId using valid CompanyId")
    void testShouldBeGetAllByCompanyId() {
        int companyId = 2
        when(jobVacancyDAO.getJobVacancyByCompanyId(companyId)).thenReturn(jobVacanciesByCompanyId2())
        List<JobVacancy> result = jobVacancyService.getAllByCompanyId(companyId)

        List<JobVacancy> expectedResult = new ArrayList<>()
        expectedResult.add(new JobVacancy(id: 4, title: "Vaga 4", description: "Descrição Vaga 4", requiredSkills: [new Skill(id: 6, title: "Java", proficiency: null), new Skill(id: 7, title: "C++", proficiency: null), new Skill(id: 8, title: "Angular", proficiency: null)], salary: 2500.0, contractType: ContractType.PJ, locationType: LocationType.Presencial))
        expectedResult.add(new JobVacancy(id: 5, title: "Vaga 5", description: "Descrição Vaga 5", requiredSkills: [new Skill(id: 9, title: "Typescript", proficiency: null), new Skill(id: 10, title: "Python", proficiency: null), new Skill(id: 11, title: "C#", proficiency: null)], salary: 3500.0, contractType: ContractType.CLT, locationType: LocationType.Presencial))
        expectedResult.add(new JobVacancy(id: 6, title: "Vaga 6", description: "Descrição Vaga 6", requiredSkills: [new Skill(id: 13, title: "SQL Server", proficiency: null), new Skill(id: 15, title: "C++", proficiency: null), new Skill(id: 16, title: "Kotlin", proficiency: null), new Skill(id: 12, title: "C#", proficiency: null)], salary: 2250.0, contractType: ContractType.PJ, locationType: LocationType.Remoto))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.title, result.title)
        assertEquals(expectedResult.description, result.description)
        assertEquals(expectedResult.salary, result.salary)
    }

    @Test
    @DisplayName("Test getAllByCompanyId using invalid CompanyId")
    void testShouldBeNullUsingInvalidCompanyId() {
        int companyId = 2452452
        when(jobVacancyDAO.getJobVacancyByCompanyId(companyId)).thenReturn(null)
        List<JobVacancy> result = jobVacancyService.getAllByCompanyId(companyId)

        assertNull(result)
    }

    @Test
    @DisplayName("Test getAllByCompanyId using null CompanyId")
    void testShouldBeNullUsingNullCompanyId() {
        int companyId
        when(jobVacancyDAO.getJobVacancyByCompanyId(companyId)).thenReturn(null)
        List<JobVacancy> result = jobVacancyService.getAllByCompanyId(companyId)

        assertNull(result)
    }

    @Test
    @DisplayName("Test getById using valid JobVacancyId")
    void testShouldGetJobVacancyByValidId() {
        int id = 3
        when(jobVacancyDAO.getJobVacancyById(id)).thenReturn(jobVacancies().find {it.id == id})
        JobVacancy result = jobVacancyService.getById(id)

        JobVacancy expectedResult = new JobVacancy(id: 3, title: "Vaga 3", description: "Descrição Vaga 3", requiredSkills: [], salary: 3000.0, contractType: ContractType.CLT, locationType: LocationType.Remoto)

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.title, result.title)
        assertEquals(expectedResult.description, result.description)
        assertEquals(expectedResult.salary, result.salary)
    }

    @Test
    @DisplayName("Test getById using invalid JobVacancyId")
    void testShouldBeNullUsingInvalidId() {
        int id = 10
        when(jobVacancyDAO.getJobVacancyById(id)).thenReturn(jobVacancies().find {it.id == id})

        JobVacancy result = jobVacancyService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test getById using null JobVacancyId")
    void testShouldBeNullUsingNullId() {
        int id
        when(jobVacancyDAO.getJobVacancyById(id)).thenReturn(jobVacancies().find {it.id == id})

        JobVacancy result = jobVacancyService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test add using valid JobVacancy")
    void testShouldBeAddJobVacancy() {
        List<JobVacancy> jobVacancies = jobVacancies()
        Answer<JobVacancy> answer = new Answer<JobVacancy>() {
            @Override
            JobVacancy answer(InvocationOnMock invocation) throws Throwable {
                JobVacancy jobVacancy = (JobVacancy) invocation.getArguments()[1]
                if (jobVacancy != null) {
                    jobVacancies.add(jobVacancy)
                }
                return null
            }
        }

        Integer companyId = 1
        JobVacancy jobVacancy = new JobVacancy(id: 7, title: "Vaga 3", description: "Descrição Vaga 3", requiredSkills: [], salary: 3000.0, contractType: ContractType.CLT, locationType: LocationType.Remoto)
        doAnswer(answer).when(jobVacancyDAO).insertJobVacancy(companyId, jobVacancy)
        jobVacancyService.add(companyId, jobVacancy)

        boolean result = jobVacancies.contains(jobVacancy)

        assertTrue(result)
    }

    @Test
    @DisplayName("Test add using null JobVacancy")
    void testShouldBeThrowExceptionAddNullJobVacancy() {
        List<JobVacancy> jobVacancies = jobVacancies()
        Answer<JobVacancy> answer = new Answer<JobVacancy>() {
            @Override
            JobVacancy answer(InvocationOnMock invocation) throws Throwable {
                JobVacancy jobVacancy = (JobVacancy) invocation.getArguments()[1]
                if (jobVacancy != null) {
                    jobVacancies.add(jobVacancy)
                }
                return null
            }
        }

        Integer companyId = 1
        Person jobVacancy = null
        doAnswer(answer).when(jobVacancyDAO).insertJobVacancy(companyId, jobVacancy)
        jobVacancyService.add(companyId, jobVacancy)

        boolean result = jobVacancies.contains(jobVacancy)
        assertFalse(result)
    }

    @Test
    @DisplayName("Test update using a valid JobVacancy")
    void testShouldBeUpdateJobVacancy() {
        List<JobVacancy> jobVacancies = jobVacancies()
        Integer id = 1
        Answer<JobVacancy> answer = new Answer<JobVacancy>() {
            @Override
            JobVacancy answer(InvocationOnMock invocation) throws Throwable {
                JobVacancy updatedJobVacancy = (JobVacancy) invocation.getArguments()[0]
                if (updatedJobVacancy != null) {
                    jobVacancies.set(id, updatedJobVacancy)
                }
                return null
            }
        }
        JobVacancy updatedJobVacancy = new JobVacancy(id: 1, title: "Vaga 33", description: "Descrição Vaga 33", requiredSkills: [], salary: 3000.0, contractType: ContractType.CLT, locationType: LocationType.Remoto)

        doAnswer(answer).when(jobVacancyDAO).updateJobVacancy(updatedJobVacancy)
        jobVacancyService.update(updatedJobVacancy)
        JobVacancy expectedResult = jobVacancies.find {it.id == id}

        assertEquals(expectedResult.id, updatedJobVacancy.id)
        assertNotEquals(expectedResult.title, updatedJobVacancy.title)
        assertNotEquals(expectedResult.description, updatedJobVacancy.description)
    }

    @Test
    @DisplayName("Test update using a null JobVacancy")
    void testShouldntBeUpdateJobVacancyUsingEmptyJobVacancy() {
        List<JobVacancy> jobVacancies = jobVacancies()
        Integer id = 1
        Answer<JobVacancy> answer = new Answer<JobVacancy>() {
            @Override
            JobVacancy answer(InvocationOnMock invocation) throws Throwable {
                JobVacancy updatedJobVacancy = (JobVacancy) invocation.getArguments()[0]
                if (updatedJobVacancy != null) {
                    jobVacancies.set(id, updatedJobVacancy)
                }
                return null
            }
        }
        Person updatedJobVacancy = null

        doAnswer(answer).when(jobVacancyDAO).updateJobVacancy(updatedJobVacancy)
        jobVacancyService.update(updatedJobVacancy)
        JobVacancy expectedResult = jobVacancies.find {it.id == id}

        assertNull(updatedJobVacancy)
        assertEquals(expectedResult.title, "Vaga 1")
        assertEquals(expectedResult.description, "Descrição Vaga 1")
    }

    @Test
    @DisplayName("Test delete using a valid id")
    void testShouldBeDeleteJobVacancy() {
        List<Candidate> jobVacancies = jobVacancies()
        int id = 4
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            Integer answer(InvocationOnMock invocation) throws Throwable {
                Integer index = (Integer) invocation.getArguments()[0]
                jobVacancies.removeIf {it.id == index}
                return null
            }
        }

        doAnswer(answer).when(jobVacancyDAO).deleteJobVacancy(id)
        jobVacancyService.delete(id)
        boolean result = jobVacancies.find {it.id == 4}

        assertFalse(result)
    }

}
