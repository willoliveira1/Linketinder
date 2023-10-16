package com.linketinder.dao.companydao.interfaces

import com.linketinder.model.company.Benefit

interface IBenefitDAO {

    List<Benefit> getBenefitsByCompanyId(int companyId)
    void insertBenefit(int companyId, Benefit benefit)
    void updateBenefit(int companyId, Benefit benefit)
    void deleteBenefit(int id)

}
