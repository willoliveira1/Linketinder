package com.linketinder.service

import com.linketinder.dao.matchdao.CandidateMatchDAO
import com.linketinder.dao.matchdao.CompanyMatchDAO
import com.linketinder.dao.matchdao.MatchDAO
import com.linketinder.model.match.Match
import com.linketinder.service.interfaces.IMatchService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runners.Parameterized
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

import static org.junit.jupiter.api.Assertions.*
import static org.mockito.Mockito.when



\

@ExtendWith(MockitoExtension)
class MatchServiceTest {

    @InjectMocks
    private IMatchService matchService = new MatchService(matchDAO, candidateMatchDAO, companyMatchDAO)

    @Mock
    private MatchDAO matchDAO

    @Mock
    private CandidateMatchDAO candidateMatchDAO

    @Mock
    private CompanyMatchDAO companyMatchDAO

    @Parameterized.Parameters
    static List<Match> matches() {
        List<Match> matches = new ArrayList<>()
        matches.add(new Match(id: 1, candidateId: 1, companyId: 1, jobVacancyId: 1))
        matches.add(new Match(id: 2, candidateId: 1, companyId: 1, jobVacancyId: 2))
        matches.add(new Match(id: 3, candidateId: 2, companyId: 2, jobVacancyId: 2))
        matches.add(new Match(id: 4, candidateId: 2, companyId: 2, jobVacancyId: 3))
        matches.add(new Match(id: 5, candidateId: 1, companyId: 2, jobVacancyId: 4))
        return matches
    }

    @Test
    @DisplayName("Test getAll")
    void testShouldGetAListOfMatches() {
        when(matchDAO.getAllMatches()).thenReturn(matches())
        List<Match> result = matchService.getAllMatches()

        List<Match> expectedResult = new ArrayList<>()
        expectedResult.add(new Match(id: 1, candidateId: 1, companyId: 1, jobVacancyId: 1))
        expectedResult.add(new Match(id: 2, candidateId: 1, companyId: 1, jobVacancyId: 2))
        expectedResult.add(new Match(id: 3, candidateId: 2, companyId: 2, jobVacancyId: 2))
        expectedResult.add(new Match(id: 4, candidateId: 2, companyId: 2, jobVacancyId: 3))
        expectedResult.add(new Match(id: 5, candidateId: 1, companyId: 2, jobVacancyId: 4))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.candidateId, result.candidateId)
        assertEquals(expectedResult.companyId, result.companyId)
        assertEquals(expectedResult.jobVacancyId, result.jobVacancyId)
    }

    @Test
    @DisplayName("Test getAllMatchesByCandidateId using valid id")
    void testShouldGetAllMAtchesByValidCandidateId() {
        int id = 1
        when(candidateMatchDAO.getAllMatchesByCandidateId(id)).thenReturn(matches().findAll {it -> it.candidateId == id})
        List<Match> result = matchService.getAllMatchesByCandidateId(id)

        List<Match> expectedResult = new ArrayList<>()
        expectedResult.add(new Match(id: 1, candidateId: 1, companyId: 1, jobVacancyId: 1))
        expectedResult.add(new Match(id: 2, candidateId: 1, companyId: 1, jobVacancyId: 2))
        expectedResult.add(new Match(id: 5, candidateId: 1, companyId: 2, jobVacancyId: 4))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.candidateId, result.candidateId)
        assertEquals(expectedResult.companyId, result.companyId)
        assertEquals(expectedResult.jobVacancyId, result.jobVacancyId)
    }

    @Test
    @DisplayName("Test getAllMatchesByCandidateId using invalid id")
    void testShouldBeEmptyListByInvalidCandidateId() {
        int id = 123123
        when(candidateMatchDAO.getAllMatchesByCandidateId(id)).thenReturn(matches().findAll {it -> it.candidateId == id})
        List<Match> result = matchService.getAllMatchesByCandidateId(id)

        assertTrue(result.isEmpty())
    }

    @Test
    @DisplayName("Test getAllMatchesByCandidateId using null id")
    void testShouldBeEmptyListByNullCandidateId() {
        int id
        when(candidateMatchDAO.getAllMatchesByCandidateId(id)).thenReturn(matches().findAll {it -> it.candidateId == id})
        List<Match> result = matchService.getAllMatchesByCandidateId(id)

        assertTrue(result.isEmpty())
    }

    @Test
    @DisplayName("Test getAllMatchesByCompanyId using valid id")
    void testShouldGetAllMatchesByValidCompanyId() {
        int id = 1
        when(companyMatchDAO.getAllMatchesByCompanyId(id)).thenReturn(matches().findAll {it -> it.companyId == id})
        List<Match> result = matchService.getAllMatchesByCompanyId(id)

        List<Match> expectedResult = new ArrayList<>()
        expectedResult.add(new Match(id: 1, candidateId: 1, companyId: 1, jobVacancyId: 1))
        expectedResult.add(new Match(id: 2, candidateId: 1, companyId: 1, jobVacancyId: 2))

        assertEquals(expectedResult.id, result.id)
        assertEquals(expectedResult.candidateId, result.candidateId)
        assertEquals(expectedResult.companyId, result.companyId)
        assertEquals(expectedResult.jobVacancyId, result.jobVacancyId)
    }

    @Test
    @DisplayName("Test getAllMatchesByCompanyId using invalid id")
    void testShouldBeEmptyListByInvalidCompanyId() {
        int id = 123123
        when(companyMatchDAO.getAllMatchesByCompanyId(id)).thenReturn(matches().findAll {it -> it.companyId == id})
        List<Match> result = matchService.getAllMatchesByCompanyId(id)

        assertTrue(result.isEmpty())
    }

    @Test
    @DisplayName("Test getAllMatchesByCompanyId using null id")
    void testShouldBeEmptyListByNullCompanyId() {
        int id
        when(companyMatchDAO.getAllMatchesByCompanyId(id)).thenReturn(matches().findAll {it -> it.companyId == id})
        List<Match> result = matchService.getAllMatchesByCompanyId(id)

        assertTrue(result.isEmpty())
    }
//void likeJobVacancy(int candidateId, int jobVacancyId) {
//    candidateMatchDAO.candidateLikeJobVacancy(candidateId, jobVacancyId)
//}
    @Test
    @DisplayName("Test likeJobVacancy")
    void test4() {

    }

    @Test
    @DisplayName("Test likeJobVacancy")
    void test5() {

    }
//void likeCandidate(int companyId, int candidateId) {
//    companyMatchDAO.companyLikeCandidate(companyId, candidateId)
//}
    @Test
    @DisplayName("Test likeCandidate")
    void test6() {

    }

    @Test
    @DisplayName("Test likeCandidate")
    void test7() {

    }

}








