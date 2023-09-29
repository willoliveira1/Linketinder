package com.linketinder.dao

import com.linketinder.database.DatabaseFactory
import com.linketinder.database.DBService
import com.linketinder.domain.candidate.AcademicExperience
import com.linketinder.domain.candidate.Candidate
import com.linketinder.domain.candidate.Certificate
import com.linketinder.domain.candidate.CourseStatus
import com.linketinder.domain.candidate.WorkExperience
import com.linketinder.domain.shared.ContractType
import com.linketinder.domain.shared.Language
import com.linketinder.domain.shared.LocationType
import com.linketinder.domain.shared.Person
import com.linketinder.domain.shared.Proficiency
import com.linketinder.domain.shared.Skill
import com.linketinder.domain.shared.State
import groovy.sql.Sql

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement;
import java.util.logging.Level
import java.util.logging.Logger

class CandidateDAO {

    Sql sql = DatabaseFactory.instance()
    DBService dbService = new DBService()

    List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf
                    FROM candidates AS c,
                        states AS s
                    WHERE c.state_id = s.id
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                Person candidate = new Candidate()
                int candidateId = result.getInt("id")
                candidate.setId(candidateId)
                candidate.setName(result.getString("name"))
                candidate.setEmail(result.getString("email"))
                candidate.setCity(result.getString("city"))
                candidate.setState(State.valueOf(result.getString("state")))
                candidate.setCountry(result.getString("country"))
                candidate.setCep(result.getString("cep"))
                candidate.setDescription(result.getString("description"))
                candidate.setCpf(result.getString("cpf"))

                String certificatesQuery = """
                    SELECT id, title, duration 
                    FROM certificates 
                    WHERE candidate_id = ${candidateId}
                """
                PreparedStatement certificatesStmt = sql.connection.prepareStatement(certificatesQuery)
                ResultSet certificatesResult = certificatesStmt.executeQuery()
                List<Certificate> certificates = new ArrayList<>()
                while (certificatesResult.next()) {
                    Certificate certificate = new Certificate()
                    certificate.setId(certificatesResult.getInt("id"))
                    certificate.setTitle(certificatesResult.getString("title"))
                    certificate.setDuration(certificatesResult.getString("duration"))
                    certificates.add(certificate)
                }
                candidate.setCertificates(certificates)

                String languagesQuery = """
                    SELECT cl.id, l.name, p.title
                        FROM candidates AS c,
                             candidate_languages AS cl,
                             languages AS l,
                             proficiences AS p
                        WHERE c.id = cl.candidate_id
                        AND l.id = cl.language_id
                        AND p.id = cl.proficiency_id
                        AND c.id = ${candidateId}
                """
                PreparedStatement languagesStmt = sql.connection.prepareStatement(languagesQuery)
                ResultSet languagesResult = languagesStmt.executeQuery()
                List<Language> languages = new ArrayList<>()
                while (languagesResult.next()) {
                    Language language = new Language()
                    language.setId(languagesResult.getInt("id"))
                    language.setName(languagesResult.getString("name"))
                    language.setProficiency(Proficiency.valueOf(languagesResult.getString("title")))
                    languages.add(language)
                }
                candidate.setLanguages(languages)

                String academicExperiencesQuery = """
                    SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title
                        FROM candidates AS c,
                            academic_experiences AS a,
                            course_status AS cs
                        WHERE c.id = a.candidate_id
                        AND a.course_status_id = cs.id
                        AND c.id = ${candidateId}
                """
                PreparedStatement academicExperiencesStmt = sql.connection.prepareStatement(academicExperiencesQuery)
                ResultSet academicExperiencesResult = academicExperiencesStmt.executeQuery()
                List<AcademicExperience> academicExperiences = new ArrayList<>()
                while (academicExperiencesResult.next()) {
                    AcademicExperience academicExperience = new AcademicExperience()
                    academicExperience.setId(academicExperiencesResult.getInt("id"))
                    academicExperience.setEducationalInstitution(academicExperiencesResult
                            .getString("educational_institution"))
                    academicExperience.setDegreeType(academicExperiencesResult.getString("degree_type"))
                    academicExperience.setFieldOfStudy(academicExperiencesResult.getString("field_of_study"))
                    academicExperience.setStatus(CourseStatus.valueOf(academicExperiencesResult
                            .getString("title")))
                    academicExperiences.add(academicExperience)
                }
                candidate.setAcademicExperiences(academicExperiences)

