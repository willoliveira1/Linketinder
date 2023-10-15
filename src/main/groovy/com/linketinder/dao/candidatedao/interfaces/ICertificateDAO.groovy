package com.linketinder.dao.candidatedao.interfaces

import com.linketinder.model.candidate.Certificate

interface ICertificateDAO {

    List<Certificate> getCertificatesByCandidateId(int candidateId)
    void insertCertificate(Certificate certificate, int candidateId)
    void updateCertificate(Certificate certificate, int candidateId)
    void deleteCertificate(int id)

}
