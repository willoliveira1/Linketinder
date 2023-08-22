package com.linketinder.service

import com.linketinder.domain.company.Company
import com.linketinder.fileProcessor.CompaniesProcessor
import com.linketinder.fileProcessor.Processor

class CompanyService implements BaseService<Company> {

    Processor<Company> processor = new CompaniesProcessor()

    List<Company> getAll() {
        return processor.readFile()
    }

    Company getById(Integer id) {
        return processor.readById(id)
    }

    void add(Company company) {
        processor.add(company)
    }

    void update(Integer id, Company company) {
        processor.update(id, company)
    }

    void delete(int id) {
        processor.delete(id)
    }

}
