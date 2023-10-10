package com.linketinder.model.shared

enum State {

    AC ("Acre"),
    AL ("Alagoas"),
    AM ("Amazonas"),
    AP ("Amapá"),
    BA ("Bahia"),
    CE ("Ceará"),
    DF ("Distrito Federal"),
    ES ("Espírito Santo"),
    GO ("Goiás"),
    MA ("Maranhão"),
    MG ("Minas Gerais"),
    MT ("Mato Grosso"),
    MS ("Mato Grosso do Sul"),
    PA ("Pará"),
    PB ("Paraíba"),
    PE ("Pernambuco"),
    PI ("Piauí"),
    PR ("Paraná"),
    RJ ("Rio de Janeiro"),
    RN ("Rio Grande do Norte"),
    RO ("Rondônia"),
    RR ("Roraima"),
    RS ("Rio Grande do Sul"),
    SC ("Santa Catarina"),
    SE ("Sergipe"),
    SP ("São Paulo"),
    TO ("Tocantins")

    final String stateName

    State (String stateName) {
        this.stateName = stateName
    }

}
