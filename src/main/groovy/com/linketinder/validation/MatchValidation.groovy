package com.linketinder.validation

import com.linketinder.util.viewtexts.MatchTexts
import com.linketinder.validation.interfaces.IMatchValidation

class MatchValidation implements IMatchValidation {

    Readable reader = System.in.newReader()

    Integer validateId() {
        try {
            return reader.readLine() as Integer
        } catch (IllegalArgumentException e) {
            println MatchTexts.INVALID_ID_TEXT
            validateId()
        }
    }

}
