package com.linketinder.service

import com.linketinder.domain.company.Company
import com.linketinder.fileprocessor.CompaniesProcessor
import com.linketinder.fileprocessor.Processor

class CompanyService implements IBaseService<Company> {

    Processor processor = new CompaniesProcessor()

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

    void delete(Integer id) {
        processor.delete(id)
    }

}
