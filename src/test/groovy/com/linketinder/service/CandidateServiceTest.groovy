package com.linketinder.service

import com.linketinder.dao.candidatedao.CandidateDAO
import com.linketinder.domain.candidate.*
import com.linketinder.domain.jobvacancy.ContractType
import com.linketinder.domain.jobvacancy.LocationType
import com.linketinder.domain.shared.*

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
class CandidateServiceTest {

    @InjectMocks
    private CandidateService candidateService = new CandidateService()

    @Mock
    private CandidateDAO candidateDAO

    @Parameterized.Parameters
    static List<Candidate> candidatesList() {
        List<Candidate> candidates = new ArrayList<>()
        Person candidate1 = new Candidate(id: 1, name: "Afonso Alberto", email: "aa@gmail.com", city: "Araraquara", state: State.SP, country: "Brasil", cep: "14800000", description: "Sobre mim 1", cpf: "12345678900", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "Anhanguera", degreeType: "Bacharel", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Cursando)], workExperiences: [new WorkExperience(id: 1, title: "Assistente Fiscal", companyName: "Empresa 1", contractType: ContractType.CLT, locationType: LocationType.Presencial, city: "Patos de Minas", state: State.MG, currentlyWork: true, description: "Descrição 1")], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado)], skills: [new Skill(id: 1, title: "Java", proficiency: Proficiency.Intermediário)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "6 meses")])
        Person candidate2 = new Candidate(id: 2, name: "Breno Bernardo", email: "bb@gmail.com", city: "São Carlos", state: State.SP, country: "Brasil", cep: "14900000", description: "Sobre mim 2", cpf: "45678912312", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "FGV", degreeType: "Bacharel", fieldOfStudy: "Análise e Desenvolvimento de Sistemas", status: CourseStatus.Concluído), new AcademicExperience(id: 2, educationalInstitution: "PUC", degreeType: "Mestrado", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Cursando)], workExperiences: [], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Inglês", proficiency: Proficiency.Básico)], skills: [new Skill(id: 1, title: "Java", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "Groovy", proficiency: Proficiency.Intermediário), new Skill(id: 3, title: "SQL", proficiency: Proficiency.Básico)], certificates: [])
        Person candidate3 = new Candidate(id: 3, name: "Carlos Carvalho", email: "cc@gmail.com", city: "Patos de Minas", state: State.MG, country: "Brasil", cep: "17800000", description: "Sobre mim 3", cpf: "12398745617", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "Logatti", degreeType: "Bacharel", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Trancado)], workExperiences: [new WorkExperience(id: 1, title: "Estagiário de Desenvolvimento", companyName: "Empresa 8", contractType: ContractType.Estágio, locationType: LocationType.Híbrido, city: "Patos de Minas", state: State.MG, currentlyWork: true, description: "Descrição 1")], languages: [new Language(id: 1, name: "Inglês", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Português", proficiency: Proficiency.Avançado)], skills: [new Skill(id: 1, title: "Rust", proficiency: Proficiency.Intermediário), new Skill(id: 2, title: "C++", proficiency: Proficiency.Básico)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "1 ano"), new Certificate(id: 2, title: "Curso Certificado 2", duration: "2 meses")])
        Person candidate4 = new Candidate(id: 4, name: "Denis Delavechia", email: "dd@hotmail.com", city: "Curitiba", state: State.PR, country: "Brasil", cep: "12300000", description: "Sobre mim 4", cpf: "45615167904", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "UFPR", degreeType: "Bacharel", fieldOfStudy: "Engenharia da Computação", status: CourseStatus.Cursando)], workExperiences: [new WorkExperience(id: 1, title: "Assistente Contábil", companyName: "Empresa Alfa", contractType: ContractType.Temporário, locationType: LocationType.Remoto, city: "Curitiba", state: State.PR, currentlyWork: false, description: "Descrição 1")], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Inglês", proficiency: Proficiency.Básico)], skills: [new Skill(id: 1, title: "Kotlin", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "PostgreSQL", proficiency: Proficiency.Intermediário), new Skill(id: 3, title: "Angular", proficiency: Proficiency.Básico)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "1 mês")])
        candidates.add(candidate1)
        candidates.add(candidate2)
        candidates.add(candidate3)
        candidates.add(candidate4)

        return candidates
    }

    @Test
    @DisplayName("Test getAll")
    void testShouldGetAListOfCandidates() {
        when(candidateDAO.getAllCandidates()).thenReturn(candidatesList())
        List<Candidate> result = candidateService.getAll()

        List<Candidate> expectedResult = new ArrayList<>()
        expectedResult.add(new Candidate(id: 1, name: "Afonso Alberto", email: "aa@gmail.com", city: "Araraquara", state: State.SP, country: "Brasil", cep: "14800000", description: "Sobre mim 1", cpf: "12345678900", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "Anhanguera", degreeType: "Bacharel", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Cursando)], workExperiences: [new WorkExperience(id: 1, title: "Assistente Fiscal", companyName: "Empresa 1", contractType: ContractType.CLT, locationType: LocationType.Presencial, city: "Patos de Minas", state: State.MG, currentlyWork: true, description: "Descrição 1")], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado)], skills: [new Skill(id: 1, title: "Java", proficiency: Proficiency.Intermediário)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "6 meses")]))
        expectedResult.add(new Candidate(id: 2, name: "Breno Bernardo", email: "bb@gmail.com", city: "São Carlos", state: State.SP, country: "Brasil", cep: "14900000", description: "Sobre mim 2", cpf: "45678912312", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "FGV", degreeType: "Bacharel", fieldOfStudy: "Análise e Desenvolvimento de Sistemas", status: CourseStatus.Concluído), new AcademicExperience(id: 2, educationalInstitution: "PUC", degreeType: "Mestrado", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Cursando)], workExperiences: [], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Inglês", proficiency: Proficiency.Básico)], skills: [new Skill(id: 1, title: "Java", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "Groovy", proficiency: Proficiency.Intermediário), new Skill(id: 3, title: "SQL", proficiency: Proficiency.Básico)], certificates: []))
        expectedResult.add(new Candidate(id: 3, name: "Carlos Carvalho", email: "cc@gmail.com", city: "Patos de Minas", state: State.MG, country: "Brasil", cep: "17800000", description: "Sobre mim 3", cpf: "12398745617", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "Logatti", degreeType: "Bacharel", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Trancado)], workExperiences: [new WorkExperience(id: 1, title: "Estagiário de Desenvolvimento", companyName: "Empresa 8", contractType: ContractType.Estágio, locationType: LocationType.Híbrido, city: "Patos de Minas", state: State.MG, currentlyWork: true, description: "Descrição 1")], languages: [new Language(id: 1, name: "Inglês", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Português", proficiency: Proficiency.Avançado)], skills: [new Skill(id: 1, title: "Rust", proficiency: Proficiency.Intermediário), new Skill(id: 2, title: "C++", proficiency: Proficiency.Básico)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "1 ano"), new Certificate(id: 2, title: "Curso Certificado 2", duration: "2 meses")]))
        expectedResult.add(new Candidate(id: 4, name: "Denis Delavechia", email: "dd@hotmail.com", city: "Curitiba", state: State.PR, country: "Brasil", cep: "12300000", description: "Sobre mim 4", cpf: "45615167904", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "UFPR", degreeType: "Bacharel", fieldOfStudy: "Engenharia da Computação", status: CourseStatus.Cursando)], workExperiences: [new WorkExperience(id: 1, title: "Assistente Contábil", companyName: "Empresa Alfa", contractType: ContractType.Temporário, locationType: LocationType.Remoto, city: "Curitiba", state: State.PR, currentlyWork: false, description: "Descrição 1")], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Inglês", proficiency: Proficiency.Básico)], skills: [new Skill(id: 1, title: "Kotlin", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "PostgreSQL", proficiency: Proficiency.Intermediário), new Skill(id: 3, title: "Angular", proficiency: Proficiency.Básico)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "1 mês")]))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.name, result.name)
        assertEquals(expectedResult.cpf, result.cpf)
        assertEquals(expectedResult.academicExperiences.educationalInstitution,
                result.academicExperiences.educationalInstitution)
    }

    @Test
    @DisplayName("Test getById using valid id")
    void testShouldGetCandidateByValidId() {
        int id = 2
        when(candidateDAO.getCandidateById(id)).thenReturn(candidatesList().find {it.id == id})
        Candidate result = candidateService.getById(id)

        Person expectedResult = new Candidate(id: 2, name: "Breno Bernardo", email: "bb@gmail.com", city: "São Carlos", state: State.SP, country: "Brasil", cep: "14900000", description: "Sobre mim 2", cpf: "45678912312", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "FGV", degreeType: "Bacharel", fieldOfStudy: "Análise e Desenvolvimento de Sistemas", status: CourseStatus.Concluído), new AcademicExperience(id: 2, educationalInstitution: "PUC", degreeType: "Mestrado", fieldOfStudy: "Engenharia de Software", status: CourseStatus.Cursando)], workExperiences: [], languages: [new Language(id: 1, name: "Português", proficiency: Proficiency.Avançado), new Language(id: 2, name: "Inglês", proficiency: Proficiency.Básico)], skills: [new Skill(id: 1, title: "Java", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "Groovy", proficiency: Proficiency.Intermediário), new Skill(id: 3, title: "SQL", proficiency: Proficiency.Básico)], certificates: [])

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.name, result.name)
        assertEquals(expectedResult.cpf, result.cpf)
    }

    @Test
    @DisplayName("Test getById using invalid id")
    void testShouldBeNullUsingInvalidId() {
        int id = 10
        when(candidateDAO.getCandidateById(id)).thenReturn(candidatesList().find {it.id == id})

        Candidate result = candidateService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test getById using null id")
    void testShouldBeNullUsingNullId() {
        int id
        when(candidateDAO.getCandidateById(id)).thenReturn(candidatesList().find {it.id == id})

        Candidate result = candidateService.getById(id)

        assertNull(result)
    }

    @Test
    @DisplayName("Test add using valid Candidate")
    void testShouldBeAddCandidate() {
        List<Candidate> candidates = candidatesList()
        Answer<Candidate> answer = new Answer<Candidate>() {
            @Override
            Candidate answer(InvocationOnMock invocation) throws Throwable {
                Candidate candidate = (Candidate) invocation.getArguments()[0]
                if (candidate != null) {
                    candidates.add(candidate)
                }
                return null
            }
        }

        Person candidate = new Candidate(id: 5, name: "Elaine Elalia", email: "elaine@gmail.com", city: "São Paulo",  state: State.SP, country: "Brasil", cep: "04500000", description: "Sobre mim 5", cpf: "98765432109", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "USP", degreeType: "Bacharel", fieldOfStudy: "Ciência da Computação", status: CourseStatus.Concluído)], workExperiences: [new WorkExperience(id: 1, title: "Desenvolvedora Web", companyName: "Empresa Neo", contractType: ContractType.PJ, locationType: LocationType.Presencial, city: "São Paulo", state: State.SP, currentlyWork: true, description: "Descrição 2")], languages: [new Language(id: 1, name: "Inglês", proficiency: Proficiency.Básico), new Language(id: 2, name: "Espanhol", proficiency: Proficiency.Intermediário)], skills: [new Skill(id: 1, title: "Python", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "JavaScript", proficiency: Proficiency.Intermediário)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "3 meses")])
        doAnswer(answer).when(candidateDAO).insertCandidate(candidate)
        candidateService.add(candidate)

        boolean result = candidates.contains(candidate)

        assertTrue(result)
    }

    @Test
    @DisplayName("Test add using null Candidate")
    void testShouldBeThrowExceptionAddNullCandidate() {
        List<Candidate> candidates = candidatesList()
        Answer<Candidate> answer = new Answer<Candidate>() {
            @Override
            Candidate answer(InvocationOnMock invocation) throws Throwable {
                Candidate candidate = (Candidate) invocation.getArguments()[0]
                if (candidate != null) {
                    candidates.add(candidate)
                }
                return null
            }
        }

        Person candidate = null
        doAnswer(answer).when(candidateDAO).insertCandidate(candidate)
        candidateService.add(candidate)

        boolean result = candidates.contains(candidate)
        assertFalse(result)
    }

    @Test
    @DisplayName("Test update using a valid Candidate")
    void testShouldBeUpdateCandidate() {
        List<Candidate> candidates = candidatesList()
        Integer id = 1
        Answer<Candidate> answer = new Answer<Candidate>() {
            @Override
            Candidate answer(InvocationOnMock invocation) throws Throwable {
                Candidate updatedCandidate = (Candidate) invocation.getArguments()[1]
                if (updatedCandidate != null) {
                    candidates.set(id, updatedCandidate)
                }
                return null
            }
        }
        Person updatedCandidate = new Candidate(id: 1, name: "Elaine Elalia", email: "elaine@gmail.com", city: "São Paulo",  state: State.SP, country: "Brasil", cep: "04500000", description: "Sobre mim 5", cpf: "98765432109", academicExperiences: [new AcademicExperience(id: 1, educationalInstitution: "USP", degreeType: "Bacharel", fieldOfStudy: "Ciência da Computação", status: CourseStatus.Concluído)], workExperiences: [new WorkExperience(id: 1, title: "Desenvolvedora Web", companyName: "Empresa Neo", contractType: ContractType.PJ, locationType: LocationType.Presencial, city: "São Paulo", state: State.SP, currentlyWork: true, description: "Descrição 2")], languages: [new Language(id: 1, name: "Inglês", proficiency: Proficiency.Básico), new Language(id: 2, name: "Espanhol", proficiency: Proficiency.Intermediário)], skills: [new Skill(id: 1, title: "Python", proficiency: Proficiency.Avançado), new Skill(id: 2, title: "JavaScript", proficiency: Proficiency.Intermediário)], certificates: [new Certificate(id: 1, title: "Curso Certificado 1", duration: "3 meses")])

        doAnswer(answer).when(candidateDAO).updateCandidate(id, updatedCandidate)
        candidateService.update(id, updatedCandidate)
        Candidate expectedResult = candidates.find {it.id == id}

        assertEquals(expectedResult.id, updatedCandidate.id)
        assertNotEquals(expectedResult.name, updatedCandidate.name)
        assertNotEquals(expectedResult.cpf, updatedCandidate.cpf)
    }

    @Test
    @DisplayName("Test update using a null Candidate")
    void testShouldntBeUpdateCandidateUsingEmptyCandidate() {
        List<Candidate> candidates = candidatesList()
        Integer id = 1
        Answer<Candidate> answer = new Answer<Candidate>() {
            @Override
            Candidate answer(InvocationOnMock invocation) throws Throwable {
                Candidate updatedCandidate = (Candidate) invocation.getArguments()[1]
                if (updatedCandidate != null) {
                    candidates.set(id, updatedCandidate)
                }
                return null
            }
        }
        Person updatedCandidate = null

        doAnswer(answer).when(candidateDAO).updateCandidate(id, updatedCandidate)
        candidateService.update(id, updatedCandidate)
        Candidate expectedResult = candidates.find {it.id == id}

        assertNull(updatedCandidate)
        assertEquals(expectedResult.name, "Afonso Alberto")
        assertEquals(expectedResult.cpf, "12345678900")
    }

    @Test
    @DisplayName("Test delete using a valid id")
    void testShouldBeDeleteCandidate() {
        List<Candidate> candidates = candidatesList()
        int id = 4
        Answer<Integer> answer = new Answer<Integer>() {
            @Override
            Integer answer(InvocationOnMock invocation) throws Throwable {
                Integer index = (Integer) invocation.getArguments()[0]
                candidates.removeIf {it.id == index}
                return null
            }
        }

        doAnswer(answer).when(candidateDAO).deleteCandidateById(id)
        candidateService.delete(id)
        boolean result = candidates.find {it.id == 4}

        assertFalse(result)
    }

}