                String workExperiencesQuery = """
                    SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description
                        FROM candidates AS c,
                             work_experiences AS we,
                             states AS s,
                             contract_types AS ct,
                             location_types AS lt
                        WHERE c.id = we.candidate_id
                        AND we.contract_type_id = ct.id
                        AND we.location_id = lt.id
                        AND we.state_id = s.id
                        AND c.id = ${candidateId}
                """
                PreparedStatement workExperiencesStmt = sql.connection.prepareStatement(workExperiencesQuery)
                ResultSet workExperiencesResult = workExperiencesStmt.executeQuery()
                List<WorkExperience> workExperiences = new ArrayList<>()
                while (workExperiencesResult.next()) {
                    WorkExperience workExperience = new WorkExperience()
                    workExperience.setId(workExperiencesResult.getInt("id"))
                    workExperience.setTitle(workExperiencesResult.getString("title"))
                    workExperience.setCompanyName(workExperiencesResult.getString("company_name"))
                    workExperience.setContractType(ContractType.valueOf(workExperiencesResult
                            .getString("contract_type_title")))
                    workExperience.setLocationType(LocationType.valueOf(workExperiencesResult
                            .getString("location_type_title")))
                    workExperience.setCity(workExperiencesResult.getString("city"))
                    workExperience.setState(State.valueOf(workExperiencesResult.getString("acronym")))
                    workExperience.setCurrentlyWork(workExperiencesResult.getBoolean("currently_work"))
                    workExperience.setDescription(workExperiencesResult.getString("description"))
                    workExperiences.add(workExperience)
                }
                candidate.setWorkExperiences(workExperiences)

                String skillsQuery = """
                    SELECT cs.id, s.title, p.title AS proficiency_title
                        FROM candidates AS c,
                             candidate_skills AS cs,
                             skills AS s,
                             proficiences AS p
                        WHERE c.id = cs.candidate_id
                        AND s.id = cs.skill_id
                        AND p.id = cs.proficiency_id
                        AND c.id = ${candidateId}
                """
                PreparedStatement skillsStmt = sql.connection.prepareStatement(skillsQuery)
                ResultSet skillsResult = skillsStmt.executeQuery()
                List<Skill> skills = new ArrayList<>()
                while (skillsResult.next()) {
                    Skill skill = new Skill()
                    skill.setId(skillsResult.getInt("id"))
                    skill.setTitle(skillsResult.getString("title"))
                    skill.setProficiency(Proficiency.valueOf(skillsResult.getString("proficiency_title")))
                    skills.add(skill)
                }
                candidate.setSkills(skills)

                candidates.add(candidate)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return candidates
    }

    Candidate getCandidateById(int id) {
        Person candidate = new Candidate()

        try {
            String query = """
                SELECT c.id, c.name, c.email, c.city, s.acronym AS state, c.country, c.cep, c.description, c.cpf
                    FROM candidates AS c,
                        states AS s
                    WHERE c.state_id = s.id
                    AND c.id = ${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                candidate.setId(result.getInt("id"))
                candidate.setName(result.getString("name"))
                candidate.setEmail(result.getString("email"))
                candidate.setCity(result.getString("city"))
                candidate.setState(State.valueOf(result.getString("state")))
                candidate.setCountry(result.getString("country"))
                candidate.setCep(result.getString("cep"))
                candidate.setDescription(result.getString("description"))
                candidate.setCpf(result.getString("cpf"))

                String certificatesQuery = "SELECT id, title, duration FROM certificates WHERE candidate_id = ${id}"
                PreparedStatement certificatesStmt = sql.connection.prepareStatement(certificatesQuery)
                ResultSet certificatesResult = certificatesStmt.executeQuery()
                List<Certificate> certificates = new ArrayList<>()
                while (certificatesResult.next()) {
                    Certificate certificate = new Certificate()
                    certificate.setId(certificatesResult.getInt("id"))
                    certificate.setTitle(certificatesResult.getString("title"))
                    certificate.setDuration(certificatesResult.getString("duration"))
                    certificates.add(certificate)
                }
                candidate.setCertificates(certificates)

                String languagesQuery = """
                    SELECT cl.id, l.name, p.title
                        FROM candidates AS c,
                             candidate_languages AS cl,
                             languages AS l,
                             proficiences AS p
                        WHERE c.id = cl.candidate_id
                        AND l.id = cl.language_id
                        AND p.id = cl.proficiency_id
                        AND c.id = ${id}
                """
                PreparedStatement languagesStmt = sql.connection.prepareStatement(languagesQuery)
                ResultSet languagesResult = languagesStmt.executeQuery()
                List<Language> languages = new ArrayList<>()
                while (languagesResult.next()) {
                    Language language = new Language()
                    language.setId(languagesResult.getInt("id"))
                    language.setName(languagesResult.getString("name"))
                    language.setProficiency(Proficiency.valueOf(languagesResult.getString("title")))
                    languages.add(language)
                }
                candidate.setLanguages(languages)

                String academicExperiencesQuery = """
                    SELECT a.id, a.educational_institution, a.degree_type, a.field_of_study, cs.title
                        FROM candidates AS c,
                            academic_experiences AS a,
                            course_status AS cs
                        WHERE c.id = a.candidate_id
                        AND a.course_status_id = cs.id
                        AND c.id = ${id}
                """
                PreparedStatement academicExperiencesStmt = sql.connection.prepareStatement(academicExperiencesQuery)
                ResultSet academicExperiencesResult = academicExperiencesStmt.executeQuery()
                List<AcademicExperience> academicExperiences = new ArrayList<>()
                while (academicExperiencesResult.next()) {
                    AcademicExperience academicExperience = new AcademicExperience()
                    academicExperience.setId(academicExperiencesResult.getInt("id"))
                    academicExperience.setEducationalInstitution(academicExperiencesResult
                            .getString("educational_institution"))
                    academicExperience.setDegreeType(academicExperiencesResult.getString("degree_type"))
                    academicExperience.setFieldOfStudy(academicExperiencesResult.getString("field_of_study"))
                    academicExperience.setStatus(CourseStatus.valueOf(academicExperiencesResult
                            .getString("title")))
                    academicExperiences.add(academicExperience)
                }
                candidate.setAcademicExperiences(academicExperiences)

                String workExperiencesQuery = """
                    SELECT we.id, we.title, we.company_name, ct.title AS contract_type_title, lt.title AS location_type_title, we.city, s.acronym, we.currently_work, we.description
                        FROM candidates AS c,
                             work_experiences AS we,
                             states AS s,
                             contract_types AS ct,
                             location_types AS lt
                        WHERE c.id = we.candidate_id
                        AND we.contract_type_id = ct.id
                        AND we.location_id = lt.id
                        AND we.state_id = s.id
                        AND c.id = ${id}
                """
                PreparedStatement workExperiencesStmt = sql.connection.prepareStatement(workExperiencesQuery)
                ResultSet workExperiencesResult = workExperiencesStmt.executeQuery()
                List<WorkExperience> workExperiences = new ArrayList<>()
                while (workExperiencesResult.next()) {
                    WorkExperience workExperience = new WorkExperience()
                    workExperience.setId(workExperiencesResult.getInt("id"))
                    workExperience.setTitle(workExperiencesResult.getString("title"))
                    workExperience.setCompanyName(workExperiencesResult.getString("company_name"))
                    workExperience.setContractType(ContractType.valueOf(workExperiencesResult
                            .getString("contract_type_title")))
                    workExperience.setLocationType(LocationType.valueOf(workExperiencesResult
                            .getString("location_type_title")))
                    workExperience.setCity(workExperiencesResult.getString("city"))
                    workExperience.setState(State.valueOf(workExperiencesResult.getString("acronym")))
                    workExperience.setCurrentlyWork(workExperiencesResult.getBoolean("currently_work"))
                    workExperience.setDescription(workExperiencesResult.getString("description"))
                    workExperiences.add(workExperience)
                }
                candidate.setWorkExperiences(workExperiences)

                String skillsQuery = """
                    SELECT cs.id, s.title, p.title AS proficiency_title
                        FROM candidates AS c,
                             candidate_skills AS cs,
                             skills AS s,
                             proficiences AS p
                        WHERE c.id = cs.candidate_id
                        AND s.id = cs.skill_id
                        AND p.id = cs.proficiency_id
                        AND c.id = ${id}
                """
                PreparedStatement skillsStmt = sql.connection.prepareStatement(skillsQuery)
                ResultSet skillsResult = skillsStmt.executeQuery()
                List<Skill> skills = new ArrayList<>()
                while (skillsResult.next()) {
                    Skill skill = new Skill()
                    skill.setId(skillsResult.getInt("id"))
                    skill.setTitle(skillsResult.getString("title"))
                    skill.setProficiency(Proficiency.valueOf(skillsResult.getString("proficiency_title")))
                    skills.add(skill)
                }
                candidate.setSkills(skills)
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }

        return candidate
    }

    void insertCandidate(Candidate candidate) {
        try {
            String insertCandidate = "INSERT INTO candidates (name, email, city, state_id, country, cep, " +
                    "description, cpf) VALUES (?,?,?,?,?,?,?,?)"
            PreparedStatement stmt = sql.connection.prepareStatement(insertCandidate,
                    Statement.RETURN_GENERATED_KEYS)
            stmt.setString(1, candidate.name)
            stmt.setString(2, candidate.getEmail())
            stmt.setString(3, candidate.getCity())
            stmt.setString(5, candidate.getCountry())
            stmt.setString(6, candidate.getCep())
            stmt.setString(7, candidate.getDescription())
            stmt.setString(8, candidate.getCpf())



            int stateId = dbService.idFinder("states", "acronym", candidate.getState().toString())
            stmt.setInt(4, stateId)



            stmt.executeUpdate()

            int candidateId = -1
            ResultSet getCandidateId = stmt.getGeneratedKeys()
            while (stmt.getGeneratedKeys().next()) {
                candidateId = getCandidateId.getInt(1)
            }



            if (candidateId != -1) {
                String insertCertificates = "INSERT INTO certificates (candidate_id, title, duration) " +
                        "VALUES (?,?,?)"
                PreparedStatement certificationsStmt = sql.connection.prepareStatement(insertCertificates,
                        Statement.RETURN_GENERATED_KEYS)
                for (Certificate certificate in candidate.certificates) {
                    certificationsStmt.setInt(1, candidateId)
                    certificationsStmt.setString(2, certificate.title)
                    certificationsStmt.setString(3, certificate.duration)
                    certificationsStmt.executeUpdate()
                }



                String insertAcademicExperiences = "INSERT INTO academic_experiences (candidate_id, " +
                        "educational_institution, degree_type, field_of_study, course_status_id) VALUES (?,?,?,?,?)"
                PreparedStatement academicExperiencesStmt = sql.connection.prepareStatement(insertAcademicExperiences,
                        Statement.RETURN_GENERATED_KEYS)
                for (AcademicExperience academicExperience in candidate.academicExperiences) {
                    academicExperiencesStmt.setInt(1, candidateId)
                    academicExperiencesStmt.setString(2, academicExperience.educationalInstitution)
                    academicExperiencesStmt.setString(3, academicExperience.degreeType)
                    academicExperiencesStmt.setString(4, academicExperience.fieldOfStudy)

                    int courseStatusId = dbService.idFinder("course_status", "title",
                            academicExperience.getStatus().toString())
                    academicExperiencesStmt.setInt(5, courseStatusId)

                    academicExperiencesStmt.executeUpdate()
                }



                String insertLanguages = "INSERT INTO candidate_languages (candidate_id, language_id, " +
                        "proficiency_id) VALUES (?,?,?)"
                PreparedStatement languagesStmt = sql.connection.prepareStatement(insertLanguages,
                        Statement.RETURN_GENERATED_KEYS)
                for (Language language in candidate.languages) {
                    languagesStmt.setInt(1, candidateId)

                    int languageId = dbService.idFinder("languages", "name", language.getName())
                    languagesStmt.setInt(2, languageId)

                    int proficiencyId = dbService.idFinder("proficiences", "title",
                            language.getProficiency().toString())
                    languagesStmt.setInt(3, proficiencyId)

                    languagesStmt.executeUpdate()
                }



                String insertSkills = "INSERT INTO candidate_skills (candidate_id, skill_id, " +
                        "proficiency_id) VALUES (?,?,?)"
                PreparedStatement skillsStmt = sql.connection.prepareStatement(insertSkills,
                        Statement.RETURN_GENERATED_KEYS)
                for (Skill skill in candidate.skills) {
                    skillsStmt.setInt(1, candidateId)

                    int skillId = dbService.idFinder("skills", "title", skill.getTitle())
                    skillsStmt.setInt(2, skillId)

                    int proficiencyId = dbService.idFinder("proficiences", "title",
                            skill.getProficiency().toString())
                    skillsStmt.setInt(3, proficiencyId)

                    skillsStmt.executeUpdate()
                }



                String insertWorkExperiences = "INSERT INTO work_experiences (candidate_id, title, company_name," +
                        " city, currently_work, description, state_id, contract_type_id, location_id) VALUES " +
                        "(?,?,?,?,?,?,?,?,?)"
                PreparedStatement workExperiencesStmt = sql.connection.prepareStatement(insertWorkExperiences,
                        Statement.RETURN_GENERATED_KEYS)
                for (WorkExperience workExperience in candidate.workExperiences) {
                    workExperiencesStmt.setInt(1, candidateId)
                    workExperiencesStmt.setString(2, workExperience.title)
                    workExperiencesStmt.setString(3, workExperience.companyName)
                    workExperiencesStmt.setString(4, workExperience.city)
                    workExperiencesStmt.setBoolean(5, workExperience.currentlyWork)

                    workExperiencesStmt.setString(6, workExperience.description)

                    int workStateId = dbService.idFinder("states", "acronym", workExperience.getState().toString())
                    workExperiencesStmt.setInt(7, workStateId)

                    int contractTypeId = dbService.idFinder("contract_types", "title", workExperience.getContractType().toString())
                    workExperiencesStmt.setInt(8, contractTypeId)

                    int locationTypeId = dbService.idFinder("location_types", "title", workExperience.getLocationType().toString())
                    workExperiencesStmt.setInt(9, locationTypeId)

                    workExperiencesStmt.executeUpdate()
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void updateCandidate(int id, Candidate candidate) {
        try {
            String updateCandidate = """
                UPDATE candidates
                    SET name=?, email=?, city=?, state_id=?, country=?, cep=?, description=?, cpf=?
                    WHERE id=${id}
            """
            PreparedStatement stmt = sql.connection.prepareStatement(updateCandidate)
            stmt.setString(1, candidate.name)
            stmt.setString(2, candidate.getEmail())
            stmt.setString(3, candidate.getCity())
            stmt.setString(5, candidate.getCountry())
            stmt.setString(6, candidate.getCep())
            stmt.setString(7, candidate.getDescription())
            stmt.setString(8, candidate.getCpf())

            int stateId = dbService.idFinder("states", "acronym", candidate.getState().toString())
            stmt.setInt(4, stateId)

            stmt.executeUpdate()



            for (Certificate certificate in candidate.certificates) {
                String updateCertificate = """
                    UPDATE certificates 
                        SET candidate_id=${id}, title=?, duration=?
                        WHERE id=${certificate.id}
                """
                PreparedStatement certificationsStmt = sql.connection.prepareStatement(updateCertificate)
                certificationsStmt.setString(1, certificate.title)
                certificationsStmt.setString(2, certificate.duration)

                certificationsStmt.executeUpdate()
            }



            for (AcademicExperience academicExperience in candidate.academicExperiences) {
                String updateAcademicExperience = """
                    UPDATE academic_experiences 
                        SET candidate_id=${id}, educational_institution=?, degree_type=?, field_of_study=?, 
                            course_status_id=?
                        WHERE id=${academicExperience.id}
                """
                PreparedStatement academicExperiencesStmt = sql.connection.prepareStatement(
                    updateAcademicExperience)
                academicExperiencesStmt.setString(1, academicExperience.educationalInstitution)
                academicExperiencesStmt.setString(2, academicExperience.degreeType)
                academicExperiencesStmt.setString(3, academicExperience.fieldOfStudy)

                int courseStatusId = dbService.idFinder("course_status", "title",
                        academicExperience.getStatus().toString())
                academicExperiencesStmt.setInt(4, courseStatusId)

                academicExperiencesStmt.executeUpdate()
            }


            for (Language language in candidate.languages) {
                String updateLanguage = """
                    UPDATE candidate_languages 
                        SET candidate_id=${id}, language_id=?, proficiency_id=?
                        WHERE id=${language.id}
                """
                PreparedStatement languagesStmt = sql.connection.prepareStatement(updateLanguage)
                int languageId = dbService.idFinder("languages", "name", language.getName())
                languagesStmt.setInt(1, languageId)

                int proficiencyId = dbService.idFinder("proficiences", "title",
                        language.getProficiency().toString())
                languagesStmt.setInt(2, proficiencyId)

                languagesStmt.executeUpdate()
            }


            for (Skill skill in candidate.skills) {
                String updateSkills = """
                    UPDATE candidate_skills 
                        SET candidate_id=${id}, skill_id=?, proficiency_id=?
                        WHERE id=${skill.id}
                """
                PreparedStatement skillsStmt = sql.connection.prepareStatement(updateSkills)
                int skillId = dbService.idFinder("skills", "title", skill.getTitle())
                skillsStmt.setInt(1, skillId)

                int proficiencyId = dbService.idFinder("proficiences", "title", skill.getProficiency().toString())
                skillsStmt.setInt(2, proficiencyId)

                skillsStmt.executeUpdate()
            }


            for (WorkExperience workExperience in candidate.workExperiences) {
                String updateWorkExperiences = """
                    UPDATE work_experiences
                        SET candidate_id=${id}, title=?, company_name=?, city=?, currently_work=?, description=?, 
                            state_id=?, contract_type_id=?, location_id=?
                        WHERE id=${workExperience.id}
                """
                PreparedStatement workExperiencesStmt = sql.connection.prepareStatement(updateWorkExperiences)
                workExperiencesStmt.setString(1, workExperience.title)
                workExperiencesStmt.setString(2, workExperience.companyName)
                workExperiencesStmt.setString(3, workExperience.city)
                workExperiencesStmt.setBoolean(4, workExperience.currentlyWork)

                workExperiencesStmt.setString(5, workExperience.description)

                int workStateId = dbService.idFinder("states", "acronym", workExperience.getState().toString())
                workExperiencesStmt.setInt(6, workStateId)

                int contractTypeId = dbService.idFinder("contract_types", "title", workExperience.getContractType().toString())
                workExperiencesStmt.setInt(7, contractTypeId)

                int locationTypeId = dbService.idFinder("location_types", "title", workExperience.getLocationType().toString())
                workExperiencesStmt.setInt(8, locationTypeId)

                workExperiencesStmt.executeUpdate()
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

    void deleteCandidateById(int id) {
        Person candidate = new Candidate()

        try {
            String query = "SELECT * FROM candidates WHERE id = ${id};"
            PreparedStatement stmt = sql.connection.prepareStatement(query)
            ResultSet result = stmt.executeQuery()
            while (result.next()) {
                    candidate.setId(result.getInt("id"))
            }

            if (candidate.id != null) {
                query = "DELETE FROM candidates WHERE id = ${id};"
                stmt = sql.connection.prepareStatement(query)
                stmt.executeUpdate()
            } else {
                println "[Exclusão] Candidato não encontrado."
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseFactory.class.getName()).log(Level.SEVERE, null, e)
        }
    }

}
